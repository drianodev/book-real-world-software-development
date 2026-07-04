import org.junit.jupiter.api.BeforeEach;

public class InMemoryTwootRepositoryTest extends AbstractTwootRepositoryTest {
    @BeforeEach
    public void setUp() {
        repository = new InMemoryTwootRepository();
    }
}