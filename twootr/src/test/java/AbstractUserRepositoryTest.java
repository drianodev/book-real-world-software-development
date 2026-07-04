import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@Disabled("abstract base test")
public abstract class AbstractUserRepositoryTest {
    private ReceiverEndPoint receiverEndPoint = mock(ReceiverEndPoint.class);
    private UserRepository repository;

    protected abstract UserRepository newRepository();

    @BeforeEach
    public void setUp() {
        repository = newRepository();

        repository.clear();
    }

    @Test
    public void shouldLoadSavedUsers() {
        repository.add(userWith(TestData.USER_ID));

        assertThat(repository.get(TestData.USER_ID).get(), matchesUser());
    }

    @Test
    public void shouldNotAllowDuplicateUsers() {
        assertTrue(repository.add(userWith(TestData.USER_ID)));

        assertFalse(repository.add(userWith(TestData.USER_ID)));
    }

    @Test
    public void shouldRecordFollowerRelationships() {
        final User user = userWith(TestData.USER_ID);
        final User otherUser = userWith(TestData.OTHER_USER_ID);

        repository.add(user);
        repository.add(otherUser);
        repository.follow(user, otherUser);

        final UserRepository reloadedRepository = newRepository();
        final User userReloaded = reloadedRepository.get(TestData.USER_ID).get();
        final User otherUserReloaded = reloadedRepository.get(TestData.OTHER_USER_ID).get();
        assertEquals(FollowStatus.ALREADY_FOLLOWING, otherUserReloaded.addFollower(userReloaded));
    }

    @Test
    public void shouldRecordPositionUpdates() {
        final String id = "1";

        final Position newPosition = new Position(2);
        final User user = userWith(TestData.USER_ID);
        repository.add(user);
        assertEquals(Position.INITIAL_POSITION, user.getLastSeenPosition());

        user.receiveTwoot(TestData.twootAt(id, newPosition));
        repository.update(user);

        final UserRepository reloadedRepository = newRepository();
        final User reloadedUser = reloadedRepository.get(TestData.USER_ID).get();
        assertEquals(newPosition, user.getLastSeenPosition());
        assertEquals(newPosition, reloadedUser.getLastSeenPosition());
    }

    @AfterEach
    public void shutdown() throws Exception {
        repository.close();
    }

    private User userWith(final String userId) {
        final User user = new User(userId, TestData.PASSWORD_BYTES, TestData.SALT, Position.INITIAL_POSITION);
        user.onLogon(receiverEndPoint);
        return user;
    }

    private Matcher<User> matchesUser() {
        return allOf(
                hasProperty("id", equalTo(TestData.USER_ID)),
                hasProperty("password", equalTo(TestData.PASSWORD_BYTES)));
    }
}