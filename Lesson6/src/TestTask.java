import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

//@RunWith(Parameterized.class)
public class TestTask {
   /* @Parameterized.Parameters //Не поняла как это работает
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                  {Arrays.asList(1, 2, 5, 4, 1), Arrays.asList(1)},
                  {Arrays.asList(1, 4, 5, 3, 1), Arrays.asList(5, 3, 1)},
                  {Arrays.asList(1, 2, 5, 4, 1, 3), Arrays.asList(1, 3)},
                  {Arrays.asList(1, 4, 5, 4, 1, 8, 9), Arrays.asList(1, 8, 9)}
        });
    }


    private List<Integer> inArr;
    private List<Integer> outArr;

    public TestTask(List<Integer> inArr, List<Integer> outArr) {
        this.inArr = inArr;
        this.outArr = outArr;
    }*/

    private Task1 task1;

    @Before
    public void init() {
        task1 = new Task1();
    }

    @Test
    public void testCheckArray() {
        List<Integer> arr = Arrays.asList(1, 2, 4, 4, 2, 3, 4, 1, 7);
        List<Integer>res=Arrays.asList(1, 7);
        Assert.assertEquals(res, task1.checkArray(arr));
        //Assert.assertEquals(outArr, task1.checkArray(inArr));
    }

    @Test
    public void testCheckArray1() {
        List<Integer> arr = Arrays.asList(1, 2, 4, 4, 2, 3, 1, 7);
        List<Integer>res=Arrays.asList(2, 3, 1, 7);
        Assert.assertEquals(res, task1.checkArray(arr));
    }

    @Test(expected = RuntimeException.class)
    public void testCheckArrayEmpty() {
        List<Integer> arr = Arrays.asList();
        List<Integer>res=Arrays.asList();
        Assert.assertEquals(res, task1.checkArray(arr));//Assert.assertEquals(outArr, task1.checkArray(inArr));
    }

    @Test(expected = RuntimeException.class)
    public void testCheckArrayEx() {
        List<Integer> arr = Arrays.asList(1, 2, 2, 3, 1, 7);
        List<Integer>res=Arrays.asList(null);
        Assert.assertEquals(res, task1.checkArray(arr));
    }

}
