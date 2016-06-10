package xml.repositories;

import xml.model.Amandman;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IAmendmentDAO extends IGenericDAO<Amandman,Long> {

    void create(Amandman amandman, String docId, String colId ) throws JAXBException,  IOException;
    void update(Amandman amandman,Long id) throws JAXBException ,IOException;
    void delete(Amandman amandman) throws JAXBException, IOException;
    List<Amandman> getAll() throws JAXBException, IOException;
    Amandman get(Long id) throws JAXBException, IOException;

}
