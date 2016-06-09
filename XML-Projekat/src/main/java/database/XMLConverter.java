package database;

import javax.xml.bind.*;
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

        try {
            fos = new FileOutputStream(file);
            jaxbContext = JAXBContext.newInstance("xml.model");
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,"aktovi");
            marshaller.marshal(object,fos);
            //marshaller.marshal(new JAXBElement<T>(new QName("aktovi", "Korisnik"), (Class<T>) TKorisnik.class,object),fos);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



    }

    public T fromXMLtoObject(){
        try {
            jaxbContext = JAXBContext.newInstance("xml.model");
            unmarshaller = jaxbContext.createUnmarshaller();
            T object = (T) unmarshaller.unmarshal(new File("./src/main/resources/temp.xml"));
            return object;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean stringToFile(String string){
        try {
            File file = new File("./src/main/resources/temp.xml");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(string);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
