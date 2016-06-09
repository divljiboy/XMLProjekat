package xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xml.Constants;
import xml.model.Korisnik;
import xml.repositories.IUserDAO;

import java.io.IOException;
import java.util.List;

/**
 * Created by Daniel on 6/8/2016.
 */
@RestController
@RequestMapping("/korisnik")
public class UserController {

    @Autowired
    private IUserDAO userDao;

    @RequestMapping(value = "/svi" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Korisnik>> getAll() {
        try{
            List<Korisnik> korisnici = userDao.getAllUsers();
            if(korisnici == null)
                return new ResponseEntity<List<Korisnik>>(HttpStatus.NO_CONTENT);

            return  new ResponseEntity<List<Korisnik>>(korisnici,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<List<Korisnik>>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/dodaj",method = RequestMethod.POST,consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity post(@RequestBody Korisnik korisnik){

        try {
            userDao.createUser(korisnik, Constants.User + korisnik.getId().toString(),Constants.UsersCollection);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Korisnik> getById(@PathVariable("id")Long id){

        try {
            Korisnik user = userDao.findById(id);
            if(user == null){
                return new ResponseEntity<Korisnik>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<Korisnik>(user,HttpStatus.OK);
            }
        } catch (IOException e) {
            return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
        }
    }

}
