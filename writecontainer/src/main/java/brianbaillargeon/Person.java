package brianbaillargeon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"name", "address", "phoneNumber", "email"})
public class Person
{
	public String name, address, phoneNumber, email;

	public String toJson()
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
}
