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
- An API key for at least one LLM provider (OpenAI by default)
- [Scala 3][Scala 3] — llm4s 1.0 is published for Scala 3 only
- [MUnit] for unit testing
- [LLM4S SDK][llm4s] (`org.llm4s %% llm4s-core`)
- Logging library [logback][logback], [scala-logging][scala-logging]

Configuration
-------------
llm4s reads its configuration through `Llm4sConfig`, not by reading environment variables from
application code. The provider is declared in `src/main/resources/application.conf` as a *named
provider*, with the secret pulled in from the environment:

```hocon
llm4s {
  providers {
    provider = "openai-main"

    openai-main {
      provider = "openai"
      model = "gpt-4o"
      apiKey = \${?OPENAI_API_KEY}
    }
  }
}
```

Edit that file to change model or provider (Anthropic and Ollama blocks are included, commented
out). If the configuration is incomplete, `Llm4sConfig.defaultProvider()` returns a `Left`
describing what is missing — it does not throw.

Run the app
-----------
1. Export your API key:
   ```bash
   export OPENAI_API_KEY=sk-xxxxxx
   ```
   You can also set this in your IDE's run configuration or use a `.env.$name$` file (Optional to use library like `dotenv-scala`.)
   ```bash
   export \$(cat ".env.$name$" | xargs)
   ```

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
  - `src/test/scala/.../PromptExecutorSpec.scala` drives `PromptExecutor` with a stub `LLMClient`,
    so the tests need neither an API key nor network access.
  - `src/test/scala/.../MainSpec.scala` checks that building a client from configuration yields a
    `Result` rather than an exception.

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
