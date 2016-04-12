package Inicializators;

import Annotations.Inject;
import Xquery.xQueryTester;

import javax.xml.xquery.XQException;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Alexandr on 19.02.2016.
 */
public class Initialize {
    public static void run(List<Field> fields, Object o) {

        String className = null;
        try {
            for(Field field: fields) {
                field.setAccessible(true);
                if(field.isAnnotationPresent(Inject.class)){
                    Inject annotation = field.getAnnotation(Inject.class);
                    className = xQueryTester.execute(annotation.value());
                    if (annotation != null&&className!=null) {

                        field.set(o,Class.forName(className).newInstance());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
