package xml.repositories;

import xml.model.PravniAkt;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IActDAO extends IGenericDAO<PravniAkt,Long>{

    void create(PravniAkt act, String docId, String colId ) throws JAXBException, IOException;
    void update(PravniAkt act,Long id) throws JAXBException, IOException;
    void delete(PravniAkt act) throws JAXBException, IOException;
    List<PravniAkt> getAll() throws JAXBException, IOException;
    PravniAkt get(Long id) throws JAXBException, IOException;

}