import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {
    @Test
    public void shouldThrowIllegalArgumentExceptionIfNameIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 10));

    }
    @Test
    public void getMessageIfNameIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10, 10));
        assertEquals(exception.getMessage(), "Name cannot be null.");
    }
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "   ", "\t", "\n" })
    public void shouldThrowIllegalArgumentExceptionIfNameIsBlank(String name){
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10, 10));
    }
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "   ", "\t", "\n" })
    public void getMessageIfNameIsBlank(String name){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10, 10));
        assertEquals(exception.getMessage(), "Name cannot be blank.");
    }
    @ParameterizedTest
    @ValueSource(doubles = { -1, -10000000})
    public void shouldThrowIllegalArgumentExceptionIfSpeedIsNegative(double speed){
        assertThrows(IllegalArgumentException.class, () -> new Horse("name", speed, 10));
    }
    @ParameterizedTest
    @ValueSource(doubles = { -1, -10000000})
    public void getMessageIfSpeedIsNegative(double speed){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("name", speed, 10));
        assertEquals(exception.getMessage(), "Speed cannot be negative.");
    }
    @ParameterizedTest
    @ValueSource(doubles = { -1, -1000000 })
    public void shouldThrowIllegalArgumentExceptionIfDistanceIsNegative(double distance){
       assertThrows(IllegalArgumentException.class, () -> new Horse("name", 10, distance));
    }
    @ParameterizedTest
    @ValueSource(doubles = { -1, -1000000 })
    public void getMessageIfDistanceIsNegative(double distance){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 10, distance));
        assertEquals(exception.getMessage(), "Distance cannot be negative.");
    }
    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        String expectedName = "horse";
        Horse horse = new Horse(expectedName, 30,30);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String actualName = (String) name.get(horse);

        assertEquals(expectedName, actualName);
    }
    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        double expectedSpeed = 123;
        Horse horse = new Horse("horse", expectedSpeed, 321);

        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        double actualSpeed = (double)speed.get(horse);

        assertEquals(expectedSpeed, actualSpeed);
    }
    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        double expectedDistance = 123;
        Horse horse = new Horse("horse", 321, expectedDistance);

        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        double actualDistance = (double)distance.get(horse);

        assertEquals(expectedDistance, actualDistance);

        distance.setAccessible(false);
    }
    @Test
    public void distanceShouldReturnZeroByDefault() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("horse", 321);

        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        double actualDistance = (double)distance.get(horse);

        assertEquals(0, actualDistance);

        distance.setAccessible(false);
    }
    @Test
    public void moveShouldUseGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {

            new Horse("horse", 1, 1).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @CsvSource({"0.2, 0.9"})
    public void distanceShouldBeCalculatedByEquation(double valueRandom1, double valueRandom2) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double random = (0.1 * (valueRandom2 - valueRandom1)) + valueRandom1;
            double expectedDistance = 100 + 10 * random;
            Horse horse = new Horse("horse", 10, 100);

            mockedStatic.when(()-> Horse.getRandomDouble(valueRandom1, valueRandom2)).thenReturn(random);

            horse.move();

            assertEquals(expectedDistance, horse.getDistance());

        }
    }

}