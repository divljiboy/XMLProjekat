package xml.controller;

import database.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xml.model.TKorisnik;
import xml.repositories.ITKorisnikDAO;

import java.io.IOException;

/**
 * Created by Daniel on 5/30/2016.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ITKorisnikDAO korisnikDAO;

    @RequestMapping("/addOdbornik")
    public String addOdbornik(){

        System.out.print("Usao u metodu addOdbornik");
/*
        TOdbornik odbornik = new TOdbornik();
        odbornik.setIme("Branislav");
        odbornik.setPrezime("Cogic");
        odbornik.setUloga("Neradnik");

        System.out.print("Napravio odbornika");

        try {
            odbornikDAO.create(odbornik, DatabaseConfig.loadProperties());
        } catch (IOException e) {
            System.out.print("Nece moci , iz resta exception");
        }
*/

        TKorisnik korisnik = new TKorisnik();
        korisnik.setUsername("aaaa");
        korisnik.setPassword("ssss");
        korisnik.setIme("Branislav");
        korisnik.setPrezime("Cogic");
        korisnik.setUloga("Neradnik");

        try {
            korisnikDAO.create(korisnik,DatabaseConfig.loadProperties());
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.print("Testing");


        return "OK";
    }

}
