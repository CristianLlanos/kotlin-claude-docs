# claude-docs

Gradle plugin that extracts `CLAUDE.md` files from your dependency JARs into a local directory.

Libraries can embed a `CLAUDE.md` at the root of their JAR to describe usage patterns, API conventions, and gotchas for AI assistants. This plugin collects those files so tools like Claude Code can discover them automatically.

## Quick start

```kotlin
// build.gradle.kts
plugins {
    id("com.cristianllanos.claude-docs") version "0.1.0"
}
```

Run:

```bash
./gradlew extractClaudeDocs
```

Extracted docs appear in `docs/deps/`, named `{group}--{artifact}.md`.

## Configuration

```kotlin
claudeDocs {
    outputDir = "docs/deps"  // default
}
```

## How it works

1. Resolves the `runtimeClasspath` configuration
2. Opens each JAR and looks for a `CLAUDE.md` entry at the root
3. Writes matching files to the output directory as `{group}--{artifact}.md`
4. Removes stale `.md` files from previous runs that no longer have a matching dependency

## For library authors

To make your library discoverable, add a `CLAUDE.md` to `src/main/resources/` in your project. It will be included in the JAR automatically. Describe your API, common patterns, and pitfalls — AI coding assistants will use it as context.
