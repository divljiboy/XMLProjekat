package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.model.PravniAkt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("pravniAktDAO")
public class ActDAO implements IActDAO {
    @Override
    public void create(PravniAkt act, String docId, String colId) throws FileNotFoundException, IOException {

    }

    @Override
    public void update(PravniAkt act, Long id) throws FileNotFoundException, IOException {

    }

    @Override
    public void delete(PravniAkt act) throws FileNotFoundException, IOException {

    }

    @Override
    public List<PravniAkt> getAll() throws FileNotFoundException, IOException {
        ArrayList<PravniAkt> akati = new ArrayList<>();
        PravniAkt akt = new PravniAkt();
        akt.setDrzava("lalalal");
        akati.add(akt);
        akati.add(akt);
        return akati;
    }

    @Override
    public PravniAkt get(Long id) throws FileNotFoundException, IOException {
        return null;
    }
}
