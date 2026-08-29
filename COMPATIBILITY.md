# Compatibility Matrix

This document outlines the compatibility between different versions of the LLM4S template, the LLM4S framework, and Scala versions.

## Version Compatibility

| Template Version | LLM4S Versions | Scala Versions | Java Version | Status |
|-----------------|----------------|----------------|--------------|---------|
| 1.1.x           | 0.3.x (default `0.3.4`) | 3.7.1 | 21 | Current |
| 1.0.x           | 0.1.0 - 0.1.x  | 2.13.16, 3.7.1 | 21 | Broken — see below |

The canonical support matrix for the library itself lives in the main repository at
[`docs/reference/v1-scope.md`](https://github.com/llm4s/llm4s/blob/main/docs/reference/v1-scope.md).
This document only records what the template generates.

## Detailed Compatibility

### Template v1.1.x (current)

**LLM4S artifact:** `"org.llm4s" %% "core"`.

The aggregate `"org.llm4s" %% "llm4s"` artifact that template v1.0.x depended on is **no longer
published** — the root project in the library build sets `publish / skip := true`, and the last
published `llm4s_3` / `llm4s_2.13` was 0.2.9. The published module that continued is `core`.
Bumping the version alone does not fix a v1.0.x-generated project; the artifact name has to change
too.

**Supported LLM4S Versions:**
- 0.3.4 ✅ (default)

**Supported Scala Versions:**
- 3.7.1 ✅ (default, and the only supported version)

**Supported Java Versions:**
- Java 21 ✅ (required)

### Scala 2.13

Scala 2.13 is **not supported** by this template going forward.

- `core_2.13` was last published at **0.2.9** (2026-01-11); `core_3` has continued (0.3.4 at the
  time of writing).
- LLM4S 1.0 targets **Scala 3 only (3.7.1)**. Scala 2.13 support is deferred to post-1.0 and, if
  it happens, would target a subset of the module tree.
- Tracking issue: [llm4s#874](https://github.com/llm4s/llm4s/issues/874).

If you need Scala 2.13, pin the template to LLM4S 0.2.9 and be aware that it is not receiving
updates:

```bash
sbt new llm4s/llm4s.g8 --scala_version=2.13.16 --llm4s_version=0.2.9
```

Note that the 0.2.9 API differs from 0.3.x, so the generated sources will need adjusting.

### Known bad versions

A version numbered `2.1.593` appears on Maven Central for these artifacts. It is an accidental
mis-publish (a typo) that cannot be retracted — do not use it.

**Features:**
- Full LLM4S API support
- Example applications
- Test setup with Munit
- GitHub Actions CI/CD
- Scalafmt configuration
- Environment configuration

## Version Selection Guide

### For New Projects

Use the latest template version with the latest stable LLM4S version:

```bash
sbt new llm4s/llm4s.g8
```

This will use:
- Latest template version
- LLM4S 0.3.4 (current default)
- Scala 3.7.1 (current default)

### For a specific LLM4S version

```bash
sbt new llm4s/llm4s.g8 --llm4s_version=0.3.4
```

Any `0.3.x` release of `core_3` should work; earlier releases use a different API and are not
supported by the generated sources.

## Migration Guide

### From Embedded Template to Standalone

If you were previously using the template from the main LLM4S repository:

1. The new template location is `llm4s/llm4s.g8`
2. All parameters remain the same
3. The generated project structure is identical

### Upgrading Template Versions

To upgrade to a newer template version for an existing project:

1. Note your current LLM4S and Scala versions
2. Generate a new project with the latest template
3. Compare and merge changes as needed

## Deprecation Policy

- Template versions are supported for at least 6 months after release
- Deprecation notices will be added to this document 3 months before end of support
- Security fixes will be backported to supported versions

## Testing Matrix

The combination in the compatibility matrix is tested in CI (template generation plus a real
`sbt compile test` inside the generated project):

- ✅ Template generation succeeds
- ✅ Generated project compiles
- ✅ Generated project tests pass
- ✅ Scalafmt validation passes

## Known Issues

- Template v1.0.x generates projects that cannot resolve their dependency: it points at
  `"org.llm4s" %% "llm4s" % "0.1.9"`, an artifact that is no longer published. Regenerate with the
  current template.

## Support

For compatibility issues or questions:
- [Open an issue](https://github.com/llm4s/llm4s.g8/issues)
- Check the [FAQ](https://github.com/llm4s/llm4s.g8/wiki/FAQ)