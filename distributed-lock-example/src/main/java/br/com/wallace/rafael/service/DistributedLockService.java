package br.com.wallace.rafael.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class DistributedLockService {

    private final StringRedisTemplate redisTemplate;

    private static byte[] serialize(StringRedisTemplate redisTemplate, String str) {
        return redisTemplate.getStringSerializer().serialize(str);
    }

    public Optional<DistributedLock> tryAcquireLock(final String resourceId, Duration durationTime) {
        final String lockerValue = UUID.randomUUID().toString();
        final boolean locked = Objects.nonNull(redisTemplate.execute(connection ->
                        connection.set(serialize(redisTemplate, resourceId),
                                serialize(redisTemplate, lockerValue),
                                Expiration.from(durationTime),
                                RedisStringCommands.SetOption.SET_IF_ABSENT)
                , false));
        if (locked) {
            return Optional.of(new DistributedLock(resourceId, lockerValue, redisTemplate));
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
