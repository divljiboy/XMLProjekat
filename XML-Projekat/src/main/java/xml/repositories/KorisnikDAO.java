package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.model.Korisnik;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("korisnikDAO")
public class KorisnikDAO extends GenericDAO<Korisnik,Long> implements IKorisnikDAO {
}
