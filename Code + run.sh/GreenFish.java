import java.util.ArrayList;
import java.util.Collections;

public class GreenFish extends Fish {

	public GreenFish(int posX, int posY) {
		super(posX, posY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void defineTarget(Board board) {
	
		ArrayList<Fish> fishList = new ArrayList<Fish>(board.getFishList());
		ArrayList<Double> distances = new ArrayList<Double>(); // liste de distances
		
		 int index=0;
		 Double min=0.0; // distance min
		 int indexMin=0; //poisson le plus proche
 
		for(Fish fish : fishList) {
			
 			distances.add(board.getDistance(fish.getPosX(), fish.getPosY(), getPosX(), getPosY()));
			if(fish != this) { // si ce n'est pas moi :
				if(min>distances.get(index)) {
					min = distances.get(index);
					indexMin=index;
				}
				}
			index++;
		}
		
		
		
		 
		
		setTargetX(fishList.get(indexMin).getPosX());
		setTargetY(fishList.get(indexMin).getPosY());
		setTargetY(board.getRandomCoordinate(board.getHeight()));
 
		
	}
	
	public static String getPathToImage(){     
        return "greenFish.png";
    }

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "greenFish";
	}

}
