import javax.swing.JFrame;

public class Frame extends JFrame{
	Frame(){
		
		this.add(new Driver());
		this.setTitle("Snek");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
