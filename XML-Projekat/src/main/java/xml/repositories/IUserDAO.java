package xml.repositories;

import xml.model.Korisnik;
import xml.model.PravniAkt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IUserDAO extends IGenericDAO<Korisnik,Long> {

    public void create(Korisnik korisnik, String docId, String colId ) throws FileNotFoundException , IOException;
    public void update(Korisnik korisnik,Long id) throws FileNotFoundException , IOException;
    public void delete(Korisnik korisnik) throws FileNotFoundException , IOException;
    public List<Korisnik> getAll() throws FileNotFoundException , IOException;

    public Korisnik getByLogin(String username,String password) throws IOException;
}
