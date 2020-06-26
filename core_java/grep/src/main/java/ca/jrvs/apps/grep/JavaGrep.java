package ca.jrvs.apps.grep;

import java.io.IOException;
import java.io.File;
import java.util.*;

public interface JavaGrep {

    void process() throws IOException;

    List<File> listFiles(String rootDir);

    List<String> readLines(File inputFile);

    boolean containsPattern(String line);

    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}
