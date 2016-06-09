package xml.repositories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import database.DatabaseConfig;
import database.XMLConverter;
import org.springframework.stereotype.Repository;
import xml.Constants;
import xml.model.Korisnik;
import xml.model.PravniAkt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("pravniAktDAO")
public class ActDAO extends GenericDAO<PravniAkt,Long> implements IActDAO {

    XMLConverter<Korisnik> xmlConverter;
    private static DatabaseClient client;
    private static final String ACT_SCHEMA_PATH = "./src/main/schema/akt.xsd";

    public ActDAO(String path) throws IOException {
        super(ACT_SCHEMA_PATH);
    }


    @Override
    public void create(PravniAkt act, String docId, String colId) throws FileNotFoundException, IOException {
        add(act,docId,colId);
    }

    @Override
    public void update(PravniAkt act, Long id) throws FileNotFoundException, IOException {

    }

    @Override
    public void delete(PravniAkt act) throws FileNotFoundException, IOException {

    }

    @Override
    public List<PravniAkt> getAll() throws FileNotFoundException, IOException {
        StringBuilder query = new StringBuilder();

        query
                .append("collection(\"")
                .append(Constants.ActCollection)
                .append("\")");

        return getByQuery(query.toString());
    }

    @Override
    public PravniAkt get(Long id) throws FileNotFoundException, IOException {
        StringBuilder query = new StringBuilder();

        query
                .append("collection(\"")
                .append(Constants.ActCollection)
                .append("\")/Pravni_akt[@Id = ")
                .append(id.toString()+"]");

        ArrayList<PravniAkt> acts = getByQuery(query.toString());
        if(acts == null) {
            return null;
        }

        return acts.get(0);
    }
}
