# Architecture

## Overview

claude-docs is a single-module Gradle plugin with three components:

```
ClaudeDocsPlugin (entry point)
├── ClaudeDocsExtension    — configuration DSL
└── ExtractClaudeDocsTask  — task implementation
```

## Components

### ClaudeDocsPlugin

Implements `Plugin<Project>`. Registers the `claudeDocs` extension and the `extractClaudeDocs` task. No other tasks or configurations are added.

### ClaudeDocsExtension

Holds user-facing configuration. Currently a single property:

| Property    | Type   | Default     | Description                        |
|-------------|--------|-------------|------------------------------------|
| `outputDir` | String | `docs/deps` | Directory for extracted `.md` files |

### ExtractClaudeDocsTask

Performs the extraction:

1. Resolves `runtimeClasspath` artifacts
2. Opens each `.jar` as a `ZipFile`
3. Reads the `CLAUDE.md` entry (if present)
4. Writes to `{outputDir}/{group}--{artifact}.md`
5. Deletes stale `.md` files from previous runs

Stale file cleanup ensures removed dependencies don't leave orphaned docs behind.

## Data flow

```
runtimeClasspath JARs
  → ZipFile scan for CLAUDE.md
    → Write to docs/deps/{group}--{name}.md
      → Delete orphaned .md files
```

## Extension points

The plugin is intentionally minimal. Possible future additions:
- Support for additional configurations beyond `runtimeClasspath`
- Custom file name patterns
- Filtering by group/artifact
