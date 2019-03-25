package MyAnnotation;


import org.apache.logging.log4j.core.util.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class AnnotationTest1 {
    public AnnotationTest1(Object clazz) throws InvocationTargetException, IllegalAccessException, RuntimeException {

        Method [] method1 = clazz.getClass().getDeclaredMethods();

        Map<Integer, List<Method>> map = new TreeMap<>();

        for (int i = 0; i < method1.length; i++) {
            AfterSuite annotation = method1[i].getAnnotation(AfterSuite.class);
            BeforeSuite befor = method1[i].getAnnotation(BeforeSuite.class);
            Test annTest = method1[i].getAnnotation(Test.class);

            if (annotation!= null && map.get(0)==null) {
                    List<Method> afterMethod = new ArrayList<>();
                    afterMethod.add(method1[i]);
                    map.put(0, afterMethod);
            } else if (annotation!= null && map.get(0)!=null){
                throw new RuntimeException("Вы можете использовать аннотацию  @AfterSuite только один раз");
            }

            if (befor != null && map.get(11)==null){
                List<Method> afterMethod = new ArrayList<>();
                afterMethod.add(method1[i]);
                map.put(11, afterMethod);
            }else if (befor != null && map.get(11)!=null){
                throw new RuntimeException("Вы можете использовать аннотацию  @BeforeSuite только один раз");
            }

            if(annTest!=null) {
                int prioritet = annTest.priority();
                if (prioritet>=1 && prioritet<=10) {
                    if (map.get(prioritet)==null){
                        List<Method> afterMethod = new ArrayList<>();
                        afterMethod.add(method1[i]);
                        map.put(prioritet, afterMethod);
                    }else {
                        map.get(prioritet).add(method1[i]);
                    }

                }else {
                    throw new RuntimeException("Приоритет должен быть от 1 до 10");
                }
            }


        }

        for(Map.Entry e : map.entrySet()){

            System.out.println(e.getKey()+" "+ e.getValue());

        }

        for (List<Method> meth: map.values()){
            for (Method m : meth){
                m.invoke(clazz);
            }
        }



    }
}

