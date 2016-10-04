import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame{
	
	public Window(){
		
	}
	
	public Window(String title, int sizeX, int sizeY, int[][] map){
		int i, j;
		JButton button;
		this.setTitle(title);
		// Each cell of the table has 100 pixels
		this.setSize(sizeX*100, sizeY*100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		this.setLayout(new GridLayout(sizeY, sizeX));
		for(i=0; i<sizeY; i++) {
			for(j=0; j<sizeX; j++) {
				final int ii = i;
				final int jj = j;
				button = new JButton(Integer.toString(i)+Integer.toString(j));
				this.getContentPane().add(button);
				if(0 == map[i][j]) {
					button.setBackground(Color.WHITE);
				} else if(1 == map[i][j]) {
					button.setBackground(Color.BLACK);
				}
				button.setOpaque(true);
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("Button"+Integer.toString(ii)+Integer.toString(jj));
					}
				});
			}
		}
		this.setVisible(true);
	}
}
