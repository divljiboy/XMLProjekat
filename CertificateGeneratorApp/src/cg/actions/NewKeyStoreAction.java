package cg.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cg.gui.CreateKeyStoreDialog;
import cg.security.KeyStoreWriter;

public class NewKeyStoreAction extends AbstractAction{
	
	private CreateKeyStoreDialog diag;
	private KeyStoreWriter keyStoreWritter;
	
	public NewKeyStoreAction(cg.gui.CreateKeyStoreDialog dialog) {
		// TODO Auto-generated constructor stub
	
		this.diag = dialog;
		keyStoreWritter = new KeyStoreWriter();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String name = diag.getName_().getText();
		char[] password = diag.getPassword_().getText().toCharArray();
		
		if(!name.isEmpty() && password.length>0){
			diag.dispose();
			keyStoreWritter.loadKeyStore(null, password);
			keyStoreWritter.saveKeyStore("./keyStores/"+name+".jks", password);			
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Oba polja moraju biti popunjena");
		}
		
		
	}

}
