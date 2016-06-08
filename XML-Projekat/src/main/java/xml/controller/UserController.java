package xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import xml.repositories.IUserDAO;

/**
 * Created by Daniel on 6/8/2016.
 */
@RestController
public class UserController {

    @Autowired
    private IUserDAO userDao;

}
