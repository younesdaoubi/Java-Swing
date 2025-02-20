public class Pellet extends GameElement {
	
	public static int slowing = 3;
	
	
	private boolean isEat;

	public Pellet(int posX, int posY) {
		super(posX, posY);
 	}


	

	@Override
	public String getType() {
 		return "pellet";
	}
	
	public static String getPathToRedInsectImage() {
		return "pellet.png";
	}
	
	public boolean isEat() {
		return isEat;
	}

	public void setEat(boolean isEat) {
		this.isEat = isEat;
	}

}
