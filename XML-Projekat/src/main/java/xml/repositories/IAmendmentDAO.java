package xml.repositories;

import xml.model.Amandman;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dorian on 8.6.2016.
 */
public interface IAmendmentDAO extends IGenericDAO<Amandman,Long> {

    public void voting(ArrayList<Long> actsIds,ArrayList<Long> amendmentsIds) throws JAXBException, IOException;
    public ArrayList<Amandman> getAmendmentsForAct(Long actId) throws JAXBException, IOException;
    String getXsltDocument(Long id) throws IOException;


}
