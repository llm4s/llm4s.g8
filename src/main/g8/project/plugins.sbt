// Plugin versions are pinned deliberately: `latest.release` silently drifts and eventually
// resolves a plugin that requires a newer sbt than project/build.properties pins.
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.6.2")  // code formatting
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.14.7") // linting and refactoring
addSbtPlugin("org.scalameta" % "sbt-munit" % "1.3.5")     // MUnit test reporting
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.4.4") // code coverage
