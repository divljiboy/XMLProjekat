package cg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class CertificateInformationDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String certificateString;
	JTextField version;
	JTextField subject;
	JTextField issuer;
	JTextField snumb;
	JTextField validFrom;
	JTextField validUntil;
	JTextField publicKey;
	JTextField signAlg;

	private final JPanel panel = new JPanel();
	private Certificate certificate;
	
	public CertificateInformationDialog(Certificate cert) 
	{	
		System.out.println(cert.toString());
		setTitle("Certificate information");
		setSize(700,550);
		this.setCertificate(cert);
		
		getContentPane().setLayout(new MigLayout("insets 10 10 10 20"));

		JLabel lblcert = new JLabel("Certificate");
		JLabel lblversion = new JLabel("Version:");
		JLabel lblsubject = new JLabel("Subject:");
		JLabel lblissuer = new JLabel("Issuer:");
		JLabel lblsnumb = new JLabel("Serial Number:");
		JLabel lblvalidFrom = new JLabel("Valid From:");
		JLabel lblvalidUntil = new JLabel("Valid Until:");
		JLabel lblpublicKey = new JLabel("Public Key:");
		JLabel lblsignAlg = new JLabel("Signature Algorithm:");
		
		
		
		version = new JTextField();
		version.setEditable(false);
		
		subject = new JTextField();
		subject.setEditable(false);
		
		issuer = new JTextField();
		issuer.setEditable(false);
		
		snumb = new JTextField();
		snumb.setEditable(false);
		
		validFrom = new JTextField();
		validFrom.setEditable(false);
		
		validUntil = new JTextField();
		validUntil.setEditable(false);
		
		publicKey = new JTextField();
		publicKey.setEditable(false);
		
		signAlg = new JTextField();
		signAlg.setEditable(false);
		this.readDetails(cert);
		//labele
		getContentPane().add(lblcert, "cell 0 0,align center, height 30, span");
		getContentPane().add(lblversion, "cell 0 1,align right, height 30");
		getContentPane().add(lblsubject, "cell 0 2,align right, height 30");
		getContentPane().add(lblissuer, "cell 0 3,align right,  height 30");
		getContentPane().add(lblsnumb, "cell 0 4,align right,  height 30");
		getContentPane().add(lblvalidFrom, "cell 0 5,align right, height 30");
		getContentPane().add(lblvalidUntil, "cell 0 6,align right, height 30");
		getContentPane().add(lblpublicKey, "cell 0 7,align right, height 30");
		getContentPane().add(lblsignAlg, "cell 0 8,align right, height 30");
		
		
		
		//text field
		getContentPane().add(version, "cell 1 1,align left, height 20, width 40::");
		getContentPane().add(subject, "cell 1 2,align left, height 20, width 400::");
		getContentPane().add(issuer, "cell 1 3,align left,  height 20, width 400::");
		getContentPane().add(snumb, "cell 1 4,align left,  height 20, width 300::");
		getContentPane().add(validFrom, "cell 1 5,align left, height 20, width 300::");
		getContentPane().add(validUntil, "cell 1 6,align left, height 20, width 300::");
		getContentPane().add(publicKey, "cell 1 7,align left, height 20, width 200::");
		getContentPane().add(signAlg, "cell 1 8,align left, height 20, width 200::");
	}
		
	public void readDetails(Certificate cert){
		

		certificateString = cert.toString();
		X509Certificate xc =(X509Certificate)cert;
		
		
		Integer k = xc.getVersion();
		
		version.setText(k.toString());
		subject.setText(xc.getSubjectDN().toString());
		signAlg.setText(xc.getSigAlgName());
		issuer.setText(xc.getIssuerDN().toString());
		snumb.setText(xc.getSerialNumber().toString());
		validFrom.setText(xc.getNotBefore().toString());
		validUntil.setText(xc.getNotAfter().toString());
		publicKey.setText(xc.getPublicKey().getAlgorithm().toString()+" (1024)");
	
}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
}
