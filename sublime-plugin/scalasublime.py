import sublime, sublime_plugin

class LookupSymbolsCommand(sublime_plugin.TextCommand):
	def run(self, edit):
		sels = self.view.sel()
		#get all the selected words
		#is it possible to remove self.view.substr(sel) redundancy?
		onlyOneWords = [self.view.substr(sel) for sel in sels if self.view.substr(sel).find(' ') < 0] 
		value = ', '.join(onlyOneWords)
		#get the word under the selection if no word have been selected
		if not value: #please god forgive me, but 1) onlyOneWords is a generator 2) if I transform it to a List and thest not list it doesnt work :()
			#duuude, gotta find a way to reduce that (139 chars!)
			selectedWords = [self.view.substr(self.view.word(sel)) for sel in sels if self.view.substr(self.view.word(sel)).find(' ') < 0]
			value = ', '.join(selectedWords) 
		self.view.set_status("scalasublime", value)
	