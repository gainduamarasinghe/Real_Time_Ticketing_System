package com.ticketing.ticketingsystem.utils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "ticketing_system.txt";
    private static BufferedWriter writer;

    static {
        try {
            // Initialize the writer with 'false' to overwrite the log file each time
            writer = new BufferedWriter(new FileWriter(LOG_FILE, false)); // false to overwrite and clear the file content
            // Register a shutdown hook to ensure the writer is closed properly
            Runtime.getRuntime().addShutdownHook(new Thread(Logger::close));
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    // Synchronized method to log messages
    public static synchronized void log(String message) {
        try {
            // Clear the previous logs and write the new log
            writer = new BufferedWriter(new FileWriter(LOG_FILE, false)); // Overwrite the file
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logMessage = timestamp + " - " + message;
            if (writer != null) {
                writer.write(logMessage);
                writer.newLine();
                writer.flush(); // Ensure immediate write to the file
            }
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }

    // Method to close the logger (e.g., when the program exits)
    public static synchronized void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Failed to close logger: " + e.getMessage());
        }
    }

    // Method to read the log file and return its contents
    public static synchronized String getLogs() {
        StringBuilder logs = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to read log file: " + e.getMessage());
        }
        return logs.toString();
    }
}
