public class DatabaseUserRepositoryTest extends AbstractUserRepositoryTest {
    @Override
    protected UserRepository newRepository() {
        return new DatabaseUserRepository();
    }
}