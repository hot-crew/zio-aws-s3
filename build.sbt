val zioVersion = "1.0.0-RC17"
val awsVersion = "2.10.68"

// *****************************************************************************
// Projects
// *****************************************************************************

lazy val root =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin)
    .settings(settings)
    .settings(
      commonDeps,
      zioDeps,
      awsDeps
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val awsDeps = libraryDependencies ++= Seq("software.amazon.awssdk" % "s3" % awsVersion)

lazy val zioDeps = libraryDependencies ++= Seq(
  "dev.zio" %% "zio"          % zioVersion,
  "dev.zio" %% "zio-test"     % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt" % zioVersion % "test"
)

lazy val commonDeps = libraryDependencies ++= Seq()

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
    scalafmtSettings ++
    sonatypeSettings

lazy val commonSettings =
  Seq(
    // scalaVersion is taken from .travis.yml via sbt-travisci
    name := "zio-aws-s3",
    organization := "com.neurodyne",
    organizationName := "Neurodyne Systems",
    startYear := Some(2019),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/Neurodyne/zio-aws-s3")),
    scalacOptions --= Seq(
      "-Xfatal-warnings",
      "-Ywarn-value-discard"
    ),
    Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
    Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true
  )

lazy val sonatypeSettings =
  Seq(
    version := "0.4.13",
    sonatypeProfileName := "tampler",
    scmInfo := Some(
      ScmInfo(
        homepage.value.get,
        "scm:git@github.com:Neurodyne/zio-aws-s3.git"
      )
    ),
    developers := List(
      Developer(
        id = "tampler",
        name = "Boris V.Kuznetsov",
        email = "socnetfpga@gmail.com",
        url = url("http://github.com/tampler")
      )
    ),
    description := "ZIO integration with AWS S3 SDK",
    pomIncludeRepository := { _ =>
      false
    },
    publishTo := sonatypePublishToBundle.value,
    publishMavenStyle := true,
    pgpPassphrase := sys.env.get("PGP_PASSWORD").map(_.toArray),
    pgpPublicRing := file("/tmp/public.asc"),
    pgpSecretRing := file("/tmp/secret.asc")
  )

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "compile")
addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
