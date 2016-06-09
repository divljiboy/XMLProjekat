package xml.repositories;

import xml.model.Korisnik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IUserDAO {

    public List<Korisnik> getAllUsers()throws FileNotFoundException,IOException;
    public Korisnik findById(Long id)throws FileNotFoundException, IOException;
    public void createUser(Korisnik korisnik,String docId,String colId) throws  FileNotFoundException,IOException;
    public ArrayList<Korisnik> getByQuery(String query) throws FileNotFoundException,IOException;
}
