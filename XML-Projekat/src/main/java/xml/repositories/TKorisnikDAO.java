package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.model.TKorisnik;

/**
 * Created by Dorian on 4.6.2016.
 */
@Repository("korisnikDAO")
public class TKorisnikDAO extends GenericDAO<TKorisnik,Integer> implements ITKorisnikDAO{
}
