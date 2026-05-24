import java.util.Scanner;

public class ShortAnswerQuestion extends Question {

    // Required for serialization aspect of the assignment - keeps saved files compatible with this class
    private static final long serialVersionUID = 1L;

    public ShortAnswerQuestion(String prompt, int numResponses)
    {
        super(prompt, numResponses);
    }

    @Override
    public void display() {

        System.out.println(prompt);

        if (numResponses > 1)
        {
            System.out.println("[Short answer - provide " + numResponses + " responses]");
        }
        else
        {
            System.out.println("[Short answer]");
        }
    }

    @Override
    public void tabulate()
    {
        System.out.println(prompt);
        System.out.println();

        // Use a LinkedHashMap to keep insertion order while counting
        java.util.LinkedHashMap<String, Integer> counts = new java.util.LinkedHashMap<>();

        for (String response : responses)
        {
            counts.put(response, counts.getOrDefault(response, 0) + 1);
        }

        for (java.util.Map.Entry<String, Integer> entry : counts.entrySet())
        {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    @Override
    public void displayWithAnswer()
    {
        display();
        System.out.println("The correct answer is " + correctAnswer);
    }

    @Override
    public void displayWithResponses() {

        System.out.println(prompt);

        if (responses.isEmpty())
        {
            System.out.println("  [No response recorded]");
        }
        else
        {
            for (int i = 0; i < responses.size(); i++)
            {
                System.out.println("  Response " + (i + 1) + ": " + responses.get(i));
            }
        }
    }

    @Override
    public void takeQuestion(Scanner scanner) {

        display();
        responses.clear();

        for (int i = 0; i < numResponses; i++) {
            String answer = "";
            while (answer.isEmpty())
            {
                System.out.print("Your answer: ");
                answer = scanner.nextLine().trim();

                if (answer.isEmpty()) {
                    System.out.println("Answer cannot be blank. Please try again.");
                }
            }
            responses.add(answer);
        }
    }

    @Override
    public void modify(Scanner scanner)
    {
        System.out.println("Current prompt: " + prompt);
        System.out.print("Do you wish to modify the prompt? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes"))
        {
            // Show the current prompt then ask for the new one
            System.out.println("Current prompt: " + prompt);
            System.out.print("Enter a new prompt: ");
            prompt = scanner.nextLine().trim();
            System.out.println("Prompt is updated.");
        }
    }
}
