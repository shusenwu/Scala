import com.shusen.blog.servlet.IndexServlet
import com.shusen.blog.db.DatabaseInit
import org.scalatra._
import javax.servlet.ServletContext


class ScalatraBootstrap extends LifeCycle with DatabaseInit {
  override def init(context: ServletContext) {
    configureDb()  // mysql conn pool
    context.mount(new IndexServlet, "/*")
  }

  override def destroy(context:ServletContext) {
    closeDbConnection()
  }
}
