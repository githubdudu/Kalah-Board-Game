// Local preview server for the browser build:  node web/serve.js  ->  http://localhost:8080
//
// CheerpJ reads the jar lazily with HTTP Range requests. `python3 -m http.server` does not
// support them and logs "CheerpJ cannot run" (it still limps along by downloading the whole
// file), so this small server handles Range properly. GitHub Pages supports Range natively,
// so this is only needed for local previewing.
const http = require("http");
const fs = require("fs");
const path = require("path");

// Serves web/dist, so run ./web/build.sh first.
const root = path.join(__dirname, "dist");
const port = Number(process.env.PORT || 8080);
const types = {
	".html": "text/html; charset=utf-8",
	".js": "text/javascript",
	".jar": "application/java-archive"
};

http.createServer((req, res) => {
	let file = path.join(root, decodeURIComponent(req.url.split("?")[0]));
	if (file.endsWith(path.sep)) {
		file = path.join(file, "index.html");
	}
	if (!file.startsWith(root)) {
		res.writeHead(403).end("forbidden");
		return;
	}
	fs.stat(file, (err, stat) => {
		if (err || !stat.isFile()) {
			res.writeHead(404).end("not found");
			return;
		}
		const type = types[path.extname(file)] || "application/octet-stream";
		const match = /bytes=(\d*)-(\d*)/.exec(req.headers.range || "");
		if (match) {
			const start = match[1] ? Number(match[1]) : 0;
			const end = match[2] ? Number(match[2]) : stat.size - 1;
			res.writeHead(206, {
				"Content-Type": type,
				"Accept-Ranges": "bytes",
				"Content-Range": `bytes ${start}-${end}/${stat.size}`,
				"Content-Length": end - start + 1
			});
			fs.createReadStream(file, { start, end }).pipe(res);
		} else {
			res.writeHead(200, {
				"Content-Type": type,
				"Accept-Ranges": "bytes",
				"Content-Length": stat.size
			});
			fs.createReadStream(file).pipe(res);
		}
	});
}).listen(port, () => console.log("Kalah web build on http://localhost:" + port));
