#Grep app
## Introduction 
Developers are tasked with creating an application that automates the grep Bash command line utility, which is used for searching through plain-text data sets for lines that match text patterns. When given a directory and text pattern, the app will traverse the directory tree and search for the given pattern. The matched lines will then be output in a file.   <br></br>
## Usage 
Grep takes in three arugments 
1. `regex`: regex pattern string 
2. `rootPath`: Search will take place in this root directory 
3. `outFile`: name of the output file, results will be stored here<br> </br>


Execute the grep app using the following argument:  
```
grep regex rootPath outFile
#example 
"File" ~/Documents ~/Documents/output.txt
```
Grep will search through all files in the document folder and it subdirectories for regex pattern "file". If file contains the pattern line will be recorded in the results.txt
## Pseudocode
Below is the pseudocode version of the `process()` method.

```
matchedLines = []

for file in listFilesRecursivelyFrom(rootDirectory)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)

writeToFile(matchedLines)
```
Grep reads thorough every line of every file is a given directory, using a nested two loop. While reading each line grep checks to see if there are any lines that match the given text pattern. If there is a match found, Grep adds the line to its record. Ones the two loops end, Grep will write all matched lines into an output file. 
## Preformance Issue 
The solution given above works for on a smaller scale, but if we need to work on with larger files grep may not have sufficient memory space to process and store matched lines.  The method `process()` uses a loop to read the files line by line then stores the lines in` ArrayList<String>`. If there is not enough room in the stack to store all lines, some lines will be lost and will not be stored. To address this Performance issue, we can use Stream instead of scanner. Stream allows to get rid of unnecessary data, storing only the needed data. This resolves the memory space issue.
##Improvements 
1.	Output file name and line of matched line. This will allow user to know where matched line is located.
2.	Take in multiple files at the same time, this will reduce the time it takes to read all files in a directory.
3.	Implement a method to check if there is enough memory space, and to alert users if there is no memory left. This will help avoid any memory storage issues. 
