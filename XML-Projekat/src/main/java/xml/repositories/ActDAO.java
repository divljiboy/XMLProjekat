package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.Constants;
import xml.model.PravniAkt;

import java.io.IOException;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("pravniAktDAO")
public class ActDAO extends GenericDAO<PravniAkt,Long> implements IActDAO {

    private static final String USER_SCHEMA_PATH = "./src/main/schema/akt.xsd";

    public ActDAO() throws IOException {
        super(USER_SCHEMA_PATH, Constants.ProposedActCollection , Constants.Act,Constants.ActNamespace);
    }

}
