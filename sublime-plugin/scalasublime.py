import sublime, sublime_plugin

class LookupSymbolsCommand(sublime_plugin.TextCommand):

	def run(self, edit):
		sels = self.view.sel()
		selsStr = map(lambda s: self.view.substr(s), sels)#[sel for sel in sels if self.view.substr(sel)]
		#get all the selected words
		onlyOneWords = filter(lambda s: s.find(' ') < 0, selsStr)
		value = ', '.join(onlyOneWords)
		#get the word under the selection if no word have been selected
		if not value: #please god forgive me, but 1) onlyOneWords is a generator 2) if I transform it to a List and thest not list it doesnt work :()
			selsWord = map(lambda s: self.view.substr(self.view.word(s)), sels)
			selectedWords = filter(lambda s: s.find(' ') < 0, selsWord)
			value = ', '.join(selectedWords) 
		self.view.set_status("scalasublime", value)
	