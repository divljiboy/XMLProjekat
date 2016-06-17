package xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.CertificateRevocationList;
import security.PasswordStorage;
import xml.Constants;
import xml.interceptors.TokenHandler;
import xml.model.Korisnik;
import xml.stateStuff.State;
import xml.stateStuff.StateManager;
import xml.repositories.IUserDAO;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 6/8/2016.
 */
@RestController
@RequestMapping("/korisnik")
@PermitAll
public class UserController {

    @Autowired
    private IUserDAO userDao;

    @RequestMapping(value = "/svi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Korisnik>> getAll() {
        try {
            List<Korisnik> korisnici = userDao.getAll();
            if (korisnici == null)
                return new ResponseEntity<List<Korisnik>>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<List<Korisnik>>(korisnici, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<List<Korisnik>>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/dodaj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity post(@RequestBody Korisnik korisnik) {
        //prvo bez validacije

        byte[] salt = new byte[0];
        try {
            salt = PasswordStorage.generateSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        korisnik.setSalt(PasswordStorage.base64Encode(salt));


        byte[] hashedPassword = new byte[0];
        try {
            hashedPassword = PasswordStorage.hashPassword(korisnik.getPassword(), salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        korisnik.setPassword(PasswordStorage.base64Encode(hashedPassword));


        try {
            Korisnik maxUser = userDao.getEntityWithMaxIdUser();
            if (maxUser == null) {
                korisnik.setId((long) 1);
            } else {
                korisnik.setId(maxUser.getId() + 1);
            }
            userDao.create(korisnik, Constants.User + korisnik.getId().toString(), Constants.UsersCollection);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (JAXBException e) {
            return new ResponseEntity<Korisnik>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Korisnik> getById(@PathVariable("id") Long id) {

        try {
            Korisnik user = null;
            try {
                user = userDao.get(id, null);
            } catch (JAXBException e) {
                return new ResponseEntity<Korisnik>(HttpStatus.NO_CONTENT);
            }
            if (user == null) {
                return new ResponseEntity<Korisnik>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<Korisnik>(user, HttpStatus.OK);
            }
        } catch (IOException e) {
            return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Korisnik> getByLogin(@RequestBody Korisnik korisnik, HttpServletResponse response) {
        CertificateRevocationList crt = new CertificateRevocationList();
        if (korisnik == null || korisnik.getUsername() == null
                || korisnik.getUsername().equals("") || korisnik.getPassword()==null || korisnik.getPassword().equals(""))
            return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
        try {

            ArrayList<Korisnik> users = (ArrayList<Korisnik>) userDao.getAll();
          //  Korisnik user = userDao.getByLogin(korisnik.getUsername(), korisnik.getPassword());

            for (Korisnik k : users) {
                if (k.getUsername().equals(korisnik.getUsername())) {
                    if (PasswordStorage.authenticate(korisnik.getPassword(), PasswordStorage.base64Decode(k.getPassword()), PasswordStorage.base64Decode(k.getSalt()))) {
                        TokenHandler handler = new TokenHandler();
                        k.setPassword("");
                        response.setHeader("x-auth-token", handler.createTokenForUser(k));
                        return new ResponseEntity<Korisnik>(k, HttpStatus.OK);
                    }else
                    {
                        return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
                    }


                }
            }
            /*
            if(user == null){
                return new ResponseEntity<Korisnik>(HttpStatus.NO_CONTENT);
            }else{
                TokenHandler handler = new TokenHandler();
                user.setPassword("");
                response.setHeader("x-auth-token",handler.createTokenForUser(user));
                return new ResponseEntity<Korisnik>(user,HttpStatus.OK);
            }
            */
        } catch (IOException e) {
            return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
        } catch (JAXBException e) {
            return new ResponseEntity<Korisnik>(HttpStatus.NO_CONTENT);
        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
        } catch (InvalidKeySpecException e) {
            return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Korisnik>(HttpStatus.BAD_REQUEST);
    }



    //for presidend
    @RolesAllowed( value = {Constants.Predsednik,Constants.Odbornik})
    @RequestMapping(value = "/state",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<State> getState(){
        State state = StateManager.getState();
        if(state == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<State>(state,HttpStatus.OK);
    }

    @RolesAllowed(value = Constants.Predsednik)
    @RequestMapping(value = "/state",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setState(@RequestBody State state){

        if(StateManager.setState(state.getState()))
            return new ResponseEntity(state,HttpStatus.OK);

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }




}
