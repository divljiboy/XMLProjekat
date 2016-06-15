package cg.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import cg.gui.GenerateCertificateDialog;
import cg.gui.MainFrame;

public class OpenDialogForGenerateCertificateAction extends AbstractAction {
	
	public OpenDialogForGenerateCertificateAction() {
		// TODO Auto-generated constructor stub
		putValue(NAME, "Generate Certificate");
	//	putValue(SMALL_ICON, new ImageIcon("./images/keystore.png"));
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		
		GenerateCertificateDialog ck =  new GenerateCertificateDialog();
		ck.setModal(true);
		ck.setLocationRelativeTo(MainFrame.getInstance());
		ck.setVisible(true);
		
	}

}
