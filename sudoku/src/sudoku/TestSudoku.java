package sudoku;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSudoku {
	private Sudoku sudoku;
	private int[][] board;
	
	@BeforeEach
	void setUp() throws Exception {
		sudoku = new Sudoku();
		board = new int[9][9];
	}

	@AfterEach
	void tearDown() throws Exception {
		sudoku = null;
	}
	
	@Test
	void testEmpty() {
		assertTrue(sudoku.solve());
	}
	
	@Test
	void testFigureOne() {
		sudoku.setNumber(0, 2, 8);
		sudoku.setNumber(0, 5, 9);
		sudoku.setNumber(0, 7, 6);
		sudoku.setNumber(0, 8, 2);
		sudoku.setNumber(1, 8, 5);
		sudoku.setNumber(2, 0, 1);
		sudoku.setNumber(2, 2, 2);
		sudoku.setNumber(2, 3, 5);
		sudoku.setNumber(3, 3, 2);
		sudoku.setNumber(3, 4, 1);
		sudoku.setNumber(3, 7, 9);
		sudoku.setNumber(4, 1, 5);
		sudoku.setNumber(4, 6, 6);
		sudoku.setNumber(5, 0, 6);
		sudoku.setNumber(5, 7, 2);
		sudoku.setNumber(5, 8, 8);
		sudoku.setNumber(6, 0, 4);
		sudoku.setNumber(6, 1, 1);
		sudoku.setNumber(6, 3, 6);
		sudoku.setNumber(6, 5, 8);
		sudoku.setNumber(7, 0, 8);
		sudoku.setNumber(7, 1, 6);
		sudoku.setNumber(7, 4, 3);
		sudoku.setNumber(7, 6, 1);
		sudoku.setNumber(8, 6, 4);
		assertTrue(sudoku.solve());
	}
	
	
	//det finns väl inga olösliga???
	@Test
	void testUnsolvable() {
		sudoku.setNumber(0,0,5);
		sudoku.setNumber(0,1,5);
		
		assertFalse(sudoku.solve());
	}
	
	@Test
	void testMethods() {
		//Test that setNumber and getNumber works
		sudoku.setNumber(0,0,2);
		assertTrue(sudoku.getNumber(0, 0) == 2);
		
		//Test that setNumber throws exeption
		assertThrows(IllegalArgumentException.class, () -> sudoku.setNumber(1, 10, 3));
		assertThrows(IllegalArgumentException.class, () -> sudoku.getNumber(-1, 2));
		
		//Test that clearNumber works
		sudoku.clearNumber(0, 0);
		assertTrue(sudoku.getNumber(0, 0) == 0);
		
		//Check isValid
		sudoku.setNumber(0,0,2);
		assertFalse(sudoku.isValid(0, 4, 2));
		assertTrue(sudoku.isValid(1, 4, 2));
		
		//Check isAllValid()
		assertTrue(sudoku.isAllValid());
		sudoku.setNumber(1, 1, 2);
		assertFalse(sudoku.isAllValid());
		
		//solve()
		sudoku.clearNumber(1, 1);
		assertTrue(sudoku.solve());
		
		//clear()
		sudoku.setNumber(4, 5, 2);
		sudoku.clear();
		assertTrue(sudoku.getNumber(4, 5) == 0 && sudoku.getNumber(0, 0) == 0);
		
		//getMatrix()
				//setMatrix(int[][] nbrs)
		sudoku.setMatrix(board);
		assertSame(board,sudoku.getMatrix());
		
	}

}
