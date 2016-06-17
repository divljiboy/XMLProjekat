package xml.repositories;

import org.xml.sax.SAXException;
import xml.controller.dto.SearchMetadataDTO;
import xml.model.PravniAkt;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IActDAO extends IGenericDAO<PravniAkt,Long>{

    public ArrayList<PravniAkt> getProposedActs() throws JAXBException, IOException;
    public ArrayList<PravniAkt> getAdoptedActs() throws JAXBException, IOException;
    public void updateActState(Long id,String state) throws IOException;
    public ArrayList<PravniAkt> getProposedActsToChangeState() throws JAXBException, IOException;
    public ArrayList<PravniAkt> searchByText(String criteria,String collection) throws IOException;

    String getXsltDocument(Long id,Integer colId) throws IOException;
    ByteArrayOutputStream getPdf(Long id, String colName) throws IOException, JAXBException, TransformerException, SAXException;

    public ArrayList<PravniAkt> searchByMetadata(Integer collectionName, ArrayList<SearchMetadataDTO> metadataType) throws JAXBException, IOException;
}