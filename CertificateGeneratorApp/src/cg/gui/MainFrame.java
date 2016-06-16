package cg.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	private static MainFrame instance = null;
	
	private MyMenuBar myMenuBar = null;
	private JPanel contentPane = null;
	
	public MainFrame(){
		
		initializeMainFrame();
		initializeMenu();
	}
	
	private void initializeMainFrame(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("Certificate Generator");
		
		setSize(tk.getScreenSize().width/2, tk.getScreenSize().height/2);
		
		setLocationRelativeTo(null);
		
		this.setLayout(new BorderLayout());
		
	}
	
	private void initializeMenu(){
		myMenuBar = new MyMenuBar();
		this.add(myMenuBar, BorderLayout.NORTH);
	}
	
	
	
	public static MainFrame getInstance(){
		if(instance == null){
			instance = new MainFrame();
		}
		return instance;
	}
}
