package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.protocol.ProtocolVersion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/** REFERENCE: https://stackoverflow.com/questions/51915100/springboot-redis-remote-host */
@Configuration()
public class RedisConfiguration {
	@Value("${spring.redis.host}")
	private String hostForRedis;

	@Value("${spring.redis.port}")
	private int portForRedis;

	@Value("${spring.redis.username}")
	private String usernameForRedis;

	@Value("${spring.redis.password}")
	private String passwordForRedis;

	public RedisConfiguration() {}

	@Bean()
    public LettuceConnectionFactory redisConnectionFactory() {
		/* 1. The credentials are explicitly supplied to the configuration of the connection to a
		 * single node instance of the Redis database.
		*/
		RedisStandaloneConfiguration serverConfiguration = 
				new RedisStandaloneConfiguration(hostForRedis, portForRedis);
		serverConfiguration.setUsername(usernameForRedis);
		serverConfiguration.setPassword(passwordForRedis);

		/* 2. The Redis client is configured to enforce the explicit RESP3 behavior or to fallback
		 * to the RESP2.
		*/
		ClientOptions clientOptions = ClientOptions.builder()
				// The RESP3 handling with the "HELLO" authentication is enforced.
				.protocolVersion(ProtocolVersion.RESP3)
				.build();
		LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
				.clientOptions(clientOptions)
				.build();

		return new LettuceConnectionFactory(serverConfiguration, clientConfiguration);
	}
}
