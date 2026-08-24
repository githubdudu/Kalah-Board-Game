package web;

import com.qualitascorpus.testsupport.IO;
import java.util.ArrayList;
import java.util.List;

/**
 * IO implementation backed by the browser instead of stdin/stdout.
 *
 * 
 */
public class WebIO implements IO {

    // Load the native kalah.js library file
    static {
        System.loadLibrary("kalah");
    }

    @Override
    public native void print(String text);

    @Override
    public native void println(String text);

    @Override
    public native String readFromKeyboard(String prompt);

    /**
     * Not used by the game, which parses the menu choice itself in GameControl.
     * Kept
     * faithful to the interface contract in case that changes.
     */
    @Override
    public int readInteger(String prompt, int min, int max, int invalidValue,
            String errorMessage) {
        return -1;
    }

    /**
     * Stale method
     * 
     * Return empty array
     */
    @Override
    public List<String> readTextFile(String fileName) {
        return new ArrayList<String>();
    }
}
