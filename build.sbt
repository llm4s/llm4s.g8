// Template metadata and version
ThisBuild / version := "1.0.0"
ThisBuild / organization := "org.llm4s"
ThisBuild / scalaVersion := "2.12.20"

lazy val root = (project in file("."))
  .settings(
    name := "llm4s.g8",
    description := "Official Giter8 template for creating LLM4S projects",
    
    // Template testing configuration
    Test / test := {
      val _ = (Test / g8Test).toTask("").value
    },
    
    scriptedLaunchOpts ++= List(
      "-Xms1024m",
      "-Xmx1024m",
      "-XX:ReservedCodeCacheSize=128m",
      "-Xss2m",
      "-Dfile.encoding=UTF-8"
    ),
    
    resolvers += Resolver.url(
      "typesafe",
      url("https://repo.typesafe.com/typesafe/ivy-releases/")
    )(Resolver.ivyStylePatterns)
  )
  .enablePlugins(ScriptedPlugin)