package cg.actions;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import cg.gui.GenerateCertificateDialog;
import cg.gui.OpenDetailsDialog;

public class LoadKeyStoreAction extends AbstractAction {

	private JPasswordField password;
	private String fileName;
	private JDialog parentDialog;
	private JDialog panel;
	private int selectedIndex;
	

	public LoadKeyStoreAction(JDialog panel, JDialog parentDialog, JPasswordField password, String fileName) {
		this.password = password;
		this.parentDialog = parentDialog;
		this.fileName = fileName;
		this.panel = panel;
		

		if (parentDialog instanceof GenerateCertificateDialog) {
			this.selectedIndex = ((GenerateCertificateDialog) parentDialog).getKeyStore().getSelectedIndex();
		} else if (parentDialog instanceof OpenDetailsDialog) {
			this.selectedIndex = ((OpenDetailsDialog) parentDialog).getKeyStoreComboBox().getSelectedIndex();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			FileInputStream file = new FileInputStream("./keyStores/" + fileName + ".jks");
			keyStore.load(file, password.getPassword());

			if (parentDialog instanceof GenerateCertificateDialog) {
				((GenerateCertificateDialog) parentDialog).setSelectedKeyStore(keyStore);
				((GenerateCertificateDialog) parentDialog).setKeyStorePassword(password.getPassword());
				((GenerateCertificateDialog) parentDialog).getKeyStore().setSelectedIndex(selectedIndex);
			} else {

				if (parentDialog instanceof OpenDetailsDialog) {

					((OpenDetailsDialog) parentDialog).setSelectedKeyStore(keyStore);
					((OpenDetailsDialog) parentDialog).setKeyStorePassword(password.getPassword());
					((OpenDetailsDialog) parentDialog).getKeyStoreComboBox().setSelectedIndex(selectedIndex);
					
					this.panel.dispose();

				} 
			}
			panel.dispose();
		} catch (Exception exp) {
			JOptionPane.showMessageDialog(panel, "Could not open keyStore!", "Error", JOptionPane.ERROR_MESSAGE);
			if (parentDialog instanceof GenerateCertificateDialog) {
				((GenerateCertificateDialog) parentDialog).getKeyStore().setSelectedIndex(0);
			} else {
				((OpenDetailsDialog) parentDialog).getKeyStoreComboBox().setSelectedIndex(0);
			}
		}
	}

}
