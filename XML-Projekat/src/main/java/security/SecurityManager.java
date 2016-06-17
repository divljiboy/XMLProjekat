package security;

import org.w3c.dom.Document;
import xml.model.Korisnik;

import java.io.*;
import java.security.*;
import java.security.Certificate;
import java.security.cert.*;
import java.time.Clock;

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
            File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
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
        System.out.println(pass);
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

            SignEnveloped signEnveloped = new SignEnveloped();
            Document document;

            document = signEnveloped.loadDocument(filePath);

            KeyStore ks = this.loadKeyStore("data/sgns.jks","sgns");
            PrivateKey pk = this.getPK(ks,user.getUsername(),user.getPassword().toCharArray());
            java.security.cert.Certificate cert = this.readCertificate(ks,user.getUsername(),user.getPassword().toCharArray());
            document = signEnveloped.signDocument(document,pk,cert);
            signEnveloped.saveDocument(document,filePath);
            ret = true;

        } catch (Exception e){

        } finally {
            return  ret;
        }
    }





}
