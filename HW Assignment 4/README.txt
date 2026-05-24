SE 310 - Homework 2 Part D: Survey / Test Generator
=====================================================
 
HOW TO COMPILE AND RUN:
    javac *.java
    java SurveyApp
 
SAMPLE FILES:

Sample survey files are located in the surveys/ folder inside this project.
Load them by running the program, selecting Survey from the main menu,
then selecting option 3 (Load Survey).
 
Sample test files are located in the tests/ folder inside this project.
Load them by running the program, selecting Test from the main menu,
then selecting option 4 (Load Test).
 
Each sample file contains one of each question type:
True/False, Multiple Choice, Short Answer, Essay, Date, and Matching.
 
Response files are saved in the responses/ folder after taking a survey or test.
Both a human-readable .txt version and a serialized .ser version are saved.
The .ser version is used for tabulation across multiple sittings.
 
FILE STRUCTURE:
    *.java          - all source files
    surveys/        - saved survey files (.ser)
    tests/          - saved test files (.ser)
    responses/      - saved response files (.ser and .txt)
    README.txt      - this file
 
KNOWN ISSUES:
None. All features are fully implemented and working with my understanding
of the assignment.