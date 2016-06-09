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
public class ActDAO /* extends GenericDAO<PravniAkt,Long>*/ implements IActDAO {

    @Override
    public void create(PravniAkt act, String docId, String colId) throws FileNotFoundException, FileNotFoundException {

    }

    @Override
    public void update(PravniAkt act, Long id) throws FileNotFoundException, FileNotFoundException {

    }

    @Override
    public void delete(PravniAkt act) throws FileNotFoundException, FileNotFoundException {

    }

    @Override
    public List<PravniAkt> getAll() throws FileNotFoundException, FileNotFoundException {
        return null;
    }

    @Override
    public PravniAkt get(Long id) throws FileNotFoundException, FileNotFoundException {
        return null;
    }
}
