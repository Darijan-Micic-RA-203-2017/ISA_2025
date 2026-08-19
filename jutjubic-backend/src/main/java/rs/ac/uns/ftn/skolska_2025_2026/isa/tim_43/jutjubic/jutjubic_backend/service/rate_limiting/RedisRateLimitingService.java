package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.rate_limiting;

/** REFERENCE: https://medium.com/@vivekrajyaguru1993/how-i-built-a-simple-rate-limiter-in-spring-boot-using-redis-step-by-step-3b70d6dee066 */
public interface RedisRateLimitingService {
	boolean isAllowed(String key, int limit, long timeWindowInMilliseconds);
}
