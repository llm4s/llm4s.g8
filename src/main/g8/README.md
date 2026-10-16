$name$
=================

Quickstart
----------
This is a production-ready Scala project pre-configured to use the [llm4s SDK](https://github.com/llm4s/llm4s).

Features
--------
- ✅ Preconfigured with `llm4s` for building AI-powered applications
- ✅ Production-ready directory layout, CI hooks, formatting, and more
- ✅ Supports `sbt test` & `sbt run` for quick CLI interaction
- ✅ Works from the comfort of your IDE, whether it's IntelliJ, VS Code, or any other
- ✅ Includes `Main.scala` + `PromptExecutor` for quick onboarding & getting started with [llm4s]

Pre-configured prerequisites
-----------
- JDK 21+
- SBT
- [Ollama](https://ollama.com) running locally (the default provider, no API key), or an API key
  for a hosted provider such as OpenAI
- [Scala 3][Scala 3] — llm4s 1.0 is published for Scala 3 only
- [MUnit] for unit testing
- [LLM4S SDK][llm4s] $llm4s_version$: `llm4s-core` plus the `llm4s-ollama` and `llm4s-openai`
  provider modules (since 0.5.0 core ships no provider), and `llm4s-testkit` for tests
- Logging library [logback][logback], [scala-logging][scala-logging]

Configuration
-------------
llm4s reads its configuration through `Llm4sConfig`, not by reading environment variables from
application code. Providers are declared in `src/main/resources/application.conf` as *named
sections*, and `llm4s.providers.provider` picks the default:

```hocon
llm4s {
  providers {
    provider = "ollama-local"          # or LLM4S_PROVIDER=openai-main

    ollama-local {
      provider = "ollama"              # from llm4s-ollama; no API key
      model    = "llama3.2"
      baseUrl  = "http://localhost:11434"
    }

    openai-main {
      provider = "openai"              # from llm4s-openai; reads OPENAI_API_KEY
      model    = "gpt-4o-mini"
    }
  }
}
```

A section's `provider` must come from a module in `build.sbt`. To add Anthropic, Gemini and
others, add their module (commented lines in `build.sbt`) and a section (commented examples in
`application.conf`); each module reads its vendor's usual API-key variable for you. If the configuration is incomplete, `Llm4sConfig.defaultProvider()` returns a `Left`
describing what is missing — it does not throw.

Run the app
-----------
1. Start a model. With [Ollama](https://ollama.com) (the default):
   ```bash
   ollama pull llama3.2
   ```
   Or use OpenAI instead:
   ```bash
   export OPENAI_API_KEY=sk-xxxxxx
   export LLM4S_PROVIDER=openai-main
   ```
   You can also set this in your IDE's run configuration or use a `.env.$name$` file (Optional to use library like `dotenv-scala`.)
   ```bash
   set -a; . "./.env.$name$"; set +a
   ```
   Load it with `source`, not `export \$(cat ... | xargs)`: `xargs` does not understand
   `#`, so every commented-out line in the file would be exported too — silently
   re-enabling defaults you had overridden, and activating the placeholder API keys.

2. Run with default or custom prompt:
   ```bash
   sbt run
   sbt run "Explain what a Monad is in Scala"
   ```

3. Format & compile (after making any changes):
   ```bash
   sbt scalafmtAll
   sbt compile
   ```
4. Running Tests: This template comes with [MUnit](https://scalameta.org/munit/) preconfigured for testing.

- Included in this setup:
  - `munit` version `$munit_version$` is added as a test dependency in `build.sbt`.
  - `src/test/scala/.../PromptExecutorSpec.scala` drives `PromptExecutor` with `llm4s-testkit`'s
    `ScriptedLLMClient`, which answers from a script and records each request, so the tests need
    neither a model, an API key nor network access.
  - `src/test/scala/.../MainSpec.scala` checks that both provider modules are registered, that the
    `ollama-local` section loads, and that building a client yields a `Result`, not an exception.

- To run tests:
  - Use SBT:
  - ```bash 
    sbt test
    ```

Error handling
--------------
Every llm4s entry point used here returns `Result[A]`, an alias for `Either[LLMError, A]`.
`PromptExecutor` propagates that type instead of throwing, and `Main` handles both branches in one
place. Keep that style as you extend the project — it is the convention the library is built on.

Development
-----------
1. Development:
    - Add your own prompts in `Main.scala`
    - Implement additional functionality in `PromptExecutor.scala`
    - Write more tests in `MainSpec.scala` / `PromptExecutorSpec.scala`

2. CI
    - The template includes a GitHub Actions workflow for CI.
    - It runs tests and checks formatting on every push and pull request.

----------------
Written in July 2025 by [Vitthal Mirji]

[g8]: http://www.foundweekends.org/giter8/
[llm4s]: https://github.com/llm4s/llm4s
[Scala 3]: https://www.scala-lang.org/
[logback]: https://logback.qos.ch/
[scala-logging]: https://github.com/lightbend-labs/scala-logging
[MUnit]: https://scalameta.org/munit/
[Vitthal Mirji]: https://github.com/vim89
