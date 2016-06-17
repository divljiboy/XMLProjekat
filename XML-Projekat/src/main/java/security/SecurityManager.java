package security;

import org.w3c.dom.Document;
import xml.model.Korisnik;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.Certificate;
import java.security.cert.*;

/**
 * Created by David on 6/17/2016.
 */
public class SecurityManager {



    public SecurityManager(){

    }



    public boolean writeStringToFile(String fileContent, String filePath)
    {
        boolean ret = false;
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + ".xml");
            fileOutputStream.write(fileContent.getBytes());
            fileOutputStream.close();
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public KeyStore loadKeyStore(String filePath,String pass){
        KeyStore keyStore = null;
        try {
            keyStore =   KeyStore.getInstance("JKS", "SUN");
            FileInputStream file = new FileInputStream(filePath);
            keyStore.load(file, pass.toCharArray());
        } catch (KeyStoreException e) {

        } catch (NoSuchProviderException e) {

            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyStore;

    }

    public PrivateKey getPK(KeyStore keyStore, String alias, char[] pass){

        try {
            return (PrivateKey)keyStore.getKey(alias, pass);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public java.security.cert.Certificate readCertificate(KeyStore ks, String alias, char[] password){
        java.security.cert.Certificate cert=null;
        try {
            if(ks.isKeyEntry(alias)) {
                cert = ks.getCertificate(alias);
            }
        } catch (KeyStoreException e) {

        }
        return cert;
    }


    public boolean singXml(String filePath, Korisnik user){

        boolean ret = false;

        try{
            SecurityManager sm = new SecurityManager();
            SignEnveloped signEnveloped = new SignEnveloped();
            Document document;
            if (filePath == null) {
                document  = signEnveloped.loadDocument("data/glupost.xml");
            }  else {
                document = signEnveloped.loadDocument(filePath);
            }
            KeyStore ks = sm.loadKeyStore("data/sgns.jks","sgns");
            PrivateKey pk = sm.getPK(ks,user.getUsername(),user.getPassword().toCharArray());
            java.security.cert.Certificate cert = sm.readCertificate(ks,user.getUsername(),user.getPassword().toCharArray());
            document = signEnveloped.signDocument(document,pk,cert);
            signEnveloped.saveDocument(document,"data/glupost2.xml");
            ret = true;

        } catch (Exception e){

        } finally {
            return  ret;
        }
    }

    /*
    XMLConverter<PravniAkt> converter = new XMLConverter<PravniAkt>("./src/main/schema/akt.xsd");
    String xml = converter.toXML((PravniAkt) entity);

    File file = new File("data/glupost.xml");


    try (FileOutputStream fop = new FileOutputStream(file)) {

        // if file doesn't exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        // get the content in bytes
        byte[] contentInBytes = xml.getBytes();

        fop.write(contentInBytes);
        fop.flush();
        fop.close();

        System.out.println("Done");

    } catch (IOException e) {
        e.printStackTrace();
    }

    boolean da = singXml(null,user);*/



}
