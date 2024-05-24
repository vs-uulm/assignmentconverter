package org.example;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Converts the whole Project to either an assignment as Zip or a solution
 */
public class ProjectConverter {
    private final FileConverter fileConverter;

    private final FileUtil fileUtil;
    private final FileFilter filter;

    public ProjectConverter(FileConverter fileConverter, FileUtil fileUtil, FileFilter filter) {
        this.fileConverter = fileConverter;
        this.fileUtil = fileUtil;
        this.filter = filter;
    }

    /**
     * copies the projectPath directory to the destinationPath and modifies files whose file endings are part of the whitelist
     *
     * @param projectPath     the path to the project that is to be converted
     * @param destinationPath the path where the result is to be stored
     * @param convertionType  the type of conversion for example Assignment when its to be converted to an Assignment
     * @param fileWhiteList   a list of file endings, filters which files are modified by the FileConverter
     * @throws IOException throws exception if something goes wrong during file modification
     */
    public void convertProject(String projectPath, String destinationPath, ConvertionType convertionType, List<String> fileWhiteList) throws IOException {
        File destinationDirectory = new FileUtil().copyDirectory(projectPath, destinationPath, filter);

        Set<String> filesToBeModifiedPaths = fileUtil.listFilesUsingFileWalk(destinationDirectory.getAbsolutePath(), fileWhiteList);

        for (String path : filesToBeModifiedPaths) {
            if (convertionType.equals(ConvertionType.ASSIGNMENT)) {
                fileConverter.convertFileToAssignment(path);
            } else if (convertionType.equals(ConvertionType.SOLUTION)) {
                fileConverter.convertFileToSolution(path);
            }
        }
    }


}
