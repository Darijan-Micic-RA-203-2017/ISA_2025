package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.rate_limiting;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.rate_limiting.RedisRateLimitingService;

/** REFERENCE: https://medium.com/@vivekrajyaguru1993/how-i-built-a-simple-rate-limiter-in-spring-boot-using-redis-step-by-step-3b70d6dee066 */
@Service()
public class RedisRateLimitingServiceImplementation implements RedisRateLimitingService {
	private StringRedisTemplate stringRedisTemplate;

	@Autowired()
	public RedisRateLimitingServiceImplementation(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Override()
	public boolean isAllowed(String key, int limit, long timeWindowInMilliseconds) {
		Long currentNumberOfCalls = stringRedisTemplate.opsForValue().increment(key);
		if (currentNumberOfCalls != null && currentNumberOfCalls == 1L) {
			stringRedisTemplate.expire(key, Duration.ofMillis(timeWindowInMilliseconds));
		}

		return currentNumberOfCalls <= limit;
	}
}
