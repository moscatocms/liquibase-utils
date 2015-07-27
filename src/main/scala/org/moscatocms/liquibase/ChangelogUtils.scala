package org.moscatocms.doctype

import sbt._
import scala.xml.{Node, XML}
import org.moscatocms.liquibase._

object ChangelogUtils {
  
  def writeChangelog(file: File, changeSets: Seq[Node]) {
    file.getParentFile.mkdirs()
    XML.save(
      filename = file.getAbsolutePath,
      node = <databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
        xsi:schemaLocation="
            http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd
            http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
        {changeSets}
      </databaseChangeLog>,
      xmlDecl = true)
  }
  
  def generateCompleteChangelog(outputDir: File, changelogPaths: Seq[String])(implicit log: Logger): File = {
    //val changelogs = changelogPaths map { _.split("/").last } mkString(", ")
    val changelogs = changelogPaths.mkString("\n")
    val liquibaseFile = outputDir / "changelog.xml"
    writeChangelog(liquibaseFile, getCompleteChangelog(changelogPaths))
    liquibaseFile
  }
  
  def getCompleteChangelog(changelogPaths: Seq[String]): Seq[Node] = {
    <include file="org/moscatocms/migrations/moscato-changelog.xml"/> ++
    { changelogPaths map { path => <include file={path}/> }}
  }
  
}
