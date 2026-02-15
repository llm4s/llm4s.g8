package $package$.testing

import org.llm4s.llmconnect.LLMClient
import org.llm4s.llmconnect.model._
import $package$.testing.model.Interaction

import java.nio.file.{ Files, Paths, StandardOpenOption }
import scala.collection.mutable

/**
 * Wraps a real LLMClient and records all interactions.
 *
 * Use this to capture real API interactions for later replay in tests.
 *
 * @example
 * {{{
 * val recorder = new RecordingLLMClient(openAIClient)
 * recorder.complete(conversation, options)
 * recorder.saveWithScrubbing("recordings/test.json", Scrubber.default)
 * }}}
 *
 * @param baseClient The underlying LLM client to delegate to
 */
class RecordingLLMClient(baseClient: LLMClient) extends LLMClient {

  private val recordedInteractions = mutable.Buffer[Interaction]()

  override def complete(conversation: Conversation, options: CompletionOptions): Either[LLMError, Completion] =
    baseClient.complete(conversation, options).map { response =>
      synchronized {
        recordedInteractions += Interaction(conversation, options, response)
      }
      response
    }

  override def streamComplete(
    conversation: Conversation,
    options: CompletionOptions,
    onChunk: StreamedChunk => Unit
  ): Either[LLMError, Completion] =
    baseClient.streamComplete(conversation, options, onChunk).map { response =>
      synchronized {
        recordedInteractions += Interaction(conversation, options, response)
      }
      response
    }

  /**
   * Get all recorded interactions.
   */
  def getRecordings: List[Interaction] = synchronized {
    recordedInteractions.toList
  }

  /**
   * Clear all recorded interactions.
   */
  def clear(): Unit = synchronized {
    recordedInteractions.clear()
  }

  /**
   * Save recordings to a file without scrubbing.
   *
   * @param path File path to save to
   */
  def save(path: String): Unit = {
    import upickle.default._
    val json = write(recordedInteractions.toList, indent = 2)
    Files.write(Paths.get(path), json.getBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
  }

  /**
   * Save recordings with sensitive data scrubbed.
   *
   * @param path File path to save to
   * @param scrubber Scrubber to use for removing sensitive data
   */
  def saveWithScrubbing(path: String, scrubber: Scrubber): Unit = {
    import upickle.default._
    val json         = write(recordedInteractions.toList, indent = 2)
    val scrubbedJson = scrubber.scrub(json)
    Files.write(Paths.get(path), scrubbedJson.getBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
  }
}
