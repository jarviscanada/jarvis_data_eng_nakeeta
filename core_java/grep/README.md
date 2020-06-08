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
