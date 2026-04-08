import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        AlarmClockRadio clock = new AlarmClockRadio(LocalTime.of(8, 0), LocalTime.of(8, 5), "1060 AM");

        System.out.println(clock.showTime() + " The radio was turned on and is playing " + clock.getRadioStation() + ".");

        for (int i = 0; i < 5; i++) {
            clock.tick();
            System.out.println(clock.showTime());
            clock.checkAlarm();
        }

        clock.snooze();

        for (int i = 0; i < 9; i++) {
            clock.tick();
            System.out.println(clock.showTime());
            clock.checkAlarm();
        }

        clock.turnAlarmOff();
    }
}