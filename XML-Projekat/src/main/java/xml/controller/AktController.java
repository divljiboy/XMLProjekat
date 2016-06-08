package xml.controller;

import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.model.PravniAkt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 6/8/2016.
 */
@RestController
public class AktController implements IBaseController<PravniAkt> {

    //@Autowired
    //private final IAktDao aktDao;


    @Override
    @RequestMapping(value = "/akt" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PravniAkt>> getAll() {
        System.out.println("Usao u getAll");
        try{

        }catch (Exception e){

        }
        List<PravniAkt> akati = new ArrayList<>();
        PravniAkt akt = new PravniAkt();
        akt.setDrzava("Bla");
        akati.add(akt);
        return new ResponseEntity<List<PravniAkt>>(akati, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/akt/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(Long id) {
        System.out.println("Usao u getId");
        try{

        }catch (Exception e){

        }
        PravniAkt akt = new PravniAkt();
        akt.setDrzava("Bla");
        return new ResponseEntity<PravniAkt>(akt, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/akt", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity post(@RequestBody PravniAkt object) {
        System.out.println("Usao u post");
        try{

        }catch (Exception e){

        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/akt/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity put(@RequestBody PravniAkt object, Long id) {
        System.out.println("Usao u put");
        try{

        }catch (Exception e){

        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
