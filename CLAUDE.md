# claude-docs

Gradle plugin that extracts `CLAUDE.md` files from dependency JARs.

## Project structure

- `src/main/kotlin/com/cristianllanos/claudedocs/ClaudeDocsPlugin.kt` — entire plugin in one file
- `build.gradle.kts` — project build config
- `docs/ARCHITECTURE.md` — architecture overview

## Build & test

```bash
./gradlew build          # compile + checks
./gradlew publishToMavenLocal  # install locally for testing
```

## Key concepts

- **ClaudeDocsPlugin**: entry point, registers extension + task
- **ClaudeDocsExtension**: DSL config (`claudeDocs { outputDir = "..." }`)
- **ExtractClaudeDocsTask**: scans `runtimeClasspath` JARs for `CLAUDE.md`, writes to output dir, cleans stale files
- Output files are named `{group}--{artifact}.md`

## Conventions

- Kotlin/JVM, Gradle plugin API
- Single-file plugin — keep it simple unless complexity warrants splitting
- The plugin uses `java-gradle-plugin` for plugin metadata and `maven-publish` for distribution
