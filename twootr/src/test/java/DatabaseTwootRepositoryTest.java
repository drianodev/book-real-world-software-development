import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;

public class DatabaseTwootRepositoryTest extends AbstractTwootRepositoryTest {
    @BeforeEach
    public void setUp() throws IOException {
        repository = new DatabaseTwootRepository();
    }
}