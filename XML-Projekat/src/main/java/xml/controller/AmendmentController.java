package xml.controller;

import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
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
    public ResponseEntity<Amandman> post(@RequestBody Amandman amendment,@PathVariable("aktId") Long actId, HttpServletRequest request) {

        if(StateManager.getState().getState().equals(StateManager.PREDLAGANJE_AMANDMANA)) {
            String token = request.getHeader("x-auth-token");
            TokenHandler handler = new TokenHandler();
            Korisnik user = handler.parseUserFromToken(token);
            try {
                Amandman maxAmendment = amendmentDao.getEntityWithMaxId(Constants.ProposedAmendmentCollection, Constants.AmendmentNamespace, Constants.Amendment);
                if (maxAmendment == null) {
                    amendment.setId((long) 1);
                } else {
                    amendment.setId(maxAmendment.getId() + 1);
                }
                amendment.setStanje(Constants.ProposedState);
                amendment.setKoDodaje(user.getUsername());
                amendment.getOvlascenoLice().setIme(user.getIme());
                amendment.getOvlascenoLice().setPrezime(user.getPrezime());
                amendment.getOvlascenoLice().setTitula(user.getUloga());
                amendment.getOvlascenoLice().setKoDodaje(user.getUsername());
                amendment.setIdAct(actId);
                for(int i = 0; i < amendment.getPodamandman().size(); i++){
                    amendment.getPodamandman().get(i).setId((long) (i+1));
                }
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
    public void voting(@RequestBody ActsAndAmendemntsIdsDTO idsDTO, HttpServletRequest request){
        String token = request.getHeader("x-auth-token");
        TokenHandler handler = new TokenHandler();
        Korisnik user = handler.parseUserFromToken(token);
        if(StateManager.getState().getState().equals(StateManager.GLASANJE)) {
            try {
                amendmentDao.voting(idsDTO.getActsIds(), idsDTO.getAmendmentsIds(),user);
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
                Amandman amandman = amendmentDao.get(id,null);
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

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/amandman/pdf/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPdf(@PathVariable("id") Long id){
        try{
            ByteArrayOutputStream pdf = amendmentDao.getPdf(id);

            //returning shit
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = "Amendment"+id+".pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity(pdf.toByteArray(), headers, HttpStatus.OK);
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (FOPException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (SAXException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (TransformerException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
