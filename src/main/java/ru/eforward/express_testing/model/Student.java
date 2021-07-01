package ru.eforward.express_testing.model;

import lombok.Getter;
import lombok.Setter;
import ru.eforward.express_testing.utils.TestFilesReader;

import java.nio.file.Path;
import java.util.List;

@Getter
@Setter
public class Student extends User{

    private int levelId;
    private int groupId;
    private List<Integer> testResults;

    /**
     * Used in development purposes only.
     * Used with fake DB only, while connection to Storage is not available
     * Reads the text file with testing information from the given path.
     * Performs content of this file to html-formatted string.
     * @param path - given path to the file with testing info.
     * @return content of this file as an html-formatted String.
     * */
    public String performTest(Path path){
        String testFile = new TestFilesReader().readTestFile(path);
        String result = createHtmlTestString(testFile);
        return  result;
    }

    /**
     * * Used in development purposes only.
     * Used with fake DB only, while connection to Storage is not available
     * Performs the given string to html-formatted String.
     * @param str - string generated by private method performTest.
     * @return html-formatted String.
     * */
    private String createHtmlTestString(String str){
        String[] strings = str.split("\n");
        StringBuilder sb = new StringBuilder("<p>");

        for(String s : strings){
            if(s.endsWith("1")){
                //check if in sb there is <li> tag unclosed. Close it if it is so;
                if(ulTagUnclosed(sb.toString())){
                    sb.append("</ul>");
                }
                sb.append("<p>Заполните недостающие слова:</p>");
                sb.append("<b>");
                sb.append(s.substring(0, s.length()-2));
                sb.append("</b>");
                sb.append("<ul>");
            }else if(s.endsWith("2")) {
                if(ulTagUnclosed(sb.toString())){
                    sb.append("</ul>");
                }
                sb.append("<p>Переведите:</p>");
                sb.append("<b>");
                sb.append(s.substring(0, s.length()-2));
                sb.append("</b>");
                sb.append("<ul>");
            }else if(s.startsWith("-")){
                sb.append("<li>");
                sb.append(s.substring(1));
                sb.append("</li>");
            }else{
                //do nothing...
            }
        }

        sb.append("</p>");
        return sb.toString();
    }

    /**
     * Used in development purposes only.
     * Used with fake DB only, while connection to Storage is not available
     * Check if this html-formatted string s contains unclosed <ul> tag somewhere at the and
     * @param s - given html-formatted String;
     * @return true if s contains unclosed <ul> tag somewhere at the end, otherwise returns false.
     */
    private boolean ulTagUnclosed(String s){
        if(s.lastIndexOf("<li>") < s.lastIndexOf("</li>")){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student{" +
                " id=" + getId() +
                ", lastName=" + getLastName() +
                ", levelId=" + levelId +
                ", groupId=" + groupId +
                ", testResults=" + testResults +
                '}';
    }
}
