package org.example;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class FileEndingDirectoryFileFilter implements FileFilter {

    private final List<String> excludedFileEndingsToCopy;

    public FileEndingDirectoryFileFilter(List<String> excludedFileEndingsToCopy) {
        this.excludedFileEndingsToCopy = excludedFileEndingsToCopy;
    }

    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return {@code true} if and only if {@code pathname}
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        for (String excludedFileEnding : excludedFileEndingsToCopy) {
            if (pathname.toPath().getFileName().toString().matches(excludedFileEnding)) {
                return false;
            }
        }
        return true;
    }
}
