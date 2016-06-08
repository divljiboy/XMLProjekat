package xml.repositories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import database.DatabaseConfig;
import database.XMLConverter;
import org.springframework.stereotype.Repository;
import xml.model.Korisnik;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("korisnikDAO")
public class UserDAO implements IUserDAO {

    XMLConverter<Korisnik> xmlConverter;
    private static DatabaseClient client;

    @Override
    public List<Korisnik> getAllUsers() throws FileNotFoundException, IOException {
        return null;
    }

    @Override
    public Korisnik findById(Long id) throws FileNotFoundException, IOException {
        return null;
    }

    @Override
    public void createUser(Korisnik korisnik, String docId, String colId) throws IOException {

        DatabaseConfig.ConnectionProperties props = DatabaseConfig.loadProperties();

        if(props.database.equals("")){
            client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, DatabaseClientFactory.Authentication.DIGEST);
        }else{
            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, DatabaseClientFactory.Authentication.DIGEST);
        }

        xmlConverter = new XMLConverter<Korisnik>();

        if (xmlConverter.fromObjectToXML(korisnik)){
            //write to Database
            XMLDocumentManager xmlManager = client.newXMLDocumentManager();

            DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
            metadataHandle.getCollections().add(colId);

            InputStreamHandle handle = new InputStreamHandle(new FileInputStream("./src/main/resources/temp.xml"));
            xmlManager.write(docId,metadataHandle, handle);

            System.out.print("Upisao uspesno u bazu");

        }else{
            //Error
            System.out.print("Cannot convert to xml file.");
        }
    }
}
