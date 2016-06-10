package xml.repositories;

import xml.model.Amandman;
import xml.model.PravniAkt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IAmendmentDAO extends IGenericDAO<Amandman,Long> {

    public void create(Amandman amandman, String docId, String colId ) throws FileNotFoundException , IOException;
    public void update(Amandman amandman,Long id) throws FileNotFoundException , IOException;
    public void delete(Amandman amandman) throws FileNotFoundException , IOException;
    public List<Amandman> getAll() throws FileNotFoundException , IOException;
    public Amandman get(Long id) throws FileNotFoundException, IOException;

}
