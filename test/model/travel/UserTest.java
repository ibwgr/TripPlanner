package model.travel;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class UserTest {

    @Test
    public void newUserWithAdminEnumerationShouldReturnWithType2() throws Exception {
        User a1 = new User();
        a1.setEmail("test@example.com");
        a1.setName("Hans");
        a1.setUsername("Hans");
        a1.setPassword("geheim");
        a1.setTypeEnum(UserTypeEnum.ADMIN);
        // Admin soll auf Wert 2 gemapped sein
        Assert.assertEquals(new Long(2),a1.getType());
    }

    @Test
    public void newUserWithUserEnumerationShouldReturnWithType1() throws Exception {
        User a1 = new User();
        a1.setEmail("test@example.com");
        a1.setName("Hans");
        a1.setUsername("Hans");
        a1.setPassword("geheim");
        a1.setTypeEnum(UserTypeEnum.USER);
        // Admin soll auf Wert 2 gemapped sein
        Assert.assertEquals(new Long(1),a1.getType());
    }
}