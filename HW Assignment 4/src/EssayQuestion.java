import java.util.Scanner;

public class EssayQuestion extends Question {

    // Required for serialization aspect of the assignment - keeps saved files compatible with this class
    private static final long serialVersionUID = 1L;

    public EssayQuestion(String prompt, int numResponses)
    {
        super(prompt, numResponses);
    }

    @Override
    public void display()
    {
        System.out.println(prompt);

        if (numResponses > 1)
        {
            System.out.println("[Essay - provide " + numResponses + " responses]");
        }
        else
        {
            System.out.println("[Essay response]");
        }
    }

    @Override
    public void tabulate()
    {
        System.out.println(prompt);
        System.out.println();

        // Essays just list all answers as-is
        for (String response : responses)
        {
            System.out.println(response);
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
            System.out.println("[No response recorded]");
        }
        else
        {

            for (int i = 0; i < responses.size(); i++)
            {
                // Label responses A, B, C... like the assignment requirements.
                char label = (char)('A' + i);
                System.out.println("  " + label + ") " + responses.get(i));
            }
        }
    }

    @Override
    public void takeQuestion(Scanner scanner) {

        display();
        responses.clear();

        for (int i = 0; i < numResponses; i++)
        {
            String answer = "";

            while (answer.isEmpty())
            {
                if (numResponses > 1)
                {
                    System.out.print("Response " + (i + 1) + ": ");
                }
                else
                {
                    System.out.print("Your response: ");
                }

                answer = scanner.nextLine().trim();

                if (answer.isEmpty())
                {
                    System.out.println("Response cannot be blank. Please try again.");
                }
            }
            responses.add(answer);
        }
    }

    @Override
    public void modify(Scanner scanner) {

        System.out.println("Current prompt: " + prompt);
        System.out.print("Do you wish to modify the prompt? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes"))
        {
            // Show the current prompt then ask for the new one
            System.out.println("Current prompt: " + prompt);
            System.out.print("Enter new prompt: ");

            prompt = scanner.nextLine().trim();

            System.out.println("Prompt updated.");
        }
    }
}
