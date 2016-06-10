package xml.repositories;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Daniel on 6/10/2016.
 */
public interface IGenericDAO<E,K> {
    public void create(E entity, String docId, String colId ) throws FileNotFoundException , IOException;
    public void update(E entity,Long id) throws FileNotFoundException , IOException;
    public void delete(E entity) throws FileNotFoundException , IOException;
    public List<E> getAll() throws FileNotFoundException , IOException;
    public E get(Long id) throws FileNotFoundException, IOException;
}