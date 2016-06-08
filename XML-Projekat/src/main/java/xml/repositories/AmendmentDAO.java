package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.model.Amandman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("amandmanDAO")
public class AmendmentDAO implements IAmendmentDAO {
    @Override
    public void create(Amandman amendment, String docId, String colId) throws FileNotFoundException, IOException {

    }

    @Override
    public void delete(Amandman amendment) throws FileNotFoundException, IOException {

    }

    @Override
    public List<Amandman> getAllFromAct(Long actId) throws FileNotFoundException, IOException {
        return null;
    }
}
