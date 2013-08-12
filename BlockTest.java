import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.util.BCELifier;


public class BlockTest {

  @Test
	public void testCheckMovement() {
		Board b = new Board(9, 9);
		b.populateBoard("4 4 4 4");
		b.populateBoard("3 3 5 3");
		b.populateBoard("5 4 5 5");
		b.populateBoard("2 5 4 5");
		Block test = b.blocklist.get(0);
		assertFalse(test.checkdown(b));
		assertFalse(test.checkleft(b));
		assertFalse(test.checkright(b));
		assertTrue(test.checkup(b));
		
		
		Board c = new Board(9, 9);
		c.populateBoard("3 3 4 5");
		c.populateBoard("5 5 5 5");
		Block test2 = c.blocklist.get(0);
		assertFalse(test2.checkdown(c));
		c.populateBoard("3 7 3 7");
		assertTrue(test2.checkright(c));
		c.populateBoard("2 4 2 4");
		assertFalse(test2.checkup(c));
		assertTrue(test2.checkleft(c));
		
		
		Board d = new Board(9, 9);
		d.populateBoard("0 0 0 0");
		Block test3 = d.blocklist.get(0);
		assertFalse(test3.checkup(d));
		assertFalse(test3.checkleft(d));
		assertTrue(test3.checkright(d));
		assertTrue(test3.checkdown(d));
		
		Board e = new Board(6, 6);
		e.populateBoard("5 5 5 5");
		Block test4 = e.blocklist.get(0);
		assertTrue(test4.checkup(e));
		assertTrue(test4.checkleft(e));
		assertFalse(test4.checkright(e));
		assertFalse(test4.checkdown(e));
	}

}
