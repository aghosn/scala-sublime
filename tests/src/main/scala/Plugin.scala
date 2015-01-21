/**
 * Main component of the compiler plugin. Apply the whole compression piteline
 * and generate the good path for storing ASTs.
 *
 * @author Mathieu Demarne, Adrien Ghosn
 */
package scala.reflect.persistence

import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }

class Plugin(val global: Global) extends NscPlugin {
  import global._

  val name = "subl"
  val description = "prototyping typing"

  val components = List[NscPluginComponent](SublPluginComponent)

  private object SublPluginComponent extends NscPluginComponent {
    import global._
    val global = Plugin.this.global

    override val runsAfter = List("typer")
    val phaseName = "subl"

    def newPhase(prev: Phase) = new StdPhase(prev) {
      def apply(unit: CompilationUnit) {
        //TODO implement
      }
    }

  }
}