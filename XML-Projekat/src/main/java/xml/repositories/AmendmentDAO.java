package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.model.Amandman;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("amandmanDAO")
public class AmendmentDAO implements IAmendmentDAO {

    @Override
    public void create(Amandman amandman, String docId, String colId) throws FileNotFoundException, FileNotFoundException {

    }

    @Override
    public void update(Amandman amandman, Long id) throws FileNotFoundException, FileNotFoundException {

    }

    @Override
    public void delete(Amandman amandman) throws FileNotFoundException, FileNotFoundException {

    }

    @Override
    public List<Amandman> getAll() throws FileNotFoundException, FileNotFoundException {
        return null;
    }

    @Override
    public Amandman get(Long id) throws FileNotFoundException, FileNotFoundException {
        return null;
    }
}
