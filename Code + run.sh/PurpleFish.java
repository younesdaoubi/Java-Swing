import java.util.ArrayList;
import java.util.Collections;

public class PurpleFish extends Fish{

	public PurpleFish(int posX, int posY) {
		super(posX, posY);
		// TODO Auto-generated constructor stub
 	}

	@Override
	public void defineTarget(Board board) {	
		// TODO Auto-generated method stub

		ArrayList<Fish> fishList = new ArrayList<Fish>(board.getFishList()); // copie de ma liste de fishs provenant de mon board.
 
		
		double min=0;  // min = distance min, cad poisson rouge le plus proche.
		int index=0; // index du poisson rouge le plus proche
		
		for(int i=0;i<fishList.size();i++) {		// je dois recupérer le poisson rouge le plus proche afin de l'éviter      
			if(fishList.get(i).getType().equals("redFish")) {
				if(index==0) {
					index=i;
					min = board.getDistance(fishList.get(i).getPosX(), fishList.get(i).getPosY(), getPosX(), getPosY()); // je recuperes les distances et je vais ensuite choisir la distance la + longue par rapport au redFish le + proche
				}else {
					if(min >board.getDistance(fishList.get(i).getPosX(), fishList.get(i).getPosY(), getPosX(), getPosY())) {
						index=i;
						min = board.getDistance(fishList.get(i).getPosX(), fishList.get(i).getPosY(), getPosX(), getPosY());
					}
				}
			}
			
	}
 
		ArrayList<Integer> x_moveOptions = new ArrayList<Integer>() ;
        ArrayList<Integer> y_moveOptions = new ArrayList<Integer>() ;
        ArrayList<Double> distances = new ArrayList<Double>() ;
        
        for (int i = -1 ; i <= 1 ; i++){
            for (int j = -1 ; j <= 1 ; j++){
                int test_pos_x = getPosX() +i* board.DOT_SIZE;//10;					 
                int test_pos_y = getPosY() +j* board.DOT_SIZE;
                if (board.isValidPosition(test_pos_x, test_pos_y)){ // pervPos sert uniquement pour les collisions.
                    x_moveOptions.add(test_pos_x);
                    y_moveOptions.add(test_pos_y);
                }
            }
        }
      
        for (int i=0; i < x_moveOptions.size() ; i++){
            Double distance = getDistance(fishList.get(index).getPosX(), fishList.get(index).getPosY(), x_moveOptions.get(i), y_moveOptions.get(i));
            distances.add(distance);
        } 
        //ici je choisi la distance la + longue face a ce redFish
        if (!distances.isEmpty()) {
	        double max = Collections.max(distances); // on cherche a aller a l'opposé. Le purpleFish va alors calculer la distance la plus longue (MAX) parmis ses positions qui l'entoure ET la position du redFish le plus proche.
	        int maxIndex = distances.indexOf(max);
	        setTargetX(x_moveOptions.get(maxIndex));
			setTargetY(y_moveOptions.get(maxIndex));
        }
		 
 	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "purpleFish";
	}
	
	public static String getPathToImage(){     
        return "purpleFish.png";
    }
	
	
	
	

}
