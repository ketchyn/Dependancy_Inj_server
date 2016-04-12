package dbService;

import accounts.UserProfile;

/**
 * Created by Alexandr on 18.02.2016.
 */
public interface DBService {
    public UserProfile getUser(String  login) throws DBException;
    public long addUser(UserProfile userProfile);
}
