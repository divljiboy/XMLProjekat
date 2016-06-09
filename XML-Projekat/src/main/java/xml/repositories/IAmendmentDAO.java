package xml.repositories;

import xml.model.Amandman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IAmendmentDAO {

    public void create(Amandman amendment, String docId, String colId) throws FileNotFoundException , IOException;
    public void delete(Amandman amendment) throws FileNotFoundException , IOException;
    public List<Amandman> getAllFromAct(Long actId) throws FileNotFoundException , IOException;

}
