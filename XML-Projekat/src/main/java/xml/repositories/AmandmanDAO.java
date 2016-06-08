package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.model.Amandman;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("amandmanDAO")
public class AmandmanDAO extends GenericDAO<Amandman,Long> implements IAmandmanDAO {
}
