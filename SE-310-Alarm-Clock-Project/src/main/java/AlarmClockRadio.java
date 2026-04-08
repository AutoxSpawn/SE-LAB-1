import java.time.LocalTime;

public class AlarmClockRadio {

    private final Radio Radio;
    private final AlarmClock AlarmClock;

    public AlarmClockRadio(LocalTime currentTime, LocalTime alarmTime, String station) {
        Radio = new Radio(station);
        AlarmClock = new AlarmClock(currentTime, alarmTime);
    }

    public void setRadioStation(String s) {
        Radio.setRadioStation(s);
    }

    public String getRadioStation() {
        return Radio.getRadioStation();
    }

    public LocalTime getCurrentTime() {
        return AlarmClock.getCurrentTime();
    }

    public void setCurrentTime(LocalTime t) {
        AlarmClock.setCurrentTime(t);
    }

    public void setAlarm(LocalTime t) {
        AlarmClock.setAlarm(t);
    }

    public LocalTime getAlarm() {
        return AlarmClock.getAlarm();
    }

    public void turnAlarmOn() {
        AlarmClock.turnAlarmOn();
    }

    public void turnAlarmOff() {
        AlarmClock.turnAlarmOff();
        System.out.println("The alarm was shut off.");
    }

    public boolean isAlarmOn() {
        return AlarmClock.isAlarmOn();
    }

    public void snooze() {
        AlarmClock.snooze();
    }

    public void tick() {
        AlarmClock.setCurrentTime(AlarmClock.getCurrentTime().plusMinutes(1));
    }

    public void checkAlarm() {
        AlarmClock.checkAlarm();
    }

    public String showTime() {
        return AlarmClock.formatTime();
    }
}
