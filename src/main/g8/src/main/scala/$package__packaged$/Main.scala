package $package;format="package"$

/** This code is part of the Giter8 template llm4s.g8 in the llm4s project, which provides a
  * standard template/archetype to improve developer onboarding when creating new projects using the
  * llm4s library.
  */

/** The Main object serves as the entry point for the application, allowing users to run prompts
  * against the LLM. Note that no exception handling is needed: `PromptExecutor` returns a
  * `Result[String]`, so both outcomes are handled in one place.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val prompt: String = args.headOption.getOrElse("Explain what a Monad is in Scala")

    PromptExecutor.run(prompt) match {
      case Right(answer) =>
        println(answer)
      case Left(error) =>
        println("LLM call failed: " + error.formatted)
        println(
          "The default provider is the `ollama-local` section of application.conf: start Ollama " +
            "and run `ollama pull llama3.2`. To use OpenAI instead, set OPENAI_API_KEY and " +
            "LLM4S_PROVIDER=openai-main. See the README for details."
        )
    }
  }
}
