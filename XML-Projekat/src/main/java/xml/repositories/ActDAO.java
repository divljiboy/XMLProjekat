package xml.repositories;

import database.XMLConverter;
import org.springframework.stereotype.Repository;
import xml.Constants;
import xml.model.PravniAkt;

import javax.xml.bind.JAXBException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
                .append("return $x/ns:Pravni_akt[ns:Stanje = \"")
                .append(Constants.ProposedState)
                .append("\"]");

        System.out.print(query.toString());
        ArrayList<PravniAkt> aktovi = getByQuery(query.toString());

        return aktovi;
    }

    @Override
    public ArrayList<PravniAkt> getAdoptedActs() throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();
            query
                    .append("collection(\"")
                    .append(Constants.ActCollection)
                    .append("\")");

        System.out.print(query.toString());
        ArrayList<PravniAkt> aktovi = getByQuery(query.toString());

        return aktovi;
    }

    @Override
    public void updateActState(Long id,String state) throws IOException {
        StringBuilder query = new StringBuilder();
        query
                .append("declare namespace ns = ")
                .append("\"")
                .append(Constants.ActNamespace)
                .append("\";\n")
                .append("xdmp:node-replace(collection(\"")
                .append(Constants.ProposedActCollection)
                .append("\")/ns:Pravni_akt[@Id = ")
                .append(id.toString())
                .append("]/ns:Stanje,")
                .append("<ns:Stanje>")
                .append(state)
                .append("</ns:Stanje>")
                .append(");");

        execQuery(query.toString());
    }

    /**
     * Vraca akate kojima je stanje "Predlozen" radi promene stanja u "Nije usvojen"
     * @return Lista akata kojima treba da se promeni stanje iz "Predlozen" u "Nije usvojen"
     * @throws JAXBException
     * @throws IOException
     */
    @Override
    public ArrayList<PravniAkt> getProposedActsToChangeState() throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();
        query
                .append("declare namespace ns = ")
                .append("\"")
                .append(Constants.ActNamespace)
                .append("\";\n")
                .append("collection(\"")
                .append(Constants.ProposedActCollection)
                .append("\")/")
                .append("ns:Pravni_akt[ns:Stanje = '")
                .append(Constants.ProposedState)
                .append("']");

        ArrayList<PravniAkt> acts = getByQuery(query.toString());
        return acts;
    }


    @Override
    public String getXsltDocument(Long id) throws IOException {
        try {
            PravniAkt akt = this.get(id);

            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource("./src/main/schema/akt.xsl");
            Source xmlDoc = new StreamSource(new ByteArrayInputStream(xmlConverter.toXML(akt).getBytes(XMLConverter.UTF_8)));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(outputStream);

            Transformer trasform = tFactory.newTransformer(xslDoc);
            trasform.transform(xmlDoc, result);
            return outputStream.toString(XMLConverter.UTF_8.name());

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
