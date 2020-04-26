package com.shusen.blog.db

import com.shusen.blog.model.{Comment, Contact}
import com.shusen.blog.model.DatabaseSchemas.{article, category, comment, contact}
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


  def searchBlogByTitle(offset:Int, pageLength:Int, articleTitle: String) =
    from(article)(a =>
      where(a.articleTitle.like("%" + articleTitle + "%"))
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

  def insertComment(name: String, email: String, content: String, blogId: Long) =
    transaction{
      comment.insert(new Comment(0, getCurrentDigitTime.toString, name, email, content, blogId))
      updateCommentNum(blogId)
    }

  def insertContact(name: String, email: String, subject: String, message:String) = {
    val createDate = getCurrentDigitTime.toString
    contact.insert(new Contact(0, name, email, subject, message, createDate))
  }


  def getCommentsByArticleId(articleId:Long) =
    from(comment)(c =>
      where(c.articleId === articleId)
      select(c) orderBy(c.commentDate)
    )


  def updateCommentNum(id: Long) =
    update(article)(a =>
    where(a.id === id)
      set(a.commentNum := a.commentNum.~ + 1)
    )

}
