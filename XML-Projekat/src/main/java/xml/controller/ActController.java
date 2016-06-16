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
import xml.controller.dto.SearchCriteriaDTO;
import xml.controller.dto.SearchMetadataDTO;
import xml.interceptors.TokenHandler;
import xml.model.Korisnik;
import xml.model.PravniAkt;
import xml.repositories.IActDAO;
import xml.stateStuff.StateManager;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 6/8/2016.
 */
@RestController
public class ActController{

    @Autowired
    private IActDAO aktDao;

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/akt" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PravniAkt>> getAll() {
        try{
            List<PravniAkt> akati = aktDao.getAll();
            if(akati.size() == 0)
                return new ResponseEntity<List<PravniAkt>>(HttpStatus.NO_CONTENT);

            return  new ResponseEntity<List<PravniAkt>>(akati,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<List<PravniAkt>>(HttpStatus.BAD_REQUEST);
        }
    }

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/akt/{colName}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("colName") String colName,@PathVariable("id") Long id) {
        try{
            int type = 0;
            switch (colName){
                case "usvojeni":
                    type = 1;
                    break;
                case "predlozeni":
                    type = 2;
                    break;
                default:
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            String html = aktDao.getXsltDocument(id,type);
            //PravniAkt akt = aktDao.get(id);
            //if(akt == null)
                //return new ResponseEntity<List<PravniAkt>>(HttpStatus.NO_CONTENT);

            if(html == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(html,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/akt", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity post(@RequestBody PravniAkt object, HttpServletRequest request) {

        if(StateManager.getState().getState().equals(StateManager.PREDLAGANJE_AKATA)) {
            String token = request.getHeader("x-auth-token");
            TokenHandler handler = new TokenHandler();
            Korisnik user = handler.parseUserFromToken(token);
            try {
                PravniAkt maxAct = aktDao.getEntityWithMaxId(Constants.ProposedActCollection, Constants.ActNamespace, Constants.Act);
                if (maxAct == null) {
                    object.setId((long) 1);
                } else {
                    object.setId(maxAct.getId() + 1);
                }
                object.setStanje(Constants.ProposedState);
                object.getOvlascenoLice().setKoDodaje(user.getEmail());
                aktDao.create(object, Constants.Act + object.getId().toString(), Constants.ProposedActCollection);
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/akt/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity put(@RequestBody PravniAkt object,@PathVariable("id") Long id) {
        try{
            aktDao.update(object,id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/akt/brisi/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("id") Long id, HttpServletRequest request){

        if(StateManager.getState().getState().equals(StateManager.PREDLAGANJE_AKATA)) {
            String token = request.getHeader("x-auth-token");
            TokenHandler handler = new TokenHandler();
            Korisnik user = handler.parseUserFromToken(token);
            try {
                PravniAkt act = aktDao.get(id,null);
                if (act.getOvlascenoLice().getKoDodaje().equals(user.getEmail())) {
                    aktDao.delete(id, Constants.Act);
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }

            } catch (JAXBException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } catch (IOException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/predlozeniAkati", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<PravniAkt>> getProposedActs(){
        try {
            ArrayList<PravniAkt> proposedActs = aktDao.getProposedActs();
            if(proposedActs == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(proposedActs,HttpStatus.OK);
        } catch (JAXBException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/usvojeniAkati", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<PravniAkt>> getAdoptedActs() {
        try {
            ArrayList<PravniAkt> adoptedActs = aktDao.getAdoptedActs();
            if (adoptedActs == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(adoptedActs, HttpStatus.OK);
        } catch (JAXBException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/akt/usvojeni/pretraga", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<PravniAkt>> searchByText(@RequestBody SearchCriteriaDTO searchCriteriaDTO) {

        ArrayList<PravniAkt> acts;

        switch (searchCriteriaDTO.getIdSearch()) {
            case 1:
                try {
                    acts = aktDao.searchByText(searchCriteriaDTO.getCriteria(), Constants.ProposedActCollection);
                    if (acts != null)
                        return new ResponseEntity(acts, HttpStatus.OK);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    acts = aktDao.searchByText(searchCriteriaDTO.getCriteria(), Constants.ActCollection);
                    if (acts != null)
                        return new ResponseEntity(acts, HttpStatus.OK);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "akt/search/metadata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<PravniAkt>> searchByMetadata(@RequestBody SearchMetadataDTO searchMetadataDTO) {
        aktDao.searchByMetadata(searchMetadataDTO.getCollectionName(), searchMetadataDTO.getMetadataType(), searchMetadataDTO.getCriteria());
        return null;
    }

    @RolesAllowed( value = {Constants.Gradjanin,Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/akt/pdf/{colName}/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPdf(@PathVariable("colName") String colName , @PathVariable("id") Long id){
        ByteArrayOutputStream pdf = null;
        String collection = null;
        switch (colName){
            case "usvojeni":
                collection = Constants.ActCollection;
                break;
            case "predlozeni":
                collection = Constants.ProposedActCollection;
                break;
            default:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            pdf = aktDao.getPdf(id,collection);


            //returning shit
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = "Act"+id+".pdf";
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
