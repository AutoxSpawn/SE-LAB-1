import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatchingQuestion extends Question {

    // Required for serialization aspect of the assignment - keeps saved files compatible with this class
    private static final long serialVersionUID = 1L;

    private List<String> leftItems;
    private List<String> rightItems;

    public MatchingQuestion(String prompt, List<String> leftItems, List<String> rightItems)
    {
        // number of responses = number of pairs
        super(prompt, leftItems.size());

        this.leftItems  = new ArrayList<>(leftItems);
        this.rightItems = new ArrayList<>(rightItems);
    }

    @Override
    public void display()
    {
        System.out.println(prompt);
        System.out.println();

        int count = Math.max(leftItems.size(), rightItems.size());

        for (int i = 0; i < count; i++)
        {
            String left  = (i < leftItems.size())  ? leftItems.get(i)  : "";
            String right = (i < rightItems.size()) ? rightItems.get(i) : "";

            char leftLabel = (char)('A' + i);

            // Printf keeps the columns aligned and together
            System.out.printf("%c) %-20s %d) %s%n", leftLabel, left, i + 1, right);
        }
    }

    @Override
    public void tabulate()
    {
        display();
        System.out.println();

        // Each full permutation is numPairs responses grouped together
        // responses list stores individual pairs like "A 2", "B 1", "C 3", "A 1", "B 2", "C 3"...
        // Group them into complete permutations of size numPairs
        int numPairs = leftItems.size();

        if (responses.isEmpty() || responses.size() % numPairs != 0)
        {
            System.out.println("[No complete responses recorded]");
            return;
        }

        // Build a string key for each full permutation and count them
        java.util.LinkedHashMap<String, Integer> permutationCounts = new java.util.LinkedHashMap<>();

        for (int i = 0; i < responses.size(); i += numPairs)
        {
            StringBuilder key = new StringBuilder();

            for (int j = 0; j < numPairs; j++)
            {
                if (key.length() > 0) key.append(",");
                key.append(responses.get(i + j));
            }

            String permKey = key.toString();
            permutationCounts.put(permKey, permutationCounts.getOrDefault(permKey, 0) + 1);
        }

        // Print each permutation with its count
        for (java.util.Map.Entry<String, Integer> entry : permutationCounts.entrySet())
        {
            System.out.println(entry.getValue());

            // Print each pair in the permutation on its own line
            for (String pair : entry.getKey().split(","))
            {
                System.out.println(pair.trim());
            }

            System.out.println();
        }
    }

    @Override
    public void displayWithAnswer()
    {
        display();

        // Correct answer is stored as "A 1,B 2,C 3" - parse and show actual item names
        System.out.println("The correct matches are:");
        String[] pairs = correctAnswer.split(",");

        for (String pair : pairs)
        {
            String[] parts = pair.trim().split(" ");
            int leftIndex  = parts[0].charAt(0) - 'A';
            int rightIndex = Integer.parseInt(parts[1]) - 1;

            String leftItem  = leftItems.get(leftIndex);
            String rightItem = rightItems.get(rightIndex);

            System.out.println("   " + leftItem + " -> " + rightItem);
        }
    }

    @Override
    public void displayWithResponses()
    {
        display();
        System.out.println("Your matches:");

        if (responses.isEmpty())
        {
            System.out.println("[No response recorded]");
        }
        else
        {

            for (String r : responses) {

                System.out.println("    " + r);
            }
        }
    }


    @Override
    public void takeQuestion(Scanner scanner)
    {
        display();
        responses.clear();
        System.out.println("For each left item, enter the number it matches with:");

        for (int i = 0; i < leftItems.size(); i++) {

            char leftLabel = (char)('A' + i);
            boolean valid = false;

            while (!valid)
            {
                System.out.print("Match for " + leftLabel + " (enter a number): ");
                String input = scanner.nextLine().trim();

                try {

                    int rightNum = Integer.parseInt(input);

                    if (rightNum >= 1 && rightNum <= rightItems.size()) {

                        // Store in the original "A 2" format so it saves correctly

                        responses.add(leftLabel + " " + rightNum);
                        valid = true;

                    }
                    else
                    {
                        System.out.println("Invalid. Enter a number between 1 and " + rightItems.size() + ".");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Invalid. Please enter a number (e.g. 1, 2, 3).");
                }
            }
        }
    }

    @Override
    public void modify(Scanner scanner)
    {
        System.out.println("Current prompt: " + prompt);
        System.out.print("Do you wish to modify the prompt? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {

            // Show the current prompt then ask for the new one
            System.out.println("Current prompt: " + prompt);
            System.out.print("Enter new prompt: ");
            prompt = scanner.nextLine().trim();
            System.out.println("Prompt updated.");
        }

        System.out.print("Do you wish to modify a left column item? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes"))
        {
            for (int i = 0; i < leftItems.size(); i++)
            {
                System.out.println("    " + (char)('A' + i) + ") " + leftItems.get(i));
            }

            System.out.print("Which item? (enter the letter): ");
            String letter = scanner.nextLine().trim().toUpperCase();

            if (letter.length() == 1)
            {
                int index = letter.charAt(0) - 'A';

                if (index >= 0 && index < leftItems.size())
                {
                    System.out.print("Enter new value: ");
                    leftItems.set(index, scanner.nextLine().trim());

                    System.out.println("Updated.");
                }
                else
                {
                    System.out.println("Invalid letter.");
                }
            }
            else
            {
                System.out.println("Invalid input. Please enter a single letter.");
            }
        }

        System.out.print("Do you wish to modify a right column item? (yes/no): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("yes"))
        {
            for (int i = 0; i < rightItems.size(); i++)
            {
                System.out.println("    " + (i + 1) + ") " + rightItems.get(i));
            }
            System.out.print("Which item? (enter the number): ");

            try {
                int num = Integer.parseInt(scanner.nextLine().trim());

                if (num >= 1 && num <= rightItems.size()) {

                    System.out.print("Enter new value: ");
                    rightItems.set(num - 1, scanner.nextLine().trim());

                    System.out.println("Updated.");
                }
                else
                {
                    System.out.println("Invalid number.");
                }

            } catch (NumberFormatException e)
            {
                System.out.println("Invalid input.");
            }
        }
    }

    public List<String> getLeftItems()
    {
        return leftItems;
    }

    public List<String> getRightItems()
    {
        return rightItems;
    }
}