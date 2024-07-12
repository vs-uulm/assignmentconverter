package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("Please add all required arguments: solution/assignment source_path destination_path (optional) --fileEndingsModifyWhiteList .java .... (optional) --fileEndingsBlacklist .txt ...");
            return;
        }

        if (args[1].equals(args[2])) {
            System.out.println("SourcePath should not be DestinationPath to prevent unwanted Dataloss!");
            return;
        }

        System.out.println(args[1] + ":" + args[2]);

        List<String> fileWhiteList = getArgumentValuesList(args, "--fileEndingsModifyWhiteList");
        List<String> fileBlackList = getArgumentValuesList(args, "--fileEndingsBlacklist");

        //fill with default parameters
        if (fileWhiteList.isEmpty()) {
            fileWhiteList.addAll(List.of(".java", ".gradle"));
            System.out.println("fileWhiteList Empty adding default values!");
        }

        new ProjectConverter(new FileConverter(), new FileUtil(), new FileEndingDirectoryFileFilter(fileBlackList)).convertProject(args[1], args[2], new StringToConvertionTypeMapper().stringToConvertionType(args[0]), fileWhiteList);
    }

    private static List<String> getArgumentValuesList(String[] args, String option) {
        List<String> optionsList = new ArrayList<>();
        int startIndex = 3;

        for (int i = 3; i < args.length; i++) {
            startIndex++;
            if (args[i].startsWith(option)) {
                break;
            }
        }

        for (int j = startIndex; j < args.length; j++) {
            if (args[j].startsWith("--")) {
                break;
            }
            optionsList.add(args[j]);
        }
        return optionsList;
    }
}