import java.util.ArrayList;
import java.util.Collections;

public class BlueFish extends Fish {
	

	public BlueFish(int posX, int posY) {
		super(posX, posY);
	}

	@Override
	public void defineTarget(Board board) {

		ArrayList<Fish> fishList = new ArrayList<Fish>(board.getFishList());
		

		//je verifie si il ya des poissons bleu ou mauve, sinon je lui random des positions afin que si il est le seul bleu et pas de mauves, il ne reste pas sur place.
		
		int count=0;//compteur;
		for(Fish fish : fishList) {
			if(fish.getType().equals("purpleFish")||fish.getType().equals("blueFish")) {
				count++;
			}
		}
		if(!(count>1)) {	//si il n y a que 1 poisson bleu ou mauve dans laquarium, cest certainemnt celui ci dont on va random ses targets.
			setTargetX(board.getRandomCoordinate(board.getWidth()));
			setTargetY(board.getRandomCoordinate(board.getHeight()));
		}else {//sinon, on prend en charge les poissons qui l'entourent
			double min=0;  // min = distance min, cad poisson bleu ou purple le plus proche.
			int index=0; // index du poisson bleu/purple le plus proche
			
			for(int i=0;i<fishList.size();i++) {
				if((fishList.get(i).getType()=="purpleFish"||fishList.get(i).getType()=="blueFish") && fishList.get(i)!=this) {	// bien sur, !=this sinon on va rester sur place
					if(index==0) {
						index=i;
						min = board.getDistance(fishList.get(i).getPosX(), fishList.get(i).getPosY(), getPosX(), getPosY());
					}else {
						if(min >board.getDistance(fishList.get(i).getPosX(), fishList.get(i).getPosY(), getPosX(), getPosY())) {
							index=i;
							min = board.getDistance(fishList.get(i).getPosX(), fishList.get(i).getPosY(), getPosX(), getPosY());
						}
					}
				}
			}
			//je defini mes target 
			setTargetX(fishList.get(index).getPosX());
			setTargetY(fishList.get(index).getPosY());
		}
		

 
		
	}

	@Override
	public String getType() {
 		return "blueFish";
	}
	
	
	public static String getPathToImage(){     
        return "blueFish.png";
    }
	

}
