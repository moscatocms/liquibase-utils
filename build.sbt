sbtPlugin := true

organization := "org.moscatocms"

name := "liquibase-utils"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions += "-deprecation"

libraryDependencies ++= Seq(
  "org.liquibase" % "liquibase-core" % "3.4.0"
)
