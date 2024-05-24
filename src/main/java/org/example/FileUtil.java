package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    /**
     * @param sourceDirectoryPath the path to the directory that is to be copied
     * @param targetDirectoryPath the path where the directory should be copied to
     * @return the File pointing to the targetDirectory that was created
     * @throws IOException is thrown when something goes wrong during copying
     */
    public File copyDirectory(String sourceDirectoryPath, String targetDirectoryPath) throws IOException {
        File sourceDirectory = new File(sourceDirectoryPath);
        File targetDirectory = new File(targetDirectoryPath);
        FileUtils.copyDirectory(sourceDirectory, targetDirectory);

        return targetDirectory;
    }

    /**
     * @param sourceDirectoryPath the path to the directory that is to be copied
     * @param targetDirectoryPath the path where the directory should be copied to
     * @param filter              Filefilter to specify which files should be filtered
     * @return the File pointing to the targetDirectory that was created
     * @throws IOException is thrown when something goes wrong during copying
     */
    public File copyDirectory(String sourceDirectoryPath, String targetDirectoryPath, FileFilter filter) throws IOException {
        File sourceDirectory = new File(sourceDirectoryPath);
        File targetDirectory = new File(targetDirectoryPath);
        FileUtils.copyDirectory(sourceDirectory, targetDirectory, filter);

        return targetDirectory;
    }

    /**
     * Lists all the files using file walk from given directory
     *
     * @param dir         the directory to start with
     * @param fileEndings the fileEndings that are to be filtered if empty no filter is applied
     * @return the Set of all the filePaths that satisfy the fileEndingsFilter
     * @throws IOException thrown if something goes wrong during access of folders
     */
    public Set<String> listFilesUsingFileWalk(String dir, List<String> fileEndings) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .filter(in -> {
                        for (String fileEnding : fileEndings) {
                            if (in.endsWith(fileEnding)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .collect(Collectors.toSet());
        }
    }
}
