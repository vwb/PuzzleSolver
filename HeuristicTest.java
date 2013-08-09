public class HeuristicTest {

  @Test
	public void OpenPathtest() {
		Board b = new Board(5,5);
		Solver.goalconfigs.add(new Block("1 1 1 1"));
		b.populateBoard("3 1 3 1");
		int temp = Heuristic.OpenPath(b);
		assertTrue(temp == 15);
		
		b.populateBoard("2 0 2 1");
		int temp2 = Heuristic.OpenPath(b);
		assertTrue(temp2 == 0);
		Solver.goalconfigs.remove(new Block("1 1 1 1"));
		
		
		//Test open path with more complex board
		Board b2 = new Board(9, 9);
		Solver.goalconfigs.add(new Block("0 3 1 5"));
		b2.populateBoard("5 3 6 5");
		b2.populateBoard("3 4 3 4");
		b2.populateBoard("2 2 4 2");
		b2.populateBoard("2 3 2 5");
		b2.populateBoard("2 6 4 6");
		int val2 = Heuristic.OpenPath(b2);
		assertEquals(val2, 0);
		
		b2.populateBoard("0 6 1 8");
		int val3 = Heuristic.OpenPath(b2);
		assertEquals(val3, 25);
		

	}

}
