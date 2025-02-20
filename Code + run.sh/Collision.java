import java.awt.Color;
import java.awt.Graphics;

public class Collision {
    
	// Attributs
	
	private int x; 
    private int y; 
    private int width;
    private int height;
    private int speed;
    private Color color;
    

    private boolean moving;
	private boolean up;
    private boolean down;
    
    
    private static final int maxHeight = Board.WIDTH/10;	/// sert à bien change les tailles et disposition des collisions en cas de changement de width ou height du board.
    private static final int minHeight = Board.WIDTH/5; 
    private static final int minWidth = Board.WIDTH/10;// les valeures se modifierons automatiquement
    private static final int maxWidth = Board.WIDTH/5;
    
  
    //Constructeur
    public Collision(int x, int y, int width, int height, Color color) {	
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        setMoving(true);
        setUp(true);
        
    }
 
    //Méthodes
    
    private void setInitialDirection() {
		// TODO Auto-generated method stub
    	// j'initialise ici ma direction (haut/bas) de facon random.
    	int up=1;
    	int down=2;	 
    	int rand = (int)((Math.random()*2)+1);
    	if(rand==up) {
    		setUp(true);
    	}else if(down==2) {
    		setDown(true);
    	}
	}
 
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
    
	public boolean isMoving() {
		return moving;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
    public boolean isUp() {
		return up;
	}


	public boolean isDown() {
		return down;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
 
	public void move(Board board) {	//
	    // La méthode move() sera utilisée pour faire bouger la collision verticalement

	    if (isUp()) {
	        if (getY() > board.getCollisionsBordsspacing()) { // 50 sert a ne pas ecraser les fish. Je mexplique : si les collisions vont de haut en baus jusquaux bords, certains poissons peuvent etre ecrasés(s'ils se trouvent juste au dessus ou en dessous au moment ou la collision est proches des bords.
	        	setY(getY() - board.DOT_SIZE);
	        } else {
	            setUp(false);
	            setDown(true);
	        }
	    } else if (isDown()) {
	        if (getY() + getHeight() < board.getHeight() - board.getCollisionsBordsspacing()) { 
	            setY(getY() + board.DOT_SIZE);
	        } else {
	            setUp(true);
	            setDown(false);
	        }
	    }
	}
}

    	
 
