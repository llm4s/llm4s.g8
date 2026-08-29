package $package;format="package"$

import munit.FunSuite
import org.llm4s.error.ValidationError
import org.llm4s.llmconnect.LLMClient
import org.llm4s.llmconnect.model.{
  AssistantMessage,
  Completion,
  CompletionOptions,
  Conversation,
  StreamedChunk,
  TokenUsage,
}
import org.llm4s.types.Result

/** This code is part of the Giter8 template llm4s.g8 in the llm4s project, which provides a
  * standard template/archetype to improve developer onboarding when creating new projects using the
  * llm4s library.
  */

/** Tests for `PromptExecutor` driven by a stub `LLMClient`.
  *
  * These tests never reach the network and never need an API key — the client is an interface, so
  * substitute your own implementation and assert on the `Result` it produces.
  */
class PromptExecutorSpec extends FunSuite {

  private def stubClient(response: Result[Completion]): LLMClient = new LLMClient {
    override def complete(
        conversation: Conversation,
        options: CompletionOptions,
    ): Result[Completion] = response

    override def streamComplete(
        conversation: Conversation,
        options: CompletionOptions,
        onChunk: StreamedChunk => Unit,
    ): Result[Completion] = response

    override def getContextWindow(): Int = 128000

    override def getReserveCompletion(): Int = 4096
  }

  private def completionOf(text: String): Completion =
    Completion(
      id = "stub-completion-id",
      created = 0L,
      content = text,
      model = "stub-model",
      message = AssistantMessage(contentOpt = Some(text)),
      usage = Some(TokenUsage(promptTokens = 10, completionTokens = 5, totalTokens = 15)),
    )

  test("run returns the assistant response on success") {
    val client = stubClient(Right(completionOf("Monads represent computations")))

    assertEquals(
      PromptExecutor.run("Explain what a Monad is in Scala", client),
      Right("Monads represent computations"),
    )
  }

  test("run sends the system prompt and the user prompt in order") {
    var seen: Seq[String] = Seq.empty

    val client = new LLMClient {
      override def complete(
          conversation: Conversation,
          options: CompletionOptions,
      ): Result[Completion] = {
        seen = conversation.messages.map(_.content)
        Right(completionOf("ok"))
      }

      override def streamComplete(
          conversation: Conversation,
          options: CompletionOptions,
          onChunk: StreamedChunk => Unit,
      ): Result[Completion] = Right(completionOf("ok"))

      override def getContextWindow(): Int = 128000

      override def getReserveCompletion(): Int = 4096
    }

    val _ = PromptExecutor.run("What is a Functor?", client)

    assertEquals(seen.size, 2)
    assertEquals(seen.last, "What is a Functor?")
  }

  test("run returns the error as a value instead of throwing") {
    val client = stubClient(Left(ValidationError("prompt", "LLM is down")))
    val result = PromptExecutor.run("Trigger error", client)

    assert(result.isLeft, "expected a Left")
    assert(result.left.exists(_.message.contains("LLM is down")))
  }
}
