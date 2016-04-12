package dbService.dao;

import accounts.UserProfile;
import dbService.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UsersHibDAO {

    private Session session;

    public UsersHibDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public UserProfile getUserProfile(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        String log = null;
        String password = null;
        String email = null;
         Object a = criteria.add(Restrictions.eq("login", login)).uniqueResult();
        if (a != null) {
             UsersDataSet b=  (UsersDataSet) a;
            log  = b.getLogin();
            password = b.getPassword();
            email = b.getEmail();
        }


         if(log==null) return null;
        UserProfile userProfile = new UserProfile(log,password,email);
        return userProfile;
    }

    public long insertUser(UserProfile userProfile) throws HibernateException {
        return (Long) session.save(new UsersDataSet(userProfile));
    }
}
