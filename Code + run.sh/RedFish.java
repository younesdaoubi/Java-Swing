import java.util.ArrayList;

public class RedFish extends Fish {

	   private int biteSize = 15;//taille de la bouchée. 

 	
	public RedFish(int posX, int posY) {
		super(posX, posY);
 		
	}

	
	@Override
	public void defineTarget(Board board) {
 		
		ArrayList<Fish> fishList = new ArrayList<Fish>(board.getFishList());
		ArrayList<Double> distances = new ArrayList<Double>();
		
	
			
		double min=0;  // min = distance min, cad poisson le plus proche.
		int index=0; // index du poisson non rouge le plus proche
		int nbRedFish=0; // compte le nombre de redFish
		for(Fish fish : fishList) {
				
				distances.add(board.getDistance(fish.getPosX(), fish.getPosY(), getPosX(), getPosY()));	// j'inclus tous mes poissons dans cette liste, meme les rouges afin de gerer les index de ma liste.
				
		}
		for(int i=0;i<distances.size();i++) {
 				if(!(fishList.get(i).getType()==getType())) { // != à un poisson rouge

					if(min==0) {
						min = distances.get(i);
						index=i;
						}
				
				else {
					if(distances.get(i)<min) {			// je cherche le poisson non rouge le plus proche
 							min=distances.get(i);
							index=i;	
							}
						}
 					}
 				else {
 					nbRedFish++;		//le nombre poisson rouge va me servir a ne pas les faire tourner en rond quand pas de poisson autre que rouge.
				}
			}
			
			if(nbRedFish<distances.size()) { // cela sert a ne pas donner de nouveau target a un rouge si aucuns autres poissons d'autres couleurs dans l'aquarium
				setTarget(fishList.get(index).getPosX(), fishList.get(index).getPosY());
				if((min<biteSize) ){  // Le redFish peu avaler un autre poisson a une distance de biteSize.
					fishList.get(index).setInLife(false);			
				}
			}else {
				//Je verifie avant tout si il ya d'autre poissons present dans l'aquarium appart les rouge, sinon je devrais donner une random coordinate, sans laquelle ils restent immobiles car pas dautre poissons a poursuivre.
				if(getPosX()==getTargetX() && getPosY()== getTargetY()) { // on attend quil atteigne son target car il est defini pas defaut qu'un nouveau target est recherché dans Board ( action performed) à chaque cycle
					setTarget(board.getRandomCoordinate(board.getWidth()), board.getRandomCoordinate(board.getHeight()));  // je defini random coordinates ici afins qu'ils se soient pas immobiles.
					
					
				}
			}
		}

	
	
	@Override
	public String getType() {
		return "redFish";
	}
	
	
	public static String getPathToImage(){     
        return "redFish.png";
    }
	
	

}
