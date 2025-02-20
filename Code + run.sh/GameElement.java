public abstract class GameElement {

    private int posX;
    private int posY;
    
    public GameElement(int posX, int posY) {
        this.posX = posX ;
        this.posY = posY ;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public int getPosY(){
        return posY;
    }
    
    public void setPosX(int new_pos){
        posX = new_pos;
    }
    
    public void setPosY(int new_pos){
        posY = new_pos;
    }
    
    public abstract String getType();
    
    
 
    
}




