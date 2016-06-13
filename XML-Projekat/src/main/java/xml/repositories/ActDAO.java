package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.Constants;
import xml.model.PravniAkt;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("pravniAktDAO")
public class ActDAO extends GenericDAO<PravniAkt,Long> implements IActDAO {

    private static final String USER_SCHEMA_PATH = "./src/main/schema/akt.xsd";

    public ActDAO() throws IOException {
        super(USER_SCHEMA_PATH, Constants.ProposedActCollection , Constants.Act,Constants.ActNamespace);
    }


    @Override
    public ArrayList<PravniAkt> getProposedActs() throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();
        query
                .append("declare namespace ns = ")
                .append("\"")
                .append(Constants.ActNamespace)
                .append("\";\n")
                .append("for $x in collection(\"")
                .append(Constants.ProposedActCollection)
                .append("\")\n")
                .append("return $x/ns:Pravni_akt[@Stanje = \"")
                .append(Constants.ProposedState)
                .append("\"]");

        System.out.print(query.toString());
        ArrayList<PravniAkt> aktovi = getByQuery(query.toString());

        return aktovi;
    }
}
