import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Question implements Serializable {

    // Required for serialization aspect of the assignment - keeps saved files compatible with this class
    private static final long serialVersionUID = 1L;

    // The text of the question
    protected String prompt;

    // Stores the user's answer when they take the survey
    protected List<String> responses;

    // How many responses this question expects
    protected int numResponses;

    public Question(String prompt, int numResponses) {

        this.prompt = prompt;
        this.numResponses = numResponses;
        this.responses = new ArrayList<>();

    }

    // Every subclass needs to have all of this
    public abstract void display();
    public abstract void displayWithResponses();
    public abstract void displayWithAnswer();
    public abstract void tabulate();
    public abstract void takeQuestion(java.util.Scanner scanner);
    public abstract void modify(java.util.Scanner scanner);

    // Stores the correct answer - only used when this question is part of a test
    protected String correctAnswer = "";

    // Getters and setters
    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer)
    {
        this.correctAnswer = correctAnswer;
    }

    public boolean hasCorrectAnswer()
    {
        return correctAnswer != null && !correctAnswer.isEmpty();
    }

    public String getPrompt()
    {
        return prompt;
    }

    public void setPrompt(String prompt)
    {
        this.prompt = prompt;
    }

    public List<String> getResponses()
    {
        return responses;
    }

    public int getNumResponses()
    {
        return numResponses;
    }

    public void setNumResponses(int n)
    {
        this.numResponses = n;
    }

    public void clearResponses()
    {
        responses.clear();
    }
}
