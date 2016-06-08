package xml.repositories;

import org.springframework.stereotype.Repository;
import xml.model.PravniAkt;

/**
 * Created by Dorian on 8.6.2016.
 */
@Repository("pravniAktDAO")
public class PravniAktDAO extends GenericDAO<PravniAkt,Long> implements IPravniAktDAO {
}
