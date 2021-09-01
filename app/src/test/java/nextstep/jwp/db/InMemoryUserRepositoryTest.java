package nextstep.jwp.db;

import nextstep.jwp.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryUserRepositoryTest {

    @BeforeEach
    void setUp() {
        InMemoryUserRepository.clear();
        final User user = new User("gugu", "password", "hkkang@woowahan.com");
        InMemoryUserRepository.save(user);
    }

    @DisplayName("유저 조회")
    @Test
    void testFindByAccount() {
        //given
        //when
        Optional<User> optionalUser = InMemoryUserRepository.findByAccount("gugu");
        //then
        assertThat(optionalUser.get()).isNotNull();
        assertThat(optionalUser.get().getPassword()).isEqualTo("password");
        assertThat(optionalUser.get().getEmail()).isEqualTo("hkkang@woowahan.com");
    }

    @DisplayName("유저 저장")
    @Test
    void testSave() {
        //given
        User newUser = new User("NEW_USER", "1234", "email@google.com");
        InMemoryUserRepository.save(newUser);
        //when
        User findUser = InMemoryUserRepository.findByAccount("NEW_USER").get();
        //then
        assertThat(findUser).isSameAs(newUser);
    }

    @DisplayName("지금까지 저장된 유저의 수 반환")
    @Test
    void testGetLatestId() {
        //given
        User newUser1 = new User("NEW_USER1", "1234", "email1@google.com");
        User newUser2 = new User("NEW_USER2", "4321", "email2@google.com");
        InMemoryUserRepository.save(newUser1);
        InMemoryUserRepository.save(newUser2);
        //when
        //then
        assertThat(InMemoryUserRepository.size()).isEqualTo(3);
    }
}