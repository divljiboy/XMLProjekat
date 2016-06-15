package cg.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import cg.gui.CreateKeyStoreDialog;
import cg.gui.MainFrame;

public class OpenDialogForCreateKeyStoreAction extends AbstractAction{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public OpenDialogForCreateKeyStoreAction() {
		// TODO Auto-generated constructor stub
		
		putValue(NAME, "Create KeyStore");
	
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		CreateKeyStoreDialog ck =  new CreateKeyStoreDialog();
		ck.setModal(true);
		ck.setLocationRelativeTo(MainFrame.getInstance());
		ck.setVisible(true);
	
	}

}
