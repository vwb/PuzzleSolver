import static org.junit.Assert.*;

import org.junit.Test;


public class BoardTest {

  @Test
    //
    public void testPopulate() {
        //Test single block input
        Board b = new Board(5, 5);
        b.populateBoard("1 1 1 1");
        assertFalse(b.getBoard()[2][1]);
        assertTrue(b.getBoard()[1][1]);

        //Test size 3 horizontal block
        Board a = new Board(5, 5);
        a.populateBoard("2 2 2 4");
        assertTrue(a.getBoard()[2][2]);
        assertTrue(a.getBoard()[2][3]);
        assertTrue(a.getBoard()[2][4]);

        //Test size 3 vertical block
        Board c = new Board(5, 5);
        c.populateBoard("1 1 1 3");
        assertTrue(a.getBoard()[2][2]);
        assertTrue(a.getBoard()[2][3]);

        //Test 3x3 block
        Board d = new Board(5, 5);
        d.populateBoard("1 1 3 3");
        assertTrue(d.getBoard()[1][1]);
        assertTrue(d.getBoard()[1][2]);
        assertTrue(d.getBoard()[1][3]);
        assertTrue(d.getBoard()[2][1]);
        assertTrue(d.getBoard()[2][2]);
        assertTrue(d.getBoard()[2][3]);
        assertTrue(d.getBoard()[3][1]);
        assertTrue(d.getBoard()[3][2]);
        assertTrue(d.getBoard()[3][3]);

    }

}
