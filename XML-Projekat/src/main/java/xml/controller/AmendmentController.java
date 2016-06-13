package xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.Constants;
import xml.controller.dto.ActsAndAmendemntsIdsDTO;
import xml.model.Amandman;
import xml.repositories.IAmendmentDAO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Daniel on 6/8/2016.
 */
@RestController
public class AmendmentController{

    @Autowired
    private IAmendmentDAO amendmentDao;

    @RequestMapping(value = "/amandman/{aktId}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Amandman>> getAllAmendmentsForAct(@PathVariable("aktId") Long aktId) {
        try{
            ArrayList<Amandman> amendments = amendmentDao.getAmendmentsForAct(aktId);
            if(amendments == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);

            return new ResponseEntity<ArrayList<Amandman>>(amendments,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/amandman/{aktId}" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Amandman> post(@RequestBody Amandman amendment) {
        amendment.setStanje(Constants.ProposedState);
        try{
            amendmentDao.create(amendment, Constants.Amendment+amendment.getId().toString(), Constants.ProposedAmendmentCollection);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/amandman/glasaj", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void voting(@RequestBody ActsAndAmendemntsIdsDTO idsDTO){

        try {
            amendmentDao.voting(idsDTO.getActsIds(),idsDTO.getAmendmentsIds());
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
