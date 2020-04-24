package com.shusen.blog.model

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._


class TestTable(val id: Int, val txt: Option[String]) {
  def this() = this(0, Some(""))
}

class Comment(val id: Long, val commentDate: String, val name: String, val email: String,
              val content: String, val articleId: Int) extends ScalatraRecord {
}

object Comment {
}


class CateGory(val cateId: Int, val category: String, val onLeft: Int) {
}

object CateGory {
}

class Article(val id: Option[Int], val articleTitle: String,
              val createDate: String, val updateDate: String,
              val commentNum: Int, val articleContent: String,
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

  on(comment)(c => declare(
    c.id is(autoIncremented)
  ))
}