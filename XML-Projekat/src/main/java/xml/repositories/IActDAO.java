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
    public void updateActState(Long id) throws IOException;

}