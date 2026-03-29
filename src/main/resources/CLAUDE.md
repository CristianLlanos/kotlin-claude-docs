# claude-docs

**Maven coordinates:** `com.cristianllanos:claude-docs:0.1.0`
**Plugin ID:** `com.cristianllanos.claude-docs`

## What it does

Gradle plugin that extracts `CLAUDE.md` files from dependency JARs into a local directory so AI coding assistants can discover library documentation automatically.

## Usage

```kotlin
plugins {
    id("com.cristianllanos.claude-docs") version "0.1.0"
}

claudeDocs {
    outputDir = "docs/deps"  // default
}
```

```bash
./gradlew extractClaudeDocs
```

## Core concepts

- **ClaudeDocsPlugin** — entry point; registers the `claudeDocs` extension and `extractClaudeDocs` task
- **ClaudeDocsExtension** — configuration DSL with `outputDir` property
- **ExtractClaudeDocsTask** — scans `runtimeClasspath` JARs for `CLAUDE.md`, writes `{group}--{artifact}.md` files, cleans stale entries

## API reference

### ClaudeDocsExtension

| Property    | Type   | Default     | Description                        |
|-------------|--------|-------------|------------------------------------|
| `outputDir` | String | `docs/deps` | Output directory (project-relative) |

### ExtractClaudeDocsTask

Registered as `extractClaudeDocs` in the `documentation` group. Reads from `runtimeClasspath`, writes to the configured output directory.
