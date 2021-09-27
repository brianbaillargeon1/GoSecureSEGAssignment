package brianbaillargeon;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import redis.clients.jedis.Jedis;

/**
 * Reads person entries from a file, then writes them to Redis.
 *
 * The filename can be passed in as an argument. Defaults to "people.csv" if an argument is not present.
 * The file should be formatted as a CSV with a header, and the columns in the following order:
 * name, address, phone number, email
 * The entries will be sent to Redis under the "person" key, and stored as a set.
 */
public class RedisWriter 
{
	public Jedis jedis;

	public static void main( String[] args )
	{
		// Connect to Redis
		System.out.println("Attempting to connect to Redis...");
		RedisWriter redisWriter = new RedisWriter();
		redisWriter.connect();
		System.out.println("Connection successful");
		
		// Grab the filename; default: people.csv
		String peopleFile = args.length > 0 ? args[0] : "people.csv";

		System.out.println("Reading from file, and writing to Redis...");
		// Read people from the file, then write them to redis
		readPeople(peopleFile).stream().forEach(person -> {
			StringBuilder sb = new StringBuilder("Sending: ").append(person.toJson());
			System.out.println(sb.toString());
			redisWriter.writePerson("person", person);
		});

		System.out.println("Complete; disconnecting...");
		redisWriter.disconnect();
		System.out.println("Disconnected");
	}

	/**
	 * Connects to redis
	 */
	public void connect()
	{
		jedis = new Jedis("personContainer", 6379);
	}

	/**
	 * Close the redis connection
	 */
	public void disconnect()
	{
		jedis.close();
	}

	/**
	 * Writes a person object using 'sadd' to the specified key.
	 * The Person will be represented as a JSON String.
	 * @param key the redis key for the person list
	 * @param person the Person object
	 */
	public void writePerson(String key, Person person)
	{
		jedis.sadd(key, person.toJson());
	}

	/**
	 * Reads people from the specified file
	 */
	public static List<Person> readPeople(String fileName)
	{
		// Open a CSVReader; skip past the header.
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build()) {
			List<String[]> people = reader.readAll();
			return people.stream().map(row -> {
				Person p = new Person();
				p.setName(row[0]);
				p.setAddress(row[1]);
				p.setPhoneNumber(row[2]);
				p.setEmail(row[3]);
				return p;
			}).collect(Collectors.toList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
}
