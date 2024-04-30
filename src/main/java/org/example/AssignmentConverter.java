package org.example;

import java.io.*;
import java.util.regex.Pattern;

public class AssignmentConverter {

    Pattern startSolution = Pattern.compile("// BEGIN SOLUTION", Pattern.CASE_INSENSITIVE);
    Pattern endSolution = Pattern.compile("// END SOLUTION", Pattern.CASE_INSENSITIVE);

    Pattern startAssignment = Pattern.compile("// BEGIN ASSIGNMENT");
    Pattern endAssignment = Pattern.compile("// END ASSIGNMENT");

    public void convertFileToAssignment(String filePath, String targetFilePath) throws IOException {
        copyToNewFile(startSolution,endSolution,filePath);
    }

    public void convertFileToSolution(String filePath, String targetFilePath) throws IOException {
        copyToNewFile(startAssignment,endAssignment,filePath);
    }

    public void copyToNewFile(Pattern start,Pattern end,String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        Boolean ignoreComments = false;

        String result = "";

        while ((currentLine= reader.readLine())!=null){
            if(start.matcher(currentLine).find()){
                ignoreComments=true;
            }

            if(!ignoreComments){
                result+=currentLine+"\n";
            }

            if(end.matcher(currentLine).find()){
                ignoreComments=false;
            }
        }
        reader.close();

        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write(result);
        printWriter.close();
    }
}
