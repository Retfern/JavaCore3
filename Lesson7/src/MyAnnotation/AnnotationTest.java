package MyAnnotation;


import org.apache.logging.log4j.core.util.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AnnotationTest {
    public AnnotationTest(Object clazz) throws InvocationTargetException, IllegalAccessException, RuntimeException {
        byte countAfter=0;
        byte countBefore = 0;
        Method [] method1 = clazz.getClass().getDeclaredMethods();

        for (int i = 0; i < method1.length; i++) {
            Method m;
            AfterSuite annotation = method1[i].getAnnotation(AfterSuite.class);
            BeforeSuite befor = method1[i].getAnnotation(BeforeSuite.class);
            Test annTest = method1[i].getAnnotation(Test.class);
            if (annotation != null) {
                countAfter++;
                if (countAfter==1 && i != 0) {
                    m = method1[0];
                    method1[0] = method1[i];
                    method1[i] = m;
                } else if (countAfter>1){
                    throw new RuntimeException("Вы можете использовать аннотацию  @AfterSuite только один раз");
                }
            }
            if (befor != null){
                countBefore++;
            if (countBefore==1 && i != method1.length - 1) {
                m = method1[method1.length - 1];
                method1[method1.length - 1] = method1[i];
                method1[i] = m;
            }else if (countAfter>1){
                throw new RuntimeException("Вы можете использовать аннотацию  @BeforeSuite только один раз");
                }
            }

            if (annTest==null && annotation==null && befor==null){
                method1=ArrayUtils.remove(method1, i);


            }

        }

        System.out.println("AfterSuite "+ method1[0]);
        System.out.println("BeforeSuite "+ method1[method1.length-1]);


        for (int j=0; j<method1.length; j++) {
            for (int i = 0; i < method1.length; i++) {
                Test annTest = method1[i].getAnnotation(Test.class);
                Method m;
                if (annTest != null) {
                    if (annTest.priority()>=1 &&annTest.priority()<=10) {

                        if (i + 1 <method1.length && method1[i + 1].getAnnotation(Test.class) != null && annTest.priority() < method1[i + 1].getAnnotation(Test.class).priority()) {
                            m = method1[i];
                            method1[i] = method1[i + 1];
                            method1[i + 1] = m;
                        }
                    }else {
                        throw new RuntimeException("Приоритет должен быть от 1 до 10");
                    }

                }
            }
        }



        for (int i = 0; i < method1.length; i++) {

            method1[i].invoke(clazz);
        }
    }
}
