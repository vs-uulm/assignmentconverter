package org.example;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Converts a single file to either a solution or an assignment
 */

public class FileConverter {

    private final String beginSolutionRegex = "// BEGIN SOLUTION";
    private final String endSolutionRegex = "// END SOLUTION";

    private final String beginAssignmentRegex = "// BEGIN ASSIGNMENT";
    private final String endAssignmentRegex = "// END ASSIGNMENT";

    private final Pattern startExclude = Pattern.compile("// BEGIN EXCLUDE");
    private final Pattern endExclude = Pattern.compile("// END EXCLUDE");

    public void convertFileToAssignment(String filePath) throws IOException {

        Pattern beginSolution = Pattern.compile(this.beginSolutionRegex, Pattern.CASE_INSENSITIVE);
        Pattern endSolution = Pattern.compile(this.endSolutionRegex, Pattern.CASE_INSENSITIVE);
        Pattern ignoreSingleLine = Pattern.compile(this.beginAssignmentRegex + "|" + this.endAssignmentRegex, Pattern.CASE_INSENSITIVE);
        copyToNewFile(beginSolution, endSolution, filePath, ignoreSingleLine);
    }

    public void convertFileToSolution(String filePath) throws IOException {
        Pattern beginAssignment = Pattern.compile(this.beginAssignmentRegex, Pattern.CASE_INSENSITIVE);
        Pattern endAssignment = Pattern.compile(this.endAssignmentRegex, Pattern.CASE_INSENSITIVE);
        Pattern ignoreSingleLine = Pattern.compile(this.beginSolutionRegex + "|" + this.endSolutionRegex, Pattern.CASE_INSENSITIVE);

        copyToNewFile(beginAssignment, endAssignment, filePath, ignoreSingleLine);
    }

    private void copyToNewFile(Pattern ignoreStart, Pattern ignoreEnd, String filePath, Pattern ignoreSingleLine) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String currentLine;

        boolean ignoreComments = false;

        StringBuilder result = new StringBuilder();

        while ((currentLine = reader.readLine()) != null) {
            if (ignoreStart.matcher(currentLine).find() || startExclude.matcher(currentLine).find()) {
                ignoreComments = true;
            }

            // continue if current line is part of the Assignment
            if (ignoreSingleLine.matcher(currentLine).find()) {
                continue;
            }

            if (!ignoreComments) {
                result.append(currentLine).append("\n");
            }

            if (ignoreEnd.matcher(currentLine).find() || endExclude.matcher(currentLine).find()) {
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
