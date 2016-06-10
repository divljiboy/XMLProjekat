package xml.repositories;

import xml.model.PravniAkt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IActDAO extends IGenericDAO<PravniAkt,Long>{

    public void create(PravniAkt act, String docId, String colId ) throws FileNotFoundException , IOException;
    public void update(PravniAkt act,Long id) throws FileNotFoundException , IOException;
    public void delete(PravniAkt act) throws FileNotFoundException , IOException;
    public List<PravniAkt> getAll() throws FileNotFoundException , IOException;
    public PravniAkt get(Long id) throws FileNotFoundException, IOException;

}