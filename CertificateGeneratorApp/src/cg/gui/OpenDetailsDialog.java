package cg.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class OpenDetailsDialog extends JDialog {

	private JComboBox keyStoreComboBox = null;
	private JComboBox certificateComboBox = null;
	private Certificate certificate;
	private JPanel panel = new JPanel();
	private KeyStore selectedKeyStore;
	private PrivateKey privateKey;

	private char[] keyStorePassword;

	private char[] certificatePassword;

	public OpenDetailsDialog() {
		// TODO Auto-generated constructor stub

		setTitle("Details");
		setResizable(false);
		setBounds(100, 100, 391, 138);
		getContentPane().setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new MigLayout("", "[][][][grow]", "[][]"));
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblKeyStore = new JLabel("KeyStore");
		panel.add(lblKeyStore, "cell 1 0");
		keyStoreComboBox = new JComboBox();
		final OpenDetailsDialog odDialog = this;
		keyStoreComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (keyStoreComboBox.getSelectedIndex() == 0) {
					// ako nije selektovan jsk prazni se ca
					certificateComboBox.removeAllItems();
					return;
				} else {
					// ako je selektovan proveravamo password
					CheckKeyStoreDialog cksd = new CheckKeyStoreDialog(odDialog,
							keyStoreComboBox.getSelectedItem().toString());
					cksd.setModal(true);
					cksd.setLocationRelativeTo(MainFrame.getInstance());
					cksd.setVisible(true);
					// ako nije kliknuto na cancel
					if (keyStoreComboBox.getSelectedIndex() != 0) {

						// keyStorePassword =
						// cksd.getPasswordField().getPassword();
						loadCertsbyKeyStores(getSelectedKeyStore());
					} else {
						// ako je kliknuto na cancel prazni se ca
						certificateComboBox.removeAllItems();
						return;
					}
				}
			}
		});

		panel.add(keyStoreComboBox, "cell 3 0,growx");

		JLabel lblCert = new JLabel("Certificate");
		panel.add(lblCert, "cell 1 1");
		certificateComboBox = new JComboBox();

		certificateComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (certificateComboBox.getSelectedIndex() != 0) {
					String alias = "";
					try {
						alias = certificateComboBox.getSelectedItem().toString();
					} catch (Exception ec) {

					}
					certificate = null;
					try {
						certificate = getSelectedKeyStore().getCertificate(alias);

					} catch (KeyStoreException e1) {
					}
					if (certificate != null) {
						String keyStoreName = keyStoreComboBox.getSelectedItem().toString() + ".jks";
						KeyStore keyStore = null;
						try {
							keyStore = KeyStore.getInstance("JKS", "SUN");
						} catch (KeyStoreException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchProviderException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						FileInputStream file = null;
						try {
							file = new FileInputStream("./keyStores/" + keyStoreName);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							keyStore.load(file, keyStorePassword);
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (CertificateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// verifikacija izabranog ca
						CheckCertificateDialog ccd = new CheckCertificateDialog(keyStoreName, keyStorePassword,
								keyStore, alias);
						ccd.setModal(true);
						ccd.setLocationRelativeTo(MainFrame.getInstance());
						ccd.setVisible(true);

						certificatePassword = ccd.getPassword();
						privateKey = ccd.getPrivateKey();
					}
				}
			}
		});

		panel.add(certificateComboBox, "cell 3 1,growx");

		JPanel buttonPane = new JPanel();
		// buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (odDialog.getKeyStoreComboBox().getSelectedIndex() != 0
						&& odDialog.getCertificateComboBox().getSelectedIndex() != 0) {
					odDialog.dispose();
					CertificateInformationDialog ct = new CertificateInformationDialog(odDialog.getCertificate());
					ct.setVisible(true);
					ct.setLocationRelativeTo(MainFrame.getInstance());
				} else {
					JOptionPane.showMessageDialog(null, "You must fill in all fields!");
				}
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				odDialog.dispose();
			}
		});

		getRootPane().setDefaultButton(okButton);
		loadKeyStores();

	}

	private void loadCertsbyKeyStores(KeyStore keystore) {
		certificateComboBox.removeAllItems();

		this.certificateComboBox.addItem(" ");

		try {
			Enumeration enumeration = keystore.aliases();
			while (enumeration.hasMoreElements()) {

				String alias = (String) enumeration.nextElement();

				this.certificateComboBox.addItem(alias);

			}
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadKeyStores() {
		File folder = new File("./keyStores");
		File[] keyStoreFiles = folder.listFiles();

		KeyStore keyStore;
		this.keyStoreComboBox.addItem(" ");
		for (File keyStoreFile : keyStoreFiles) {
			this.keyStoreComboBox.addItem(keyStoreFile.getName().split(".jks")[0]);
		}
	}

	public JComboBox getKeyStoreComboBox() {
		return keyStoreComboBox;
	}

	public void setKeyStoreComboBox(JComboBox keyStoreComboBox) {
		this.keyStoreComboBox = keyStoreComboBox;
	}

	public JComboBox getCertificateComboBox() {
		return certificateComboBox;
	}

	public void setCertificateComboBox(JComboBox certificateComboBox) {
		this.certificateComboBox = certificateComboBox;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public KeyStore getSelectedKeyStore() {
		return selectedKeyStore;
	}

	public void setSelectedKeyStore(KeyStore selectedKeyStore) {
		this.selectedKeyStore = selectedKeyStore;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public char[] getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(char[] keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public char[] getCertificatePassword() {
		return certificatePassword;
	}

	public void setCertificatePassword(char[] certificatePassword) {
		this.certificatePassword = certificatePassword;
	}

}
