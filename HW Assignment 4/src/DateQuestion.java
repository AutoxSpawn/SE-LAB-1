import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateQuestion extends Question {

    // Required for serialization aspect of the assignment - keeps saved files compatible with this class
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DateQuestion(String prompt, int numResponses)
    {
        super(prompt, numResponses);
    }

    @Override
    public void display()
    {
        System.out.println(prompt);

        // Instructions say to always show the format hint for date questions
        System.out.println("A date should be entered in the following format: YYYY-MM-DD");
    }

    @Override
    public void tabulate()
    {
        System.out.println(prompt);
        System.out.println();

        // Use a LinkedHashMap to count each unique date response
        java.util.LinkedHashMap<String, Integer> counts = new java.util.LinkedHashMap<>();

        for (String response : responses)
        {
            counts.put(response, counts.getOrDefault(response, 0) + 1);
        }

        // Sort by count descending as shown in the example
        counts.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(entry -> {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue());
                    System.out.println();
                });
    }

    @Override
    public void displayWithAnswer()
    {
        display();
        System.out.println("The correct answer is " + correctAnswer);
    }

    @Override
    public void displayWithResponses()
    {
        display();

        if (responses.isEmpty())
        {
            System.out.println("[No response recorded]");
        }
        else
        {
            for (int i = 0; i < responses.size(); i++)
            {
                System.out.println("Response " + (i + 1) + ": " + responses.get(i));
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
            boolean valid = false;

            while (!valid)
            {
                System.out.print("Enter date (YYYY-MM-DD): ");
                answer = scanner.nextLine().trim();

                try {
                    LocalDate.parse(answer, FORMATTER);
                    valid = true;

                } catch (DateTimeParseException e)
                {
                    System.out.println("Invalid date. Use format YYYY-MM-DD (e.g. 2024-05-15).");
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

            System.out.println("Prompt is updated.");
        }
    }
}
