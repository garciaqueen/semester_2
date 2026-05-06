import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Clock {

    private LocalTime clock;

    public void setCurrentTime() {
        this.clock = LocalTime.now();
    }

    public void setTime(int hour, int min, int sec) throws IllegalArgumentException{
        this.clock = LocalTime.of(hour, min, sec);
    }

    public String toString() {
        return this.clock.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
    }
}
