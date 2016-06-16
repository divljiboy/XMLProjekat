package cg.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cg.actions.NewKeyStoreAction;

public class CreateKeyStoreDialog extends JDialog{

	private JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JTextField name_;
	private JPasswordField password_;
	
	/**
	 * Kreiranje nove instance KeyStore dijaloga.
	 */
	public CreateKeyStoreDialog() 
	{
		setTitle("Create new key store");
		setResizable(false);
		setBounds(100, 100, 391, 138);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		
		
		JLabel lblName = new JLabel("Name");
		panel.add(lblName);
	
		name_ = new JTextField();
		panel.add(name_);
		name_.setColumns(25);

		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword);

		password_ = new JPasswordField();
		panel.add(password_);
		password_.setColumns(25);

		JPanel buttonPane = new JPanel();
		//buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new NewKeyStoreAction(this));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		final CreateKeyStoreDialog cksd = this;
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cksd.dispose();
			}
		});
		buttonPane.add(cancelButton);

	}

	
	public JPanel getPanel() {
		return panel;
	}
	public JTextField getName_() {
		return name_;
	}
	public void setName_(JTextField name_) {
		this.name_ = name_;
	}
	public JPasswordField getPassword_() {
		return password_;
	}
	public void setPassword_(JPasswordField password_) {
		this.password_ = password_;
	}
	
}
