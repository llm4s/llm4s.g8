// build.sbt — at project root

// =========== Project metadata & versions ===========
ThisBuild / organization := "$package$"
ThisBuild / version := "$version$"
ThisBuild / scalaVersion := "$scala_version$"

// Enable SemanticDB for Scalafix semantic rules
ThisBuild / semanticdbEnabled  := true
ThisBuild / semanticdbVersion  := scalafixSemanticdb.revision

// =========== Common Settings ===========
lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",         // Warn about use of deprecated APIs
    "-feature",             // Warn about misused features
    "-unchecked",           // Additional warnings for unhandled cases
    "-Xlint",               // Recommended additional warnings
    "-Wdead-code",          // Warn when dead code is identified (was -Ywarn-dead-code)
    "-Wunused:locals",      // Warn when local defs are unused (was -Ywarn-unused)
    "-encoding", "UTF-8",   // Specify character encoding
    {
      if (scalaVersion.value.startsWith("2.12"))
        "-Ywarn-unused-import" // 2.12 specific
      else
        "-Wunused:imports"     // Scala 2.13+ and Scala 3
    }
  ),
  libraryDependencies ++= Seq(
    "org.llm4s" %% "llm4s" % "$llm4s_version$", // LLM library dependency
    "ch.qos.logback" % "logback-classic" % "1.4.14",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
  )
)

// =========== Project definition ===========
lazy val root = (project in file("."))
  .settings(
    name := "$name$",
    commonSettings,
    libraryDependencies += "org.scalameta" %% "munit" % "$munit_version$" % Test,
    Compile / mainClass := Some("$package$.Main"), // optional
    Compile / scalafmtOnCompile := false // turn off automatic formatting on compile
  )
  .dependsOn(testing)
  .aggregate(testing)

lazy val testing = (project in file("llm4s-testing"))
  .settings(
    name := "llm4s-testing",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "upickle" % "4.2.1",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test
    )
  )

// Scalafix dependencies (needed for custom rules or built-ins)
ThisBuild / scalafixDependencies += "ch.epfl.scala" %% "scalafix-rules" % "0.14.3" // adjust version as needed

// =========== Best Practices ===========
Global / onChangedBuildSource := ReloadOnSourceChanges
