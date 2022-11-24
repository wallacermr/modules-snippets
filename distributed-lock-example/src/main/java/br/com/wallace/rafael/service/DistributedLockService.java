package br.com.wallace.rafael.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class DistributedLockService {

    private final StringRedisTemplate redisTemplate;
    private static final String RESOURCE_ID = "redis_example_";
    private static final String LOCKER_VALUE = UUID.randomUUID().toString();

    @Autowired
    public DistributedLockService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static byte[] serialize(StringRedisTemplate redisTemplate, String str) {
        return redisTemplate.getStringSerializer().serialize(str);
    }

    public Optional<DistributedLock> tryAcquireLock(Duration durationTime) {
        final boolean locked = Objects.nonNull(redisTemplate.execute(connection ->
                        connection.set(serialize(redisTemplate, RESOURCE_ID),
                                serialize(redisTemplate, LOCKER_VALUE),
                                Expiration.from(durationTime),
                                RedisStringCommands.SetOption.SET_IF_ABSENT)
                , false));
        if (locked) {
            return Optional.of(new DistributedLock(RESOURCE_ID, LOCKER_VALUE, redisTemplate));
        }
        return Optional.empty();
    }

    @AllArgsConstructor
    static class DistributedLock implements AutoCloseable {

        @Getter
        private final String lockerKey;
        private final String lockerValue;
        private final StringRedisTemplate redisTemplate;

        /**
         * release resource
         */
        public void release() {
            /*
             * Using best practice indicated by redis documentation we will first check the key value
             * before delete
             */
            redisTemplate.execute(connection -> {
                final String value = deserialize(redisTemplate, connection.get(serialize(redisTemplate, lockerKey)));
                if (Objects.nonNull(value) && Objects.equals(lockerValue, value)) {
                    redisTemplate.delete(lockerKey);
                }
                return value;
            }, false);
        }

        @Override
        public void close() {
            release();
        }

        public String deserialize(final StringRedisTemplate redisTemplate, final byte[] data) {
            return redisTemplate.getStringSerializer().deserialize(data);
        }
    }
}
