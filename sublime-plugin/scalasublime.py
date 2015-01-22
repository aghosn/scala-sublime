import sublime, sublime_plugin

class LookupSymbolsCommand(sublime_plugin.TextCommand):
	def run(self, edit):
		self.view.insert(edit, 0, "Scala Sublime!")
	