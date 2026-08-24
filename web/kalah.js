// Native library for web.WebIO, loaded by CheerpJ via System.loadLibrary("kalah").
// CheerpJ fetches this file from java.library.path (set in index.html), so it runs in its
// own module scope - the DOM work stays in index.html and is reached through window.kalahUI.

const ui = () => window.kalahUI;

export default {
	async Java_web_WebIO_print(lib, self, text) {
		ui().write(text);
	},
	async Java_web_WebIO_println(lib, self, text) {
		ui().write(text + "\n");
	},
	async Java_web_WebIO_readFromKeyboard(lib, self, prompt) {
		const line = await ui().readLine(prompt);
		return line == null ? "" : line;
	}
};
