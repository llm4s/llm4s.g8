# Contributing to LLM4S Template

Thank you for your interest in contributing to the LLM4S Giter8 template! This document provides guidelines and instructions for contributing.

## How to Contribute

### Reporting Issues

If you find a bug or have a suggestion:

1. Check if the issue already exists in our [issue tracker](https://github.com/llm4s/llm4s.g8/issues)
2. If not, create a new issue with:
   - Clear description of the problem or suggestion
   - Steps to reproduce (for bugs)
   - Expected vs actual behavior
   - Template version, LLM4S version, and Scala version

### Submitting Changes

1. **Fork the repository**
   ```bash
   git clone https://github.com/llm4s/llm4s.g8.git
   cd llm4s.g8
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow existing code style and conventions
   - Update documentation if needed
   - Add tests for new functionality

4. **Test your changes**
   ```bash
   # Test template generation
   sbt test
   
   # Test with specific parameters
   sbt new file://$(pwd) --name=test-project
   ```

5. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add: Brief description of your changes"
   ```

6. **Push and create a Pull Request**
   ```bash
   git push origin feature/your-feature-name
   ```

## Development Setup

### Prerequisites

- Java 21 or higher
- SBT 1.10.6 or higher
- Git

### Local Testing

1. **Test template generation**:
   ```bash
   sbt test
   ```

2. **Test with custom parameters**:
   ```bash
   sbt new file://$(pwd) \
     --name=my-test \
     --scala_version=3.7.1 \
     --llm4s_version=0.1.1
   ```

3. **Validate generated project**:
   ```bash
   cd my-test
   sbt compile test
   ```

## Template Structure

```
llm4s.g8/
├── src/main/g8/              # Template files
│   ├── .github/              # GitHub Actions templates
│   ├── src/                  # Source code templates
│   ├── project/              # SBT configuration templates
│   ├── build.sbt             # Build configuration template
│   └── default.properties   # Template parameters
├── build.sbt                 # Template build configuration
├── project/                  # Template SBT plugins
└── .github/workflows/        # CI/CD for the template itself
```

## Template Parameters

When adding new parameters:

1. Add to `src/main/g8/default.properties`
2. Use in template files with `$parameter_name$`
3. Document in README.md
4. Add tests for new parameters

Example:
```properties
# In default.properties
new_feature=yes

# In template files
$if(new_feature.truthy)$
// Feature-specific code
$endif$
```

## Code Style

- Follow Scala style guidelines
- Use Scalafmt for formatting
- Keep template files simple and readable
- Comment complex template logic

## Testing Guidelines

### Template Tests

- Test default parameter values
- Test custom parameter combinations
- Test generated project compilation
- Test generated project tests

### CI/CD Tests

The CI pipeline tests:
- Multiple Scala versions (2.13.16, 3.7.1)
- Multiple LLM4S versions
- Template syntax validation
- Generated project validation

## Documentation

When making changes:

1. Update README.md if adding features
2. Update COMPATIBILITY.md for version changes
3. Update CHANGELOG.md for all changes
4. Include docstrings for complex template logic

## Pull Request Guidelines

### PR Title Format

Use conventional commits format:
- `feat:` New features
- `fix:` Bug fixes
- `docs:` Documentation changes
- `test:` Test additions/changes
- `chore:` Maintenance tasks

### PR Description

Include:
- What changes were made
- Why the changes are needed
- How to test the changes
- Any breaking changes

### Review Process

1. CI must pass
2. At least one maintainer review
3. Documentation updated
4. CHANGELOG.md updated

## Release Process

Releases are managed by maintainers:

1. Update version in `build.sbt`
2. Update CHANGELOG.md
3. Create a git tag: `git tag v1.0.0`
4. Push tag: `git push origin v1.0.0`
5. GitHub Actions creates the release

## Getting Help

- [Open an issue](https://github.com/llm4s/llm4s.g8/issues)
- [Discussions](https://github.com/llm4s/llm4s.g8/discussions)
- [LLM4S Documentation](https://llm4s.org)

## License

By contributing, you agree that your contributions will be licensed under the MIT License.