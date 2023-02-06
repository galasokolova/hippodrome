import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

public class Hippodrome {
    private  static  final Logger log = Logger.getLogger(Hippodrome.class);

    private final List<Horse> horses;

    public Hippodrome(List<Horse> horses) {
        if (isNull(horses)) {
            log.error("Horses cannot be null.");
            throw new IllegalArgumentException("Horses cannot be null.");
        } else if (horses.isEmpty()) {
            log.error("Horses cannot be empty.");
            throw  new IllegalArgumentException("Horses cannot be empty.");

        }
        this.horses = horses;
        log.debug("Creating Hippodrome, there are " + "[" +horses.size()+"] of horses");
    }


    public List<Horse> getHorses() {
        return Collections.unmodifiableList(horses);
    }

    public void move() {
        horses.forEach(Horse::move);
    }

    public Horse getWinner() {
        return horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get();
    }
}
