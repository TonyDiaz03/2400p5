//
//	Name:		Diaz, Tony
//	Project:	5
//	Due: 		December 9, 2022
//	Course:	cs-2400-02-f22
//
//	Description:	
//			This project will take in 2 files holding information about different airports and the user
//			can ask for the information on the airports and check the shortest path between 2 airports.
import java.util.Scanner;
import java.util.HashMap;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Stack;

public class AirportApp 
{
	public static void main(String[] args)
	{
		HashMap<String, String> airports = new HashMap<>();//stores the airports
		DirectedGraph<String> distance = new DirectedGraph<>();


		try
		{
			Scanner airportFile = new Scanner(new File("airports.csv"));
			airportFile.useDelimiter(",");
			while(airportFile.hasNextLine())
			{
				String info = airportFile.nextLine();
				String[] tempArray = info.split(",");
				Airports data = new Airports(tempArray[1], tempArray[2], tempArray[3]);
				distance.addVertex(tempArray[0]);
				airports.put(tempArray[0], data.toString());
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + e.getMessage());
		}

		try
		{
			Scanner airportDistance = new Scanner(new File("distances.csv"));
			airportDistance.useDelimiter(",");
			while(airportDistance.hasNextLine())
			{
				String info = airportDistance.nextLine();
				String[] tempArray = info.split(",");
				distance.addEdge(tempArray[0], tempArray[1], Double.parseDouble(tempArray[2]));
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found: " + e.getMessage());
		}

		System.out.println("Airports v0.1 by T. Diaz");
		Scanner keyboard = new Scanner(System.in);
		boolean repeat = true;
		do
		{
			System.out.print("\nCommand? ");
			String command = keyboard.nextLine().toUpperCase();
			String[] commandArray = command.split(" ");
			
			
			
			switch (commandArray[0])
			{
				case "H" :
					System.out.println("Q Query the airport information by entering the aiport code.");
					System.out.println("D find the minimum distance between two airports.");
					System.out.println("E Exit.");
					break;
				case "Q" :
					for(int i = 1; i < commandArray.length; i++)
					{
						if(airports.containsKey(commandArray[i]))
						{
							System.out.println(commandArray[i]+ " - "+airports.get(commandArray[i]));
						}
						else
							System.out.println( commandArray[i] + " code unkwown");
					}
					break;
				case "D" :
							
				try{
							Stack<String> checkPath = new Stack<>();
							if(distance.getCheapestPath(commandArray[1], commandArray[2], checkPath)==0)
							{
								System.out.println("Airports not connected");
							}
							else{
								Stack<String> path = new Stack<>();
								System.out.print(airports.get(commandArray[1]) +" to " +airports.get(commandArray[2])+" is ");
								System.out.print( distance.getCheapestPath(commandArray[1], commandArray[2], path)+" through the route:\n" );
								while(!path.isEmpty())
           							{
              							String airport = path.pop();
              							System.out.println(airport +" - " + airports.get(airport));
								}
							}
						}
						catch(Exception e)
						{
							System.out.println("Airports not connected");
						}	
					
					
					break;
				case "E" :
					repeat = false;
					break;
				case "I" :
					try
					{
						distance.addEdge(commandArray[1], commandArray[2], Double.parseDouble(commandArray[3]));
						System.out.println(airports.get(commandArray[1]) + " to " + airports.get(commandArray[2])+ " with a distance of " + commandArray[3]);
					}
					catch(Exception e)
					{
						System.out.println("Invalid input.");
					}

					break;
				default :
					System.out.println("Invalid command");
					break;
					
			}
		}while(repeat);
		keyboard.close();
		

	}
}