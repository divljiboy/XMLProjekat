package xml.repositories;

import xml.model.PravniAkt;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;

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

    public ArrayList<PravniAkt> searchByMetadata(Integer collectionName, Integer metadataType, String criteria);
}