package xml.repositories;

import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.*;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import database.DatabaseManager;
import database.XMLConverter;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;
import xml.Constants;
import xml.model.PravniAkt;

import javax.xml.bind.JAXBException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
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
    private static final String AKT_XSL_PATH = "./src/main/schema/akt.xsl";

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
    public ArrayList<PravniAkt> searchByText(String criteria,String collection) throws IOException {

        QueryManager queryManager = DatabaseManager.Client.getClient().newQueryManager();
        StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
        queryDefinition.setCriteria(criteria);
        queryDefinition.setCollections(collection);

        SearchHandle results = queryManager.search(queryDefinition,new SearchHandle());

        MatchDocumentSummary matches[] = results.getMatchResults();

        MatchDocumentSummary result;
        MatchLocation locations[];
        String text;

        ArrayList<PravniAkt> searchedActs = new ArrayList<>();

        for (int i = 0; i < matches.length; i++) {
            result = matches[i];

            System.out.println((i+1) + ". RESULT DETAILS: ");
            System.out.println("Result URI: " + result.getUri());

            StringBuilder query = new StringBuilder();
            query
                    .append("fn:doc(\"")
                    .append(result.getUri())
                    .append("\")");

            try {
                PravniAkt act = getByQuery(query.toString()).get(0);
                if(collection.equals(Constants.ActCollection)){
                    if(act.getStanje().equals("Usvojen"))
                        searchedActs.add(act);
                }else if(collection.equals(Constants.ProposedActCollection)){
                    if(act.getStanje().equals("Predlozen"))
                        searchedActs.add(act);
                }


            } catch (JAXBException e) {
                e.printStackTrace();
            }


            String uriToSearchedElement;

            locations = result.getMatchLocations();
            System.out.println("Document locations matched: " + locations.length + "\n");

            for (MatchLocation location : locations) {

                System.out.print(" - ");
                for (MatchSnippet snippet : location.getSnippets()) {
                    text = snippet.getText().trim();
                    if (!text.equals("")) {
                        System.out.print(snippet.isHighlighted()? text.toUpperCase() : text);
                        System.out.print(" ");
                    }
                }
                System.out.println("\n - Match location XPath: " + location.getPath());
                System.out.println();
            }

            System.out.println();
        }

        return searchedActs;
    }


    @Override
    public String getXsltDocument(Long id,Integer colId) throws IOException {
        try {
            PravniAkt akt = null;
            if(colId == 1)
                akt = this.get(id,Constants.ActCollection);
            else
                akt = this.get(id,Constants.ProposedActCollection);

            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource(AKT_XSL_PATH);
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

    @Override
    public ArrayList<PravniAkt> searchByMetadata(Integer collectionName, Integer metadataType, String criteria) {


        switch (metadataType) {
            case 1: //taksonomija

                break;
            case 2: //Predlagac
                break;
            case 3: //Datum objave
                break;
            case 4: //Ko je usvojio
                break;
            case 5: //Za
                break;
            case 6: //Protiv
                break;
            case 7: //Suzdrzano
                break;

        }

        return null;
    }

    @Override
    public ByteArrayOutputStream getPdf(Long id, String colName) throws JAXBException, IOException, TransformerException, SAXException {
        FopFactory fopFactory = FopFactory.newInstance();
        //fopFactory.setUserConfig(Constants.FOP_CONF);
        TransformerFactory transformerFactory = new TransformerFactoryImpl();
        StreamSource transformSource = new StreamSource(new File("./src/main/schema/akt-fo.xsl"));
        StreamSource xmlSource = new StreamSource(new ByteArrayInputStream(xmlConverter.toXML(this.get(id,colName)).getBytes(XMLConverter.UTF_8)));
        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
        Result res = new SAXResult(fop.getDefaultHandler());
        xslFoTransformer.transform(xmlSource, res);

        return outStream;
    }

}
