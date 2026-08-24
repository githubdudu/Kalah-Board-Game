// End-to-end test for the browser build: drives the real page in a headless browser and
// plays actual games. Run it with `npm test` from web/ (see web/README.md).
//
// This catches what unit tests cannot: that CheerpJ boots, that the JS <-> Java native
// bridge works, and that the Promise returned by jsReadLine really does suspend the Java
// thread so the blocking read loop in GameControl keeps working.
//
// It requires a built web/dist/kalah-web.jar and network access to the CheerpJ CDN.

const { spawn } = require("child_process");
const path = require("path");

let chromium;
try {
	({ chromium } = require("playwright"));
} catch (e) {
	console.error("playwright is not installed. Run `npm install` in web/ first.");
	process.exit(2);
}

const PORT = Number(process.env.PORT || 8123);
const URL = "http://127.0.0.1:" + PORT + "/";
const BOOT_TIMEOUT = 180000; // The CheerpJ runtime is a few MB on a cold cache.

const results = [];

function check(name, ok) {
	results.push({ name, ok });
}

function startServer() {
	const server = spawn(process.execPath, [path.join(__dirname, "..", "serve.js")], {
		env: { ...process.env, PORT: String(PORT) },
		stdio: "ignore"
	});
	return server;
}

async function waitForServer() {
	for (let i = 0; i < 50; i++) {
		try {
			const response = await fetch(URL);
			if (response.ok) {
				return;
			}
		} catch (e) {
			// Not listening yet.
		}
		await new Promise((r) => setTimeout(r, 100));
	}
	throw new Error("server did not start on port " + PORT);
}

/** Wraps a page in the few operations a player can perform. */
function session(page) {
	return {
		screen: () => page.locator("#screen").innerText(),
		waitForPrompt: () => page.waitForSelector("#line:not([hidden])", { timeout: BOOT_TIMEOUT }),
		waitForEnd: () => page.waitForFunction(
			() => document.getElementById("screen").innerText.includes("[session ended"),
			null, { timeout: 20000 }),
		async send(key) {
			await page.fill("#entry", key);
			await page.press("#entry", "Enter");
		}
	};
}

const EMPTY_ROW_P1 = "|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |";

/** A move, then save / move / load, then an invalid entry (which ends the game). */
async function testPlayAndSaveLoad(browser, errors) {
	const page = await browser.newPage();
	page.on("pageerror", (e) => errors.push("pageerror: " + e.message));
	page.on("console", (m) => { if (m.type() === "error") errors.push(m.text()); });

	const io = session(page);
	await page.goto(URL);

	const boot = Date.now();
	await io.waitForPrompt();
	console.log("  booted in " + ((Date.now() - boot) / 1000).toFixed(1) + "s");

	const start = await io.screen();
	check("board is rendered", /\+----\+-------\+/.test(start));
	check("P1 moves first", /Player P1/.test(start));

	await io.send("1");
	await io.waitForPrompt();
	const afterMove = await io.screen();
	check("turn passes to P2", /Player P2/.test(afterMove.slice(start.length)));
	check("seeds were sown", /1\[ 0\]/.test(afterMove.slice(start.length)));

	// Save, make a move that changes the board, then load it back.
	await io.send("s");
	await io.waitForPrompt();
	await io.send("2");
	await io.waitForPrompt();
	const afterSecondMove = await io.screen();
	await io.send("l");
	await io.waitForPrompt();
	const afterLoad = (await io.screen()).slice(afterSecondMove.length);
	check("load restores the saved position", /2\[ 4\]/.test(afterLoad) && /Player P2/.test(afterLoad));

	const beforeInvalid = await io.screen();
	await io.send("x");
	await io.waitForEnd();
	const afterInvalid = (await io.screen()).slice(beforeInvalid.length);
	check("invalid input is rejected", /Invalid input/i.test(afterInvalid));

	await page.close();
}

/** New game resets the board, and quit ends the session. */
async function testNewGameAndQuit(browser, errors) {
	const page = await browser.newPage();
	page.on("pageerror", (e) => errors.push("pageerror: " + e.message));
	page.on("console", (m) => { if (m.type() === "error") errors.push(m.text()); });

	const io = session(page);
	await page.goto(URL);
	await io.waitForPrompt();

	await io.send("4");
	await io.waitForPrompt();
	const afterMove = await io.screen();

	await io.send("N");
	await io.waitForPrompt();
	const afterNew = (await io.screen()).slice(afterMove.length);
	check("new game resets the board", afterNew.includes(EMPTY_ROW_P1));

	await io.send("q");
	await io.waitForEnd();
	const afterQuit = await io.screen();
	check("quit ends the game", /Game over/.test(afterQuit));

	await page.close();
}

(async () => {
	const server = startServer();
	let browser;
	const errors = [];
	try {
		await waitForServer();
		browser = await chromium.launch();

		console.log("scenario: play, save, load, invalid input");
		await testPlayAndSaveLoad(browser, errors);
		console.log("scenario: new game, quit");
		await testNewGameAndQuit(browser, errors);

		check("no JavaScript errors", errors.length === 0);
	} finally {
		if (browser) {
			await browser.close();
		}
		server.kill();
	}

	console.log("");
	for (const { name, ok } of results) {
		console.log((ok ? "  PASS  " : "  FAIL  ") + name);
	}
	if (errors.length) {
		console.log("\nJavaScript errors:\n  " + errors.join("\n  "));
	}

	const failed = results.filter((r) => !r.ok).length;
	console.log("\n" + (results.length - failed) + "/" + results.length + " checks passed");
	process.exit(failed === 0 ? 0 : 1);
})().catch((error) => {
	console.error(error);
	process.exit(1);
});
