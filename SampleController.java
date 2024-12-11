package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class SampleController implements Initializable
{	
	//Control
		//label
		@FXML private Label turnTxt;
		//pane
		@FXML private Pane CheckerBoard;
		//buttons
		@FXML private Button ResetButton;
		@FXML private Button DebugButton;
		//blue chips
		@FXML private Circle Blue1, Blue5, Blue9, Blue2, Blue6, Blue10, Blue3, Blue7, Blue11, Blue4, Blue8, Blue12;
		//red chips
		@FXML private Circle Red1, Red5, Red9, Red2, Red6, Red10, Red3, Red7, Red11, Red4, Red8, Red12;
	
		
	//Lists
		protected static List<Chip>RedChips = new ArrayList<>();
		protected static List<Chip>BlueChips = new ArrayList<>();

	//Debug stuff
		Debug debug = new Debug();
		
	//adds blue chips to list
	private void addBlue()
	{
		BlueChips.add(new Chip(Blue1,"A1"));	BlueChips.add(new Chip(Blue5,"H2"));	BlueChips.add(new Chip(Blue9,"A3"));	
		BlueChips.add(new Chip(Blue2,"C1"));	BlueChips.add(new Chip(Blue6,"F2"));	BlueChips.add(new Chip(Blue10,"C3"));	
		BlueChips.add(new Chip(Blue3,"E1"));	BlueChips.add(new Chip(Blue7,"D2")); 	BlueChips.add(new Chip(Blue11,"E3"));	
		BlueChips.add(new Chip(Blue4,"G1"));	BlueChips.add(new Chip(Blue8,"B2"));	BlueChips.add(new Chip(Blue12,"G3"));
	}
	
	//adds red chips to list
	private void addRed()
	{
		RedChips.add(new Chip(Red1,"B8"));	RedChips.add(new Chip(Red5,"G7"));	RedChips.add(new Chip(Red9,"B6"));	
		RedChips.add(new Chip(Red2,"D8"));	RedChips.add(new Chip(Red6,"E7"));	RedChips.add(new Chip(Red10,"D6"));	
		RedChips.add(new Chip(Red3,"F8"));	RedChips.add(new Chip(Red7,"C7"));	RedChips.add(new Chip(Red11,"F6"));	
		RedChips.add(new Chip(Red4,"H8"));	RedChips.add(new Chip(Red8,"A7"));	RedChips.add(new Chip(Red12,"H6"));
	}
	
  	// Reset scene method 
 	public void resetScene(ActionEvent Event) 
 	{ 
 		try 
 		{ 
 			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml")); 
 			Parent root = loader.load(); 
 			Stage stage = (Stage) CheckerBoard.getScene().getWindow(); 
 			stage.setScene(new Scene(root)); 
 		} 
 		catch (Exception e) 
 		{ 
 			e.printStackTrace(); 
 		}
 	}
 	
 	//on startup
 	@Override
 	public void initialize(URL url, ResourceBundle RB) 
 	{
 		//sets variables at start up
 		addRed();
 		addBlue();
 		
 		//starts game
 		startGame();
 		
 	}
 	
 	private void startGame()
 	{
 		debug.write("Starting...\n");
 		Draggable draggable = new Draggable(turnTxt);
 	}
 	
 	public void debugButton(ActionEvent Event)
 	{
 		debug.openFile();
 	}
}

