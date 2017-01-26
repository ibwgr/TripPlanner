package model.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import testFramework.UnitTest;

import java.awt.*;

/**
 * Created by dieterbiedermann on 25.01.17.
 */
public class ViewInfoTest {

    @Category({ UnitTest.class })
    @Test
    public void getTitleReturnsCorrectTitle() {
        Component compo = Mockito.mock(Component.class);

        ViewInfo viewInfo = new ViewInfo("title", compo, false, false);
        String result = viewInfo.getTitle();
        Assert.assertEquals(result, "title");
    }

    @Category({ UnitTest.class })
    @Test
    public void getCompoReturnsCorrectCompo() {
        Component compo = Mockito.mock(Component.class);

        ViewInfo viewInfo = new ViewInfo("title", compo, false, false);
        Component result = viewInfo.getCompo();
        Assert.assertEquals(result, compo);
    }

    @Category({ UnitTest.class })
    @Test
    public void getShowSubTitleReturnsCorrectShowSubTitle() {
        Component compo = Mockito.mock(Component.class);

        ViewInfo viewInfo = new ViewInfo("title", compo, false, false);
        Boolean result = viewInfo.getShowSubTitle();
        Assert.assertEquals(result, false);
    }

    @Category({ UnitTest.class })
    @Test
    public void getNeedsRefreshReturnsCorrectNeedsRefresh() {
        Component compo = Mockito.mock(Component.class);

        ViewInfo viewInfo = new ViewInfo("title", compo, false, false);
        Boolean result = viewInfo.getNeedsRefresh();
        Assert.assertEquals(result, false);
    }

}