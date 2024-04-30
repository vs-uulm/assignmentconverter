package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {

        if(args.length<3){
            System.out.println("Please add all required arguments: solution/assignment source_path destination_path");
            return;
        }

        if(args[1].equals(args[2])){
            System.out.println("SourcePath should not be DestinationPath to prevent unwanted Dataloss!");
            return;
        }

        System.out.println(args[1]+":"+args[2]);
        AssignmentConverter converter = new AssignmentConverter();

        File sourceDirectory = new File(args[1]);
        File destinationDirectory = new File(args[2]);
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);

        Set<String> javaPaths = listFilesUsingFileWalk(destinationDirectory.getAbsolutePath());

        for(String path: javaPaths){
            if(args[0].equals("assignment")){
                converter.convertFileToAssignment(path,path);
            } else if (args[0].equals("solution")) {
                converter.convertFileToSolution(path,path);
            }

        }
    }

    public static Set<String> listFilesUsingFileWalk(String dir) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .filter(in->in.endsWith(".java"))
                    .collect(Collectors.toSet());
        }
    }
}