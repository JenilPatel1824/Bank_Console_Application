import dbhelper.DbHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleTest {

    private DbHelper db;

    @Before
    public void setup() {
        db = new DbHelper();
    }

    @Test
    public void testDeposit() {
        Runnable r1 = () -> {
            db.deposit(101, 100);
        };
        Assert.assertEquals(100, db.view_balance(101), 0.0);
    }
}
