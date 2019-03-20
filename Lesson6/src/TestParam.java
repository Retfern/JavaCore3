import org.junit.Before;
import org.junit.Assert;

import org.junit.Test;

import java.util.Arrays;

import java.util.List;


public class TestParam {

    private Task1 task1;

    @Before
    public void init() {
        task1 = new Task1();
    }

    @Test
    public void checkArrTest() {
        List<Integer> arr = Arrays.asList(1, 1, 1, 4, 4, 1, 4, 4);
        Assert.assertEquals(true, task1.checkArr(arr));

    }

    @Test
    public void checkArrTest1() {
        List<Integer> arr = Arrays.asList(1, 1, 1, 1);
        Assert.assertEquals(false, task1.checkArr(arr));

    }

    @Test
    public void checkArrTest2() {
        List<Integer> arr = Arrays.asList(4, 4, 4, 4);
        Assert.assertEquals(false, task1.checkArr(arr));

    }

    @Test
    public void checkArrTest3() {
        List<Integer> arr = Arrays.asList(4, 4, 4, 4, 1, 3, 5);
        Assert.assertEquals(false, task1.checkArr(arr));

    }

}