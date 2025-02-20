import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

public class Board extends JPanel implements ActionListener {
	
	private String [] waterTemperatureTab = { "cold", "lukewarm", "hot" }; //Tableau de température 
	private Color [] waterTemperaturetabColor = { new Color(0, 128, 255),  new Color(102, 178, 255),new Color(173, 216, 230) };//couleures de laquarium.
	private String currentWaterTemperature;
	
    private static final int B_WIDTH = 800;
    private static final int B_HEIGHT = 500;
    public final int DOT_SIZE = 10;
    private final int DELAY = 100; //  a l'aise a manipuler les secondes (*10 pour 1000mils soit 1 seconde)
    private int nbFishInit = 8; // j'initialise a mettre tous les fish au nombre de 5 pour avoir une bonne diversifications dans l'aquarium
    private int nbRedFishInit = 2;// je met moins de poissons rouge sinons mangent tous les autre trop rapidement
    private int nbInsectAndPellet = 2;
    //listes d'element du jeu
    private ArrayList<Insect> insectList; // insectes
    private ArrayList<Fish> fishList;	//poissons
    private ArrayList<Pellet> pelletList;	//pastilles commestibles

    
    HashMap<String, ImageIcon> gameElementImageMap ; // images dans une hashmap
    
    private boolean inGame = true;
    private Timer timer;	// temps

    private int collisionsBordsspacing = 50;//sert a ne pas ecraser les fish. Je mexplique : si les collisions vont de haut en baus jusquaux bords, certains poissons peuvent etre ecrasés(s'ils se trouvent juste au dessus ou en dessous au moment ou la collision est proches des bords (0 ou 
 
    //Liste de collisions
    private ArrayList<Collision> collisionList;
    
    public static boolean [][] tabCollisions;    // va servir a ne pas placer de poisson dans une collision à l'initialisation
    private int nbCollisions = 4; // nb collisions définis
    
    //Bonus température de l'eau à chaud
    private int hotTemperatureBonus = 3 ; //Bonus triturbo, plus rapide que n'importe quel autre poissons.
    

    ///// Reproduction /////
    HashMap<String, Integer> positionAndTypeMap;	//HashMap pour stocker les positions et les types afin de vérifier si il y a reproduction.
    
    private int maxFishInBoard = 60; // 60 poissons max.
   
    public int getWidth() {
    	return this.B_WIDTH;
    }
    
    public int getHeight() {
    	return this.B_HEIGHT;
    }
    
   public ArrayList<Fish> getFishList(){	//get la liste de poissons
	return this.fishList;
	   
   }
   
   public ArrayList<Insect> getInsectList(){ //get la liste d'insectes
		return this.insectList;   
	   }
   
   public ArrayList<Pellet> getPelletList(){ //get la liste de pastilles
		return this.pelletList;   
	   }
   
      
   public String getCurrentWaterTemperature() {	// get la temperature actuelle de l'eau
   	return this.currentWaterTemperature;
   }
   
   public void setCurrentWaterTemperature(String currentWaterTemperature) {	//Set la température de l'eau
	   this.currentWaterTemperature = currentWaterTemperature;
   }
   
