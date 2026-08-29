# Changelog

All notable changes to the LLM4S Giter8 template will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Fixed
- **The generated project no longer depends on a dead artifact.** The template depended on
  `"org.llm4s" %% "llm4s"`, which is no longer published — the root project of the library build
  sets `publish / skip := true`, and the last `llm4s_3` / `llm4s_2.13` on Maven Central is 0.2.9.
  The dependency is now `"org.llm4s" %% "llm4s-core"`, the module that is actually published.
  (Publishing continued under `org.llm4s:core` through 0.3.4; the 0.4.0 release renamed the
  published modules to the `llm4s-*` prefix, so `llm4s-core` is the current coordinate.)
- **Defaults now point at a current release.** `llm4s_version` moves from `0.1.9` to `0.4.0` and
  `scala_version` from `2.13.16` to `3.7.1`; `.scalafmt.conf` uses `runner.dialect = scala3` to
  match.
- **Template sources compile against the current API.** `org.llm4s.llmconnect.LLM`,
  `LLMProvider`, and `config.OpenAIConfig` no longer exist. `PromptExecutor` now builds its client
  through `Llm4sConfig.defaultProvider()` + `LLMConnect.getClient`, and returns
  `Result[String]` (`Either[LLMError, String]`) instead of collapsing errors into a string.
- **Configuration follows the library's own guidance.** The template read `sys.env` directly,
  which the library explicitly tells users not to do. Provider configuration now lives in
  `src/main/resources/application.conf` as a named provider, with secrets substituted from the
  environment and loaded via `Llm4sConfig`.
- **Tests are deterministic and offline.** The old tests asserted on the text of a live OpenAI
  "Incorrect API key provided" error, so they required network access to pass. They now drive
  `PromptExecutor` with a stub `LLMClient`.
- **`sbt test` in this repository actually tests the template.** `g8Test` runs the generated
  project through `scripted`, whose test list is computed at build load from a directory that only
  exists after `Test / g8` has run — so on a clean checkout `sbt test` passed in ~3 seconds without
  generating or compiling anything. It now generates the project and runs
  `sbt clean scalafmtCheckAll test` inside it.
- **Generated project's sbt plugins are pinned.** `latest.release` had drifted to an sbt-scalafmt
  release requiring sbt 1.12.9+ while `project/build.properties` pinned 1.10.7, breaking
  `sbt scalafmtCheckAll` in every generated project. sbt is now 1.12.15 and each plugin has an
  explicit version.

### Changed
- Scala 2.13 is no longer supported by this template. `core_2.13` stopped at 0.2.9 and LLM4S 1.0
  targets Scala 3 only; see [llm4s#874](https://github.com/llm4s/llm4s/issues/874) and
  `docs/reference/v1-scope.md` in the main repository.
- `.scalafmt.conf` sets `rewrite.scala3.removeOptionalBraces = false` so that switching the dialect
  to `scala3` does not silently rewrite the generated sources into significant-indentation syntax.
- README, COMPATIBILITY.md, CONTRIBUTING.md, and both CI workflows updated to describe Scala 3.7.1
  and LLM4S 0.4.0. COMPATIBILITY.md records the full coordinate history: `org.llm4s:llm4s` (up to
  0.2.9, unpublished), `org.llm4s:core` (up to 0.3.4, frozen), `org.llm4s:llm4s-core` (0.4.0
  onward).
- The generated project's GitHub Actions workflow now uses `actions/setup-java@v4`,
  `actions/cache@v4`, `actions/upload-artifact@v4` (v3 has been decommissioned) and
  `codecov/codecov-action@v5`, and `.pre-commit-config.yaml` uses the `pre-commit` / `pre-push`
  stage names required by pre-commit 4.x.

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