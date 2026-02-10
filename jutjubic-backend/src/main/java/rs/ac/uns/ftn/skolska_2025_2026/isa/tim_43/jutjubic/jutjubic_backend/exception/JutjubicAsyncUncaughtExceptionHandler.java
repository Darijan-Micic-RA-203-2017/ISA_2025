package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe2/async_example<br />
 * https://dzone.com/articles/effective-advice-on-spring-async-exceptionhandler-1<br />
 * https://www.baeldung.com/spring-asyncv
*/
public class JutjubicAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
	public JutjubicAsyncUncaughtExceptionHandler() {}

	@Override()
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		System.err.println("Async exception detected!");
		System.err.println("Exception message:\n\t" + ex.getMessage());
		System.err.println("Method name: " + method.getName());
		for (Object p: params) {
			System.err.println("Parameter value: " + p);
		}
	}
}
