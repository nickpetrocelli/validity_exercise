import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import app.process.Process;

/**
 * Created by Nick on 10/31/18.
 */
public class ProcessTest {

  @Test
  public void testStringOutput() {
    String str = "";
    try {
      str = Process.parse();
    }
    catch (IOException e) {
      Assert.fail(e.getMessage());
    }
    if (str == null) {
      Assert.fail("Error: parse file not found");
    }
    //Visually check output
    System.out.println(str);
    //Test that second instances of duplicate strings are not included
    boolean cond1 = str.contains("15,Jacqueline,Ilchenko,Goodwin Inc,jilchenkoe@wisc.edu,9160 " +
            "Cherokee Avenue,,18763,Wilkes Barre,Pennsylvania,PA,570-384-8352");
    boolean cond2 = str.contains("19,Rouvin,Leacock,Monahan and Sons,rleacocki@dailymotion.com," +
            "02254 Delladonna Road,,39534,Biloxi,Mississippi,MS,228-265-4371");

    //Two checks where match is not exact should be enough to make sure the behavior is correct
    Assert.assertTrue("ERROR: second duplicate instance appears in output",
            !(cond1 || cond2));

  }
}
