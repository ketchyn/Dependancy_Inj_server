package Inicializators;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexandr on 21.02.2016.
 */
public class ReflectionFieldsDeployer {
    private static boolean first = true;
    private static List<Field> list = new ArrayList() ;
   private static final  String limitClass = "servlets.MainServlet";

    public static List<Field> deploy(Class o) {

        Class SuperClazz = o.getSuperclass();
        if (SuperClazz.getName().equals(Object.class.getName())) return list;

        if (first == true) {
            Field[] fields = o.getDeclaredFields();
            first = false;
            list.addAll(Arrays.asList(fields));
        }

        if (o.getName().toString().equals(limitClass)) {
            return list;
        } else {

            deploy(SuperClazz);
            return list;
        }


    }


}
