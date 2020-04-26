package com.shusen.blog.model

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._


class TestTable(val id: Long, val txt: Option[String]) {
  def this() = this(0, Some(""))
}

class Contact(val id: Long, val name: String, val email: String, val subject: String, val message: String,
              val createDate: String) extends ScalatraRecord{
}

object Contact {}

class Comment(val id: Long, val commentDate: String, val name: String, val email: String,
              val content: String, val articleId: Long) extends ScalatraRecord {
}

object Comment {
}


class CateGory(val cateId: Long, val category: String, val onLeft: Long) {
}

object CateGory {
}

class Article(val id: Option[Long], val articleTitle: String,
              val createDate: String, val updateDate: String,
              val commentNum: Long, val articleContent: String,
              val picUrl: String, val category: String,
              val description: String) {
  def this() = this(None, "", "", "", 0, "", "", "", "")
}

object Article {
}

object DatabaseSchemas extends Schema {
  val testTable = table[TestTable]
  val category = table[CateGory]
  val article = table[Article]
  val comment = table[Comment]
  val contact = table[Contact]

  on(comment)(c => declare(
    c.id is(autoIncremented)
  ))

  on(contact)(c => declare(
    c.id is(autoIncremented)
  ))
}