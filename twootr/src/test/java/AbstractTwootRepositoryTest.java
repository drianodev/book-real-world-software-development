import java.util.Optional;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@Disabled("abstract base test")
public abstract class AbstractTwootRepositoryTest {
    @SuppressWarnings("unchecked")
    private Consumer<Twoot> callback = mock(Consumer.class);
    private TwootQuery twootQuery = new TwootQuery();

    protected TwootRepository repository;

    @AfterEach
    public void clear() {
        verifyNoMoreInteractions(callback);

        repository.clear();
    }

    @Test
    public void shouldLoadTwootsFromPosition() {
        final Position position = add("1", TestData.TWOOT);
        final Position position2 = add("2", TestData.TWOOT_2);

        repository.query(twootQuery.inUsers(TestData.USER_ID).lastSeenPosition(position), callback);

        verify(callback).accept(new Twoot("2", TestData.USER_ID, TestData.TWOOT_2, position2));
    }

    @Test
    public void shouldGetTwootsFromPosition() {
        final String id = "1";

        add(id, TestData.TWOOT);

        final Optional<Twoot> result = repository.get(id);
        assertTrue(result.isPresent());
        final Twoot twoot = result.get();
        assertEquals(id, twoot.getId());
        assertEquals(TestData.USER_ID, twoot.getSenderId());
        assertEquals(TestData.TWOOT, twoot.getContent());
    }

    @Test
    public void shouldDeleteTwootsFromPosition() {
        final String id = "1";

        final Twoot twoot = repository.add(id, TestData.USER_ID, TestData.TWOOT);

        repository.delete(twoot);

        final Optional<Twoot> result = repository.get(id);
        assertFalse(result.isPresent(), "Twoot wasn't deleted");
    }

    @Test
    public void shouldOnlyLoadTwootsFromFollowedUsers() {
        add("1", TestData.TWOOT);

        repository.query(twootQuery.lastSeenPosition(Position.INITIAL_POSITION), callback);
    }

    private Position add(final String id, final String content) {
        final Twoot twoot = repository.add(id, TestData.USER_ID, content);
        assertEquals(TestData.USER_ID, twoot.getSenderId());
        assertEquals(content, twoot.getContent());
        return twoot.getPosition();
    }
}