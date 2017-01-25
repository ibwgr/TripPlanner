package model.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import testFramework.UnitTest;

/**
 * Created by dieterbiedermann on 25.01.17.
 */
public class PairTest {

    @Category({ UnitTest.class })
    @Test
    public void getKeyReturnsCorrectKey() {
        Pair<String, String> pair = new Pair<>("key", "value");
        String result = pair.getKey();
        Assert.assertEquals(result, "key");
    }

    @Category({ UnitTest.class })
    @Test
    public void getValueReturnsCorrectValue() {
        Pair<String, String> pair = new Pair<>("key", "value");
        String result = pair.getValue();
        Assert.assertEquals(result, "value");
    }

    @Category({ UnitTest.class })
    @Test
    public void toStringReturnsCorrectString() {
        Pair<String, String> pair = new Pair<>("key", "value");
        String result = pair.toString();
        Assert.assertEquals(result, "key");
    }

}