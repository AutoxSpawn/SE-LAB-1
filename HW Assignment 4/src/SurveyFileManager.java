import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyFileManager {

    // Folder where all of the survey files are being saved (relative path)
    private static final String SURVEYS_DIRECTORY = "surveys";

    // Folder where all of the test files are being saved (relative path)
    private static final String TESTS_DIRECTORY = "tests";

    // Folder where serialized response files are saved
    private static final String RESPONSES_DIRECTORY = "responses";

    // All survey and test files end with this extension
    private static final String EXTENSION = ".ser";

    // ==================== SURVEY METHODS ====================

    public static void saveSurvey(String surveyName, List<Question> questions) throws IOException {

        // Delegate to shared helper using the surveys directory
        saveToDirectory(SURVEYS_DIRECTORY, surveyName, questions);

        System.out.println("Survey saved to: " + SURVEYS_DIRECTORY + File.separator + surveyName + EXTENSION);
    }

    public static List<Question> loadSurvey(String fileName) throws IOException, ClassNotFoundException {

        // Delegate to shared helper using the surveys directory
        return loadFromDirectory(SURVEYS_DIRECTORY, fileName);
    }

    public static String loadSurveyName(String fileName) throws IOException, ClassNotFoundException {

        // Delegate to shared helper using the surveys directory
        return loadNameFromDirectory(SURVEYS_DIRECTORY, fileName);
    }

    public static List<String> listSurveyFiles() {

        // Delegate to shared helper using the surveys directory
        return listFilesInDirectory(SURVEYS_DIRECTORY);
    }

    // ==================== TEST METHODS ====================

    public static void saveTest(String testName, List<Question> questions) throws IOException {

        // Delegate to shared helper using the tests directory
        // correctAnswer is a field on each Question so it is saved automatically
        saveToDirectory(TESTS_DIRECTORY, testName, questions);

        System.out.println("Test saved to: " + TESTS_DIRECTORY + File.separator + testName + EXTENSION);
    }

    public static List<Question> loadTest(String fileName) throws IOException, ClassNotFoundException {

        // Delegate to shared helper using the tests directory
        return loadFromDirectory(TESTS_DIRECTORY, fileName);
    }

    public static String loadTestName(String fileName) throws IOException, ClassNotFoundException {

        // Delegate to shared helper using the tests directory
        return loadNameFromDirectory(TESTS_DIRECTORY, fileName);
    }

    public static List<String> listTestFiles() {

        // Delegate to shared helper using the tests directory
        return listFilesInDirectory(TESTS_DIRECTORY);
    }

    // ==================== SHARED PRIVATE HELPERS ====================

    // Saves a name and question list to a .ser file in the given directory
    private static void saveToDirectory(String directory, String name, List<Question> questions) throws IOException {

        // Create the folder if it doesn't exist yet
        File dir = new File(directory);

        if (!dir.exists() && !dir.mkdirs())
        {
            throw new IOException("Failed to create directory: " + directory);
        }

        // Build the file path using File.separator
        String filePath = directory + File.separator + name + EXTENSION;

        // ObjectOutputStream serializes the entire question list into a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {

            // save the name
            oos.writeObject(name);

            // save all of the questions
            oos.writeObject(questions);
        }
    }

    // Loads and returns the question list from a .ser file in the given directory
    @SuppressWarnings("unchecked")
    private static List<Question> loadFromDirectory(String directory, String fileName) throws IOException, ClassNotFoundException {

        String filePath = directory + File.separator + fileName;

        // ObjectInputStream reads the serialized data back into Java objects
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {

            // read the name
            String name = (String) ois.readObject();

            // read the questions
            return (List<Question>) ois.readObject();
        }
    }

    // Loads and returns just the name from a .ser file in the given directory
    private static String loadNameFromDirectory(String directory, String fileName) throws IOException, ClassNotFoundException {

        String filePath = directory + File.separator + fileName;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {

            // just read the name, ignore the rest
            return (String) ois.readObject();
        }
    }

    // Returns a list of all .ser filenames found in the given directory
    private static List<String> listFilesInDirectory(String directory) {

        List<String> files = new ArrayList<>();
        File dir = new File(directory);

        // If the folder doesn't exist yet, return empty list
        if (!dir.exists() || !dir.isDirectory())
        {
            return files;
        }

        // Only include files that end in .ser
        File[] found = dir.listFiles((d, name) -> name.endsWith(EXTENSION));

        if (found != null)
        {
            for (File f : found)
            {
                files.add(f.getName());
            }
        }

        return files;
    }

    // Returns a list of all serialized response filenames for a given test/survey name
    public static List<String> listResponseFiles(String name) {

        List<String> files = new ArrayList<>();
        File dir = new File(RESPONSES_DIRECTORY);

        if (!dir.exists() || !dir.isDirectory())
        {
            return files;
        }

        // Only include .ser response files matching this name
        File[] found = dir.listFiles((d, fname) ->
                fname.startsWith(name + "_responses_") && fname.endsWith(EXTENSION));

        if (found != null)
        {
            for (File f : found)
            {
                files.add(f.getName());
            }
        }

        return files;
    }

    // Loads a single response file and returns the list of response lists (one per question)
    @SuppressWarnings("unchecked")
    public static List<List<String>> loadResponseFile(String fileName) throws IOException, ClassNotFoundException {

        String filePath = RESPONSES_DIRECTORY + File.separator + fileName;

        List<List<String>> allResponses = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath)))
        {
            int numQuestions = ois.readInt();

            for (int i = 0; i < numQuestions; i++)
            {
                allResponses.add((List<String>) ois.readObject());
            }
        }

        return allResponses;
    }

    // ==================== RESPONSE SAVING ====================

    public static void saveResponses(String name, List<Question> questions) throws IOException {

        // Create a responses folder if it doesn't exist yet
        File dir = new File(RESPONSES_DIRECTORY);

        if (!dir.exists() && !dir.mkdirs())
        {
            throw new IOException("Failed to create directory: " + RESPONSES_DIRECTORY);
        }

        // Use current time as part of filename so every response file is unique
        String timestamp = String.valueOf(System.currentTimeMillis());

        // Save a human-readable .txt version for viewing
        saveResponsesTxt(name, questions, timestamp);

        // Also save a serialized .ser version for tabulation across multiple sittings
        saveResponsesSer(name, questions, timestamp);
    }

    // Saves a human-readable text version of the responses
    private static void saveResponsesTxt(String name, List<Question> questions, String timestamp) throws IOException {

        String filePath = RESPONSES_DIRECTORY + File.separator + name + "_responses_" + timestamp + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writer.println("Survey: " + name);
            writer.println("=".repeat(40));
            writer.println();

            for (int i = 0; i < questions.size(); i++)
            {
                Question q = questions.get(i);

                // Write the question number and prompt
                writer.println((i + 1) + ") " + q.getPrompt());

                // Write each response the user gave
                if (q.getResponses().isEmpty())
                {
                    writer.println("   [No response]");

                }
                else if (q instanceof MatchingQuestion)
                {
                    //For matching questions show the actual items not just "A 1"
                    MatchingQuestion mq = (MatchingQuestion) q;

                    for (String response : q.getResponses())
                    {
                        //response is like "A 1" - parse out the letter and number
                        String[] parts = response.split(" ");
                        int leftIndex  = parts[0].charAt(0) - 'A';
                        int rightIndex = Integer.parseInt(parts[1]) - 1;

                        String leftItem  = mq.getLeftItems().get(leftIndex);
                        String rightItem = mq.getRightItems().get(rightIndex);

                        writer.println("   " + leftItem + " -> " + rightItem);
                    }
                }
                else if (q instanceof MultipleChoiceQuestion)
                {
                    // For multiple choice show the actual choice text not just the letter
                    MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) q;

                    for (String response : q.getResponses())
                    {
                        int index = response.charAt(0) - 'A';
                        String choiceText = mcq.getChoices().get(index);
                        writer.println("   -> " + response + ") " + choiceText);
                    }
                }
                else
                {
                    for (String response : q.getResponses())
                    {
                        writer.println("   -> " + response);
                    }
                }
                writer.println();
            }
        }
        System.out.println("Responses saved to: " + filePath);
    }

    // Saves a serialized version of just the response lists so tabulation can reload them
    private static void saveResponsesSer(String name, List<Question> questions, String timestamp) throws IOException {

        String filePath = RESPONSES_DIRECTORY + File.separator + name + "_responses_" + timestamp + EXTENSION;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {

            // Save the number of questions so we know how many response lists to read back
            oos.writeInt(questions.size());

            // Save each question's response list
            for (Question q : questions)
            {
                oos.writeObject(q.getResponses());
            }
        }
    }

    // Loads all serialized response files for a given survey/test name and merges them
    // into the question objects so tabulation sees all responses across all sittings
    @SuppressWarnings("unchecked")
    public static void loadAndMergeResponses(String name, List<Question> questions) {

        File dir = new File(RESPONSES_DIRECTORY);

        if (!dir.exists() || !dir.isDirectory())
        {
            return;
        }

        // Find all .ser response files for this survey/test name
        File[] found = dir.listFiles((d, fname) ->
                fname.startsWith(name + "_responses_") && fname.endsWith(EXTENSION));

        if (found == null || found.length == 0)
        {
            return;
        }

        // Clear current responses before merging all saved ones
        for (Question q : questions)
        {
            q.clearResponses();
        }

        // Load each response file and add its responses to the matching question
        for (File file : found)
        {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
            {
                int numQuestions = ois.readInt();

                for (int i = 0; i < numQuestions && i < questions.size(); i++)
                {
                    List<String> savedResponses = (List<String>) ois.readObject();
                    questions.get(i).getResponses().addAll(savedResponses);
                }

            } catch (Exception e)
            {
                System.out.println("Warning: could not load response file " + file.getName());
            }
        }
    }
}