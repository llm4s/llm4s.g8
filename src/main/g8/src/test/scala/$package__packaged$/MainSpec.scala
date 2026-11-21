package $package;format="package"$

import munit.FunSuite
import org.llm4s.config.Llm4sConfig
import org.llm4s.llmconnect.spi.ProviderRegistry

/** This code is part of the Giter8 template llm4s.g8 in the llm4s project, which provides a
  * standard template/archetype to improve developer onboarding when creating new projects using the
  * llm4s library.
  */

/** Smoke tests for the application wiring.
  *
  * They deliberately make no LLM call: loading configuration and building a client only read
  * configuration, and a missing or incomplete configuration comes back as a `Left` rather than an
  * exception. See `PromptExecutorSpec` for the behaviour tests, which use a scripted client.
  */
class MainSpec extends FunSuite {

  test("the provider modules in build.sbt are on the classpath") {
    // llm4s-core ships no provider; each module registers its own through ServiceLoader.
    val registered = ProviderRegistry.default.ids
    assert(registered.contains("ollama"), "registered providers: " + registered.mkString(", "))
    assert(registered.contains("openai"), "registered providers: " + registered.mkString(", "))
  }

  test("the ollama-local section in application.conf loads without an API key") {
    Llm4sConfig.provider("ollama-local") match {
      case Right(config) => assertEquals(config.providerId.asString, "ollama")
      case Left(error)   => fail("ollama-local did not load: " + error.formatted)
    }
  }

  test("building a client from configuration yields a Result, never an exception") {
    PromptExecutor.client() match {
      case Right(client) =>
        assert(client.getContextWindow() > 0)
        client.close()
      case Left(error) =>
        // The selected provider is not usable in this environment - a value, not a crash.
        assert(error.message.nonEmpty)
    }
  }
}
