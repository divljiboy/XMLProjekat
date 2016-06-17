package security;

import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CRLConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import sun.security.x509.X500Name;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.security.*;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by David on 6/17/2016.
 */
public class CertificateRevocationList {
    private X509CRL crl = null;
    private SecurityManager sm = new SecurityManager();
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    public CertificateRevocationList(){

        KeyStore ks = sm.loadKeyStore("data/sgns.jks","sgns");
        PrivateKey pk = null;
        try {
            pk = (PrivateKey) ks.getKey("sgns",("sgns").toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        X509Certificate cert = null;
        try {
            cert = (X509Certificate) ks.getCertificate("sgns");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        // Issuer name
        X500Principal prnc = cert.getIssuerX500Principal();
        org.bouncycastle.asn1.x500.X500Name CA = org.bouncycastle.asn1.x500.X500Name.getInstance(prnc.getEncoded());

        // Signed by our CA
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        builder.setProvider("BC");
        ContentSigner contentSigner = null;
        try {
            contentSigner = builder.build(pk);
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }

        Date today = Calendar.getInstance().getTime();
        // Create the CRL
        X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(CA, today);
        crlBuilder.addCRLEntry(cert.getSerialNumber(), today, CRLReason.unspecified);
        X509CRLHolder holder = crlBuilder.build(contentSigner);
        JcaX509CRLConverter cnv = new JcaX509CRLConverter();
        cnv.setProvider("BC");
        X509CRL crl = null;
        try {
             crl = cnv.getCRL(holder);
        } catch (CRLException e) {
            e.printStackTrace();
        }

       // this.loadCLR("./crl/sgns.crl");
        System.out.println(crl.isRevoked(cert));

    }

    public boolean isRevoked(Certificate certificate){
        if (crl.isRevoked(certificate)) return true;
        else return false;
    }

    public boolean saveCLR(String filePath)  {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            fileOutputStream.write(crl.getEncoded());
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e){
            e.printStackTrace();

        } catch (CRLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;

    }


    public boolean loadCLR(String filePath){
        try{
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
