package xml.repositories;

import database.XMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xml.Constants;
import xml.model.*;

import javax.xml.bind.JAXBException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("amandmanDAO")
public class AmendmentDAO extends GenericDAO<Amandman,Long> implements IAmendmentDAO {

    @Autowired
    private IActDAO actDAO;
    private static final String AMENDMENT_SCHEMA_PATH = "./src/main/schema/amandman.xsd";
    private static final String AMENDMENT_XSL_PATH = "./src/main/schema/amandman.xsl";

    public AmendmentDAO() throws IOException {
        super(AMENDMENT_SCHEMA_PATH,Constants.ProposedAmendmentCollection,Constants.Amendment,Constants.AmendmentNamespace);
    }

    /**
     * Metoda reprezentuje glasanje na sednici
     * @param actsIds Id-jevi akata koji su izglasani za usvajanje
     * @param amendmentsIds Id-jevi amandmana koji su izglasani za usvajanje
     */
    @Override
    public void voting(ArrayList<Long> actsIds,ArrayList<Long> amendmentsIds) throws JAXBException, IOException {

        copyActToAdopted(actsIds);
        changeStateToNotAdopted();

        for(Long id : amendmentsIds){
            applyAmendment(id);
        }
    }

    @Override
    public ArrayList<Amandman> getAmendmentsForAct(Long actId) throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();
        query
                .append("declare namespace ns = ")
                .append("\"")
                .append(Constants.AmendmentNamespace)
                .append("\";\n")
                .append("collection(\"")
                .append(Constants.ProposedAmendmentCollection)
                .append("\")/")
                .append("ns:Amandman/ns:Kontekst[@actId = \"")
                .append(actId.toString())
                .append("\"]/parent::ns:Amandman");

        ArrayList<Amandman> amendments = getByQuery(query.toString());

