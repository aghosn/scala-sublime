import java.net._
import java.io._
import scala.io._

object Server {

  def main(args: Array[String]): Unit = {
    val listener = new ServerSocket(9999)
    while(true){
      val s = listener.accept()
      val in = new BufferedSource(s.getInputStream).getLines()
      val out = new PrintStream(s.getOutputStream)

      out.println(in.next)
      out.flush
      s.close
    }
  }
}