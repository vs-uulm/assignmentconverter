# assignmentconverter

This is a small command line tool to convert solutions to assignments.

# How to use
This project uses Java 21 and uses gradle 8.5.
To use clone this project and run the gradle task shadowJar.
This results in a runnable jar in build/libs/.
Then the tool can be used as follows:

```
 java -jar [theJar] [assignment/solution] [pathToProjectToBeConverted] [destinationPath]
```