        return amendments;
    }

    @Override
    public String getXsltDocument(Long id) throws IOException {
        try {
            Amandman amandman = this.get(id);

            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource(AMENDMENT_XSL_PATH);
            Source xmlDoc = new StreamSource(new ByteArrayInputStream(xmlConverter.toXML(amandman).getBytes(XMLConverter.UTF_8)));

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

    /**
     * Metoda kopira sve izglasane predlozene aktove u usvojene aktove
     * from collection(/predlozeniAkati) to collection(/usvojeniAkati)
     * @param actsIds Id-jevi akata koji su izglasani za usvajanje
     */
    protected void copyActToAdopted(ArrayList<Long> actsIds){
        for(Long id : actsIds){
            try {

                PravniAkt act = actDAO.get(id);
                act.setStanje(Constants.AdoptedState);
                actDAO.updateActState(id,Constants.AdoptedState);
                actDAO.create(act,Constants.AdoptedAct+act.getId().toString(),Constants.ActCollection);
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metoda primenjuje amandman na akt
     * @param id Id amandmana
     */
    protected void applyAmendment(Long id) {

        try {
            Amandman amendment = get(id);
            for(Podamandman amendmentPart : amendment.getPodamandman()){
                applyOnAct(amendment.getIdAct(),amendment.getId(),amendmentPart.getId(),amendmentPart.getSadrzaj(),amendmentPart.getOperacija().toString());
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * U zavisnosti od operacije, vrsi se dodavanje/brisanje izmena akta iz collection("usvojeniAkati")
     * Metoda generise xQuery
     * @param actId Id akta
     * @param amemndmentId Id amandmana
     * @param content Sadrzaj iz amandmana koji ce se primeniti na akt u zavisnosti od operacije
     * @param operation Operacija amandmana (dodavanje, brisanje, izmena)
     * @throws JAXBException
     * @throws IOException
     */
    protected void applyOnAct(Long actId, Long amemndmentId,Long amendmentPartId, Podamandman.Sadrzaj content, String operation) throws JAXBException, IOException {

        StringBuilder xQuery = new StringBuilder();

        if(operation.equals(Constants.Add)){
            xQuery
                    .append("declare namespace ns = \"aktovi\";\n")
                    .append("xdmp:node-insert-after(collection(\"/usvojeniAkati\")/ns:")
                    .append(Constants.Act)
                    .append("[@Id = ")
                    .append(actId.toString())
                    .append("]/")
                    .append("ns:Glavni_deo/");


            if(content.getRDeo() != null){
                xQuery
                        .append("ns:Deo[@Id=")
                        .append(content.getRDeo())
                        .append("]/");
            }

            if(content.getRGlava() != null){
                xQuery
                        .append("ns:Glava[@Id=")
                        .append(content.getRGlava())
                        .append("]/");

            }

            if(content.getROdeljak() != null){
                xQuery
                        .append("ns:Odeljak[@Id=")
                        .append(content.getROdeljak())
                        .append("]/");
            }

            if(content.getRClan() != null){
                xQuery
                        .append("ns:Clan[@Broj_clana=")
                        .append(content.getRClan())
                        .append("]\n");
            }

            if(content.getRStav() != null){
                xQuery
                        .append("/ns:Stav[@Id=")
                        .append(content.getRStav())
                        .append("]\n");
            }

            if(content.getRTacka() != null){
                xQuery
                        .append("/ns:Tacka[@broj=")
                        .append(content.getRTacka())
                        .append("]\n");
                if(content.getRPodtacka() != null){
                    xQuery
                            .append("/ns:Podtacka[@broj=")
                            .append(content.getRTacka())
                            .append("]\n");
                }
            }

            if(content.getRAlineja() != null){
                xQuery
                        .append("ns:Alineja[@Id=")
                        .append(content.getRAlineja())
                        .append("]\n");
            }
            xQuery.append(",");
            if(content.getGlavniDeo() != null)
                xQuery.append(getAmendmentContent(amemndmentId,amendmentPartId,true));
            else
                xQuery.append(getAmendmentContent(amemndmentId,amendmentPartId,false));

            xQuery.append(");");

            System.out.print(xQuery.toString());
            execQuery(xQuery.toString());


        }else if(operation.equals(Constants.Update)){
            xQuery
                    .append("declare namespace ns = \"aktovi\";\n")
                    .append("xdmp:node-replace(collection(\"/usvojeniAkati\")/ns:")
                    .append(Constants.Act)
                    .append("[@Id = ")
                    .append(actId.toString())
                    .append("]/")
                    .append("ns:Glavni_deo/");

            if(content.getRDeo() != null){
                xQuery
                        .append("ns:Deo[@Id=")
                        .append(content.getRDeo())
                        .append("]/");
            }

            if(content.getRGlava() != null){
                xQuery
                        .append("ns:Glava[@Id=")
                        .append(content.getRGlava())
                        .append("]/");

            }

            if(content.getROdeljak() != null){
                xQuery
                        .append("ns:Odeljak[@Id=")
                        .append(content.getROdeljak())
                        .append("]/");
            }

            if(content.getRClan() != null){
                xQuery
                        .append("ns:Clan[@Broj_clana=")
                        .append(content.getRClan())
                        .append("]\n");
            }

            if(content.getRStav() != null){
                xQuery
                        .append("/ns:Stav[@Id=")
                        .append(content.getRStav())
                        .append("]\n");
            }

            if(content.getRTacka() != null){
                xQuery
                        .append("/ns:Tacka[@broj=")
                        .append(content.getRTacka())
                        .append("]\n");
                if(content.getRPodtacka() != null){
                    xQuery
                            .append("/ns:Podtacka[@broj=")
                            .append(content.getRTacka())
                            .append("]\n");
                }
            }

            if(content.getRAlineja() != null){
                xQuery
                        .append("/ns:Alineja[@Id=")
                        .append(content.getRAlineja())
                        .append("]\n");
            }

            xQuery.append(",");

            if(content.getGlavniDeo() != null)
                xQuery.append(getAmendmentContent(amemndmentId,amendmentPartId,true));
            else
                xQuery.append(getAmendmentContent(amemndmentId,amendmentPartId,false));

            xQuery.append(");");

            System.out.print(xQuery.toString());
            execQuery(xQuery.toString());

        }else if (operation.equals(Constants.Delete)){
            xQuery
                    .append("declare namespace ns = \"aktovi\";\n")
                    .append("xdmp:node-delete(collection(\"/usvojeniAkati\")/ns:")
                    .append(Constants.Act)
                    .append("[@Id = ")
                    .append(actId.toString())
                    .append("]/")
                    .append("ns:Glavni_deo/");

            if(content.getRDeo() != null){
                xQuery
                        .append("ns:Deo[@Id=")
                        .append(content.getRDeo())
                        .append("]/");
            }

            if(content.getRGlava() != null){
                xQuery
                        .append("ns:Glava[@Id=")
                        .append(content.getRGlava())
                        .append("]/");

            }

            if(content.getROdeljak() != null){
                xQuery
                        .append("ns:Odeljak[@Id=")
                        .append(content.getROdeljak())
                        .append("]/");
            }

            if(content.getRClan() != null){
                xQuery
                        .append("ns:Clan[@Broj_clana=")
                        .append(content.getRClan())
                        .append("]");
            }


            if(content.getRStav() != null){
                xQuery
                        .append("/ns:Stav[@Id=")
                        .append(content.getRStav())
                        .append("]\n");
            }

            if(content.getRTacka() != null){
                xQuery
                        .append("/ns:Tacka[@broj=")
                        .append(content.getRTacka())
                        .append("]\n");
                if(content.getRPodtacka() != null){
                    xQuery
                            .append("/ns:Podtacka[@broj=")
                            .append(content.getRTacka())
                            .append("]\n");
                }
            }

            if(content.getRAlineja() != null){
                xQuery
                        .append("/ns:Alineja[@Id=")
                        .append(content.getRAlineja())
                        .append("]\n");
            }

            xQuery.append(")");

            System.out.print(xQuery.toString());
            execQuery(xQuery.toString());
        }


    }

    /**
     * Metoda generise xQuery za dobijanje sadrzaja iz amandmana sa zadatim id-jem
     * @param id Id amandmana
     * @return String XML sadrzaj amandmana
     */
    protected String getAmendmentContent(Long id,Long amendmentPartId,boolean isGlavniDeo) {
        StringBuilder query = new StringBuilder();
        query
                .append("declare namespace ns = \"")
                .append("amandmani")
                .append("\";\n")
                .append("declare namespace ns1 = \"")
                .append("aktovi")
                .append("\";\n")
                .append("for $x in collection(\"")
                .append(Constants.ProposedAmendmentCollection)
                .append("\")\n")
                .append("return $x/ns:")
                .append("Amandman")
                .append("[@Id = ");

        //String contetn = null;
        if(isGlavniDeo) {
            query.append(id.toString() + "]/ns:Podamandman[@Id = " + amendmentPartId + "]/ns:Sadrzaj/ns1:Glavni_deo/node()");
            //contetn = getStringByQuery(query.toString()).get(1);
        }else {
            query.append(id.toString() + "]/ns:Podamandman[@Id = " + amendmentPartId + "]/ns:Sadrzaj/node()");
            //contetn = getStringByQuery(query.toString()).get(0);
        }

        return getStringByQuery(query.toString());
    }

    /**
     * Preuzima aktove koji nisu usvojeni i postavlja stanje na NotAdopted
     * @throws JAXBException
     * @throws IOException
     */
    protected void changeStateToNotAdopted() throws JAXBException, IOException {

        ArrayList<PravniAkt> acts = actDAO.getProposedActsToChangeState();
        for(PravniAkt act : acts){
            actDAO.updateActState(act.getId(),Constants.NotAdoptedState);
        }

    }

}