   public int getCollisionsBordsspacing() {
	   return this.collisionsBordsspacing;	//  get les board a ne pas depasser pour ne pas ecraser les poissons (collisions)
   }
    
   
    public Board() {
        
        initBoard(); // initialisation 
            
    }
    
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(waterTemperaturetabColor[1]);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
        
 
    }
    
    
    public int getHotTemperatureBonus() {
    	return this.hotTemperatureBonus;
    }
 

	private void loadImages() {
    
		gameElementImageMap = new HashMap<String, ImageIcon>();
		
		//poissons
        ImageIcon iiof = new ImageIcon(OrangeFish.getPathToImage());
        gameElementImageMap.put("orangeFish", iiof);
        //orangeFish = iiof.getImage();
        
        ImageIcon iirf = new ImageIcon(RedFish.getPathToImage());
        gameElementImageMap.put("redFish", iirf);
        
        ImageIcon iibf = new ImageIcon(BlueFish.getPathToImage());
        gameElementImageMap.put("blueFish", iibf);
        //orangeFish = iiof.getImage();
        
        ImageIcon iipf = new ImageIcon(PurpleFish.getPathToImage());
        gameElementImageMap.put("purpleFish", iipf);
        
        ImageIcon iigf = new ImageIcon(GreenFish.getPathToImage());
        gameElementImageMap.put("greenFish", iigf);
        
        //insectes
        ImageIcon iigi = new ImageIcon(Insect.getPathToGreenInsectImage());
        gameElementImageMap.put("greenInsect", iigi);
        
        ImageIcon iibri = new ImageIcon(Insect.getPathToBrownInsectImage());
        gameElementImageMap.put("brownInsect", iibri);
        
        ImageIcon iiri = new ImageIcon(Insect.getPathToRedInsectImage());
        gameElementImageMap.put("redInsect", iiri);
        
        ImageIcon iibli = new ImageIcon(Insect.getPathToBlackInsectImage());
        gameElementImageMap.put("blackInsect", iibli);
        
        //pellet
        ImageIcon iip = new ImageIcon(Pellet.getPathToRedInsectImage());
        gameElementImageMap.put("pellet", iip);
}

    private void initGame() {

    	setCurrentWaterTemperature(waterTemperatureTab[1]); 	// Initialisation de l'aquarium à l'eau tiède
     	initCollisions();   //creation de mes collisions , 4 collisions de taille random et de position random, chacune occupant l'espace de 1/4 du width du board (afin de ne pas se superposer) et prenant tout le height
 
        insectList = new ArrayList<Insect>();	
        fishList = new ArrayList<Fish>();
        pelletList = new ArrayList<Pellet>();
        
        initFishs(); //ajout de fish
        initInsects(); // ajout d'insectes
        initPellets(); // ajouts de pastilles
		System.out.println(insectList.size());

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initInsects() {
		
    	for(int i=0;i<nbInsectAndPellet;i++) {		//ajout de 2 insectes de chaques types
    		insectList.add(new Insect(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight()), Insect.insectTypeTab[0]));  //vert
    		insectList.add(new Insect(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight()), Insect.insectTypeTab[1]));  //brun
    		insectList.add(new Insect(getRandomCoordinate(getWidth()),getRandomCoordinate(getHeight()), Insect.insectTypeTab[2]));  //rouge
    		insectList.add(new Insect(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight()), Insect.insectTypeTab[3]));  //noir
    		
    	}
		
	}

	private void initPellets() {		

    	for(int i=0;i<nbInsectAndPellet;i++) {		//ajout de 2 pellets
    		pelletList.add(new Pellet(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight())));
    	}
	}

	private void initFishs() {
 
    	
    	addRedFish(nbRedFishInit);	// 2 rouges
    	addOrangeFish(nbFishInit);// jajoute 5 fishs mauves, oranges,bleues,...
    	addBlueFish(nbFishInit);
    	addPurpleFish(nbFishInit);
    	addGreenFish(nbFishInit);
       
      
	}

	private void addGreenFish(int nb) {

		int xPos=0;
    	int yPos=0;
      for(int i = 0; i < nb ; i++){
      	do {
      		xPos=getRandomCoordinate(B_WIDTH);
      		yPos = getRandomCoordinate(B_HEIGHT);
      	}while(tabCollisions[yPos][xPos]!= false);	// sert afin de ne pas savoir de fish dans les collisions commme les collisions sont initialisées avant.
      		fishList.add(new GreenFish(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight())));
      }
		
	}

	private void addPurpleFish(int nb) {
		
		int xPos=0;
    	int yPos=0;
      for(int i = 0; i < nb ; i++){
      	do {
      		xPos=getRandomCoordinate(B_WIDTH);
      		yPos = getRandomCoordinate(B_HEIGHT);
      	}while(tabCollisions[yPos][xPos]!= false);	// sert afin de ne pas savoir de fish dans les collisions commme les collisions sont initialisées avant.
      		fishList.add(new PurpleFish(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight())));
      }
		
	}

	private void addBlueFish(int nb) {
	
		int xPos=0;
    	int yPos=0;
      for(int i = 0; i < nb ; i++){
      	do {
      		xPos=getRandomCoordinate(B_WIDTH);
      		yPos = getRandomCoordinate(B_HEIGHT);
      	}while(tabCollisions[yPos][xPos]!= false);	// sert afin de ne pas savoir de fish dans les collisions commme les collisions sont initialisées avant.
      		fishList.add(new BlueFish(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight())));
      }
	}

	private void addOrangeFish(int nb) {

		int xPos=0;
    	int yPos=0;
      for(int i = 0; i < nb ; i++){
      	do {
      		xPos=getRandomCoordinate(B_WIDTH);
      		yPos = getRandomCoordinate(B_HEIGHT);
      	}while(tabCollisions[yPos][xPos]!= false);	// sert afin de ne pas savoir de fish dans les collisions commme les collisions sont initialisées avant.
      		fishList.add(new OrangeFish(getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight()), getRandomCoordinate(getWidth()), getRandomCoordinate(getHeight())));
      }	
	}

	private void addRedFish(int nb) {
		
		int xPos=0;
    	int yPos=0;
      for(int i = 0; i < nb ; i++){
      	do {
      		xPos=getRandomCoordinate(B_WIDTH);
      		yPos = getRandomCoordinate(B_HEIGHT);
      	}while(tabCollisions[yPos][xPos]!= false);	// sert afin de ne pas savoir de fish dans les collisions commme les collisions sont initialisées avant.
      		fishList.add(new RedFish(getRandomCoordinate(getWidth()),  getRandomCoordinate(getHeight())));
      }
}

	private void initCollisions() {

    	
    	collisionList = new ArrayList<Collision>();
    	tabCollisions = new boolean[getHeight()][getWidth()]; // ceci va sevir a ne pas placer de fish dans mes collisions a l'initialisation. 

    	int randWidthCollision;
    	int randHeightCollision;
    	int x,y;
    	
    	
    	
    	for(int i=0;i<4;i++) {
    		do {
    	    	x = (int)(Math.random()*i)+(i*200);
    	    	randWidthCollision = (int)((Math.random()*100)+60);
    	    	
    	    	y = (int)(Math.random()*500);
    	    	randHeightCollision = (int)((Math.random()*200)+60);
    	    	
    	    	
    	    	}while(!(((x+randWidthCollision)<i+(i*200))) && ((y+randHeightCollision)<500));
    			collisionList.add(new Collision(x ,y ,randWidthCollision, randHeightCollision, Color.GREEN));
    			
    			
    			for(int row=x;row<x+randHeightCollision;row++) {		//ceci va sevir a ne pas placer de fish dans mes collisions a l'initialisation. je retent de créer un fish tant que ma position est dans une collision.
    				for(int col=y;col<randWidthCollision;col++) {
    					tabCollisions[row][col]=true;
    				}
    			}
    	}
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
 

        // j'affiche mes collisions
        displayCollisions(g);
        
        doDrawing(g);
}
    
    private void displayCollisions(Graphics g) {
		
    	for(int i=0;i<collisionList.size();i++) {
        	int x = collisionList.get(i).getX();
            int y = collisionList.get(i).getY();
            int width = collisionList.get(i).getWidth();
            int height = collisionList.get(i).getHeight();
            
            g.setColor(Color.blue); // Couleur bleu
            g.fillRect(x, y, width, height);
        }
		
	}

	private void doDrawing(Graphics g) {
        
        if (inGame) {
        	
        	
        	for(GameElement pellet: pelletList){               
                g.drawImage(gameElementImageMap.get(pellet.getType()).getImage(), pellet.getPosX(), pellet.getPosY(), this);
             } 
        	
            for(GameElement insect: insectList){               
                g.drawImage(gameElementImageMap.get(insect.getType()).getImage(), insect.getPosX(), insect.getPosY(), this);
             }     
            
            for(GameElement fish: fishList){               
                g.drawImage(gameElementImageMap.get(fish.getType()).getImage(), fish.getPosX(), fish.getPosY(), this);
            }
            Toolkit.getDefaultToolkit().sync();

        }       
    }
    
    public int getRandomCoordinate(int maxSize) {
    	
    		int r = (int) (Math.random() * (maxSize/DOT_SIZE)-1);
    	       // int r = (int) (Math.random() * RAND_POS);
    	        return ((r * DOT_SIZE));
    		}

    
    public boolean isValidPosition(int pos_x, int pos_y) {
    	
    	int fishWidth = 30;
        int fishHeight = 30; // verifie les bords du poissons car sa pos x y est sur son visage.

       
        if (pos_y + fishHeight > B_HEIGHT || pos_y < 0 || pos_x + fishWidth > B_WIDTH || pos_x < 0) {
            return false;
        }
 
        return true;  
    }

 
 
    
    public double getDistance(int pos_x0, int pos_y0, int pos_x1, int pos_y1){
        int x_dist = pos_x1-pos_x0;
        int y_dist = pos_y1-pos_y0;
        return Math.sqrt(Math.pow(x_dist, 2)+Math.pow(y_dist, 2));
    }
    
    private void checktarget() { // principalement pour les oranges fish, car les autres poissons changent a,chaque cycle de target.
		
    	for(Fish fish : fishList) {
    		 
    			if((fish.getPosX() == fish.getTargetX())  && (fish.getPosY() == fish.getTargetY())){
    				fish.defineTarget(this);  
    				//System.out.println("new o");
    			}
    	}
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

        	 
        	checkFishAlive(); //verifie si les poissons sont en vie (si inLife=true, alors on efface)
            checkInsectAlive(); // verifie insectes vivants (mangés)
            checkPelletAlive();	// verifie pastille mangée.
            checkFishBreeding(); // check de reproduction des poissons;   
            moveCollision(); //move mes collisions;
            

            int nbOrangeFish=0; // va servir au comportement de certains poissons.
            for(Fish fish : fishList) {
            	
	            checkInsect(fish);// je avant de move si on est a la meme position q'un insecte 
	            checkPellet(fish);//		//	meme pour pastille
	            checkTemperature(fish); // check le comportement des poissons vis à vis de la température actuelle. 
	            

	            	
	            if(!fish.getOnBreak()) { // un poisson bouge seulement si il n'est pas en position d'arret ( commande touche rmbo)
	            		fish.move(this);	// MOVE LE FISH
	            }

                //debeugage vitesse boost ci dessous
               //System.out.println("poisson : " +fish.getType()+" de vitesse : "+fish.getSpeed()+" et de boost : "+fish.getBoost()+" et de boostDelay : "+fish.getBoostDelay()+" et de slowingDelay : "+fish.getSlowingDelay());  
               if(fish.getType()=="redFish" || fish.getType()=="purpleFish" || fish.getType()=="blueFish" || fish.getType()=="orangeFish" && fish.getInsectMode() || fish.getType()=="greenFish"|| fish.getType()=="orangeFish" && fish.getPelletMode()) { 	   
                	fish.defineTarget(this); // defini un target constamment aux fish (orange fish attend detre arrivé a son target random en mode normal)
               }
               if(fish.getType()=="orangeFish") {	
                	nbOrangeFish++;	// compte le nombre de poisson orange present actuellement afin de varier la vitesse du mauve
               }
            }
            
            
            for(Fish fish : fishList) {
            	if(!(fish.checkTarget())) {
            		fish.defineTarget(this);
            	}	
            }
            
 
            
            for(Fish fish : fishList) {
            	if(fish.getType()=="purpleFish") {	//varie la vitesse du mauve a chaque cycle
            		if(nbOrangeFish>nbFishInit) {// si + de poisson qu'au départ, on augmente la vitesses des poissons mauves
            			fish.setSpeed(fish.getSpeed()+1);           //System.out.println("vitesse du mauve : " +fish.getSpeed());

            		}	 
            	}	
            }
        }
        repaint();
        }

    private void moveCollision() {

    	for(int i=0;i<collisionList.size();i++) {	// move les collisions
        	collisionList.get(i).move(this);

        }
	}

	private void checkFishBreeding() {

    	HashMap<String, ArrayList<Fish>> breedingMap = new HashMap<>(); // hashMap afin de recuperer ou créer une liste relié à sa clé
    	
    	for (Fish fish : fishList) {
    	    String key = fish.getPosX() +"-"+ fish.getPosY() +"-"+ fish.getType(); // exemple de clé : 280-320-orangeFish -> qui signifie -> poisson orange a la posX : 280 et la posY = 320./ Les "-" sont super importants, beaucoups trop de poissons se créent sans les "-" porte a confuson le x et y (exemple x=3 et y = 450 alors 3450 et x=345 et y = 0 alors 3450...  
    	    breedingMap.putIfAbsent(key, new ArrayList<>()); // si la clé est absente on l'ajoute dans breedingMap et on créer une nouvelle liste; liste qui contient les poissons à la meme clé.
    	    breedingMap.get(key).add(fish); // ajoute le poisson dans la breeding map
    	}
     	for (ArrayList<Fish> fishes : breedingMap.values()) {
    	    if (fishes.size() > 1) { 
    	    	if(birthIsPossible()){   // verification si possibilité de naissance, en foncto-ion du nombre de poisson déjà présents dans l'aquarium.
    	    		String type = fishes.get(0).getType();	
        	    	fishes.get(0).setInLife(false);
        	        fishes.get(1).setInLife(false);// on recupere seulement les 2 premiers, car il y a reproduction que avec 2 poissons min et maximum. si 3 poissons sont présents dans la breedingMap, on retiendra les 2 premiers.   	    	
        	        addNewFishs(type);
    	    	
    	          
    	    	}
    	         
    	    }
    	}
	}

    private boolean birthIsPossible() {
		// TODO Auto-generated method stub
    	int maxLength = maxFishInBoard/DOT_SIZE; //6
    	int possibility = fishList.size()/DOT_SIZE; 	// en gros, si 11 poissons presents on a 11/10 qui fait 1, et random * 1 nous fait 0, donc 1 chance sur 1 de donner naissance a un poisson.
 
    	
    	if(possibility<maxLength) {	// securité en plus avec un nombre max de poissons dans l'aquarium. fixé à 60, donc si 60/10 = 6 pas possible(<= 6)
		if(possibility>0) { // je verifie si >=10 dans le cas de figure ou la taille est 7 et 7/10 fait 0.  (jutilise des entiers)
	    	if((int)(Math.random()*possibility)==0) {						// Si j'ai 37 poissons, 37/10 fait 3, random * 3 nous donnera 1 chance sur 3 possibilité d'avoir 0,
	    		return true;
	    	}
	    	}else {
	    		return true;
	    	}
			return false;
	}
	return false;

 
	}

	private void addNewFishs(String type) {
		// TODO Auto-generated method stub
		int nb =3; //2 poissons disparaissent et 3 reaparaissent durant une naissance.
    	if(type == "purpleFish") {
    		addPurpleFish(nb);
    	}else if(type == "orangeFish"){
    		addOrangeFish(nb);
    	}else if(type == "blueFish") {
    		addBlueFish(nb);

    	}else if(type == "redFish") {
    		addRedFish(nb);
    	}else if(type == "greenFish") {
    		addGreenFish(nb);
    	}
		
		
	}
 
    
	private void checkTemperature(Fish fish) {
		// TODO Auto-generated method stub
    	if(fish.getType()=="redFish" && getCurrentWaterTemperature()==waterTemperatureTab[0]) // si on a affaire a un poisson rouge et que la température est froide, le poisson à un malu de vitesse.
    	{
    		fish.setSpeed(fish.getNormalSpeed());
    		fish.setBoost(1); // remet le boost a 1 afin de ne pas avoir un poisson avec un boost insecte ou température chaude ET boost température.
    		fish.setSpeed(fish.getSpeed()/getHotTemperatureBonus());  //ici on réduit la vitesse par 3 ( poisson rouge)
    	}else if(fish.getType()=="redFish" && getCurrentWaterTemperature()==waterTemperatureTab[2]) {
    		fish.setSpeed(fish.getNormalSpeed());
    		fish.setBoost(1); // remet le boost a 1 afin de ne pas avoir un poisson avec un boost insecte + boost température.
    		fish.setBoost(getHotTemperatureBonus());
    	}else if(fish.getType()=="redFish" && getCurrentWaterTemperature()==waterTemperatureTab[1]) {
    		fish.setSpeed(fish.getNormalSpeed());
    		fish.setBoost(1);
    	}
	}
    

	private void checkPelletAlive() {	//supprime les pastilles mangées
		// TODO Auto-generated method stub
    	for(int i=pelletList.size()-1;i>=0;i--) {
    		if((pelletList.get(i).isEat())) {
    			pelletList.remove(i);
    		}
    	}
	}

	private void checkPellet(Fish fish) {
 
		for(Pellet pellet : pelletList) {
    		if(fish.getPosX()==pellet.getPosX() && fish.getPosY()==pellet.getPosY()) {	//check si meme positions
    			fish.setSpeed(fish.getSpeed()/Pellet.slowing);
    			fish.setSlowingDelay(nbCollisions*DOT_SIZE); // chrono à 4 secondes ( plus précisément 40 décisecondes)
    			pellet.setEat(true); // indique que la pastille a été mangée afin de la supprimer de la liste par la suite.
    		}
    	}
	}

	private void checkInsectAlive() {	//supprime les insectes mangés
 
		for(int i=insectList.size()-1;i>=0;i--) {
    		if(!(insectList.get(i).isInLife())) {
    			insectList.remove(i);	
    		}
    	}	
	}

	private void checkInsect(Fish fish) {
    	
    	for(Insect insect : insectList) {
    		if(fish.getPosX()==insect.getPosX() && fish.getPosY()==insect.getPosY()) {	//check si meme position
    			if(insect.getType()==Insect.insectTypeTab[3]) { //si l'insecte est un insecte noir (venimeux)
    				fish.setInLife(false);// poisson meurt
    			}else {
    				fish.setBoost(Insect.boost);	// insecte d'autre couleure que noir, bonus temporel
        			fish.setBoostDelay(insect.getBoostDelay()); // je recupere le boost de l'insecte mangé et je le met dans le boostDelay du fish.
    			}
    			insect.setInLife(false); // l'insecte mangé est inLife(false); sert a ensuite supprimer les insectes de la liste.
    			}	
    		}    		
    	}
	

	private void checkFishAlive() {
		 
		int nbOfDeadOrangeFish=0; // Sert a gérer la vitesse du purpleFish
		for(int i= fishList.size()-1;i>=0;i--) {	
			if((!(fishList.get(i).getInLife()))) { 
				if(fishList.get(i).getType()=="orangeFish") {	// vérification si un orange est retiré de la liste, afin de modifier la vitesse du purpleFish
					nbOfDeadOrangeFish++;
				}
				fishList.remove(i);		// supprime les fish mort de ma list de fishs. j'ai su les remove en prenant ceux dont getInLife==false.
				System.out.println("Un poisson mort");
 			}
			}
	
		//Ralentissement des purpleFish si nbOfDeadOrangeFish>0
	if(nbOfDeadOrangeFish>0) {
		for(Fish fish : fishList) {
			if(fish.getType()=="purpleFish") {			
				fish.setSpeed(fish.getSpeed()-nbOfDeadOrangeFish);	// mise a jour de la vitesse du mauve si le nombre de poissons oranges présents diminue.
				}
			}
		}
	}

	private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {


            //commandes de mon projet
        	
        	int key = e.getKeyCode();
            if ((key == KeyEvent.VK_0)) {
            	initBoard();
            }
            
            if ((key == KeyEvent.VK_1)) {
            	setCurrentWaterTemperature(waterTemperatureTab[0]); // température à froid
            	setBackground(waterTemperaturetabColor[0]);
             }
            
            if ((key == KeyEvent.VK_2)) {
            	setCurrentWaterTemperature(waterTemperatureTab[1]); // tiède
            	System.out.println(getCurrentWaterTemperature());
            	setBackground(waterTemperaturetabColor[1]);

            }
            
            if ((key == KeyEvent.VK_3)) {
            	setCurrentWaterTemperature(waterTemperatureTab[2]); // chaud
            	System.out.println(getCurrentWaterTemperature());
            	setBackground(waterTemperaturetabColor[2]);

            }
            
            if ((key == KeyEvent.VK_4)) {
            	//ajout d'un insecte aleatoire
            	String type=""; //type de l'insecte
            	int insect=(int)(Math.random()*Insect.insectTypeTab.length);
            	for(int i=0;i<Insect.insectTypeTab.length;i++) {
            		if(insect==i) {
            			type=Insect.insectTypeTab[i];
            			break;
            		}
            	}
            	
            	insectList.add(new Insect(getRandomCoordinate(B_WIDTH), getRandomCoordinate(B_HEIGHT), type));
            		
            }
            
            if ((key == KeyEvent.VK_5)) {
            	pelletList.add(new Pellet(getRandomCoordinate(B_WIDTH), getRandomCoordinate(B_HEIGHT)));
            }
            
            
            if ((key == KeyEvent.VK_6)) {
            	for(Fish fish : fishList) {
            		if(!fish.getOnBreak()) {
            			fish.setPelletMode(false);//je retire lancien mode
            			fish.setInsectMode(true);
            		}
            	}
            }
            
            if ((key == KeyEvent.VK_7)) {
            	resetAllFishOnBreak();
            	for(Fish fish : fishList) {
            		if(!fish.getOnBreak()) {
            			fish.setInsectMode(false);//je retire lancien mode
            			fish.setPelletMode(true);
            		}
            	}
            }
            
            
            if ((key == KeyEvent.VK_R)) {
            	resetAllFishOnBreak();
            	for(Fish fish : fishList) {
            		if(!fish.getType().equals("redFish")) {
            			fish.setOnBreak(true);
            		}
            	}
            }
            
            if ((key == KeyEvent.VK_B)) {
            	resetAllFishOnBreak();
            	for(Fish fish : fishList) {
            		if(!fish.getType().equals("blueFish")) {
            			fish.setOnBreak(true);
            		}
            	}
            }
            
            if ((key == KeyEvent.VK_M)) {
            	resetAllFishOnBreak();
            	for(Fish fish : fishList) {
            		if(!fish.getType().equals("purpleFish")) {
            			fish.setOnBreak(true);
            		}
            	}
            }
            
            if ((key == KeyEvent.VK_O)) {
            	resetAllFishOnBreak();
            	for(Fish fish : fishList) {
            		if(!fish.getType().equals("orangeFish")) {
            			fish.setOnBreak(true);
            		}
            	}
            }	
        }
        	
        private void resetAllFishOnBreak() {
 			
			for(Fish fish : fishList) {
				fish.setOnBreak(false);
			}			
		}
    }
}
