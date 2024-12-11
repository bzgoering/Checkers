package application;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;


public class Debug 
{
	BufferedWriter debugWriter;
	File file = new File("debug.txt");
	
	protected Debug()
	{
		try {
			debugWriter = new BufferedWriter(new FileWriter(file));
		}
		catch(IOException e)
		{
			System.out.println("error - making debugWriter");
		}
	}
	
	
	//writes to file debug stuff
	protected void write(String str)
	{
		try 
		{
			debugWriter.write(str);
			debugWriter.flush();
		}
		catch(IOException e)
		{
			System.out.println("error - writting to debugWritter");
		}
	}
	
	public void openFile()
	{
		//opens file
		if (Desktop.isDesktopSupported())
		{
			Desktop desktop = Desktop.getDesktop();
			if (file.exists())
			{
				try {
					desktop.open(file);
				}
				catch(IOException e)
				{
					System.out.println("Error opening file");
				}
			}
			else
			{
				System.out.println("Error - File doesn't exist");
			}
		}
		else
		{
			System.out.println("Error - Desktop not supported");
		}
	}
}
