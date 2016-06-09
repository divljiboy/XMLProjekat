package xml.repositories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import database.DatabaseConfig;
import database.XMLConverter;
import org.springframework.stereotype.Repository;
import xml.Constants;
import xml.model.Korisnik;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

        StringBuilder query = new StringBuilder();

        query.append("collection(\"");
        query.append(Constants.UsersCollection);
        query.append("\")");

        return getByQuery(query.toString());

    }

    @Override
    public Korisnik findById(Long id) throws FileNotFoundException, IOException {

        StringBuilder query = new StringBuilder();

        query.append("collection(\"");
        query.append(Constants.UsersCollection);
        query.append("\")/Korisnik[@Id = ");
        query.append(id.toString()+"]");

        System.out.print(query.toString());

        ArrayList<Korisnik> korisnici = getByQuery(query.toString());
        if(korisnici == null) {
            return null;
        }

        return korisnici.get(0);

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

    @Override
    public ArrayList<Korisnik> getByQuery(String query) throws FileNotFoundException, IOException {
        DatabaseConfig.ConnectionProperties props = DatabaseConfig.loadProperties();

        if(props.database.equals("")){
            client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, DatabaseClientFactory.Authentication.DIGEST);
        }else{
            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, DatabaseClientFactory.Authentication.DIGEST);
        }

        xmlConverter = new XMLConverter<Korisnik>();

        EvalResultIterator iterator = null;

        ServerEvaluationCall invoker = client.newServerEval();
        invoker.xquery(query);

        iterator = invoker.eval();

        ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();


        if(iterator.hasNext()) {
            for (EvalResult res : iterator) {
                if (xmlConverter.stringToFile(res.getString())) {
                    Korisnik kor = xmlConverter.fromXMLtoObject();
                    korisnici.add(kor);
                }
            }
        }else{
            return null;
        }

        return korisnici;
    }
}
