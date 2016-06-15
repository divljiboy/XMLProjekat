package cg.gui;


import javax.swing.JMenu;
import javax.swing.JMenuBar;

import cg.actions.DetailsAction;
import cg.actions.OpenDialogForCreateKeyStoreAction;
import cg.actions.OpenDialogForGenerateCertificateAction;

public class MyMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public MyMenuBar(){
		
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		JMenu about = new JMenu("About");
		//*************
		OpenDialogForGenerateCertificateAction gs = new OpenDialogForGenerateCertificateAction();
		OpenDialogForCreateKeyStoreAction ks = new OpenDialogForCreateKeyStoreAction();
		DetailsAction da = new DetailsAction();
		//*************
		file.add(gs);
		file.add(ks);
		file.add(da);
		
		this.add(file);
		this.add(help);
		this.add(about);
		
		
		
		
	}

}
