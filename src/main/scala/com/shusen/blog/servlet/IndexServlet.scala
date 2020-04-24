package com.shusen.blog.servlet

import org.scalatra.ScalatraServlet
import org.scalatra.scalate.ScalateSupport
import com.shusen.blog.db.BlogDao
import com.shusen.blog.db.DatabaseSessionSupport


class IndexServlet extends ScalatraServlet with ScalateSupport with DatabaseSessionSupport {
  val pageLength = 5

  post("/comment") {
    val name = params("name")
    val email = params("email")
    val content = params("content")
    val blogId = params("blogId").toInt
    BlogDao.insertComment(name, email, content, blogId)
    redirect("/blog/"+blogId)
  }


  get("/blog/:id") {
    contentType = "text/html"
    val id = params("id").toInt
    val article = BlogDao.getArticleById(id)
    val categories = BlogDao.getCategories
    val rightCategories = BlogDao.getCategoriesRight
    val topBlogs = BlogDao.topBlogs
    val comments = BlogDao.getCommentsByArticleId(id)
    ssp("/article", "article" -> article, "categories" -> categories,
      "rightCategories" -> rightCategories, "topBlogs" -> topBlogs, "comments" -> comments)
  }

  get("/type/:category") {
    contentType = "text/html"
    val curPath = params("category")
    curPath match {
      case "home" => redirect("/")
      case "contact" =>
        contentType = "text/html"
        val curPath = "contact"
        val categories = BlogDao.getCategories
        ssp("/contact", "categories" -> categories, "curPath" -> curPath)
      case "about" =>
        contentType = "text/html"
        val curPath = "about"
        val categories = BlogDao.getCategories
        ssp("/about", "categories" -> categories, "curPath" -> curPath)
      case _ =>
        getSSP(curPath)

    }
  }

  get("/") {
    contentType = "text/html"
    val curPath = "home"
    getSSP(curPath)
  }


  def getSSP(curPath: String) = {
    val categories = BlogDao.getCategories
    val page = params.getOrElse("page", "1").toInt
    val blogs = getBlogs((page - 1) * pageLength, pageLength, page, curPath)
    val rightCategories = BlogDao.getCategoriesRight
    val totalBlog = BlogDao.totalBlogs(pageLength, curPath.capitalize).head.measures

    val totalPage = if (totalBlog % 2 == 0) {
      totalBlog / pageLength
    } else {
      totalBlog / pageLength + 1
    }
    val topBlogs = BlogDao.topBlogs

    ssp("/index", "categories" -> categories, "curPath" -> curPath, "blogs" -> blogs,
      "rightCategories" -> rightCategories, "totalPage" -> totalPage, "curPage" -> page, "topBlogs" -> topBlogs)
  }


  def getBlogs(offSet: Int, pageLength: Int, page: Int, curPath: String) = curPath match {
    case "home" => BlogDao.searchForAllBlogs((page - 1) * pageLength, pageLength)
    case _ => BlogDao.searchBlogByCate((page - 1) * pageLength, pageLength, curPath.capitalize)
  }


}
