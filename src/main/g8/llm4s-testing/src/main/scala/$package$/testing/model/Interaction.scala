package $package$.testing.model

import org.llm4s.llmconnect.model._

import upickle.default._

/**
 * Represents a recorded interaction with an LLM.
 *
 * @param conversation The input conversation history.
 * @param options      Configuration used for this request.
 * @param response     The response received from the LLM.
 */
case class Interaction(
  conversation: Conversation,
  options: CompletionOptions,
  response: Completion
)

object Interaction {

  // Custom ReadWriter for ToolCall
  implicit val toolCallRW: ReadWriter[ToolCall] = macroRW
  
  // Custom ReadWriter for ToolMessage
  implicit val toolMessageRW: ReadWriter[ToolMessage] = macroRW

  // Standard message types
  implicit val userMessageRW: ReadWriter[UserMessage]           = macroRW
  implicit val systemMessageRW: ReadWriter[SystemMessage]       = macroRW
  implicit val assistantMessageRW: ReadWriter[AssistantMessage] = macroRW
  implicit val messageRW: ReadWriter[Message]                   = macroRW

  implicit val conversationRW: ReadWriter[Conversation] = macroRW

  // Custom ReadWriter for CompletionOptions
  implicit val completionOptionsRW: ReadWriter[CompletionOptions] = readwriter[ujson.Value].bimap[CompletionOptions](
    opts => ujson.Obj(
      "maxTokens" -> opts.maxTokens.map(ujson.Num(_)).getOrElse(ujson.Null),
      "temperature" -> ujson.Num(opts.temperature),
      "topP" -> ujson.Num(opts.topP)
    ),
    json => CompletionOptions(
      maxTokens = if (json.obj.contains("maxTokens") && !json("maxTokens").isNull) Some(json("maxTokens").num.toInt) else None,
      temperature = if (json.obj.contains("temperature") && !json("temperature").isNull) json("temperature").num else 0.7,
      topP = if (json.obj.contains("topP") && !json("topP").isNull) json("topP").num else 1.0
    )
  )

  // TokenUsage RW
  implicit val tokenUsageRW: ReadWriter[TokenUsage] = macroRW

  // Custom RW for Completion
  implicit val completionRW: ReadWriter[Completion] = macroRW

  implicit val rw: ReadWriter[Interaction] = macroRW
}
