import javafx.scene.shape.Circle;

public class Chip
{
	private Circle circle;
	private String occupy;
	private boolean alive;
	private boolean king;

	public Chip(Circle circ, String oc)
	{
		alive = true;
		occupy = oc;
		circle = circ;
		king = false;
	}
	
	public Circle getCircle()
	{
		return circle;
	}
	
	public String getTile()
	{
		return occupy;
	}
	
	protected void setTile(String rec)
	{
		occupy = rec;
	}
}
