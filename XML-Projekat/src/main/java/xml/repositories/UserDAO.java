package xml.repositories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.sun.tools.jxc.ap.Const;
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
public class UserDAO extends GenericDAO<Korisnik,Long> implements IUserDAO {

    private static final String USER_SCHEMA_PATH = "./src/main/schema/korisnici.xsd";

    public UserDAO() throws IOException {
        super(USER_SCHEMA_PATH);
    }

    @Override
    public List<Korisnik> getAllUsers() throws FileNotFoundException, IOException {

        StringBuilder query = new StringBuilder();

        query
                .append("collection(\"")
                .append(Constants.UsersCollection)
                .append("\")");

        return getByQuery(query.toString());

    }

    @Override
    public Korisnik findById(Long id) throws FileNotFoundException, IOException {

        StringBuilder query = new StringBuilder();

        query
                .append("collection(\"")
                .append(Constants.UsersCollection)
                .append("\")/")
                .append(Constants.User)
                .append("[@Id = ")
                .append(id.toString()+"]");

        ArrayList<Korisnik> korisnici = getByQuery(query.toString());
        if(korisnici == null) {
            return null;
        }

        return korisnici.get(0);

    }

    @Override
    public void createUser(Korisnik korisnik, String docId, String colId) throws IOException {

        add(korisnik,docId,colId);
    }

    @Override
    public Korisnik getByLogin(String username, String password) throws IOException {
        StringBuilder query = new StringBuilder();

        query
                .append("collection(\"")
                .append(Constants.UsersCollection)
                .append("\")")
                .append("/Korisnik[Username=\"")
                .append(username)
                .append("\" and Password=\"")
                .append(password)
                .append("\"]");


        return getByQuery(query.toString()).get(0);
    }
}
