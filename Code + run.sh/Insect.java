public class Insect extends GameElement {

	
	public static int boost = 2; // Booster biTurbo :p
	
	
	public static String [] insectTypeTab = {"greenInsect", "brownInsect", "redInsect","blackInsect"};
		
	
	

	
	private String type;
	private boolean inLife;
	
	
	
	private int noDelay=0; // avec un insect noir le poisson aura un delai de 0, car meurt directement car empoisoné.
	private int shortDelay=30;
	private int mediumDelay=50;
	private int longDelay=70;
	
	
	
	
	
	
    public Insect(int pos_x, int pos_y, String type) {
        super(pos_x, pos_y);
        //this.type = type;
        if(verifyTypeExist(type)) {
        	setType(type);
        }else {
            throw new IndexOutOfBoundsException("Ce type est inexistant. vérifier tableau insectTypeTab.");

        }
        
        
        setInLife(true);
 }

    
    private boolean verifyTypeExist(String type) {
	// TODO Auto-generated method stub
	
    	for(String insect : insectTypeTab) {
    		if(insect == type) {
    			return true;
		}
	}
    	return false;
}
 
	
	public static String getPathToGreenInsectImage() {
		return "greenInsect.png";
	}
	
	public static String getPathToBrownInsectImage() {
		return "brownInsect.png";
	}
	
	public static String getPathToRedInsectImage() {
		return "redInsect.png";
	}
	
	public static String getPathToBlackInsectImage() {
		return "blackInsect.png";
	}
	
	
	@Override
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getBoostDelay(){
		int delay = 0;
		if(getType()==insectTypeTab[0]) {
			delay = shortDelay;
		}else if(getType()==insectTypeTab[1]) {
			delay = mediumDelay;
		}else if(getType()==insectTypeTab[2]) {
			delay = longDelay;
		}else if(getType()==insectTypeTab[3]) {
			delay = noDelay;
}
		return delay;
}


	public boolean isInLife() {
		return inLife;
	}


	public void setInLife(boolean inLife) {
		this.inLife = inLife;
	}
	
	
}





