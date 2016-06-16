package cg.actions;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import cg.gui.GenerateCertificateDialog;

public class LoadCertificateAction extends AbstractAction{

	
	private JPasswordField password;
	private String fileName;
	private JDialog parentDialog;
	private JDialog panel;
	private int selectedIndex;
	
	public LoadCertificateAction(JDialog panel,JDialog parentDialog, JPasswordField password, String fileName) 
	{
		this.password = password;
		this.parentDialog= parentDialog;
		this.fileName = fileName;
		this.panel = panel;
		if(parentDialog instanceof GenerateCertificateDialog)
		{
			this.selectedIndex =((GenerateCertificateDialog)parentDialog).getCa().getSelectedIndex();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS","SUN");
			FileInputStream file = new FileInputStream("./keyStore/"+fileName);
			keyStore.load(file, password.getPassword());
			if(parentDialog instanceof GenerateCertificateDialog){
				((GenerateCertificateDialog)parentDialog).setSelectedKeyStore(keyStore);
				((GenerateCertificateDialog)parentDialog).setKeyStorePassword(password.getPassword());
				((GenerateCertificateDialog)parentDialog).getKeyStore().setSelectedIndex(selectedIndex);
			}
			panel.dispose();
		} catch (Exception exp) {
			JOptionPane.showMessageDialog(panel, "Could not open keyStore!", "Error", JOptionPane.ERROR_MESSAGE);
			if(parentDialog instanceof GenerateCertificateDialog){
				((GenerateCertificateDialog)parentDialog).getKeyStore().setSelectedIndex(0);
			}
		}
	}
}
