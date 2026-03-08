package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.address.AddressDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UserRegistrationException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.RegistrationService;

/** REFERENCES:<br />
 * https://jonasg.io/posts/subtle-art-of-java-test-method-naming/<br />
 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
*/
@SpringBootTest()
public class JutjubicBackEndApplicationTests {
	private RegistrationService registrationService;

	@Autowired()
	public JutjubicBackEndApplicationTests(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	/** REFERENCES:<br />
	 * https://www.javabyexamples.com/shut-down-thread-pool-using-java-executorservice<br />
	 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html
	*/
	private void shutdownAndAwaitTerminationOf(ExecutorService executorService) {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
				if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
					System.err.println("Threads pool did not terminate!");
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();

			executorService.shutdownNow();
		}
	}

	/** REFERENCES:<br />
	 * https://www.mindbowser.com/mastering-executorservice-java-guide/<br />
	 * https://www.baeldung.com/java-executor-service-tutorial<br />
	 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html<br />
	 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/Executors.html<br />
	 * https://www.baeldung.com/java-runnable-callable
	*/
	@Test()
	public void shouldAcceptOnlyOneRegistration_When2ConcurrentUsers_TryRegisteringWithSameUsername() throws Throwable {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		Callable<User> registrationOfUserFailureTask = new Callable<User>() {
			@Override()
			public User call() throws UserRegistrationException, MailException {
				System.out.println("Started thread #1.");

				UserRegistrationRequestDTO userRegistrationRequestDTO = 
						new UserRegistrationRequestDTO("darijan.micic10@gmail.com", 
								"|Tad1ja_Ostojic$", "vRasic#46", "Вељко", "Рашић", 
								new AddressDTO(0L, "Јована Дучића", "3А", "22320", "Инђија", 
										"Србија", 45.97, 20.56));

				return registrationService.registerUserBasedOn(userRegistrationRequestDTO);
			}
		};
		Future<User> future1 = executorService.submit(registrationOfUserFailureTask);

		Callable<User> registrationOfUserSuccessTask = new Callable<User>() {
			@Override()
			public User call() throws UserRegistrationException, MailException {
				System.out.println("Started thread #2.");

				UserRegistrationRequestDTO userRegistrationRequestDTO = 
						new UserRegistrationRequestDTO("darijan.micic@uns.ac.rs", 
								"|Tad1ja_Ostojic$", "T_OSTOJIC^2!99", "Тадија", "Остојић", 
								new AddressDTO(0L, "22. октобра", "19", "22320", "Инђија", 
										"Србија", 45.77, 20.63));

				try {
					Thread.sleep(50L);
				} catch (InterruptedException iE) {}

				System.out.println("Thread #2 is awoken.");

				return registrationService.registerUserBasedOn(userRegistrationRequestDTO);
			}
		};
		Future<User> future2 = executorService.submit(registrationOfUserSuccessTask);

		Callable<Integer> totalNumberOfUsersTask = new Callable<Integer>() {
			@Override()
			public Integer call() {
				System.out.println("Started thread #3.");

				try {
					Thread.sleep(20000L);
				} catch (InterruptedException iE) {}

				System.out.println("Thread #3 is awoken.");

				List<User> allUsers = registrationService.getUserService().findAll();
				int totalNumberOfUsers = allUsers.size();
				System.out.println("Total number of users: " + totalNumberOfUsers);

				return totalNumberOfUsers;
			}
		};
		Future<Integer> future3 = executorService.submit(totalNumberOfUsersTask);

		User newUser = null;
		try {
			newUser = future1.get();
		} catch (ExecutionException eE) {
			Throwable exception = eE.getCause();
			System.out.println("Exception from thread: " + exception.getClass());
			exception.printStackTrace();
		} catch (InterruptedException iE) {
			iE.printStackTrace();
		}

		int totalNumberOfUsers = 0;
		try {
			newUser = future2.get();
			totalNumberOfUsers = future3.get();
		} catch (ExecutionException eE) {
			Throwable exception = eE.getCause();
			System.err.println("Exception from thread: " + exception.getClass());
			exception.printStackTrace();
		} catch (InterruptedException iE) {
			iE.printStackTrace();
		} finally {
			shutdownAndAwaitTerminationOf(executorService);
		}

		assertNotNull(newUser, "Expected new user to not be null.");
		assertEquals("darijan.micic@uns.ac.rs", newUser.getEmailAddress(), 
				"Expected the new user to have the e-mail address \"\"darijan.micic@uns.ac.rs\"\".");
		assertEquals("22. октобра", newUser.getAddress().getStreet(), 
				"Expected the new user to have the address street \"\"22. октобра\"\".");
		assertEquals(3, totalNumberOfUsers, "Expected 3 users.");
	}
}
