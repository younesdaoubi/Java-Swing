import java.awt.EventQueue;
import javax.swing.JFrame;

public class HelbAquarium extends JFrame {

    public HelbAquarium() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
               
        setResizable(false);
        pack();
        
        setTitle("HelbAquarium");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new HelbAquarium();
            ex.setVisible(true);
        });
    }
}
