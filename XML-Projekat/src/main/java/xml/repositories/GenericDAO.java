package xml.repositories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;
import database.DatabaseConfig;
import database.XMLConverter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Dorian on 31.5.2016.
 */
public class GenericDAO<T,K> implements IGenericDAO<T,K> {

    XMLConverter<T> xmlConverter;
    private static DatabaseClient client;

    @Override
    public void create(T object,String docId,String colId, DatabaseConfig.ConnectionProperties props) throws FileNotFoundException{
//  FileOutputStream fos = new FileOutputStream("/src/main/resources/testing.xml")

        if(props.database.equals("")){
            client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, DatabaseClientFactory.Authentication.DIGEST);
        }else{
            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, DatabaseClientFactory.Authentication.DIGEST);
        }

        xmlConverter = new XMLConverter<T>("nesto");

        if (xmlConverter.fromObjectToXML(object)){
            //write to Database
            XMLDocumentManager xmlManager = client.newXMLDocumentManager();

            InputStreamHandle handle = new InputStreamHandle(new FileInputStream("./src/main/resources/temp.xml"));
            xmlManager.write(docId, handle);

            System.out.print("Upisao uspesno u bazu");

        }else{
            //Error
            System.out.print("Cannot convert to xml file.");
        }

    }

    @Override
    public void update(T object,Long id) {

    }

    @Override
    public void delete(T object) {

    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public T get(Long id) {
        return null;
    }
}
