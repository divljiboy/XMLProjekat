package xml.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.tools.jxc.ap.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.Constants;
import xml.model.PravniAkt;
import xml.repositories.IActDAO;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
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
    @RequestMapping(value = "/akt/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") Long id) {
        try{
            String html = aktDao.getXsltDocument(id);
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
    @RequestMapping(value = "/akt", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity post(@RequestBody PravniAkt object) {
        object.setStanje(Constants.ProposedState);
        try{
            aktDao.create(object,Constants.Act+object.getId().toString(), Constants.ProposedActCollection);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
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
    @RequestMapping(value = "/akt/brisi/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public void delete(@PathVariable("id") Long id){
        System.out.print(id);
        try {
            aktDao.delete(id,Constants.Act);
            System.out.print("Successfully deleted from db");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
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

    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/usvojeniAkati", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<PravniAkt>> getAdoptedActs(){
        try {
            ArrayList<PravniAkt> adoptedActs = aktDao.getAdoptedActs();
            if(adoptedActs == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            return new ResponseEntity(adoptedActs,HttpStatus.OK);
        } catch (JAXBException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update")
    public void updateState(){
        try {
            aktDao.updateActState((long)5,Constants.AdoptedState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
