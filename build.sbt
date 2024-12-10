import com.typesafe.sbt.packager.Keys.{ dockerBaseImage, dockerExposedPorts, dockerUpdateLatest, packageName }
import sbt.{ Resolver, ThisBuild }
import com.typesafe.sbt.packager.Keys.{ dockerBaseImage, dockerExposedPorts, dockerUpdateLatest }
import sbt.{ url, Resolver, ThisBuild }
import sbtbuildinfo.BuildInfoKey
import sbtbuildinfo.BuildInfoKeys.buildInfoKeys
import sbtrelease.ReleaseStateTransformations._

name := "fatturazione"

scalaVersion := "2.13.6"

useCoursier := false

val logstash = "6.3"
val logback = "1.2.3"
val scalaLogging = "3.9.2"

val slick = "3.3.2"
val mssqlJdbc = "7.0.0.jre8"
val oracleJdbc = "12.2.0.1"

val janalyseSsh = "0.10.4"
val scalaXml = "1.2.0"
val jsch = "0.1.55"

resolvers += "lightbend-commercial-mvn" at "https://repo.lightbend.com/pass/JyW5QK33nrXXJp3WyVLeW7QW3fhyk2Fs9qTfktetbuhDLTnN/commercial-releases"
resolvers += Resolver.url(
  "lightbend-commercial-ivy",
  url("https://repo.lightbend.com/pass/JyW5QK33nrXXJp3WyVLeW7QW3fhyk2Fs9qTfktetbuhDLTnN/commercial-releases")
)(Resolver.ivyStylePatterns)

scalacOptions ++= List(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

cinnamon in run := true
cinnamon in test := true
cinnamonLogLevel := "INFO"

libraryDependencies ++= Seq(
  Cinnamon.library.cinnamonSlf4jMdc,
  "com.typesafe.akka" %% "akka-http" % "10.1.12",
  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.12",
  "net.logstash.logback" % "logstash-logback-encoder" % logstash,
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.26",
  "ch.qos.logback" % "logback-classic" % logback,
  "ch.qos.logback" % "logback-core" % logback,
  "ch.qos.logback" % "logback-access" % logback,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging,
  "com.typesafe.slick" %% "slick" % slick,
  "com.typesafe.slick" %% "slick-hikaricp" % slick,
  "com.jcraft" % "jsch" % jsch,
  "org.scala-lang.modules" %% "scala-xml" % scalaXml
)

enablePlugins(JavaAppPackaging, Cinnamon, BuildInfoPlugin)

Docker / packageName := "fatturazione"
dockerBaseImage := "adoptopenjdk:11-jdk-hotspot"
dockerUpdateLatest := true

buildInfoKeys := Seq[BuildInfoKey](name, version)
buildInfoPackage := "eu.sia.pagopa"

releaseIgnoreUntrackedFiles := true
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies, // : ReleaseStep
  inquireVersions, // : ReleaseStep
  runClean, // : ReleaseStep
  runTest, // : ReleaseStep
  setReleaseVersion, // : ReleaseStep
  commitReleaseVersion, // : ReleaseStep, performs the initial git checks
  tagRelease, // : ReleaseStep
  //      publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  releaseStepTask(stage),
  setNextVersion, // : ReleaseStep
  commitNextVersion, // : ReleaseStep
  pushChanges // : ReleaseStep, also checks that an upstream branch is properly configured
)
