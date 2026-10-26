package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Draggable extends SampleController
{
	//coordinates
		private double mouseAnchorX;
		private double mouseAnchorY;
		private double initialX;
		private double initialY;
	
	//paints
		private Paint BLUEPAINT;
		private Paint REDPAINT;
		private Paint turn = null;
	
	//label
		private Label turnTxt;
		
	//game variables
		private boolean endGame = false;
		private boolean friendly;
		private Chip enemy;
		private Chip chipchip;
		private String landingTile;
		private boolean taking = false;
		private int bluePoints = 0;
		private int redPoints = 0;
	
	//make draggable:
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
	
	/////////////////////////////////////////////////////////////
	//normal moving rules:
	
	//main check method
	private void check(Chip chip)
	{
		if (endGame)
		{
			reset(chip);
		}
		else if (taking)
		{
			take(chip);
		}
		else
		{
			checkTurn(chip);
		}
	}
		
	//checks the chip color moved
	private void checkTurn(Chip chip)
	{
		if (!chip.getCircle().getFill().equals(turn))
		{
			reset(chip);
			debug.write("Not you turn\n");
		}
		else
		{
			checkMove(chip);
		}
	}

	//check where they moved the chip
	private void checkMove(Chip chip)
	{
		//makes sure user can only go diagonal fowards
		double newX = chip.getCircle().getLayoutX();
		double newY = chip.getCircle().getLayoutY();
		
		String newTile;
		if (chip.isKing())
		{
			//new tile if king, allows for backwards move sets
			newTile = kingMath(chip,newX,newY);
		}
		else
		{
			//gets next tile
			newTile = math(chip,newX,newY);
		}
		
	
		//if player makes invalid move
		if (newTile.equals("Invalid"))
		{
			debug.write("Invalid Move - must be diagonal, forwards\n");
			reset(chip);
		}
		//valid move
		else
		{
			//check if occupied
			if (isOccupy(chip, newTile))
			{
				//check if we can take the chip occupied
				if (canTake(chip,newTile))
				{
					debug.write("Valid Move - can take\n");
				}
				//can't take chip
				else
				{
					reset(chip);
				}
			}
			else
			{
				//regular move
				confirmMove(chip, newX, newY, newTile);
			}
		}
	}

	//math getting diagonal forwards
	private String math(Chip chip, double x, double y)
	{
		//dropped off the board
		if (!onBoard(x, y))
		{
			return "Invalid";
		}

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
	
	//checks if occupied
	public boolean isOccupy(Chip chip, String newTile) 
	{	
		boolean occupy = false;
		enemy = null;

		//Traverse chips
		for (Chip chips : RedChips)
		{
			//check if location is the same
			if (chips.getTile().equals(newTile))
			{
				enemy =  chips;
				occupy = true;
				break;
			}
		}
		for (Chip chips : BlueChips)
		{
			//checks if location is the same
			if (chips.getTile().equals(newTile))
			{
				enemy =  chips;
				occupy = true;
				break;
			}
		}
		
		//gets enemy if there is one
		if (enemy != null)
		{
			friendly = turn.equals(enemy.getCircle().getFill());
		}

		return occupy;
	}
	
	////////////////////////////////////////////////////////////
	//taking rules:
	
	private boolean canTake(Chip chip, String newTile)
	{
		if (friendly)
		{
			debug.write("Invalid move - tile occupied by friendly");
			return false;
		}
		
		//gets the tile behind the enemy, where the chip will land
		String nextTile = getNextTile(chip);

		if (nextTile.equals("Invalid"))
		{
			debug.write("Invalid Move - can not take, tile behind enemy is not open\n");
			return false;
		}
		else
		{
			taking = true;
			chipchip = chip;
			landingTile = nextTile;
			
			//confirms move, this is where the enemy is at
			confirmMove(chip,chip.getCircle().getLayoutX(), chip.getCircle().getLayoutY(),newTile);
			
			//find enemy and removes
			if (chip.getCircle().getFill().equals(BLUEPAINT))
			{
				for (Chip enemyChip : RedChips)
				{
					if (enemyChip.getTile().equals(chip.getTile()))
					{
						enemyChip.getCircle().setVisible(false);
						RedChips.remove(enemyChip);
						debug.write("Blue Gains Point for taking\n");
						bluePoints ++;
						break;
					}
				}
			}
			else if (chip.getCircle().getFill().equals(REDPAINT))
			{
				for (Chip enemyChip : BlueChips)
				{
					if (enemyChip.getTile().equals(chip.getTile()))
					{
						enemyChip.getCircle().setVisible(false);
						BlueChips.remove(enemyChip);
						debug.write("Red Gains Point for taking\n");
						redPoints ++;
						break;
					}
				}
			}
			
			return true;
		}
	}
	
	//get next tile, helper method for canTake() to check if we can take or not
	public String getNextTile(Chip chip)
		{
			char chipColumn = chip.getTile().charAt(0);
			int chipRow = Integer.parseInt(chip.getTile().substring(1));
			char currentColumn = enemy.getTile().charAt(0);
			int currentRow = Integer.parseInt(enemy.getTile().substring(1));

			//continue in the same diagonal direction the chip moved
			char nextColumn = (char) (currentColumn + (currentColumn - chipColumn));
			int nextRow = currentRow + (currentRow - chipRow);

			if (nextColumn < 'A' || nextColumn > 'H' || nextRow < 1 || nextRow > 8)
			{
				return "Invalid";
			}

			String nextTile=  nextColumn + "" + nextRow;

			//isOccupy overwrites enemy, so keep track of it
			Chip target = enemy;
			boolean occupied = isOccupy(chip, nextTile);
			enemy = target;
			friendly = false;

			if (occupied)
			{
				return "Invalid";
			}

			return nextTile;
		}

	//moving rules for taking a chip
	public void take(Chip chip)
	{
		//player moved wrong piece
		if (!chip.equals(chipchip))
		{
			debug.write("Invalid Move - finish your move\n");
			reset(chip);
		}
		else
		{
			//player must finish the jump by moving to the tile behind the taken piece
			double newX = chip.getCircle().getLayoutX();
			double newY = chip.getCircle().getLayoutY();

			if (onBoard(newX, newY) && tileAt(newX, newY).equals(landingTile))
			{
				confirmMove(chip, newX, newY, landingTile);
				taking = false;
				chipchip = null;
				landingTile = null;
				switchTurn();
			}
			else
			{
				debug.write("Invalid Move - finish taking by moving to " + landingTile + "\n");
				reset(chip);
			}
		}
	}

	//check if piece can turn into a king
	public void checkKing(Chip chip)
	{
		if (chip.isKing())
		{
			return;
		}

		boolean blueKing = chip.getCircle().getFill().equals(BLUEPAINT) && chip.getTile().substring(1).equals("8");
		boolean redKing = chip.getCircle().getFill().equals(REDPAINT) && chip.getTile().substring(1).equals("1");

		if (blueKing || redKing)
		{
			debug.write("king!!\n");
			chip.setKing(true);
			chip.getCircle().setStroke(Color.GOLD);
			chip.getCircle().setStrokeWidth(5);
		}
	}

	//checks if a position is on the board
	private boolean onBoard(double x, double y)
	{
		return x >= 0 && x < 520 && y >= 0 && y < 520;
	}

	//gets the tile at a position on the board
	private String tileAt(double x, double y)
	{
		return (char) ('A' + (int) (x/65)) + "" + (8 - (int) (y/65));
	}
	
	//////////////////////////////////////////////////////////////
	//king rules
	
	//moving math for the king allowing it to go backwards
	public String kingMath(Chip chip, double x, double y)
	{
		//dropped off the board
		if (!onBoard(x, y))
		{
			return "Invalid";
		}

		//math
		char currentColumn = chip.getTile().charAt(0);
		int currentRow = Integer.parseInt(chip.getTile().substring(1));
		char newColumn = (char) ('A' + (int) (x/65));
		int newRow = 8 - (int) (y/65);
		
		//for backwards and forwards
		boolean validMove = Math.abs(newColumn - currentColumn) == 1 && (newRow - currentRow == 1) || Math.abs(newColumn - currentColumn) == 1 && (newRow - currentRow == -1);

		if (validMove)
		{
			return newColumn + "" + newRow;
		}
		else
		{
			return "Invalid";
		}
	}
	
	//winner declaration
	public void isWinner()
	{
		if (RedChips.isEmpty())
		{
			debug.write("Blue Wins!\n");
			turnTxt.setText("Blue Wins!");
			endGame = true;
		}
		else if (BlueChips.isEmpty())
		{
			debug.write("Red Wins!\n");
			turnTxt.setText("Red Wins!");
			endGame = true;
		}
	}

	//makes move
 	private void confirmMove(Chip chip, double newX, double newY, String newTile)
	{
		//snap chip to the center of the new tile
		chip.getCircle().setLayoutX((newTile.charAt(0) - 'A') * 65 + 33);
		chip.getCircle().setLayoutY((8 - Integer.parseInt(newTile.substring(1))) * 65 + 33);
		debug.write(chip.getCircle().getId() + " moved from " + chip.getTile().charAt(0) + chip.getTile().substring(1));
		debug.write(" to " + newTile.charAt(0) + newTile.substring(1) + "\n");
		
		String newTileId = "" + newTile.charAt(0) + Integer.parseInt(newTile.substring(1));
		chip.setTile(newTileId);
		
		if (!taking)
		{
			switchTurn();
		}
		
		checkKing(chip);
	}
	
	//Switches turn
	private void switchTurn()
	{
		if (turn == null || turn.equals(REDPAINT))
		{
			turn = BLUEPAINT;
			turnTxt.setText("Blue's Turn");
		}
		else
		{
			turn = REDPAINT;
			turnTxt.setText("Red's Turn");
		}
		debug.write("Blue: " + bluePoints + "\n" + "Red: " + redPoints + "\n");
		debug.write("turn: " + turn + "\n");
		
		isWinner();
	}

	//reset chip to its previous spot
	private void reset(Chip chip)
	{
		chip.getCircle().setLayoutX(initialX);
		chip.getCircle().setLayoutY(initialY);
	}

	//currently taking is buggy
	//need to finish king function - buggy due to taking function
}
