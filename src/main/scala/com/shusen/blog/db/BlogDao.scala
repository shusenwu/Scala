package com.shusen.blog.db

import com.shusen.blog.model.Comment
import com.shusen.blog.model.DatabaseSchemas.{article, category, comment}
import org.squeryl.PrimitiveTypeMode._
import com.shusen.blog.utils.tools.getCurrentDigitTime



object BlogDao {
  def getCategories =
    from(category)(c =>
      where(c.onLeft === 1)
        select(c)
        orderBy(c.cateId)
    )


  def searchForAllBlogs(offset:Int, pageLength:Int) =
    from(article)(a =>
      select(a)
        orderBy(a.updateDate desc)
    ).page(offset, pageLength)

  def searchBlogByCate(offset:Int, pageLength:Int, category: String) =
    from(article)(a =>
      where(a.category === category)
      select(a)
        orderBy(a.updateDate desc)
    ).page(offset, pageLength)

  def topBlogs() =
    from(article)(a =>
      select(a)
        orderBy(a.commentNum desc)
    ).page(0, 3)


  def getArticleById(id: Int) =
    from(article)(a =>
      where(a.id === id)
        select(a)
    ).toList.head


  def getCategoriesRight =
    from(article)(a =>
      groupBy(a.category)
      compute(countDistinct(a.id))
    )

  def totalBlogs(pageLength:Int, category: String) = category.toLowerCase match {
    case "home" =>
      from(article)(a=>
      compute(countDistinct(a.id))
    )
    case _ =>
      from(article)(a=>
        where(a.category === category)
        compute(countDistinct(a.id))
      )
  }

  def insertComment(name: String, email: String, content: String, blogId: Int) =
    comment.insert(new Comment(0, getCurrentDigitTime.toString, name, email, content, blogId))

  def getCommentsByArticleId(articleId:Long) =
    from(comment)(c =>
      where(c.articleId === articleId)
      select(c) orderBy(c.commentDate)
    )


}
