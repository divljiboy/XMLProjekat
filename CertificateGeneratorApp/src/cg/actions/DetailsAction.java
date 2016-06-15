package cg.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import cg.gui.MainFrame;
import cg.gui.OpenDetailsDialog;

public class DetailsAction extends AbstractAction {
	
	public DetailsAction() {
		// TODO Auto-generated constructor stub
		putValue(NAME, "Details");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		OpenDetailsDialog diag = new OpenDetailsDialog();
		diag.setModal(true);
		diag.setLocationRelativeTo(MainFrame.getInstance());
		diag.setVisible(true);
		
	}

}
