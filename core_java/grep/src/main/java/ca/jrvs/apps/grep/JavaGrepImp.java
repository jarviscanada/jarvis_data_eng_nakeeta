package ca.jrvs.apps.grep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

  private String regex;
  private String rootPath;
  private String outFile;
  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  public static void main (String[] args) {

    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }

  // Traverses and returns all files in a directory
  @Override
  public void process() throws IOException {
    List<String> matchedLines= new LinkedList<>();
    for (File files :listFiles(getRootPath())){
      for (String lists: readLines(files)){
        if (containsPattern(lists)){
          matchedLines.add(lists);
        }
      }
    }
    writeToFile(matchedLines);
  }

  // list all files in a given directory and it subdirectories.
  @Override
  public List<File> listFiles(String rootDir){
    File root = new File(rootDir);
    List<File> files = new ArrayList<>();
    File[] lsFlie = root.listFiles();


    for (File filename: lsFlie) {

      if (filename.isDirectory()) {
        List<File> subdirectoryContents = listFiles(filename.toString());
        files.addAll(subdirectoryContents);
      }
      else {
        files.add(filename);
      }
    }
    return files;
  }


  // read file line by line
  @Override
  public List<String> readLines(File inputFile) {
    List<String> fileLines = new LinkedList<>();
    try{
      BufferedReader buffread = new BufferedReader(new FileReader(inputFile));
      String lines = buffread.readLine();
      while (lines != null) {
        fileLines.add(lines);
        lines= buffread.readLine();
      }
      buffread.close();
    } catch (IOException ex){

      logger.error(ex.getMessage(),ex);
    }
    return fileLines;
  }

  // checkif a line contains a given rex pattern
  @Override
  public boolean containsPattern(String line) {
    Pattern findPattern = Pattern.compile(this.regex);
    Matcher matched = findPattern.matcher(line);
    return matched.matches();
  }

  // write lines into a file
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter buffWrite = new BufferedWriter(new FileWriter(this.getOutFile()));
    for (String line : lines){
      buffWrite.write(lines + System.lineSeparator());
    }
    buffWrite.close();
  }

  // get and set variables
  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
