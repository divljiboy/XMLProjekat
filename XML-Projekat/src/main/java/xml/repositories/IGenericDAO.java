package xml.repositories;

import database.DatabaseConfig;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Dorian on 31.5.2016.
 */
public interface IGenericDAO<T,K> {

    public void create(T object,String docId,String colId, DatabaseConfig.ConnectionProperties props) throws FileNotFoundException;
    public void update(T object);
    public void delete(T object);
    public List<T> getAll();
    public T get(String id);

}
