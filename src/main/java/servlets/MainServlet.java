package servlets;

import Annotations.Inject;

import Inicializators.Initialize;
import Inicializators.ReflectionFieldsDeployer;
import Xquery.xQueryTester;
import accounts.AccountService;
import dbService.DBService;

import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xquery.XQException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


public class MainServlet extends HttpServlet {

//maybe replace in constructor
    @Inject("DBService")
    protected static DBService dbService;

    protected static   AccountService accountService;
    public int a;

    @Override
    public void init(ServletConfig config) throws ServletException {
        List<Field> list = ReflectionFieldsDeployer.deploy(this.getClass());
       Initialize.run(list,this);
        accountService = new AccountService(dbService);
        }


    public MainServlet() {

    }





    //get logged user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
      String login = request.getParameter("login");
        String pass = request.getParameter("password");
        if ((login !=null || pass != null)) {
            if ( accountService.getUserByLogin(login)!=null) {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(" You hava allready   registred");


            } else  response.sendRedirect("/register/index.html");

        }


    }


}
