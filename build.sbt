// Template metadata and version
ThisBuild / version := "1.0.0"
ThisBuild / organization := "org.llm4s"
ThisBuild / scalaVersion := "2.12.20"

lazy val root = (project in file("."))
  .settings(
    name := "llm4s.g8",
    description := "Official Giter8 template for creating LLM4S projects",
    
    // Stage the generated project under target/ rather than src/.
    sbtTestDirectory := target.value / "sbt-test",

    // Template testing configuration.
    //
    // This deliberately does not use `(Test / g8Test).toTask("")`. `g8Test` runs the generated
    // project through sbt's `scripted`, whose list of tests is computed from `sbtTestDirectory`
    // when the build loads — but that directory is only populated by `Test / g8`, which runs
    // later. On a clean checkout the list is therefore empty and `sbt test` "passes" in a couple
    // of seconds without generating or compiling anything. Instead, generate the project and then
    // run sbt inside it directly, so a failure in the generated project fails this build.
    Test / test := {
      val log     = streams.value.log
      val _       = (Test / g8).value
      val project = (Test / g8 / target).value
      val sbtExe  = if (sys.props.getOrElse("os.name", "").toLowerCase.contains("win")) "sbt.bat" else "sbt"

      log.info(s"Running 'sbt clean scalafmtCheckAll test' in the generated project at $project")
      val exitCode = scala.sys.process.Process(
        Seq(sbtExe, "-batch", "clean", "scalafmtCheckAll", "test"),
        project
      ).!
      if (exitCode != 0) {
        sys.error(s"The project generated from this template failed (sbt exited with $exitCode)")
      }
      log.info("Generated project compiled, formatted cleanly, and its tests passed")
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