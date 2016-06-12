package xml.repositories;

import xml.model.Amandman;

import java.util.ArrayList;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IAmendmentDAO extends IGenericDAO<Amandman,Long> {

    public void voting(ArrayList<Long> actsIds,ArrayList<Long> amendmentsIds);

}
