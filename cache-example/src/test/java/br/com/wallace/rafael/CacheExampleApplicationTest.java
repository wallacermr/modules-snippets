package br.com.wallace.rafael;

import br.com.wallace.rafael.document.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.Cache;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class CacheExampleApplicationTest extends BaseIntegrationTest {

    @Test
    public void should_return_user() {
        createUsers();
        Optional<User> mark = userRepository.findByName("mark");
        List<User> all = userRepository.findAll();
        Collection<String> cacheNames = cacheManager.getCacheNames();
        Cache users = cacheManager.getCache("users");
        assertTrue(mark.isPresent());
        assertEquals(mark, getCachedUser("mark"));
    }

    private Optional<User> getCachedUser(String name) {
        return ofNullable(cacheManager.getCache("users")).map(c -> c.get(name, User.class));
    }

    private void createUsers() {
        User user1 = User.builder()
                .birthDate(LocalDate.of(1988, 5, 15))
                .name("joana")
                .build();

        User user2 = User.builder()
                .birthDate(LocalDate.of(1972, 6, 23))
                .name("mark")
                .build();

        User user3 = User.builder()
                .birthDate(LocalDate.of(1995, 1, 1))
                .name("mongo")
                .build();

        List<User> users = Arrays.asList(user1, user2, user3);
        userRepository.saveAll(users);
    }
}
