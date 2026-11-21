package $package;format="package"$

import munit.FunSuite
import org.llm4s.error.RateLimitError
import org.llm4s.llmconnect.model.{ SystemMessage, UserMessage }
import org.llm4s.testkit.{ Reply, ScriptedLLMClient, UnscriptedCallError }

/** This code is part of the Giter8 template llm4s.g8 in the llm4s project, which provides a
  * standard template/archetype to improve developer onboarding when creating new projects using the
  * llm4s library.
  */

/** Tests for `PromptExecutor`, driven by llm4s-testkit's `ScriptedLLMClient`.
  *
  * `ScriptedLLMClient` is an `LLMClient` that answers from a script and records every request, so
  * these tests never reach the network and never need an API key. Script a reply with `Reply.text`,
  * `Reply.toolCall` or `Reply.failure`, run your code, then assert on both the `Result` and what
  * was sent (`client.calls`).
  */
class PromptExecutorSpec extends FunSuite {

  test("run returns the assistant response on success") {
    val client = ScriptedLLMClient.sequence(Reply.text("Monads represent computations"))

    assertEquals(
      PromptExecutor.run("Explain what a Monad is in Scala", client),
      Right("Monads represent computations"),
    )
    assertEquals(client.callCount, 1)
  }

  test("run sends the system prompt, then the user prompt") {
    val client = ScriptedLLMClient.always(Reply.text("ok"))

    val _ = PromptExecutor.run("What is a Functor?", client)

    val messages = client.calls.head.messages
    assertEquals(messages.size, 2)
    assert(messages.head.isInstanceOf[SystemMessage], "expected the system prompt first")
    assert(messages.last.isInstanceOf[UserMessage], "expected the user prompt last")
    assertEquals(client.calls.head.lastUserText, Some("What is a Functor?"))
  }

  test("run can pick its answer from the conversation") {
    val client = ScriptedLLMClient.respondingTo {
      case conversation if conversation.messages.exists(_.content.contains("capital of France")) =>
        Reply.text("Paris")
    }

    assertEquals(PromptExecutor.run("What is the capital of France?", client), Right("Paris"))
  }

  test("run returns a provider error as a value instead of throwing") {
    val client = ScriptedLLMClient.sequence(Reply.failure(RateLimitError("openai")))

    val result = PromptExecutor.run("Trigger error", client)

    assert(result.isLeft, "expected a Left")
    assert(result.left.exists(_.isInstanceOf[RateLimitError]))
  }

  test("a call the script did not expect fails with a Left that says why") {
    val client = ScriptedLLMClient.sequence() // no replies scripted

    val result = PromptExecutor.run("Unexpected call", client)

    assert(result.left.exists(_.isInstanceOf[UnscriptedCallError]), result.toString)
    assert(result.left.exists(_.message.contains("Unexpected call")), result.toString)
  }
}
