package accounts;

import dbService.DBException;
import dbService.DBService;
import org.hibernate.exception.ConstraintViolationException;


public class AccountService {

    private final DBService dbservice;


    public AccountService( DBService dbService) {

        this.dbservice = dbService;

    }

    public void addNewUser(UserProfile userProfile) throws ConstraintViolationException {


        dbservice.addUser(userProfile);


    }

    public UserProfile  getUserByLogin(String login)  {
        try {
            return dbservice.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
