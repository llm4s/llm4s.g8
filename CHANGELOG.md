# Changelog

All notable changes to the LLM4S Giter8 template will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2024-08-16

### Added
- Initial release of standalone LLM4S Giter8 template
- Migrated from main LLM4S repository with full Git history preserved
- Support for LLM4S versions 0.1.0 - 0.1.x
- Support for Scala 2.13.16 and 3.7.1
- Comprehensive CI/CD pipeline for template validation
- Compatibility matrix documentation
- Template metadata and versioning system
- Release automation workflow

### Changed
- Template now lives in its own repository at `llm4s/llm4s.g8`
- Independent versioning from LLM4S framework

### Template Features
- Pre-configured `build.sbt` with LLM4S dependencies
- Example application demonstrating LLM4S usage
- Test setup with Munit
- Scalafmt configuration
- GitHub Actions CI/CD workflows
- Environment configuration support
- Pre-commit hooks for code quality

### Migration Notes
- Users can now use `sbt new llm4s/llm4s.g8` to create new projects
- All existing template parameters remain unchanged
- Generated project structure is identical to previous embedded template

[Unreleased]: https://github.com/llm4s/llm4s.g8/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/llm4s/llm4s.g8/releases/tag/v1.0.0