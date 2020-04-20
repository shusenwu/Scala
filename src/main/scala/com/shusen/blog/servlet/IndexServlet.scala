package com.shusen.blog.servlet

import com.shusen.blog.db.DatabaseSessionSupport
import org.scalatra.ScalatraServlet
import com.shusen.blog.db.DatabaseSchemas.testTable
import com.shusen.blog.db.TestTable


class IndexServlet extends ScalatraServlet with DatabaseSessionSupport{

  get("/") {
    testTable.insert(new TestTable(1, Some("abc")))
    views.html.hello()
  }

}
