package ru.eforward.express_testing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class is used to read files from file system
 */
public class TestFilesReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestFilesReader.class);
    /**
     * Reads text file to String
     * @param path - a path to the file to be read.
     * @return String representation of a text file.
     */
    public String readTestFile(Path path){
        if(path == null){
            throw new IllegalArgumentException("readTestFile method: path is null", new NullPointerException());
        }
        StringBuilder sb = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            while(bufferedReader.ready()){
                sb.append(bufferedReader.readLine());
                sb.append("\n");
            }
        }catch(FileNotFoundException fe){
            LOGGER.error("file with test was not found: " + path);
            fe.printStackTrace();
        }catch(IOException ioe){
            LOGGER.error("IOException while reading file: " + path);
            ioe.printStackTrace();
        }
        return sb.toString();
    }
}
