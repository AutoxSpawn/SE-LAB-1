import java.time.LocalTime;

public class AlarmClock extends Clock {

    protected LocalTime alarmTime;
    protected boolean isAlarmOn;

    public AlarmClock(LocalTime currentTime, LocalTime alarmTime) {

        super(currentTime);
        this.alarmTime = alarmTime;
        this.isAlarmOn = true;
    }

    public void setAlarm(LocalTime t) {
        alarmTime = t;
    }

    public LocalTime getAlarm() {
        return alarmTime;
    }

    public void turnAlarmOn() {
        isAlarmOn = true;
    }

    public void turnAlarmOff() {
        isAlarmOn = false;
    }

    public boolean isAlarmOn() {
        return isAlarmOn;
    }

    public void snooze() {
        System.out.println("Snooze was pressed");
        alarmTime = alarmTime.plusMinutes(9);
        isAlarmOn = true;
    }

    public void checkAlarm() {
        if (isAlarmOn && currentTime.getHour() == alarmTime.getHour() && currentTime.getMinute() == alarmTime.getMinute())
        {
            System.out.println("Buzz Buzz Buzz");
        }
    }
}