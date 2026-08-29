// build.sbt — at project root

// =========== Project metadata & versions ===========
ThisBuild / organization := "$package$"
ThisBuild / version := "$version$"
ThisBuild / scalaVersion := "$scala_version$"

// Enable SemanticDB for Scalafix semantic rules.
// Scala 3 emits SemanticDB from the compiler itself, so no semanticdbVersion is needed.
ThisBuild / semanticdbEnabled := true

// =========== Dependencies ===========
libraryDependencies ++= Seq(
  "org.llm4s" %% "llm4s-core" % "$llm4s_version$", // LLM4S library dependency
  "org.scalameta" %% "munit" % "$munit_version$" % Test,

  // Logger dependencies
  "ch.qos.logback" % "logback-classic" % "1.4.14", // Logback backend
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5" // scala-logging wrapper
  // integrating scala-logging into LLM (Large Language Model) scala applications is a recommended practice,
  // especially when using SLF4J and Logback.
  // This combination is widely adopted in the Scala ecosystem for its simplicity, performance, and compatibility with structured logging


  // Other Logger dependencies: For FP + LLM pipelines - LogStage is a top-tier choice—structured, efficient, observable
  // "io.7mind.izumi" %% "logstage-core" % "1.2.19", // core
  // "io.7mind.izumi" %% "logstage-rendering-circe" % "1.2.19", // JSON output
  // "io.7mind.izumi" %% "logstage-adapter-slf4j" % "1.2.19"    // optional SLF4J integration
  // LogStage, Woof are community‑endorsed for modern stacks but, not officially approved by scala core.
)

// Scalafix dependencies (needed for custom rules or built-ins)
ThisBuild / scalafixDependencies += "ch.epfl.scala" %% "scalafix-rules" % "0.14.7" // adjust version as needed

// =========== Compiler options ===========
// LLM4S 1.0 is published for Scala 3 only, so these are the Scala 3 option names.
ThisBuild / scalacOptions ++= Seq(
  "-deprecation",       // Warn about use of deprecated APIs
  "-feature",           // Warn about misused features
  "-unchecked",         // Additional warnings for unhandled cases
  "-Wunused:all",       // Warn about unused imports, locals, privates, params
  "-encoding", "UTF-8", // Specify character encoding
)

// =========== Project definition ===========
lazy val root = (project in file("."))
  .settings(
    name := "$name$",
    Compile / mainClass := Some("$package$.Main"), // optional
    Compile / scalafmtOnCompile := false // turn off automatic formatting on compile
  )
  .enablePlugins() // Add plugins as needed

// =========== Best Practices ===========
compileOrder := CompileOrder.Mixed
Global / onChangedBuildSource := ReloadOnSourceChanges
