package $package;format="package"$

import munit.FunSuite

/** This code is part of the Giter8 template llm4s.g8 in the llm4s project, which provides a
  * standard template/archetype to improve developer onboarding when creating new projects using the
  * llm4s library.
  */

/** Smoke tests for the application wiring.
  *
  * They deliberately make no LLM call: building a client only reads configuration, and a missing or
  * incomplete configuration comes back as a `Left` rather than an exception. See
  * `PromptExecutorSpec` for the behaviour tests, which use a stub client.
  */
class MainSpec extends FunSuite {

  test("basic assertion") {
    assertEquals(1 + 1, 2)
  }

  test("building a client from configuration yields a Result, never an exception") {
    PromptExecutor.client() match {
      case Right(client) =>
        assert(client.getContextWindow() > 0)
        client.close()
      case Left(error) =>
        // No provider configured in this environment — that is a value, not a crash.
        assert(error.message.nonEmpty)
    }
  }
}
