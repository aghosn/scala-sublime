import java.net._
import java.io._
import scala.io._

/* For the moment the proxy echoes 1 message and closes the connection
 */
object Server {

  def main(args: Array[String]): Unit = {
    val listener = new ServerSocket(9999)
    
    while(true){
      val s = listener.accept()
      val in = new BufferedReader(new InputStreamReader(s.getInputStream()))
      val out = new PrintWriter(s.getOutputStream, true)

      val msg = in.readLine
      if(msg != null) {
        println(s"Server received msg: ${msg} from ${s.getInetAddress}")
        out.println(msg)
        out.flush()
      }

      s.close
    }
  }
}