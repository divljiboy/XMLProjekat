package cg.actions;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import cg.gui.GenerateCertificateDialog;
import cg.security.CertificateGenerator;
import cg.security.IssuerData;
import cg.security.SubjectData;

public class GenerateCertificateAction extends AbstractAction {
	
	private GenerateCertificateDialog diag;
	
	
	public GenerateCertificateAction(GenerateCertificateDialog diag) {
		this.diag = diag;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//validacija unosa
		if ( validation()){
			
		
		CertificateGenerator generator = new CertificateGenerator();
		//ca sertifikat
		Certificate cerf = diag.getCertificate();
		PrivateKey privateKey = diag.getPrivateKey();
		
		KeyPair keyPair = generator.generateKeyPair();
		KeyStore keyStore = diag.getSelectedKeyStore();

		X500NameBuilder builder = generateBuilder();

		int snb=new Double( Math.random() * 100000 ).intValue();
		String certificateNumber = Integer.toString(snb);

		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = iso8601Formater.parse(diag.getDateField().getText());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			endDate = iso8601Formater.parse(diag.getEndDate().getText());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		IssuerData issuerData = null;
		
		if(cerf != null){
			
			//nije ss
			X500NameBuilder builder_issuer = parseDataFromCertificate((X509Certificate)cerf);
			issuerData = new IssuerData(privateKey, builder_issuer.build());
		}else{
			//ss
			issuerData = new IssuerData(keyPair.getPrivate(), builder.build());
		}
		 
		SubjectData subjectData = new SubjectData(keyPair.getPublic(),builder.build(),certificateNumber,startDate, endDate);
		
		//sertifikat
		X509Certificate cert = generator.generateCertificate(issuerData, subjectData);
		
		String alias = diag.getAlias().getText();
		char[] password = diag.getPasswordField().getPassword();
		try {
			diag.getSelectedKeyStore().setKeyEntry(alias,keyPair.getPrivate(),password,  new X509Certificate[] {cert});
			
			FileOutputStream outputFile = new FileOutputStream("./keyStores/" + (String)diag.getKeyStore().getSelectedItem() + ".jks");
			diag.getSelectedKeyStore().store(outputFile, diag.getKeyStorePassword());
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		saveCertificate(cert, alias);
		

	    diag.dispose();
		}
	}
	
	
	
	private X500NameBuilder generateBuilder(){
		

		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, diag.getCommon_name().getText());
	    builder.addRDN(BCStyle.SURNAME, diag.getSurname().getText());
	    builder.addRDN(BCStyle.GIVENNAME, diag.getGiven_name().getText());
	    builder.addRDN(BCStyle.O, diag.getOrganization_name().getText());
	    builder.addRDN(BCStyle.OU, diag.getOrganization_unit().getText());
	    builder.addRDN(BCStyle.C, diag.getCountry_code().getText());
	    builder.addRDN(BCStyle.E, diag.getEmail_address().getText());
	    int uid = new Double( Math.random() * 100000 ).intValue();
	    builder.addRDN(BCStyle.UID, Integer.toString(uid));
	    return builder;
	}
	
	//izvlaci podatke iz sertifikata
 	public X500NameBuilder parseDataFromCertificate(X509Certificate certificateForParse){
		
		String stringForParse = certificateForParse.toString();
			
	 		String [] arrayForParse = stringForParse.split("Issuer:")[1].split(",");
	 		String UID = arrayForParse[0].split("=")[1];
	 		String EMAILADDRESS = arrayForParse[1].split("=")[1];
	 		String C = arrayForParse[2].split("=")[1];
	 		String OU = arrayForParse[3].split("=")[1];
	 		String O = arrayForParse[4].split("=")[1];
	 		String GIVENNAME = arrayForParse[5].split("=")[1];
	 		String SURNAME = arrayForParse[6].split("=")[1];
	 		String CN = arrayForParse[7].split("=")[1].split("\n")[0];

	 		
	 		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	 	    builder.addRDN(BCStyle.CN, CN);
	 	    builder.addRDN(BCStyle.SURNAME, SURNAME);
	 	    builder.addRDN(BCStyle.GIVENNAME, GIVENNAME);
	 	    builder.addRDN(BCStyle.O, O);
	 	    builder.addRDN(BCStyle.OU, OU);
	 	    builder.addRDN(BCStyle.C, C);
	 	    builder.addRDN(BCStyle.E, EMAILADDRESS);
	 	    builder.addRDN(BCStyle.UID, UID);
	 		
	 		return builder;
	 	}
	
 	private void saveCertificate(X509Certificate cert, String alias){
 		
 		byte[] buf;
		try {
			buf = cert.getEncoded();
			FileOutputStream os;	
			os = new FileOutputStream("./certificates/"+alias+".cer");	
			os.write(buf);
			os.close(); 
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
 	
 	private boolean validation(){
 		if(!diag.checkAllFields()){
			JOptionPane.showMessageDialog(null, "You must fill in all fields!");
			return false;
		}else if(!diag.checkValidDate()){
			JOptionPane.showMessageDialog(null, "Date is not in valid format! Valid format is yyyy-mm-dd");
			return false;
		}
 		return true;
 		
 	}

}
