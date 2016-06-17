package xml.repositories;

import xml.model.Korisnik;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IUserDAO extends IGenericDAO<Korisnik,Long> {

    public Korisnik getByLogin(String username, String password) throws IOException, JAXBException;


    Korisnik getEntityWithMaxIdUser() throws JAXBException, IOException;
}
