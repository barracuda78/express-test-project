package ru.eforward.express_testing.model.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class TestFilesReader {

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
            fe.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return sb.toString();
    }
}
