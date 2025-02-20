import java.util.ArrayList;
import java.util.Collections;

public class OrangeFish extends Fish {
	
	 
 	public OrangeFish(int posX, int posY, int targetX, int targetY) {
		super(posX, posY);
		 
	}

	public static String getPathToImage(){     
        return "orangeFish.png";
    }

	@Override
	public String getType() {
 		return "orangeFish";
	}
	
 
	
	
	@Override
	public void defineTarget(Board board) {
 

		
		if(!getInsectMode()||(!getPelletMode())) {
			setTargetX(board.getRandomCoordinate(board.getWidth()));
			setTargetY(board.getRandomCoordinate(board.getHeight()));
		}else if(getInsectMode()){
			//en mode insect on recherche linsecte le + proche : 
			ArrayList<Double> distances = new ArrayList<Double>();
 			for(Insect insect : board.getInsectList()) {
				distances.add(board.getDistance(insect.getPosX(), insect.getPosY(), getPosX(), getPosY()));	 
 			}
			
			double min = Collections.min(distances);
			int index = distances.indexOf(min);
			
			setTargetX(board.getInsectList().get(index).getPosX());
			setTargetY(board.getInsectList().get(index).getPosY());

		}else if(getPelletMode()) { // pellet mode
			
			ArrayList<Double> distances = new ArrayList<Double>();

			for(Pellet pellet : board.getPelletList()) {
				distances.add(board.getDistance(pellet.getPosX(), pellet.getPosY(), getPosX(), getPosY()));	 
				//index
			}
			
			double min = Collections.min(distances);
			int index = distances.indexOf(min);
			
			setTargetX(board.getInsectList().get(index).getPosX());
			setTargetY(board.getInsectList().get(index).getPosY());
		}
		
		
	}


	} 
