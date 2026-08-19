package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.security.rate_limiting;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.RateLimitExceededException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.rate_limiting.RedisRateLimitingService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.HttpRequestUtilities;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.RateLimit;

/** REFERENCE: https://medium.com/@vivekrajyaguru1993/how-i-built-a-simple-rate-limiter-in-spring-boot-using-redis-step-by-step-3b70d6dee066 */
@Aspect()
@Component()
public class RateLimitingAspect {
	private RedisRateLimitingService redisRateLimitingService;
	private HttpServletRequest request;

	@Autowired()
	public RateLimitingAspect(RedisRateLimitingService redisRateLimitingService, 
			HttpServletRequest request) {
		this.redisRateLimitingService = redisRateLimitingService;
		this.request = request;
	}

	@Around(value = "@annotation(rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.RateLimit)")
	public Object rateLimit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		RateLimit rateLimit = method.getAnnotation(RateLimit.class);

		// REFERENCE: https://stackoverflow.com/questions/4678797/how-do-i-get-the-remote-address-of-a-client-in-servlet
		String ipAddressOfUser = HttpRequestUtilities.extractIpAddressOfUserFrom(request);
		StringBuilder redisKeyBuilder = new StringBuilder("rateLimit:");
		redisKeyBuilder.append(ipAddressOfUser).append(":").append(method.getName());
		String redisKey = redisKeyBuilder.toString();

		boolean isAllowed = redisRateLimitingService.isAllowed(redisKey, rateLimit.limit(), 
				rateLimit.timeWindowInMilliseconds());
		if (!isAllowed) {
			throw new RateLimitExceededException(
					"The server endpoint's rate limit has been exceeded! Please try again later.");
		}

		return proceedingJoinPoint.proceed();
	}
}
