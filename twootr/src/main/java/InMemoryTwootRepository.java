
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class InMemoryTwootRepository implements TwootRepository {

    private final List<Twoot> twoots = new ArrayList<>();

    private Position currentPosition = Position.INITIAL_POSITION;

    @Override
    public Twoot add(final String id, final String userId, final String content) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(content, "content");

        currentPosition = currentPosition.next();
        final Twoot twoot = new Twoot(id, userId, content, currentPosition);
        twoots.add(twoot);
        return twoot;
    }

    @Override
    public Optional<Twoot> get(final String id) {
        Objects.requireNonNull(id, "id");
        return twoots.stream()
                .filter(twoot -> twoot.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(final Twoot twoot) {
        Objects.requireNonNull(twoot, "twoot");
        twoots.remove(twoot);
    }

    @Override
    public void query(final TwootQuery twootQuery, final Consumer<Twoot> callback) {
        Objects.requireNonNull(twootQuery, "twootQuery");
        Objects.requireNonNull(callback, "callback");

        twoots.stream()
                .filter(twoot -> twootQuery.hasUsers() && twootQuery.getInUsers().contains(twoot.getSenderId()))
                .filter(twoot -> twoot.isAfter(twootQuery.getLastSeenPosition()))
                .forEach(callback);
    }

    @Override
    public void clear() {
        twoots.clear();
        currentPosition = Position.INITIAL_POSITION;
    }
}
