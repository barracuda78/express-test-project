package ru.eforward.express_testing.utils;

/**
 * This is a simple wrapper to PrintStream.out
 * Used for development purposes only
 */
public class LogHelper {
    /**
     * Prints given string message to console.
     * @param message - string message to be printed to console.
     */
    public static void writeMessage(String message) {
        System.out.println(message);
    }

}
