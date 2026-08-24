# Kalah in the browser

Runs the unmodified game logic from [`src/kalah`](../src/kalah) in a web page, using
[CheerpJ](https://cheerpj.com) (a WebAssembly JVM). No server, no backend.

## How it works

`Kalah.play(IO io)` takes any `IO` implementation, so the browser build does not need
stdin/stdout at all — it only swaps in a different `IO`:

| | command line | browser |
|---|---|---|
| entry point | `kalah.Kalah.main` | `web.Main` |
| `IO` implementation | `MockIO` (course jar) | [`WebIO`](java/kalah/web/WebIO.java) |
| input | `System.in` | JS native returning a Promise |
| output | `System.out` | DOM |

`WebIO.jsReadLine` is a `native` method implemented in JavaScript in
[`web/index.html`](web/index.html). It returns a Promise, which suspends the Java
thread until the player presses Enter — so the blocking read loop in `GameControl` works
unchanged.


## Build

```sh
./web/build.sh                 # -> web/dist/kalah-web.jar
# or
make build-web
```

Set JDK compile to Java 8;  Set CheerpJ to Java 8.

Rerun this after any change to `src/kalah`.

## Preview locally

```sh
node web/serve.js              # -> http://localhost:8080
# or
make serve-web
```

## Test

```sh
cd web && npm install     # playwright + a headless chromium (~120 MB, once)
npm test
# or
make test-web
```

[`test/e2e.js`](test/e2e.js) starts `serve.js`, drives the real page in headless Chromium
and plays actual games: a move, save/load, an invalid entry, new game, and quit. It fails
the build on any JavaScript error, and exits non-zero so CI can gate on it.

This one checks that CheerpJ boots, that the JS ↔ Java native bridge is
wired up, and that the Promise returned by `jsReadLine` genuinely suspends the Java thread.

Needs `web/dist/kalah-web.jar` to be built first, and network access to the CheerpJ CDN.

## Deploy

`web/dist/` is a plain static directory (`index.html` + the jar) — copy it to any static
host. The only requirement is that the host supports HTTP Range requests, which CheerpJ
uses to read the jar.

