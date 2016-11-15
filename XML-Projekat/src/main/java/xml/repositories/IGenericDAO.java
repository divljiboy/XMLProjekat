package xml.repositories;

import xml.model.Korisnik;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Daniel on 6/10/2016.
 */
public interface IGenericDAO<E,K extends Serializable> {
  //  void create(E entity, String docId, String colId, Korisnik user) throws JAXBException, IOException;
    void create(E entity, String docId, String colId) throws JAXBException, IOException;
    void update(E entity,Long id) throws JAXBException, IOException;
    void delete(Long id, String constant) throws JAXBException, IOException;
    List<E> getAll() throws JAXBException, IOException;
    E get(Long id,String collection) throws JAXBException, IOException;
    E getEntityWithMaxId(String colId, String ns, String entity) throws JAXBException, IOException;
}