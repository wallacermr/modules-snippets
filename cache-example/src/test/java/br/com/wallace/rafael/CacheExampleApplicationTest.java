package br.com.wallace.rafael;

import br.com.wallace.rafael.document.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class CacheExampleApplicationTest extends BaseIntegrationTest {

    @Test
    public void should_return_user() {
        Optional<User> mark = userRepository.findByName("mark");
        Assert.assertTrue(mark.isPresent());
    }
}
