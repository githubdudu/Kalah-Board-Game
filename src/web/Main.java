package web;

import kalah.Kalah;

/**
 * Entry point for the browser build.
 */
public class Main {
    public static void main(String[] args) {
        new Kalah().play(new WebIO());
    }
}
