package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.configuration;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.JutjubicAsyncUncaughtExceptionHandler;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe2/async_example<br />
 * https://dzone.com/articles/effective-advice-on-spring-async-exceptionhandler-1<br />
 * https://www.baeldung.com/spring-async
*/
@Configuration()
@EnableAsync()
public class SpringAsyncConfiguration implements AsyncConfigurer {
	public SpringAsyncConfiguration() {}

	@Override()
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("EmailThreadPool-");
		executor.setQueueCapacity(25);
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);

		executor.initialize();

		return executor;
	}

	@Override()
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new JutjubicAsyncUncaughtExceptionHandler();
	}
}
