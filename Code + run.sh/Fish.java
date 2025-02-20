import java.util.ArrayList;
import java.util.Collections;

public abstract class Fish extends GameElement {
	
	private int normalSpeed= 10;
	 
	//attributs 
	
    private int targetX;
    private int targetY;
 
    private boolean inLife;
    private int speed;
    
    private int boostDelay;// delay du boost apres avoir manger un insecte. 
    private int boost;		//0 ou voir variable static boost dans la classe Insect
    
    private int slowingDelay;

    private boolean onBreak; //va permettre a stopper les fishs. (touches de commandes rbmo)
    private boolean insectMode;
    private boolean pelletMode;
    
    //constructeur
    public Fish(int posX, int posY) {
	    super(posX, posY);
	    setTargetX(targetX);
	    setTargetY(targetY);

	    setInLife(true); //en vie
	    setSpeed(getNormalSpeed()); // vitesse normale
	    setBoost(1);// boost de 1 ( *1 donc rien)
	    
	    setOnBreak(false);	// initialisation des modes (touches de controles)
	    setInsectMode(false);
	    setPelletMode(false);
    }
    
    
   
    // Méthodes
 
    
    public int getTargetX() {
    	return this.targetX;
    }
    
    public void setTargetX(int targetX) {
    	this.targetX = targetX;
    }
    
    public int getTargetY() {
    	return this.targetY;
    }
    
    public void setTargetY(int targetY) {
    	this.targetY=targetY;
    }
    
    public void setTarget(int x, int y) {
    	this.targetX = x;
    	this.targetY = y;
    }
    
    public void setInLife(boolean inLife) {	//mettre en vie/tuer
    	this.inLife=inLife;
    }
    
    public boolean getInLife() {
    	return this.inLife;
    }
    
    public int getSpeed() {
    	return this.speed;
    }
    
    public void setSpeed(int speed) {	//ajuster vitesse
    	this.speed = speed;
    }
    
    
    public int getNormalSpeed() {		//recevoir la vitesse normal(initiale)
    	return this.normalSpeed;
    }
    
    public int getBoost() {
    	return this.boost;
    }
    
    public void setBoost(int boost) {			//boost (bonus)
    	this.boost = boost;
    }
    	
    public int getBoostDelay() {				//delay boost bonus
    	return this.boostDelay;
    }
    
    public void setBoostDelay(int boostDelay) {
    	this.boostDelay = boostDelay;
    }
    
	public int getSlowingDelay() {					// ralentissement delay
		return slowingDelay;
	}


	public void setSlowingDelay(int slowingDelay) {
		this.slowingDelay = slowingDelay;
	}
    
	public boolean getOnBreak() {
		return this.onBreak;
	}
	
	public void setOnBreak(boolean onBreak) {
		this.onBreak = onBreak;
	}
	
	public boolean getInsectMode() {				// mode	insecte
		return this.onBreak;
	}
	
	public void setInsectMode(boolean insectMode) {
		this.insectMode = insectMode;
	}
	
	public boolean getPelletMode() {
		return this.pelletMode;
	}
	
	public void setPelletMode(boolean pelletMode) {			
		this.pelletMode = pelletMode;
	}
	
	
    public abstract void defineTarget(Board board);		//methode abstraite, chaque fish a sa propore méthode
 
//    		
    public boolean checkTarget() {
		
    	if((getPosX() == getTargetX()) && (getPosY() == getTargetY())) {	// check si la pos = le target
		  return false;
    	 
    	}
    	return true;
	
	}
	

	

    public void move(Board board) {	// méthode move, la même pour tous les poissons.
        
        ArrayList<Integer> x_moveOptions = new ArrayList<Integer>() ;
        ArrayList<Integer> y_moveOptions = new ArrayList<Integer>() ;
        ArrayList<Double> distances = new ArrayList<Double>() ;		//tableau de distances
        	
        for (int i = -1 ; i <= 1 ; i++){						//je vais check les positions autour de moi afin de choisir la + proche à mon target(9 position autour de moi.
            for (int j = -1 ; j <= 1 ; j++){
                int test_pos_x = getPosX() +i* getSpeed() * getBoost();		
                int test_pos_y = getPosY() +j* getSpeed() * getBoost();  
                if (board.isValidPosition(test_pos_x, test_pos_y)){
                	 
                		x_moveOptions.add(test_pos_x);
                        y_moveOptions.add(test_pos_y);
                	//}
                }
            }
        }
      
        for (int i=0; i < x_moveOptions.size() ; i++){
            Double distance = getDistance(getTargetX(), getTargetY(), x_moveOptions.get(i), y_moveOptions.get(i));
            distances.add(distance);
        } 
 
        if (!distances.isEmpty()) {
         
        	//Juste en dessous pour mes poissons je recupere ma liste de distances et je prend la distance la + courte. Ensuite, suite a des beugs en manipulant la vitesse ( par exemple la vitesse de base de 10 et en la changeant a 21 ou 37 par exemple, le fish va a un moment rester figé car il est a X=200 et son targetX=205 mais son dotSize(ici Speed) à 31. il ne parviendra pas a atteindre son target sans verifier si la distance entre sa position et celle de son target est + petite de son dotSize(speed).
        double min = Collections.min(distances);
        if(min<getDistance(getPosX(), getPosY(), getTargetX(), getTargetY())) {
            int min_index = distances.indexOf(min);
                
            setPosX(x_moveOptions.get(min_index));
            setPosY(y_moveOptions.get(min_index));
        }else {
        	setPosX(getTargetX());
        	setPosY(getTargetY());
            }
            //
        	
        }
        //Verification du boost
        if(getBoostDelay()>1) {
        	setBoostDelay(getBoostDelay()-1);
        }else{
        	setBoost(1);
        }


        
        	 if(getSlowingDelay()>0) {				// ici si le poisson est en ralentissement, cela vient du pellet.
             	setSlowingDelay(getSlowingDelay()-1);
             	if(getSlowingDelay()==0) {
             		setSpeed(getNormalSpeed());
             	}      
        	 }
        	 
         }
    

    
     public double getDistance(int xFish, int yFish, int posX1, int posY1) {		// recupere la distance entre un point a et un point b (en decimale)
		
		int x_dist = posX1-xFish;
		int y_dist = posY1-yFish;
		return Math.sqrt(Math.pow(x_dist, 2)+Math.pow(y_dist, 2));
	}

 }
