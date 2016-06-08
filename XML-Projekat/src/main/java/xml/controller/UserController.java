package xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xml.Constants;
import xml.model.Korisnik;
import xml.repositories.IUserDAO;

import java.io.IOException;

/**
 * Created by Daniel on 6/8/2016.
 */
@RestController
public class UserController {

    @Autowired
    private IUserDAO userDao;

    @RequestMapping(value = "/korisnik/dodaj",method = RequestMethod.POST,consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity post(@RequestBody Korisnik korisnik){
        try {
            userDao.createUser(korisnik, Constants.User + korisnik.getId().toString(),Constants.UsersCollection);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
