package nextstep.jwp.db;


import nextstep.jwp.domain.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository {

    private static final Map<String, User> database = new ConcurrentHashMap<>();

    private InMemoryUserRepository() {
        throw new IllegalStateException("Utility Class");
    }

    static {
        final User user = new User(1, "gugu", "password", "hkkang@woowahan.com");
        database.put(user.getAccount(), user);
    }

    public static void save(User user) {
        database.put(user.getAccount(), user);
    }

    public static Optional<User> findByAccount(String account) {
        return Optional.ofNullable(database.get(account));
    }

    public static int getLatestId() {
        return database.size();
    }

    public static void clear() {
        database.clear();
    }
}
