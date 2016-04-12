package Xquery;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xquery.*;

import net.sf.saxon.xqj.SaxonXQDataSource;

/**
 * Created by Alexandr on 18.02.2016.
 */
public class xQueryTester {


   public static String execute( String name) throws FileNotFoundException, XQException {
        List<String> list = new ArrayList<>();
        XQDataSource ds = new SaxonXQDataSource();
        XQConnection conn = ds.getConnection();
       XQExpression exp = conn.createExpression();
       String xqueryString =
       "for $x in doc('src/main/resourses/classes.xml')/Context/"+ name +  " return $x/ClassName"+ "/text()";
       XQResultSequence result = null;
       try {
           result = exp.executeQuery(xqueryString);
       } catch (XQException e) {
           //
       }

   String a  =null;
       if (result!=null&&result.next()) {
           a = result.getItemAsString(null);
           while(a.contains(" ")) {
               String replace = a.replace(" ", "");
               a=replace;
           }
       } else System.out.println("failed");

       return a;
    }


}
