package database;

import xml.model.TKorisnik;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.*;

/**
 * Created by Dorian on 1.6.2016.
 */
public class XMLConverter<T> {

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private JAXBContext jaxbContext;

    public boolean fromObjectToXML(T object){

        File file = new File("./src/main/resources/temp.xml");
        FileOutputStream fos = null;

        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }


        try {
            fos = new FileOutputStream(file);
            jaxbContext = JAXBContext.newInstance("xml.model");
            System.out.print("napravio jaxb context");
            marshaller = jaxbContext.createMarshaller();
            System.out.print("Napravio marshaller");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,"aktovi");
            System.out.print("Setovao propertije");
            //marshaller.marshal(object,fos);
            marshaller.marshal(new JAXBElement<T>(new QName("aktovi", "Korisnik"), (Class<T>) TKorisnik.class,object),fos);
            System.out.print("napravio objekat");
            fos.close();
            System.out.print("Zavrsio Marshallovanje");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (JAXBException e) {
            System.out.print("OVDE JE USAOOOOO");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



    }

    public boolean fromXMLtoObject(){
        return false;
    }

}
