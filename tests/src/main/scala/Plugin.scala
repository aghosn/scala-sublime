
package scala.sublime.tests

import scala.collection.mutable.ListBuffer
import scala.tools.nsc.{ Global, Phase, SubComponent }
import scala.tools.nsc.plugins.{ Plugin => NscPlugin, PluginComponent => NscPluginComponent }

class Plugin(val global: Global) extends NscPlugin {
  import global._
  reporter.info(NoPosition, "Plugin loaded", true)

  val name = "subl"
  val description = "prototyping typing"

  val components = List[NscPluginComponent](SublPluginComponent)

  override def processOptions(options: List[String], error: String => Unit) {
    options.foreach { opt =>
      if (opt.startsWith("symb:")) {
        SymbTable.symbols = opt.substring("symb:".length).split(";").toList
      } else {
        reporter.error(NoPosition, "Bad Option")
      }
    }
  }

  private object SublPluginComponent extends NscPluginComponent {
    import global._
    val global = Plugin.this.global

    override val runsAfter = List("typer")
    val phaseName = "subl"

    object LookupSymbol extends Traverser {
      val treesAtSymbols = new ListBuffer[Tree]()

      override def traverse(tree: Tree): Unit = tree match {
        case x if x.symbol != null && SymbTable.symbols.contains(x.symbol.toString) =>
          treesAtSymbols += x
          super.traverse(tree)
        case x if x.symbol != null =>
          reporter.info(NoPosition, "symbol:" + x.symbol, true)
          super.traverse(tree)
        case _ => super.traverse(tree)
      }

      def apply(tree: Tree): List[Tree] ={
        reporter.info(NoPosition, "traversing", true)
        treesAtSymbols.clear()
        traverse(tree)
        treesAtSymbols.toList
      }
    }

    def newPhase(prev: Phase) = new StdPhase(prev) {
      reporter.info(NoPosition, "newPhase: " + SymbTable.symbols, true)
      def apply(unit: CompilationUnit) {
        LookupSymbol(unit.body).foreach{t =>
          reporter.info(t.pos, show(t), true)
        }
      }
    }

  }
}