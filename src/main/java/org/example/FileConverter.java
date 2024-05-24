package org.example;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Converts a single file to either a solution or an assignment
 */

public class FileConverter {

    Pattern startSolution = Pattern.compile("// BEGIN SOLUTION", Pattern.CASE_INSENSITIVE);
    Pattern endSolution = Pattern.compile("// END SOLUTION", Pattern.CASE_INSENSITIVE);

    Pattern startAssignment = Pattern.compile("// BEGIN ASSIGNMENT");
    Pattern endAssignment = Pattern.compile("// END ASSIGNMENT");

    Pattern startExclude = Pattern.compile("// BEGIN EXCLUDE");
    Pattern endExclude = Pattern.compile("// END EXCLUDE");

    public void convertFileToAssignment(String filePath) throws IOException {
        copyToNewFile(startSolution, endSolution, filePath);
    }

    public void convertFileToSolution(String filePath) throws IOException {
        copyToNewFile(startAssignment, endAssignment, filePath);
    }

    private void copyToNewFile(Pattern start, Pattern end, String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        boolean ignoreComments = false;

        StringBuilder result = new StringBuilder();

        while ((currentLine = reader.readLine()) != null) {
            if (start.matcher(currentLine).find() || startExclude.matcher(currentLine).find()) {
                ignoreComments = true;
            }

            if (!ignoreComments) {
                result.append(currentLine).append("\n");
            }

            if (end.matcher(currentLine).find() || endExclude.matcher(currentLine).find()) {
                ignoreComments = false;
            }
        }
        reader.close();

        FileWriter fileWriter = new FileWriter(filePath);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write(result.toString());
        printWriter.close();
    }
}
