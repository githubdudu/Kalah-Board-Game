# The Github continuous integration actions for this course use this Makefile,
# so do not change it unless you really know what you are doing. If the CI
# process fails because you changed this file, it is on you.

# Note that the syntax used here is typical for most forms of Unix. For
# Windows, at minimum "/" has to be replaced by "\" and ":" (colon) has to 
# be replaced by ";" (semicolon). Other changes may be necessary.

tests: compile
	java -cp resources/junit-3.8.2.jar:resources/kalah-compsci701-a3-20210910.jar:bin junit.textui.TestRunner kalah.test.TestKalahLSN
play: compile
	java -cp resources/junit-3.8.2.jar:resources/kalah-compsci701-a3-20210910.jar:bin kalah.Kalah
  
compile:
	mkdir -p bin
	javac -d bin -cp resources/junit-3.8.2.jar:resources/kalah-compsci701-a3-20210910.jar:bin:src src/kalah/Kalah.java
clean:
	rm -rf bin

# Command for Kalah Web/Browser
test-web: build-web
	node web/test/e2e.js

serve-web: build-web
	node web/serve.js

build-web: compile-web
	jar cf web/dist/kalah-web.jar -C bin .
	cp web/index.html web/kalah.js resources/kalah-io.jar web/dist

compile-web: clean-web
	mkdir -p web/dist
	javac --release 8 -d bin -cp resources/junit-3.8.2.jar:resources/kalah-compsci701-a3-20210910.jar:bin:src src/web/Main.java

clean-web: clean
	rm -rf web/dist
