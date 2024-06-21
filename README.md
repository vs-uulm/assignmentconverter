# assignmentconverter

This is a small command line tool to convert solutions to assignments.
It does that by copying over all the files in a gradle project and storing them in the destination directory.
Afterwards all files that are specified are scanned for //BEGIN SOLUTION, //END SOLUTION comments.
Everything that is enclosed in the comments is deleted.

# How to use
This project uses Java 21 and uses gradle 8.5.
To use clone this project and run the gradle task shadowJar.
This results in a runnable jar in build/libs/.
Then the tool can be used as follows:

```
 java -jar [theJar] [assignment/solution] [pathToProjectToBeConverted] [destinationPath] --fileEndingsModifyWhiteList [INSERT FILE ENDING HERE] --fileEndingsBlacklist [INSERT FILE ENDINGS HERE] 
```
Example:
```
java -jar libs/AssignmentConverter-1.0-SNAPSHOT-all.jar assignment . ./build/generatedAssignment --fileEndingsModifyWhiteList .java .gradle .zip --fileEndingsBlacklist .zip
```

The --fileEndingsModifyWhiteLists specifies what types of files should be edited by the assignment converter
The --fileEndingsBlackList specifies which files should be copied over in the example we want to exclude zip files

In order to automate this,
one can add the following Code to the build.gradle:
This makes two extra tasks available prepareAssignment and zipAssignment
One has to then just call '''gradle task zipAssignment''' in the project directory to create a zip of the assignment.

The code to be added to gradle
Everything that is enclosed in //BEGIN EXCLUDE, //END EXCLUDE is not copied over to the assignment

```
// BEGIN EXCLUDE
task prepareAssignment(type:Exec) {

  //on linux
  commandLine 'java', '-jar', 'libs/AssignmentConverter-1.0-SNAPSHOT-all.jar', 'assignment','.', './build/generatedAssignment', '--fileEndingsModifyWhiteList','.java','.gradle', '.zip','--fileEndingsBlacklist', '.zip'

  doLast {
    println "Executed!"
  }
}

task zipAssignment(type:Zip){
  from './build/generatedAssignment'
  archiveBaseName = 'ex4_assignment'
  archiveVersion = null
  destinationDirectory = file('./build/generatedAssignmentZip')
  dependsOn prepareAssignment
}

// END EXCLUDE
```
