import java.io.FileWriter;
import java.io.IOException;

public class Debug 
{
	FileWriter debugWriter;
	
	public Debug()
	{
		try {
			debugWriter = new FileWriter("debug.txt");
		}
		catch(IOException e)
		{
			System.out.println("error - making debugWriter");
		}
	}
	
	
	//writes to file debug stuff
	public void write(String str)
	{
		try {
			debugWriter.write(str);
			debugWriter.flush();
		}
		catch(IOException e)
		{
			System.out.println("error - writting to debugWritter");
		}
	}
}
