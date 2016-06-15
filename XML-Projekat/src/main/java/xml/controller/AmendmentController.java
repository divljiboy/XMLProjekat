package xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.Constants;
import xml.controller.dto.ActsAndAmendemntsIdsDTO;
import xml.interceptors.TokenHandler;
import xml.model.Amandman;
import xml.model.Korisnik;
import xml.repositories.IAmendmentDAO;
import xml.stateStuff.StateManager;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
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

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
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

    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/amandman/{aktId}" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Amandman> post(@RequestBody Amandman amendment) {

        if(StateManager.getState().getState().equals(StateManager.PREDLAGANJE_AMANDMANA)) {
            try {
                Amandman maxAmendment = amendmentDao.getEntityWithMaxId(Constants.ProposedAmendmentCollection, Constants.AmendmentNamespace, Constants.Amendment);
                if (maxAmendment == null) {
                    amendment.setId((long) 1);
                } else {
                    amendment.setId(maxAmendment.getId() + 1);
                }
                amendment.setStanje(Constants.ProposedState);

                amendmentDao.create(amendment, Constants.Amendment + amendment.getId().toString(), Constants.ProposedAmendmentCollection);
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RolesAllowed( value = {Constants.Predsednik})
    @RequestMapping(value = "/amandman/glasaj", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void voting(@RequestBody ActsAndAmendemntsIdsDTO idsDTO){

        if(StateManager.getState().getState().equals(StateManager.GLASANJE)) {
            try {
                amendmentDao.voting(idsDTO.getActsIds(), idsDTO.getAmendmentsIds());
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.print("State is not Glasanje");
        }

    }

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/amandman/html/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") Long id) {
        try{
            String html = amendmentDao.getXsltDocument(id);
            //PravniAkt akt = aktDao.get(id);
            //if(akt == null)
            //return new ResponseEntity<List<PravniAkt>>(HttpStatus.NO_CONTENT);
            if(html == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(html,HttpStatus.OK);

            // return new ResponseEntity(akt,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/amandman/brisi/{id}", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id")Long id, HttpServletRequest request){
        if(StateManager.getState().getState().equals(StateManager.PREDLAGANJE_AMANDMANA)) {
            String token = request.getHeader("x-auth-token");
            TokenHandler handler = new TokenHandler();
            Korisnik user = handler.parseUserFromToken(token);
            try {
                Amandman amandman = amendmentDao.get(id);
                if (amandman.getKoDodaje().equals(user.getEmail())) {
                    amendmentDao.delete(id, Constants.Amendment);
                    System.out.print("Successfully deleted from db");
                } else {
                    System.out.print("Delete forbidden to this user");
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.print("State is not Predlaganje amandmana");
        }
    }

}
