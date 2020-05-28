package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp  extends JavaGrepImp {
    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: JavaGrep [regex] [rootPath] [outFilePath]");
        }
            JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
            javaGrepLambdaImp.setRegex(args[0]);
            javaGrepLambdaImp.setRootPath(args[1]);
            javaGrepLambdaImp.setOutFile(args[2]);
            try {
                javaGrepLambdaImp.process();
            } catch (Exception ex) {
                javaGrepLambdaImp.logger.error(ex.getMessage(),ex);
            }
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files = new LinkedList<>();
        try {
            files = Files.walk(Paths.get(rootDir))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader Files = new BufferedReader(new FileReader(inputFile));
            lines = Files.lines().collect(Collectors.toList());
            Files.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return lines;
    }
}
