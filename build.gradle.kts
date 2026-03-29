plugins {
    kotlin("jvm") version "1.9.25"
    `java-gradle-plugin`
    signing
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "com.cristianllanos"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib"))
}

gradlePlugin {
    plugins {
        create("claudeDocs") {
            id = "com.cristianllanos.claude-docs"
            implementationClass = "com.cristianllanos.claudedocs.ClaudeDocsPlugin"
        }
    }
}

signing {
    useGpgCmd()
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates("com.cristianllanos", "claude-docs", version.toString())

    pom {
        name.set("claude-docs")
        description.set("Gradle plugin that extracts CLAUDE.md files from dependency JARs")
        url.set("https://github.com/CristianLlanos/kotlin-claude-docs")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("CristianLlanos")
                name.set("Cristian Llanos")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/CristianLlanos/kotlin-claude-docs.git")
            developerConnection.set("scm:git:ssh://github.com/CristianLlanos/kotlin-claude-docs.git")
            url.set("https://github.com/CristianLlanos/kotlin-claude-docs")
        }
    }
}
