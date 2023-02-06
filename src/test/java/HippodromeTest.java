import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {
    @Test
    public void shouldThrowIllegalArgumentExceptionIfArrayListIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }
    @Test
    public void getMessageIfArrayListIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals(exception.getMessage(), "Horses cannot be null.");
    }
    @Test
    public void shouldThrowIllegalArgumentExceptionIfArrayListIsEmpty(){
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }
    @Test
    public void getMessageIfArrayListIsEmpty(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));

        assertEquals(exception.getMessage(), "Horses cannot be empty.");
    }
    @Test
    public void getHorses(){
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(" " + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }
    @Test
    public void move(){
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        new Hippodrome(horses).move();

        for (Horse horse: horses){
            verify(horse).move();
        }
    }
    @Test
    public void shouldReturnAHorseWithTheLongestDistance(){
        Horse horse1 = new Horse("horse1", 1,4);
        Horse horse2 = new Horse("horse2", 1,2);
        Horse horse3 = new Horse("horse3", 1,3);
        Horse horse4 = new Horse("horse4", 1,1);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse1, hippodrome.getWinner());
    }

}