import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

public class Draggable extends SampleController
{
	//coordinates
		private double mouseAnchorX;
		private double mouseAnchorY;
		private double initialX;
		private double initialY;

	//boolean
		private boolean endGame = false;
	
	//paints
		private Paint BLUEPAINT;
		private Paint REDPAINT;
		private Paint turn = null;
	
	//label
		private Label turnTxt;
	
	//constructor
	protected Draggable(Label txt)
	{
 		REDPAINT = RedChips.get(0).getCircle().getFill();
		BLUEPAINT = BlueChips.get(0).getCircle().getFill();
		this.turnTxt = txt;
		switchTurn();
		
		for(int x = 0; x < RedChips.size(); x++)
		{
			makeDraggable(RedChips.get(x));
		}
		
		for(int x = 0; x < BlueChips.size(); x++)
		{
			makeDraggable(BlueChips.get(x));
		}
	}
 	
 	//makes chips draggable
	private void makeDraggable(Chip chip)
	{
		chip.getCircle().setOnMousePressed(mouseEvent -> 
		{
			mouseAnchorX = mouseEvent.getX();
			mouseAnchorY = mouseEvent.getY();
			initialX = chip.getCircle().getLayoutX(); 
			initialY = chip.getCircle().getLayoutY();
		});
	
		chip.getCircle().setOnMouseDragged(mouseEvent -> 
		{
			chip.getCircle().setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
			chip.getCircle().setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
		});
		chip.getCircle().setOnMouseReleased(mouseEvent -> 
		{
			check(chip);
		});
	}
	
	//main check method
	private void check(Chip chip)
	{
		checkTurn(chip);
	}
	
	//checks the chip color moved
	private void checkTurn(Chip chip)
	{
		if (!chip.getCircle().getFill().equals(turn))
		{
			reset(chip);
			System.out.println("Not you turn");
		}
		else
		{
			checkMove(chip);
		}
	}
	
	//math getting diagonal forwards
	private String math(Chip chip, double x, double y)
	{	
		//math
		char currentColumn = chip.getTile().charAt(0);
		int currentRow = Integer.parseInt(chip.getTile().substring(1));
		char newColumn = (char) ('A' + (int) (x/65));
		int newRow = 8 - (int) (y/65);

		//check if diagonal
		boolean validMove;
		if (turn.equals(BLUEPAINT))
		{
			validMove = Math.abs(newColumn - currentColumn) == 1 && (newRow - currentRow == 1);
		}
		else
		{
			validMove = Math.abs(newColumn - currentColumn) == 1 && (newRow - currentRow == -1);
		}
		
		if (validMove)
		{
			return newColumn + "" + newRow;
		}
		else
		{
			return "Invalid";
		}
	}
	
	//check where they moved the chip
	private void checkMove(Chip chip)
	{
		//makes sure user can only go diagonal fowards
		double newX = chip.getCircle().getLayoutX();
		double newY = chip.getCircle().getLayoutY();
		
		String newTile = math(chip,newX,newY);
		
		//invalid move
		if (newTile.equals("Invalid"))
		{
			System.out.println("Invalid Move - must be diagonal, forwards");
			reset(chip);
		}
		//sets chip if tile is open
		else if (!isOccupy(chip, newTile))
		{
			chip.getCircle().setLayoutX(newX);
			chip.getCircle().setLayoutY(newY);
			System.out.print(chip.getCircle().getId() + " moved from " + chip.getTile().charAt(0) + chip.getTile().substring(1));
			System.out.println(" to " + newTile.charAt(0) + newTile.substring(1));
			
			updateChipTile(chip, newTile.charAt(0), Integer.parseInt(newTile.substring(1)));
			switchTurn();
		}
	}
	
	//updates the tile chip is on
	private void updateChipTile(Chip chip, char newColumn, int newRow)
	{
		String newTileId = "" + newColumn + newRow;
		chip.setTile(newTileId);
	}
	
	public boolean isOccupy(Chip chip, String newTile) 
	{	
		boolean occupy = false;
		String occupyChip = "";
		
		//check red
		for (Chip chips : RedChips)
		{
			if (chips.getTile().equals(newTile))
			{
				occupyChip =  chip.getCircle().getId();
				occupy = true;
				break;
			}
		}
		
		//check blue
		for (Chip chips : BlueChips)
		{
			if (chips.getTile().equals(newTile))
			{
				occupyChip =  chip.getCircle().getId();
				occupy = true;
				break;
			}
		}
		
		//looks if we can take
		if (occupy)
		{
			//allows move and next move to take
			if (canTake(chip))
			{
				System.out.println(chip.getCircle().getId() + " can take");
				return false;
			}
			//resets chip if can't take
			else
			{
				System.out.println("Error - occupied by: " + occupyChip + ". You can't Take it");
				reset(chip);
				return true;
			}
		}
		else
		{
			return occupy;
		}
	}
	
	private boolean canTake(Chip chip)
	{
		boolean canTake = false;
		//keep initial spot (should be on top of enemy)
		//looks if next spot is open (call math) and let them stay there, turn will be over
		//if it isn't, go back of OG spot and retry
		
		
		return canTake;
	}
	//add take method that removes chip from list and board
	//add king method that allows chip to move backwards diagonal too
	//add point system based of takes
	//add win if a list of chip is empty\
	
	//figure out how to change label, having issues
	
	
	
	
	
	
	//Switches turn
	private void switchTurn()
	{
		if (turn == null || turn.equals(REDPAINT))
		{
			turn = BLUEPAINT;
			turnTxt.setText("Blue's Turn");
			System.out.println("turn: " + turn);
		}
		else
		{
			turn = REDPAINT;
			turnTxt.setText("Red's Turn");
			System.out.println("turn: " + turn);

		}
	}

	//reset chip to its previous spot
	private void reset(Chip chip)
	{
		chip.getCircle().setLayoutX(initialX);
		chip.getCircle().setLayoutY(initialY);
	}
}
