package $package;format="package"$

import com.typesafe.scalalogging.LazyLogging
import org.llm4s.config.Llm4sConfig
import org.llm4s.llmconnect.model.{ Conversation, SystemMessage, UserMessage }
import org.llm4s.llmconnect.{ LLMClient, LLMConnect }
import org.llm4s.model.ModelRegistryService
import org.llm4s.types.Result

/** This code is part of the Giter8 template llm4s.g8 in the llm4s project, which provides a
  * standard template/archetype to improve developer onboarding when creating new projects using the
  * llm4s library.
  */

/** Executes a single prompt against the configured LLM provider.
  *
  * Two things worth copying from this file into your own code:
  *
  *   1. Configuration is loaded through `Llm4sConfig` at the edge of the application. LLM4S
  *      deliberately does not read `sys.env` for you inside library code, and neither should your
  *      application code.
  *   2. Everything returns `Result[A]` (an alias for `Either[LLMError, A]`) rather than throwing.
  *      Errors are values; the caller decides what to do with them.
  */
object PromptExecutor extends LazyLogging {

  private val SystemPrompt = "You are a helpful assistant."

  /** Builds a client for the configured default named provider.
    *
    * Configure one in `application.conf` (or `application.local.conf`):
    *
    * {{{
    * llm4s {
    *   providers {
    *     provider = "openai-main"
    *
    *     openai-main {
    *       provider = "openai"
    *       model    = "gpt-4o"
    *       apiKey   = \${?OPENAI_API_KEY}
    *     }
    *   }
    * }
    * }}}
    */
  def client(): Result[LLMClient] =
    for {
      providerConfig  <- Llm4sConfig.defaultProvider()
      registryService <- Llm4sConfig.modelRegistryService()
      given ModelRegistryService = registryService
      llmClient <- LLMConnect.getClient(providerConfig)
    } yield llmClient

  /** Runs a prompt against a client you supply — handy for tests and for reusing one client. */
  def run(prompt: String, llmClient: LLMClient): Result[String] = {
    val conversation = Conversation(
      Seq(
        SystemMessage(SystemPrompt),
        UserMessage(prompt),
      )
    )

    logger.debug("Sending prompt to the LLM: " + prompt)

    val completion = llmClient.complete(conversation).map(_.message.content)

    completion.fold(
      error => logger.error("LLM call failed: " + error.formatted),
      answer => logger.debug("Assistant response: " + answer),
    )

    completion
  }

  /** Runs a prompt against the configured default provider. */
  def run(prompt: String): Result[String] =
    client().flatMap { llmClient =>
      val result = run(prompt, llmClient)
      llmClient.close()
      result
    }
}
