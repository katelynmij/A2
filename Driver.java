//Driver class
public class Driver {
	public static void main(String[] args) {		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
        	  TreeView.createAndShowGUI();   
          }
      });
	}
}