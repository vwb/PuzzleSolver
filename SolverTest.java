import static org.junit.Assert.*;

import org.junit.Test;


public class SolverTest {

  
	/*
	 * This test checks that compareToGoal returns
	 * TRUE for a one-block board that matches the 
	 * 1-block goal configuration and on which no
	 * moves have been made.
	 */
	@Test
	public void simpleCompareToGoalTest() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		Board b = new Board(5, 5);
		Block blk = new Block("1 0 0 0");
		b.blocklist.add(blk);
		assertTrue(s.compareToGoal(b));
	}
	
	/* 
	 * This test checks that compareToGoal returns
	 * TRUE for a 2-block board that matches the
	 * 1-block goal configuration and on which no
	 * moves have been made.
	 */
	@Test
	public void simpleCompareToGoalTest2() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("2 0 0 0");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		assertTrue(s.compareToGoal(b));
	}
	
	/* 
	 * This test checks that compareToGoal returns
	 * TRUE for a 3-block board that matches the
	 * 1-block goal configuration and on which 
	 * no moves have been made.
	 */
	@Test
	public void simpleCompareToGoalTest3() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("2 0 0 0");
		Block blk3 = new Block("5 4 0 4");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		assertTrue(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * TRUE for a 2-block board that matches the
	 * 2-block goal configuration and on which no
	 * moves have been made.
	 */
	@Test
	public void simpleCompareToGoalTest4() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		s.goalconfigs.add("5 4 0 4");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("5 4 0 4");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		assertTrue(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * TRUE for a 3-block board that matches the
	 * 2-block goal configuration and on which no
	 * moves have been made.
	 */
	@Test
	public void simpleCompareToGoalTest5() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		s.goalconfigs.add("5 4 0 4");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("5 4 0 4");
		Block blk3 = new Block("0 0 1 1");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		assertTrue(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * TRUE for a 5-block board that matches the
	 * 2-block goal configuration and on which no
	 * moves have been made.
	 */
	@Test
	public void simpleCompareToGoalTest6() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		s.goalconfigs.add("5 4 0 4");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("5 4 0 4");
		Block blk3 = new Block("0 0 1 1");
		Block blk4 = new Block("3 2 3 2");
		Block blk5 = new Block("2 1 0 1");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		b.blocklist.add(blk4);
		b.blocklist.add(blk5);
		assertTrue(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * TRUE for a 5-block board that matches the
	 * 3-block goal configuration and on which no
	 * moves have been made.
	 */
	@Test
	public void simpleCompareToGoalTest7() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		s.goalconfigs.add("5 4 0 4");
		s.goalconfigs.add("0 0 1 1");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("5 4 0 4");
		Block blk3 = new Block("0 0 1 1");
		Block blk4 = new Block("3 2 3 2");
		Block blk5 = new Block("2 1 0 1");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		b.blocklist.add(blk4);
		b.blocklist.add(blk5);
		assertTrue(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * FALSE for a 1-block board that does not match
	 * the 1-block goal configuration.
	 */
	@Test
	public void simpleCompareToGoalTest8() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		Board b = new Board(5, 5);
		Block blk = new Block("1 0 0 1");
		b.blocklist.add(blk);
		assertFalse(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * FALSE for a 2-block board that does not match
	 * the 1-block goal configuration.
	 */
	@Test
	public void simpleCompareToGoalTest9() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 1");
		Block blk2 = new Block("2 0 0 0");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		assertFalse(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * FALSE for a 3-block board that does not match
	 * the 1-block goal configuration.
	 */
	@Test
	public void simpleCompareToGoalTest10() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 1");
		Block blk2 = new Block("2 0 0 0");
		Block blk3 = new Block("5 4 0 4");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		assertFalse(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * FALSE for a 2-block board that does not match
	 * the 2-block goal configuration.
	 * 
	 * Note that one block does match -- compareToGoal
	 * should still return FALSE.
	 */
	@Test
	public void simpleCompareToGoalTest11() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		s.goalconfigs.add("2 0 0 0");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 1");
		Block blk2 = new Block("2 0 0 0");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		assertFalse(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * FALSE for a 3-block board that does not match
	 * the 2-block goal configuration.
	 */
	@Test
	public void simpleCompareToGoalTest12() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 1");
		s.goalconfigs.add("5 4 0 4");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("5 4 0 4");
		Block blk3 = new Block("0 0 1 1");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		assertFalse(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * FALSE for a 5-block board that does not match
	 * the 2-block goal configuration.
	 */
	@Test
	public void simpleCompareToGoalTest13() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 1");
		s.goalconfigs.add("5 4 0 4");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("5 4 0 4");
		Block blk3 = new Block("0 0 1 1");
		Block blk4 = new Block("3 2 3 2");
		Block blk5 = new Block("2 1 0 1");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		b.blocklist.add(blk4);
		b.blocklist.add(blk5);
		assertFalse(s.compareToGoal(b));
	}
	
	/*
	 * This test checks that compareToGoal returns
	 * FALSE for a 5-block board that does not match
	 * the 3-block goal configuration.
	 * 
	 * Note that two of the blocks do match --
	 * compareToGoal should still return FALSE.
	 */
	@Test
	public void simpleCompareToGoalTest14() {
		String[] args = {"testboardfile.txt", "testgoalfile.txt"};
		Solver s = new Solver(args);
		s.goalconfigs.add("1 0 0 0");
		s.goalconfigs.add("5 4 0 4");
		s.goalconfigs.add("0 0 0 1");
		Board b = new Board(5, 5);
		Block blk1 = new Block("1 0 0 0");
		Block blk2 = new Block("5 4 0 4");
		Block blk3 = new Block("0 0 1 1");
		Block blk4 = new Block("3 2 3 2");
		Block blk5 = new Block("2 1 0 1");
		b.blocklist.add(blk1);
		b.blocklist.add(blk2);
		b.blocklist.add(blk3);
		b.blocklist.add(blk4);
		b.blocklist.add(blk5);
		assertFalse(s.compareToGoal(b));
	}
}
