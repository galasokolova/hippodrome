import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {
    @Disabled
    @Test
    @Timeout(value = 22)
    public void timeOut() throws Exception {
        Main.main(null);
    }
}