package brianbaillargeon;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Tests writing Person objects to Redis
 */
public class RedisWriterTest
{
	private String johnDoeJson = "{\"name\":\"John Doe\",\"address\":\"123 Example Street\",\"phoneNumber\":\"123-456-7890\",\"email\":\"jdoe@example.com\"}";

	private Jedis createJedisConnection()
	{
		return new Jedis("localhost", 6379);
	}

	private Person createJohnDoe()
	{
		Person johnDoe = new Person();
		johnDoe.setName("John Doe");
		johnDoe.setAddress("123 Example Street");
		johnDoe.setPhoneNumber("123-456-7890");
		johnDoe.setEmail("jdoe@example.com");
		return johnDoe;
	}

	@Test
	public void testJson()
	{
		Person johnDoe = createJohnDoe();
		assertTrue(johnDoeJson.equals(johnDoe.toJson()));
	}

	@Test
	public void testWriteAndRead()
	{
		RedisWriter writer = new RedisWriter();
		writer.connect("localhost");

		// Write John Doe as a test key
		writer.writePerson("test_person", createJohnDoe());

		// Connect and check John Doe is present
		Jedis jedis = createJedisConnection();
		assertTrue(jedis.smembers("test_person").stream().anyMatch(entry -> johnDoeJson.equals(entry)));

		// Cleanup
		jedis.del("test_person");
		jedis.close();
	}

	@Test
	public void testReadPeople()
	{
		List<Person> people = RedisWriter.readPeople("src/test/resources/people.csv");
		assertTrue(people != null);
		assertTrue(people.size() == 2);

	}
}
