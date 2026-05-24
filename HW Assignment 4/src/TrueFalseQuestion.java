import java.util.Scanner;

public class TrueFalseQuestion extends Question {

    // Required for serialization aspect of the assignment - keeps saved files compatible with this class
    private static final long serialVersionUID = 1L;

    public TrueFalseQuestion(String prompt)
    {
        super(prompt, 1);
    }

    @Override
    public void display() {
        System.out.println(prompt + " (T/F)");
    }

    @Override
    public void tabulate()
    {
        System.out.println(prompt);
        System.out.println();

        int trueCount  = 0;
        int falseCount = 0;

        for (String response : responses)
        {
            if (response.equalsIgnoreCase("T") || response.equalsIgnoreCase("True"))
            {
                trueCount++;
            }
            else if (response.equalsIgnoreCase("F") || response.equalsIgnoreCase("False"))
            {
                falseCount++;
            }
        }

        System.out.println("True: "  + trueCount);
        System.out.println("False: " + falseCount);
    }

    @Override
    public void displayWithAnswer()
    {
        display();
        System.out.println("The correct answer is " + correctAnswer);
    }

    @Override
    public void displayWithResponses() {

        display();

        if (responses.isEmpty()) {
            System.out.println("[No response recorded]");

        }
        else
        {
            for (int i = 0; i < responses.size(); i++) {

                //Basically makes it so it auto increases the num of responses displayed
                System.out.println("Response " + (i + 1) + ": " + responses.get(i));
            }
        }
    }

    @Override
    public void takeQuestion(Scanner scanner) {

        display();
        responses.clear();

        for (int i = 0; i < numResponses; i++) {

            String answer = "";

            while (!answer.equalsIgnoreCase("T") && !answer.equalsIgnoreCase("F"))
            {
                System.out.print("Your answer (T/F): ");
                answer = scanner.nextLine().trim();

                if (!answer.equalsIgnoreCase("T") && !answer.equalsIgnoreCase("F"))
                {
                    System.out.println("Invalid input. Please enter T or F.");
                }
            }

            responses.add(answer.toUpperCase());
        }
    }

    @Override
    public void modify(Scanner scanner) {

        System.out.println("Current prompt: " + prompt);
        System.out.print("Do you wish to modify the current prompt? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {

            // Show the current prompt then ask for the new one
            System.out.println("Current prompt: " + prompt);
            System.out.print("Enter new prompt: ");
            prompt = scanner.nextLine().trim();

            System.out.println("Prompt is updated.");
        }
    }
}