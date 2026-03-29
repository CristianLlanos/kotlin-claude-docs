package com.cristianllanos.claudedocs

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.zip.ZipFile

open class ClaudeDocsExtension {
    var outputDir: String = "docs/deps"
}

abstract class ExtractClaudeDocsTask : DefaultTask() {

    @get:Input
    var outputPath: String = "docs/deps"

    @TaskAction
    fun extract() {
        val outputDir = project.rootDir.resolve(outputPath)
        outputDir.mkdirs()

        val existingFiles = outputDir.listFiles()?.toSet() ?: emptySet()
        val generatedFiles = mutableSetOf<File>()

        val configuration = project.configurations.getByName("runtimeClasspath")

        for (artifact in configuration.resolvedConfiguration.resolvedArtifacts) {
            val file = artifact.file
            if (!file.name.endsWith(".jar")) continue

            try {
                ZipFile(file).use { zip ->
                    val entry = zip.getEntry("CLAUDE.md") ?: return@use

                    val group = artifact.moduleVersion.id.group
                    val name = artifact.moduleVersion.id.name
                    val outputFile = File(outputDir, "${group}--${name}.md")
                    generatedFiles.add(outputFile)

                    val content = zip.getInputStream(entry).bufferedReader().readText()
                    outputFile.writeText(content)
                    logger.lifecycle("Extracted CLAUDE.md from $group:$name -> ${outputFile.relativeTo(project.rootDir)}")
                }
            } catch (e: Exception) {
                logger.debug("Skipping ${file.name}: ${e.message}")
            }
        }

        for (file in existingFiles) {
            if (file.extension == "md" && file !in generatedFiles) {
                file.delete()
                logger.lifecycle("Removed stale ${file.relativeTo(project.rootDir)}")
            }
        }

        if (generatedFiles.isEmpty()) {
            logger.lifecycle("No CLAUDE.md files found in dependencies")
        }
    }
}

class ClaudeDocsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("claudeDocs", ClaudeDocsExtension::class.java)

        project.tasks.register("extractClaudeDocs", ExtractClaudeDocsTask::class.java) {
            it.group = "documentation"
            it.description = "Extracts CLAUDE.md files from dependency JARs into docs/deps/"
            it.outputPath = extension.outputDir
        }
    }
}
