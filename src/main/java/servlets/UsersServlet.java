package servlets;

import accounts.UserProfile;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UsersServlet extends MainServlet {



    public UsersServlet() {

    }

    //get public user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(dbService.toString());
        String login = request.getParameter("login");

        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        UserProfile user =  new UserProfile(login, pass, email);

        if(accountService.getUserByLogin(login)==null){

            try {
                accountService.addNewUser(user);
                response.getWriter().print(" registred");

            } catch (ConstraintViolationException e) {
                String  str = e.getCause().getMessage();
                if (str.toString().contains("email")) response.getWriter().print(" such email already exists");
                else
                if (str.toString().contains("login")) response.getWriter().print(" such login already exists");
                else response.getWriter().print(str);

            }

        } else   response.getWriter().print(" allready registred");


    }

    //change profile
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

    }

    //unregister
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

    }
}
