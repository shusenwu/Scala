package com.shusen.blog.db
import org.squeryl.Schema


class TestTable(val id: Long, val txt: Option[String]){
  def this() = this(0, Some(""))
}


object DatabaseSchemas extends Schema {
  val testTable = table[TestTable]
}