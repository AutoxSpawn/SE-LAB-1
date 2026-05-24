import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultipleChoiceQuestion extends Question {

    // Required for serialization aspect of the assignment - keeps saved files compatible with this class
    private static final long serialVersionUID = 1L;

    // The answer choices
    private List<String> choices;

    public MultipleChoiceQuestion(String prompt, List<String> choices, int numResponses)
    {
        super(prompt, numResponses);
        this.choices = new ArrayList<>(choices);
    }

    @Override
    public void display() {

        System.out.println(prompt);

        for (int i = 0; i < choices.size(); i++)
        {
            // We are basically converting the loop number 1, 2,3  into a letter a, b, c. Making it so labeling the questions are automatic instead of you having to put everything in.
            char letter = (char) ('A' + i);
            System.out.print("  " + letter + ") " + choices.get(i) + "   ");
        }

        System.out.println();
    }

    @Override
    public void tabulate()
    {
        System.out.println(prompt);
        System.out.println();

        // Initialize a count array - one slot per choice
        int[] counts = new int[choices.size()];

        for (String response : responses)
        {
            int index = response.toUpperCase().charAt(0) - 'A';

            if (index >= 0 && index < choices.size())
            {
                counts[index]++;
            }
        }

        // Print each choice letter with its count
        for (int i = 0; i < choices.size(); i++)
        {
            System.out.println((char)('A' + i) + ": " + counts[i]);
        }
    }

    @Override
    public void displayWithAnswer()
    {
        display();

        // Convert stored letter back to the full choice text for display
        int index = correctAnswer.charAt(0) - 'A';
        String choiceText = choices.get(index);

        System.out.println("The correct choice is " + correctAnswer + ") " + choiceText);
    }

    @Override
    public void displayWithResponses() {

        display();

        if (responses.isEmpty())
        {
            System.out.println("[No response recorded]");
        }
        else
        {
            for (int i = 0; i < responses.size(); i++) {

                System.out.println("Response " + (i + 1) + ": " + responses.get(i));
            }
        }
    }

    @Override
    public void takeQuestion(Scanner scanner) {

        display();
        responses.clear();

        if (numResponses > 1)
        {
            System.out.println("Please give " + numResponses + " choices:");
        }

        for (int i = 0; i < numResponses; i++) {

            String answer = "";
            boolean valid = false;

            while (!valid) {

                System.out.print("Your choice (enter a letter): ");
                answer = scanner.nextLine().trim().toUpperCase();

                if (answer.length() == 1)
                {
                    int index = answer.charAt(0) - 'A';

                    if (index >= 0 && index < choices.size())
                    {
                        valid = true;
                    }
                }

                if (!valid)
                {
                    System.out.println("Invalid. Please enter a letter between A and "+ (char)('A' + choices.size() - 1) + ".");
                }
            }

            responses.add(answer);
        }
    }

    @Override
    public void modify(Scanner scanner) {

        // Modify the current prompt that you have.
        System.out.println("Current prompt: " + prompt);
        System.out.print("Do you wish to modify the prompt? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes"))
        {
            // Show the current prompt then ask for the new one
            System.out.println("Current prompt: " + prompt);
            System.out.print("Enter new prompt: ");
            prompt = scanner.nextLine().trim();

            System.out.println("Prompt is updated.");
        }

        // Modify the specific choice
        System.out.print("Would you wish to modify a choice? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes"))
        {
            System.out.println("Current choices: ");

            for (int i = 0; i < choices.size(); i++) {
                // Loop through each choice and print it with a letter label
                System.out.println("    " + (char)('A' + i) + ") " + choices.get(i));
            }

            System.out.print("Which choice to modify? (enter the letter): ");

            String letter = scanner.nextLine().trim().toUpperCase();

            if (letter.length() == 1)
            {
                int index = letter.charAt(0) - 'A';

                if (index >= 0 && index < choices.size())
                {
                    System.out.print("Enter new value: ");
                    choices.set(index, scanner.nextLine().trim());
                    System.out.println("Choice updated.");

                }
                else
                {
                    System.out.println("Invalid letter.");
                }
            }
        }
    }

    public List<String> getChoices()
    {
        return choices;
    }
}