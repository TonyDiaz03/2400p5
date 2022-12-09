public class Airports 
{
	private String city;
	private String name;
	private String state;

	public Airports(String city, String name, String state)
	{
		this.city = city;
		this.name = name;
		this.state = state;
	}//end constructor

	@Override
	public String toString()
	{
		return String.format("%s; %s; %s", city, name, state);
	}//end toString
}
