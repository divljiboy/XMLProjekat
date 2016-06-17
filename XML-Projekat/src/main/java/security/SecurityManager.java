package security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

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






}
