# Module claude-docs

Gradle plugin for extracting `CLAUDE.md` documentation from dependency JARs.

# Package com.cristianllanos.claudedocs

Core plugin components: [ClaudeDocsPlugin], [ClaudeDocsExtension], and [ExtractClaudeDocsTask].

## Overview

Apply the plugin to your Gradle project and run `./gradlew extractClaudeDocs` to scan
`runtimeClasspath` JARs for embedded `CLAUDE.md` files. Extracted docs are written to
`docs/deps/` by default, named `{group}--{artifact}.md`.

## Quick start

```kotlin
plugins {
    id("com.cristianllanos.claude-docs") version "0.1.0"
}

// Optional: customize output directory
claudeDocs {
    outputDir = "docs/deps"
}
```

```bash
./gradlew extractClaudeDocs
```
