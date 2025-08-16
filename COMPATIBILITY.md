# Compatibility Matrix

This document outlines the compatibility between different versions of the LLM4S template, the LLM4S framework, and Scala versions.

## Version Compatibility

| Template Version | LLM4S Versions | Scala Versions | Java Version | Status |
|-----------------|----------------|----------------|--------------|---------|
| 1.0.x           | 0.1.0 - 0.1.x  | 2.13.16, 3.7.1 | 21          | Current |

## Detailed Compatibility

### Template v1.0.x

**Supported LLM4S Versions:**
- 0.1.0 ✅
- 0.1.1 ✅ (default)

**Supported Scala Versions:**
- 2.13.16 ✅ (default)
- 3.7.1 ✅

**Supported Java Versions:**
- Java 21 ✅ (required)

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
- LLM4S 0.1.1 (current default)
- Scala 2.13.16 (current default)

### For Specific LLM4S Versions

#### LLM4S 0.1.0
```bash
sbt new llm4s/llm4s.g8 --llm4s_version=0.1.0
```

#### LLM4S 0.1.1 (latest)
```bash
sbt new llm4s/llm4s.g8 --llm4s_version=0.1.1
```

### For Scala 3 Projects

```bash
sbt new llm4s/llm4s.g8 --scala_version=3.7.1
```

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

All combinations in the compatibility matrix are tested in CI:

- ✅ Template generation succeeds
- ✅ Generated project compiles
- ✅ Generated project tests pass
- ✅ Scalafmt validation passes

## Known Issues

None at this time.

## Support

For compatibility issues or questions:
- [Open an issue](https://github.com/llm4s/llm4s.g8/issues)
- Check the [FAQ](https://github.com/llm4s/llm4s.g8/wiki/FAQ)