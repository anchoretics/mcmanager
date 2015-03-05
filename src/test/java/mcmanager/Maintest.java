package mcmanager;

import static org.junit.Assert.*;
import io.github.anchoretics.HttpTest;

import org.junit.Test;


public class Maintest {

	@Test
	public void test() {
		HttpTest.post("chat","ds");
		assertEquals(1, 1);
	}

}
