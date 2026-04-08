import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock {
    protected LocalTime currentTime;

    public Clock(LocalTime t) {
        currentTime = t;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalTime t) {
        currentTime = t;
    }

    public String formatTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        return currentTime.format(formatter);
    }
}