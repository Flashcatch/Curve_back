name := "dental"

version := "1.0"

lazy val `dental` = (project in file(".")).enablePlugins(PlayJava, PlayScala)

javacOptions ++= Seq("-Xlint:all")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "jitpack" at "https://jitpack.io"
resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += jcache
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.mybatis.caches" % "mybatis-ehcache" % "1.1.0"
libraryDependencies += "org.postgresql" % "postgresql" % "42.1.4"
libraryDependencies += "org.jsr107.ri" % "cache-annotations-ri-guice" % "1.0.0"
libraryDependencies += "io.swagger" %% "swagger-play2" % "1.6.0"
libraryDependencies += "org.projectlombok" % "lombok" % "1.16.18" % "provided"
libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % "2.9.6"

libraryDependencies ++= Seq(
  javaJdbc, javaWs, cacheApi, ehcache, ws, evolutions,
  "org.mybatis" % "mybatis" % "3.3.1",
  "org.mybatis" % "mybatis-guice" % "3.10",
  "com.nimbusds" % "nimbus-jose-jwt" % "4.40", 
  "com.google.inject.extensions" % "guice-multibindings" % "4.1.0",
)

updateOptions := updateOptions.value.withCachedResolution(true)
routesGenerator := InjectedRoutesGenerator

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))


unmanagedResourceDirectories in Compile += (baseDirectory.value / "conf")

