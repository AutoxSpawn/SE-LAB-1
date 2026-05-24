import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SurveyApp {

    static Scanner scanner = new Scanner(System.in);

    // The list of questions for the current survey
    static List<Question> questions = new ArrayList<>();

    // The name of the current survey
    static String surveyName = "";

    // The list of questions for the current test
    static List<Question> testQuestions = new ArrayList<>();

    // The name of the current test
    static String testName = "";

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("   Welcome to the Survey/Test Generator  ");
        System.out.println("==========================================");

        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = readInt("Enter your choice: ", 1, 3);

            switch (choice) {

                case 1:

                    surveyMenu();
                    break;

                case 2:

                    testMenu();
                    break;

                case 3:

                    System.out.println("Goodbye!");
                    running = false;
                    break;
            }
        }

        scanner.close();
    }

    // Main menu - choose between Survey, Test, or Quit
    static void showMainMenu() {

        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1) Survey");
        System.out.println("2) Test");
        System.out.println("3) Quit");
        System.out.println("================================");
    }

    // Survey sub-menu
    static void showSurveyMenu() {

        System.out.println("\n---------- SURVEY MENU ----------");

        // Show the current survey name so the user knows what is loaded
        if (!surveyName.isEmpty())
        {
            System.out.println("(Current survey: " + surveyName + ")");
        }
        else
        {
            System.out.println("(No survey loaded)");
        }

        System.out.println("1) Create a new Survey");
        System.out.println("2) Display an existing Survey");
        System.out.println("3) Load an existing Survey");
        System.out.println("4) Save the current Survey");
        System.out.println("5) Take the current Survey");
        System.out.println("6) Modify the current Survey");
        System.out.println("7) Tabulate a Survey");
        System.out.println("8) Return to previous menu");
        System.out.println("---------------------------------");
    }

    // Test sub-menu
    static void showTestMenu() {

        System.out.println("\n---------- TEST MENU ----------");

        // Show the current test name so the user knows what is loaded
        if (!testName.isEmpty())
        {
            System.out.println("(Current test: " + testName + ")");
        }
        else
        {
            System.out.println("(No test loaded)");
        }

        System.out.println("1)  Create a new Test");
        System.out.println("2)  Display an existing Test without correct answers");
        System.out.println("3)  Display an existing Test with correct answers");
        System.out.println("4)  Load an existing Test");
        System.out.println("5)  Save the current Test");
        System.out.println("6)  Take the current Test");
        System.out.println("7)  Modify the current Test");
        System.out.println("8)  Tabulate a Test");
        System.out.println("9)  Grade a Test");
        System.out.println("10) Return to previous menu");
        System.out.println("-------------------------------");
    }

    // Loop for the survey menu
    static void surveyMenu() {

        boolean inSurveyMenu = true;

        while (inSurveyMenu) {

            showSurveyMenu();
            int choice = readInt("Enter your choice: ", 1, 8);

            switch (choice) {

                case 1:

                    createSurvey();
                    break;
                case 2:

                    displaySurvey();
                    break;
                case 3:

                    loadSurvey();
                    break;
                case 4:

                    saveSurvey();
                    break;
                case 5:

                    takeSurvey();
                    break;
                case 6:

                    modifySurvey();
                    break;
                case 7:

                    tabulateSurvey();
                    break;
                case 8:

                    inSurveyMenu = false;
                    break;
            }
        }
    }

    // Loop for the test menu
    static void testMenu() {

        boolean inTestMenu = true;

        while (inTestMenu) {

            showTestMenu();
            int choice = readInt("Enter your choice: ", 1, 10);

            switch (choice) {

                case 1:

                    createTest();
                    break;
                case 2:

                    displayTestWithoutAnswers();
                    break;
                case 3:

                    displayTestWithAnswers();
                    break;
                case 4:

                    loadTest();
                    break;
                case 5:

                    saveTest();
                    break;
                case 6:

                    takeTest();
                    break;
                case 7:

                    modifyTest();
                    break;
                case 8:

                    tabulateTest();
                    break;
                case 9:

                    gradeTest();
                    break;
                case 10:

                    inTestMenu = false;
                    break;
            }
        }
    }

    static void displaySurvey() {

        // Instructions say to show this message if no survey is loaded
        if (surveyName.isEmpty())
        {
            System.out.println("You must have a survey loaded in order to display it.");
            return;
        }

        if (questions.isEmpty())
        {
            System.out.println("This survey has no questions yet.");
            return;
        }

        System.out.println("\n=== Survey: " + surveyName + " ===\n");

        // Loop through every question and display it numbered from 1
        for (int i = 0; i < questions.size(); i++)
        {

            // Print the number first, then let the question print itself on the same line
            System.out.print((i + 1) + ") ");
            questions.get(i).display();

            System.out.println();
        }
        System.out.println("\n=== End of Survey ===");
    }

    static void loadSurvey() {

        // Get the list of saved survey files from the surveys folder
        List<String> files = SurveyFileManager.listSurveyFiles();

        // Use shared helper to show the list and get the user's selection
        String fileName = selectFileFromList(files, "surveys");
        if (fileName == null) return;

        try {

            // Load the questions from the file
            questions = SurveyFileManager.loadSurvey(fileName);

            // Load the survey name from the file
            surveyName = SurveyFileManager.loadSurveyName(fileName);

            // Clear any stale responses so a fresh take starts clean
            for (Question q : questions) { q.clearResponses(); }

            System.out.println("Survey '" + surveyName + "' loaded successfully!");

        } catch (Exception e)
        {
            System.out.println("Error loading survey: " + e.getMessage());
        }
    }

    static void saveSurvey() {

        if (surveyName.isEmpty())
        {
            System.out.println("You must have a survey loaded in order to save it.");
            return;
        }

        try
        {
            SurveyFileManager.saveSurvey(surveyName, questions);
            System.out.println("Survey '" + surveyName + "' saved successfully!");

        } catch (Exception e)
        {
            System.out.println("Error saving survey: " + e.getMessage());
        }
    }

    static void saveTest() {

        if (testName.isEmpty())
        {
            System.out.println("You must have a test loaded in order to save it.");
            return;
        }

        try
        {
            SurveyFileManager.saveTest(testName, testQuestions);
            System.out.println("Test '" + testName + "' saved successfully!");

        } catch (Exception e)
        {
            System.out.println("Error saving test: " + e.getMessage());
        }
    }

    static void takeSurvey() {

        // Instructions say to show this message if no survey is loaded
        if (surveyName.isEmpty())
        {
            System.out.println("You must have a survey loaded in order to take it.");
            return;
        }

        if (questions.isEmpty())
        {
            System.out.println("This survey has no questions to answer.");
            return;
        }

        System.out.println("\n=== Taking Survey: " + surveyName + " ===\n");

        // Go through every question one by one and ask the user to answer it
        for (int i = 0; i < questions.size(); i++)
        {
            System.out.println("Question " + (i + 1) + ":");

            questions.get(i).takeQuestion(scanner);
            System.out.println();
        }

        System.out.println("Survey complete! Thank you for taking the survey.");

        // Automatically save responses to a file after the survey is done
        try {
            SurveyFileManager.saveResponses(surveyName, questions);

        } catch (Exception e)
        {
            System.out.println("Error saving responses: " + e.getMessage());
        }
    }

    static void takeTest() {

        // Instructions say to show this message if no test is loaded
        if (testName.isEmpty())
        {
            System.out.println("You must have a test loaded in order to take it.");
            return;
        }

        if (testQuestions.isEmpty())
        {
            System.out.println("This test has no questions to answer.");
            return;
        }

        System.out.println("\n=== Taking Test: " + testName + " ===\n");

        // Go through every question one by one and ask the user to answer it
        for (int i = 0; i < testQuestions.size(); i++)
        {
            System.out.println("Question " + (i + 1) + ":");

            testQuestions.get(i).takeQuestion(scanner);
            System.out.println();
        }

        System.out.println("Test complete! Thank you for taking the test.");

        // Automatically save responses to a file after the test is done
        try {
            SurveyFileManager.saveResponses(testName, testQuestions);

        } catch (Exception e)
        {
            System.out.println("Error saving responses: " + e.getMessage());
        }
    }

    static void gradeTest()
    {
        // Step 1 - pick a test to grade
        List<String> testFiles = SurveyFileManager.listTestFiles();

        if (testFiles.isEmpty())
        {
            System.out.println("No saved tests found. Create and save a test first.");
            return;
        }

        System.out.println("\nSelect an existing test to grade:");

        for (int i = 0; i < testFiles.size(); i++)
        {
            System.out.println((i + 1) + ") " + testFiles.get(i));
        }

        int testChoice = readInt("Enter your choice: ", 1, testFiles.size());
        String testFileName = testFiles.get(testChoice - 1);

        // Load the test questions so we have the correct answers
        List<Question> gradingQuestions;

        try
        {
            gradingQuestions = SurveyFileManager.loadTest(testFileName);

        } catch (Exception e)
        {
            System.out.println("Error loading test: " + e.getMessage());
            return;
        }

        // Get the test name from the filename (strip .ser extension)
        String gradingTestName = testFileName.replace(".ser", "");

        // Step 2 - pick a response file to grade
        List<String> responseFiles = SurveyFileManager.listResponseFiles(gradingTestName);

        if (responseFiles.isEmpty())
        {
            System.out.println("No saved responses found for this test. Take the test first.");
            return;
        }

        System.out.println("\nSelect an existing response set:");

        for (int i = 0; i < responseFiles.size(); i++)
        {
            // Show a friendly name like "Test 1 - Response 1"
            System.out.println((i + 1) + ") " + gradingTestName + " - Response " + (i + 1));
        }

        int responseChoice = readInt("Enter your choice: ", 1, responseFiles.size());
        String responseFileName = responseFiles.get(responseChoice - 1);

        // Step 3 - load the responses
        List<List<String>> allResponses;

        try
        {
            allResponses = SurveyFileManager.loadResponseFile(responseFileName);

        } catch (Exception e)
        {
            System.out.println("Error loading responses: " + e.getMessage());
            return;
        }

        // Step 4 - compute the grade
        int totalQuestions   = gradingQuestions.size();
        int essayCount       = 0;
        int gradableCount    = 0;
        int correctCount     = 0;

        for (int i = 0; i < gradingQuestions.size() && i < allResponses.size(); i++)
        {
            Question q = gradingQuestions.get(i);
            List<String> responses = allResponses.get(i);

            // Essays cannot be auto-graded - exclude from gradable points
            if (q instanceof EssayQuestion)
            {
                essayCount++;
                continue;
            }

            gradableCount++;

            // Matching questions store multiple responses - compare all pairs against correct answer
            if (q instanceof MatchingQuestion)
            {
                // Correct answer stored as "A 1,B 2,C 3" - split into individual pairs
                String[] correctPairs = q.getCorrectAnswer().split(",");
                boolean allCorrect = true;

                if (responses.size() != correctPairs.length)
                {
                    allCorrect = false;
                }
                else
                {
                    for (int j = 0; j < correctPairs.length; j++)
                    {
                        if (!responses.get(j).trim().equalsIgnoreCase(correctPairs[j].trim()))
                        {
                            allCorrect = false;
                            break;
                        }
                    }
                }

                if (allCorrect) correctCount++;
            }
            // All other question types - compare first response to correct answer
            else if (!responses.isEmpty() && responses.get(0).equalsIgnoreCase(q.getCorrectAnswer()))
            {
                correctCount++;
            }
        }

        // Step 5 - display the grade
        // Each question has equal weight out of 100 points
        double pointsPerQuestion = 100.0 / totalQuestions;
        double gradablePoints    = pointsPerQuestion * gradableCount;
        double score             = (gradableCount > 0)
                ? (correctCount / (double) gradableCount) * gradablePoints
                : 0;

        int roundedScore        = (int) Math.round(score);
        int roundedGradable     = (int) Math.round(gradablePoints);

        System.out.println();

        if (essayCount == 0)
        {
            // No essays - all questions auto-gradable
            System.out.println("You received a " + roundedScore + " on the test.");

        } else
        {
            // Essays present - explain the reduction
            String essayWord = essayCount == 1 ? "essay question" : "essay questions";

            System.out.println("You received a " + roundedScore + " on the test. " +
                    "The test was worth 100 points, but only " + roundedGradable +
                    " of those points could be auto-graded because there " +
                    (essayCount == 1 ? "was" : "were") + " " + essayCount + " " + essayWord + ".");
        }
    }

    static void tabulateSurvey()
    {
        if (surveyName.isEmpty())
        {
            System.out.println("You must have a survey loaded in order to tabulate it.");
            return;
        }

        if (questions.isEmpty())
        {
            System.out.println("This survey has no questions to tabulate.");
            return;
        }

        // Load and merge all saved response files so we tabulate across all sittings
        SurveyFileManager.loadAndMergeResponses(surveyName, questions);

        System.out.println("\n=== Tabulation: " + surveyName + " ===\n");
        tabulateQuestions(questions);
        System.out.println("=== End of Tabulation ===");
    }

    static void tabulateTest()
    {
        if (testName.isEmpty())
        {
            System.out.println("You must have a test loaded in order to tabulate it.");
            return;
        }

        if (testQuestions.isEmpty())
        {
            System.out.println("This test has no questions to tabulate.");
            return;
        }

        // Load and merge all saved response files so we tabulate across all sittings
        SurveyFileManager.loadAndMergeResponses(testName, testQuestions);

        System.out.println("\n=== Tabulation: " + testName + " ===\n");
        tabulateQuestions(testQuestions);
        System.out.println("=== End of Tabulation ===");
    }

    // Shared helper - loops through questions and calls tabulate() on each one
    static void tabulateQuestions(List<Question> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            list.get(i).tabulate();
            System.out.println();
        }
    }

    static void modifySurvey() {

        // Instructions say to show this message if no survey is loaded
        if (surveyName.isEmpty())
        {
            System.out.println("You must have a survey loaded in order to modify it.");
            return;
        }

        if (questions.isEmpty())
        {
            System.out.println("This survey has no questions to modify.");
            return;
        }

        System.out.println("\n=== Survey: " + surveyName + " ===\n");

        for (int i = 0; i < questions.size(); i++)
        {
            System.out.print((i + 1) + ") ");
            questions.get(i).display();

            System.out.println();
        }

        // Ask which question they want to modify
        int questionNum = readInt("What question do you wish to modify? (enter the number): ", 1, questions.size());

        Question q = questions.get(questionNum - 1);

        System.out.println("\nModifying question " + questionNum + "...");

        // Each question type knows how to modify itself
        q.modify(scanner);

        System.out.println("Question " + questionNum + " updated successfully!");
    }

    static void modifyTest() {

        // Instructions say to show this message if no test is loaded
        if (testName.isEmpty())
        {
            System.out.println("You must have a test loaded in order to modify it.");
            return;
        }

        if (testQuestions.isEmpty())
        {
            System.out.println("This test has no questions to modify.");
            return;
        }

        System.out.println("\n=== Test: " + testName + " ===\n");

        for (int i = 0; i < testQuestions.size(); i++)
        {
            System.out.print((i + 1) + ") ");
            testQuestions.get(i).display();

            System.out.println();
        }

        // Ask which question they want to modify
        int questionNum = readInt("What question do you wish to modify? (enter the number): ", 1, testQuestions.size());

        Question q = testQuestions.get(questionNum - 1);

        System.out.println("\nModifying question " + questionNum + "...");

        // Reuse each question type's existing modify() method for prompt and choices
        q.modify(scanner);

        // After the standard modify, also offer to update the correct answer
        modifyCorrectAnswer(q);

        System.out.println("Question " + questionNum + " updated successfully!");
    }

    // Asks the user if they want to update the correct answer for a test question
    // Each question type has its own validation for what a valid correct answer looks like
    static void modifyCorrectAnswer(Question q)
    {
        System.out.print("Do you wish to modify the correct answer? (yes/no): ");

        if (!scanner.nextLine().trim().equalsIgnoreCase("yes"))
        {
            return;
        }

        System.out.println("Current correct answer: " + q.getCorrectAnswer());

        // T/F - must be T or F
        if (q instanceof TrueFalseQuestion)
        {
            String correct = "";

            while (!correct.equalsIgnoreCase("T") && !correct.equalsIgnoreCase("F"))
            {
                System.out.print("Enter new correct answer (T/F): ");
                correct = scanner.nextLine().trim();

                if (!correct.equalsIgnoreCase("T") && !correct.equalsIgnoreCase("F"))
                {
                    System.out.println("Invalid input. Please enter T or F.");
                }
            }

            q.setCorrectAnswer(correct.toUpperCase());
        }
        // Multiple choice - must be a valid letter in range
        else if (q instanceof MultipleChoiceQuestion)
        {
            MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) q;
            String correct = "";
            boolean valid = false;

            while (!valid)
            {
                System.out.print("Enter new correct choice (enter a number): ");
                String input = scanner.nextLine().trim();

                try
                {
                    int num = Integer.parseInt(input);

                    if (num >= 1 && num <= mcq.getChoices().size())
                    {
                        // Convert number to letter for storage
                        correct = String.valueOf((char)('A' + num - 1));
                        valid = true;
                    }
                    else
                    {
                        System.out.println("Invalid. Please enter a number between 1 and " + mcq.getChoices().size() + ".");
                    }

                } catch (NumberFormatException e)
                {
                    System.out.println("Invalid. Please enter a number.");
                }
            }

            q.setCorrectAnswer(correct);
        }
        // Short answer - cannot be blank
        else if (q instanceof ShortAnswerQuestion)
        {
            String correct = "";

            while (correct.isEmpty())
            {
                System.out.print("Enter new correct answer: ");
                correct = scanner.nextLine().trim();

                if (correct.isEmpty())
                {
                    System.out.println("Correct answer cannot be blank.");
                }
            }

            q.setCorrectAnswer(correct);
        }
        // Essay - manual grading, no change needed
        else if (q instanceof EssayQuestion)
        {
            System.out.println("Essay questions require manual grading and cannot have a correct answer set.");
        }
        // Date - must be valid YYYY-MM-DD format
        else if (q instanceof DateQuestion)
        {
            String correct = "";
            boolean valid = false;

            while (!valid)
            {
                System.out.print("Enter new correct date (YYYY-MM-DD): ");
                correct = scanner.nextLine().trim();

                try
                {
                    java.time.LocalDate.parse(correct, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    valid = true;

                } catch (java.time.format.DateTimeParseException e)
                {
                    System.out.println("Invalid date. Use format YYYY-MM-DD (e.g. 2024-05-15).");
                }
            }

            q.setCorrectAnswer(correct);
        }
        // Matching - re-enter all correct pairs
        else if (q instanceof MatchingQuestion)
        {
            MatchingQuestion mq = (MatchingQuestion) q;
            int numPairs = mq.getLeftItems().size();

            System.out.println("Enter the new correct matches:");
            mq.display();

            StringBuilder correctAnswer = new StringBuilder();

            for (int i = 0; i < numPairs; i++)
            {
                char leftLabel = (char)('A' + i);
                boolean valid = false;

                while (!valid)
                {
                    System.out.print("Correct match for " + leftLabel + " (enter a number): ");
                    String input = scanner.nextLine().trim();

                    try
                    {
                        int rightNum = Integer.parseInt(input);

                        if (rightNum >= 1 && rightNum <= numPairs)
                        {
                            if (correctAnswer.length() > 0)
                            {
                                correctAnswer.append(",");
                            }

                            correctAnswer.append(leftLabel).append(" ").append(rightNum);
                            valid = true;

                        } else
                        {
                            System.out.println("Invalid. Enter a number between 1 and " + numPairs + ".");
                        }

                    } catch (NumberFormatException e)
                    {
                        System.out.println("Invalid. Please enter a number.");
                    }
                }
            }

            q.setCorrectAnswer(correctAnswer.toString());
        }

        System.out.println("Correct answer updated.");
    }

    static void createSurvey() {

        System.out.print("Enter a name for your survey: ");
        surveyName = scanner.nextLine().trim();

        // Make sure the name is not blank
        if (surveyName.isEmpty())
        {
            System.out.println("Survey name cannot be blank.");
            surveyName = "";
            return;
        }

        questions = new ArrayList<>();
        System.out.println("\nStarting survey '" + surveyName + "'. Add your questions below.");

        // Shared helper handles the add-question loop for both surveys and tests
        addQuestionsLoop(questions);

        System.out.println("\nSurvey created with " + questions.size() + " question(s).");
    }

    // Create a new test - same flow as survey but uses testQuestions list
    static void createTest() {

        System.out.print("Enter a name for your test: ");
        testName = scanner.nextLine().trim();

        // Make sure the name is not blank
        if (testName.isEmpty())
        {
            System.out.println("Test name cannot be blank.");
            testName = "";
            return;
        }

        testQuestions = new ArrayList<>();
        System.out.println("\nStarting test '" + testName + "'. Add your questions below.");

        // Test loop asks for correct answer after each question
        addQuestionsLoopForTest(testQuestions);

        System.out.println("\nTest created with " + testQuestions.size() + " question(s).");
    }

    // Display the test questions without showing correct answers
    static void displayTestWithoutAnswers() {

        // Instructions say to show this message if no test is loaded
        if (testName.isEmpty())
        {
            System.out.println("You must have a test loaded in order to display it.");
            return;
        }

        if (testQuestions.isEmpty())
        {
            System.out.println("This test has no questions yet.");
            return;
        }

        System.out.println("\n=== Test: " + testName + " ===\n");

        // Loop through every question and display it numbered from 1
        for (int i = 0; i < testQuestions.size(); i++)
        {
            System.out.print((i + 1) + ") ");
            testQuestions.get(i).display();

            System.out.println();
        }

        System.out.println("\n=== End of Test ===");
    }

    // Shared loop for adding questions - used by both createSurvey and createTest
    static void addQuestionsLoop(List<Question> list)
    {
        boolean addingQuestions = true;

        while (addingQuestions)
        {
            showAddQuestionMenu();
            int choice = readInt("Enter your choice: ", 1, 7);

            switch (choice) {
                case 1:

                    addTrueFalseQuestion(list);
                    break;
                case 2:

                    addMultipleChoiceQuestion(list);
                    break;
                case 3:

                    addShortAnswerQuestion(list);
                    break;
                case 4:

                    addEssayQuestion(list);
                    break;
                case 5:

                    addDateQuestion(list);
                    break;
                case 6:

                    addMatchingQuestion(list);
                    break;
                case 7:

                    addingQuestions = false;
                    break;
            }
        }
    }

    // Add-question loop for tests - same as survey loop but calls test versions that ask for correct answer
    static void addQuestionsLoopForTest(List<Question> list)
    {
        boolean addingQuestions = true;

        while (addingQuestions)
        {
            showAddQuestionMenu();
            int choice = readInt("Enter your choice: ", 1, 7);

            switch (choice) {
                case 1:

                    addTrueFalseQuestionForTest(list);
                    break;
                case 2:

                    addMultipleChoiceQuestionForTest(list);
                    break;
                case 3:

                    addShortAnswerQuestionForTest(list);
                    break;
                case 4:

                    addEssayQuestionForTest(list);
                    break;
                case 5:

                    addDateQuestionForTest(list);
                    break;
                case 6:

                    addMatchingQuestionForTest(list);
                    break;
                case 7:

                    addingQuestions = false;
                    break;
            }
        }
    }

    // T/F for test - builds the question using the survey method then asks for correct answer
    static void addTrueFalseQuestionForTest(List<Question> list)
    {
        // Reuse survey method to build and add the question
        addTrueFalseQuestion(list);

        // Grab the question that was just added
        TrueFalseQuestion q = (TrueFalseQuestion) list.get(list.size() - 1);

        // Ask for the correct answer and validate it must be T or F
        String correct = "";

        while (!correct.equalsIgnoreCase("T") && !correct.equalsIgnoreCase("F"))
        {
            System.out.print("Enter the correct answer (T/F): ");
            correct = scanner.nextLine().trim();

            if (!correct.equalsIgnoreCase("T") && !correct.equalsIgnoreCase("F"))
            {
                System.out.println("Invalid input. Please enter T or F.");
            }
        }

        q.setCorrectAnswer(correct.toUpperCase());
    }

    // Multiple choice for test - builds the question using the survey method then asks for correct answer
    static void addMultipleChoiceQuestionForTest(List<Question> list)
    {
        // Reuse survey method to build and add the question
        addMultipleChoiceQuestion(list);

        // Grab the question that was just added
        MultipleChoiceQuestion q = (MultipleChoiceQuestion) list.get(list.size() - 1);

        // Ask for correct answer - user enters the number of the choice (1, 2, 3...)
        // then we convert it to the letter (A, B, C...) for storage
        String correct = "";
        boolean valid = false;

        while (!valid)
        {
            System.out.print("Enter the correct choice (enter a number): ");
            String input = scanner.nextLine().trim();

            try
            {
                int num = Integer.parseInt(input);

                if (num >= 1 && num <= q.getChoices().size())
                {
                    // Convert the number to the matching letter (1 -> A, 2 -> B, etc.)
                    correct = String.valueOf((char)('A' + num - 1));
                    valid = true;
                }
                else
                {
                    System.out.println("Invalid. Please enter a number between 1 and " + q.getChoices().size() + ".");
                }

            } catch (NumberFormatException e)
            {
                System.out.println("Invalid. Please enter a number.");
            }
        }

        q.setCorrectAnswer(correct);
    }

    // Short answer for test - builds the question using the survey method then asks for correct answer
    static void addShortAnswerQuestionForTest(List<Question> list)
    {
        // Reuse survey method to build and add the question
        addShortAnswerQuestion(list);

        // Grab the question that was just added
        ShortAnswerQuestion q = (ShortAnswerQuestion) list.get(list.size() - 1);

        // Ask for the correct answer
        String correct = "";

        while (correct.isEmpty())
        {
            System.out.print("Enter the correct answer: ");
            correct = scanner.nextLine().trim();

            if (correct.isEmpty())
            {
                System.out.println("Correct answer cannot be blank.");
            }
        }

        q.setCorrectAnswer(correct);
    }

    // Essay for test - builds the question using the survey method then marks it as manual grading
    static void addEssayQuestionForTest(List<Question> list)
    {
        // Reuse survey method to build and add the question
        addEssayQuestion(list);

        // Grab the question that was just added
        EssayQuestion q = (EssayQuestion) list.get(list.size() - 1);

        // Essay questions cannot be auto-graded so we note that
        q.setCorrectAnswer("[Essay - requires manual grading]");
    }

    // Date for test - builds the question using the survey method then asks for correct answer
    static void addDateQuestionForTest(List<Question> list)
    {
        // Reuse survey method to build and add the question
        addDateQuestion(list);

        // Grab the question that was just added
        DateQuestion q = (DateQuestion) list.get(list.size() - 1);

        // Ask for the correct date and validate the format
        String correct = "";
        boolean valid = false;

        while (!valid)
        {
            System.out.print("Enter the correct date (YYYY-MM-DD): ");
            correct = scanner.nextLine().trim();

            try
            {
                java.time.LocalDate.parse(correct, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                valid = true;

            } catch (java.time.format.DateTimeParseException e)
            {
                System.out.println("Invalid date. Use format YYYY-MM-DD (e.g. 2024-05-15).");
            }
        }

        q.setCorrectAnswer(correct);
    }

    // Matching for test - builds the question using the survey method then asks for correct matches
    static void addMatchingQuestionForTest(List<Question> list)
    {
        // Reuse survey method to build and add the question
        addMatchingQuestion(list);

        // Grab the question that was just added
        MatchingQuestion q = (MatchingQuestion) list.get(list.size() - 1);

        // Display the pairs and ask for correct matches
        System.out.println("Enter the correct matches:");
        q.display();

        StringBuilder correctAnswer = new StringBuilder();
        int numPairs = q.getLeftItems().size();

        for (int i = 0; i < numPairs; i++)
        {
            char leftLabel = (char)('A' + i);
            boolean valid = false;

            while (!valid)
            {
                System.out.print("Correct match for " + leftLabel + " (enter a number): ");
                String input = scanner.nextLine().trim();

                try
                {
                    int rightNum = Integer.parseInt(input);

                    if (rightNum >= 1 && rightNum <= numPairs)
                    {
                        // Store as "A 1,B 2,C 3" format
                        if (correctAnswer.length() > 0)
                        {
                            correctAnswer.append(",");
                        }

                        correctAnswer.append(leftLabel).append(" ").append(rightNum);
                        valid = true;

                    } else
                    {
                        System.out.println("Invalid. Enter a number between 1 and " + numPairs + ".");
                    }

                } catch (NumberFormatException e)
                {
                    System.out.println("Invalid. Please enter a number.");
                }
            }
        }

        q.setCorrectAnswer(correctAnswer.toString());
    }

    // Shared helper - shows a numbered list of files and returns the selected filename
    // Returns null if no files exist
    static String selectFileFromList(List<String> files, String type)
    {
        if (files.isEmpty())
        {
            System.out.println("No saved " + type + " found. Create and save one first.");
            return null;
        }

        // Show the user all available files to pick from
        System.out.println("\nPlease select a file to load:");

        for (int i = 0; i < files.size(); i++)
        {
            System.out.println((i + 1) + ") " + files.get(i));
        }

        // Let the user pick a file by number
        int choice = readInt("Enter the number of the " + type.substring(0, type.length() - 1) + " to load: ", 1, files.size());
        return files.get(choice - 1);
    }

    static void loadTest() {

        // Get the list of saved test files from the tests folder
        List<String> files = SurveyFileManager.listTestFiles();

        // Use shared helper to show the list and get the user's selection
        String fileName = selectFileFromList(files, "tests");
        if (fileName == null) return;

        try {

            // Load the questions from the file (correct answers come along automatically)
            testQuestions = SurveyFileManager.loadTest(fileName);

            // Load the test name from the file
            testName = SurveyFileManager.loadTestName(fileName);

            // Clear any stale responses so a fresh take starts clean
            for (Question q : testQuestions) { q.clearResponses(); }

            System.out.println("Test '" + testName + "' loaded successfully!");

        } catch (Exception e)
        {
            System.out.println("Error loading test: " + e.getMessage());
        }
    }

    // Display the test questions with the correct answer shown under each one
    static void displayTestWithAnswers()
    {
        // Instructions say to show this message if no test is loaded
        if (testName.isEmpty())
        {
            System.out.println("You must have a test loaded in order to display it.");
            return;
        }

        if (testQuestions.isEmpty())
        {
            System.out.println("This test has no questions yet.");
            return;
        }

        System.out.println("\n=== Test: " + testName + " (with correct answers) ===\n");

        // Loop through every question and display it with its correct answer
        for (int i = 0; i < testQuestions.size(); i++)
        {
            System.out.print((i + 1) + ") ");
            testQuestions.get(i).displayWithAnswer();

            System.out.println();
        }

        System.out.println("\n=== End of Test ===");
    }

    static void showAddQuestionMenu()
    {
        System.out.println("\n--- ADD A QUESTION ---");
        System.out.println("1) Add a new T/F question");
        System.out.println("2) Add a new multiple-choice question");
        System.out.println("3) Add a new short answer question");
        System.out.println("4) Add a new essay question");
        System.out.println("5) Add a new date question");
        System.out.println("6) Add a new matching question");
        System.out.println("7) Return to previous menu");
        System.out.println("----------------------");
    }

    // Each add method builds the question and adds it to the list - used by surveys and as base for tests
    static void addTrueFalseQuestion(List<Question> list)
    {
        System.out.print("Enter the prompt for your True/False question: ");
        String prompt = scanner.nextLine().trim();

        list.add(new TrueFalseQuestion(prompt));

        System.out.println("T/F question added! (Total questions: " + list.size() + ")");
    }

    static void addMultipleChoiceQuestion(List<Question> list)
    {
        System.out.print("Enter the prompt for your multiple-choice question: ");
        String prompt = scanner.nextLine().trim();

        int numChoices = readInt("Enter the number of choices for your multiple-choice question: ", 2, 26);
        List<String> choices = new ArrayList<>();

        for (int i = 0; i < numChoices; i++)
        {
            System.out.print("Enter choice #" + (i + 1) + ": ");
            choices.add(scanner.nextLine().trim());
        }

        int numResponses = readInt("How many choices can the user select? (1 = single, 2+ = multiple): ", 1, numChoices);

        list.add(new MultipleChoiceQuestion(prompt, choices, numResponses));

        System.out.println("Multiple-choice question added! (Total questions: " + list.size() + ")");
    }

    static void addShortAnswerQuestion(List<Question> list)
    {
        System.out.print("Enter the prompt for your short answer question: ");
        String prompt = scanner.nextLine().trim();

        int numResponses = readInt("How many responses? (usually 1): ", 1, 10);

        list.add(new ShortAnswerQuestion(prompt, numResponses));

        System.out.println("Short answer question added! (Total questions: " + list.size() + ")");
    }

    static void addEssayQuestion(List<Question> list)
    {
        System.out.print("Enter the prompt for your essay question: ");
        String prompt = scanner.nextLine().trim();

        int numResponses = readInt("How many responses/paragraphs? (usually 1): ", 1, 10);

        list.add(new EssayQuestion(prompt, numResponses));

        System.out.println("Essay question added! (Total questions: " + list.size() + ")");
    }

    static void addDateQuestion(List<Question> list)
    {
        System.out.print("Enter the prompt for your date question: ");
        String prompt = scanner.nextLine().trim();

        int numResponses = readInt("How many date responses? (usually 1): ", 1, 10);

        list.add(new DateQuestion(prompt, numResponses));

        System.out.println("Date question added! (Total questions: " + list.size() + ")");
    }

    static void addMatchingQuestion(List<Question> list)
    {
        System.out.print("Enter the prompt for your matching question: ");
        String prompt = scanner.nextLine().trim();

        int numPairs = readInt("How many pairs to match?: ", 2, 26);

        List<String> leftItems  = new ArrayList<>();
        List<String> rightItems = new ArrayList<>();

        System.out.println("Enter the LEFT column items:");

        for (int i = 0; i < numPairs; i++)
        {
            System.out.print("  Left item " + (char)('A' + i) + ": ");
            leftItems.add(scanner.nextLine().trim());
        }

        System.out.println("Enter the RIGHT column items:");

        for (int i = 0; i < numPairs; i++)
        {
            System.out.print("  Right item " + (i + 1) + ": ");
            rightItems.add(scanner.nextLine().trim());
        }

        list.add(new MatchingQuestion(prompt, leftItems, rightItems));
        System.out.println("Matching question added! (Total questions: " + list.size() + ")");
    }

    //helps to see if you input something wrong so it doesn't crash if you input something that's not suppose to be inputted.
    static int readInt(String prompt, int min, int max)
    {
        while (true)
        {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max)
                {
                    return value;
                }
                else
                {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }

            } catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}