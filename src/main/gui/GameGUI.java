package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;



/*Probleme
 * 
 * 	Hintergrun+Spielfeld nicht möglich Warum?
 *
 */



public class GameGUI extends JFrame{

	
 	private static final Graphics Graphics = null;
	private JLabel myLabel;
	private JButton option1;
	private JButton option2;
	private JButton option3;
	private JButton würfeln;
	private JPanel mainPanel;
	private JPanel rightPanel;
	private Panel_with_background content;
	private Circle circle;
	 
	public GameGUI(){
		
		this.setTitle("Mensch ärgere dich nicht!");



		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setPreferredSize(new Dimension(700, 700));
		this.add(panel, BorderLayout.CENTER);

		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.red);
		panel1.setPreferredSize(new Dimension(300, 700));
		this.add(panel1, BorderLayout.EAST);


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);






/*
		mainPanel = new JPanel(null);
		mainPanel.setSize(1000,700);
		this.setContentPane(mainPanel);
		
	
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		

		panel1.setBackground(Color.red);
		panel.setBackground(Color.black);

		mainPanel.add(panel,panel1);
		
		frame.add(panel, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(700, 700));
		frame.add(panel1, BorderLayout.EAST);
		panel1.setPreferredSize(new Dimension(300, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
*/
		
		
		/*
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setSize(new Dimension(700,700));
		setTitle("Mensch ärgere dich nicht");
		JLayeredPane layeredPane = new Panel_with_background(new ImageIcon("./images/background.png").getImage());
		layeredPane.setBounds(0,0,500,500);
		setContentPane(layeredPane);
		pack();
		setVisible(true); 
		*/
	 
	}	
	
	/*
	public void paint(Graphics g){
		//Image img = new ImageIcon("./images/background.png").getImage();
		//g.drawImage(img, 0, 0, null);
		 // 					(Zeile,Spalte)
		 
		 //Spielfeld
		 g.setColor(Color.black);
		 g.fillOval(200,40,30,30);//0,5
		 g.fillOval(240,40,30,30);//0,6
		 g.fillOval(40,240,30,30);//5,0
		 g.fillOval(40,280,30,30);//6,0
		 g.fillOval(440,200,30,30);//4,10
		 g.fillOval(440,240,30,30);//5,10
		 g.fillOval(240,440,30,30);//10,5
		 g.fillOval(280,440,30,30);//10,6
		 
		 
		 g.fillOval(80,200,30,30);//4,1
		 g.fillOval(120,200,30,30);//4,2
		 g.fillOval(160,200,30,30);//4,3
		 g.fillOval(200,200,30,30);//4,4
		 g.fillOval(280,200,30,30);//4,6
		 g.fillOval(320,200,30,30);//4,7
		 g.fillOval(360,200,30,30);//4,8
		 g.fillOval(400,200,30,30);//4,9
		 
		
		
		 
		 g.fillOval(80,280,30,30);//6,1
		 g.fillOval(120,280,30,30);//6,2
		 g.fillOval(160,280,30,30);//6,3
		 g.fillOval(200,280,30,30);//6,4
		
		 g.fillOval(280,280,30,30);//6,6
		 g.fillOval(320,280,30,30);//6,7
		 g.fillOval(360,280,30,30);//6,8
		 g.fillOval(400,280,30,30);//6,9
		 
		
		 
		 g.fillOval(200,80,30,30);//1,4
		 g.fillOval(200,120,30,30);//2,4
		 g.fillOval(200,160,30,30);//3,4
		 
		 
		 g.fillOval(280,80,30,30); //1,6
		 g.fillOval(280,120,30,30); //2,6
		 g.fillOval(280,160,30,30); //3,6
		
		
		 g.fillOval(200,400,30,30); //9,4
		 g.fillOval(200,360,30,30);	//8,4
		 g.fillOval(200,320,30,30); //7,4
		
		 g.fillOval(280,400,30,30); //9,6
		 g.fillOval(280,360,30,30);	//8,6
		 g.fillOval(280,320,30,30); //7,6
		 
		 
		 g.setColor(Color.blue);
		 //Haus 
		 g.fillOval(40,40,30,30); //0,0
		 g.fillOval(80,40,30,30); //0,1
		 g.fillOval(40,80,30,30); //1,0
		 g.fillOval(80,80,30,30); //1,1
		 //Hotzone
		 g.fillOval(40,200,30,30); //4,0
		 //Safezone
		 g.fillOval(80,240,30,30); //5,1
		 g.fillOval(120,240,30,30); //5,2
		 g.fillOval(160,240,30,30); //5,3
		 g.fillOval(200,240,30,30); //5,4
		 
	 
		 g.setColor(Color.green);
		 //Haus
		 g.fillOval(400,40,30,30);//0,9
		 g.fillOval(400,80,30,30);//1,9
		 g.fillOval(440,40,30,30);//0,10
		 g.fillOval(440,80,30,30);//1,10
		 //Hotzone
		 g.fillOval(280,40,30,30);//0,7
		 //Safezone
		 g.fillOval(240,80,30,30); //1,5
		 g.fillOval(240,120,30,30); //2,5
		 g.fillOval(240,160,30,30); //3,5
		 g.fillOval(240,200,30,30); //4,5
		 
		 
		 g.setColor(Color.red);
		 //Haus
		 g.fillOval(40,400,30,30);//9,0
		 g.fillOval(40,440,30,30);//10,0
		 g.fillOval(80,400,30,30);//9,1
		 g.fillOval(80,440,30,30);//10,1
		 //Hotzone
		 g.fillOval(200,440,30,30);//10,4
		 //Safezone
		 g.fillOval(240,400,30,30); //9,5
		 g.fillOval(240,360,30,30);	//8,5
		 g.fillOval(240,320,30,30); //7,5
		 g.fillOval(240,280,30,30);	//6,5
		 
		 
		 g.setColor(Color.yellow);
		 //Haus
		 g.fillOval(400,400,30,30); //9,9
		 g.fillOval(400,440,30,30); //10,9
		 g.fillOval(440,400,30,30); //9,10
		 g.fillOval(440,440,30,30); //10,10
		 //Hotzone
		 g.fillOval(440,280,30,30);//6,10
		 //Safezone
		 g.fillOval(280,240,30,30);//5,6
		 g.fillOval(320,240,30,30);//5,7
		 g.fillOval(360,240,30,30);//5,8
		 g.fillOval(400,240,30,30);//5,9
		 
		 
	 }
	*/
}

















