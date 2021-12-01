package sudoku;

public class Sudoku implements SudokuSolver {
	private int[][] board;
	
	public Sudoku() {
		this.board = new int[9][9];
	}
	
	@Override
	public void setNumber(int r, int c, int nbr) {
		if(r < 0 || r >= 9 || c < 0 || c >= 9 || nbr < 1 || nbr > 9) {
			throw new IllegalArgumentException("Out of bounds");
		}
		board[r][c] = nbr;
		
	}

	@Override
	public int getNumber(int r, int c) {
		if(r < 0 || r >= 9 || c < 0 || c >= 9) {
			throw new IllegalArgumentException("Out of bounds");
		}
		return this.board[r][c];
	}

	@Override
	public void clearNumber(int r, int c) {
		// TODO Auto-generated method stub
		if(r < 0 || r >= 9 || c < 0 || c >= 9) {
			throw new IllegalArgumentException("Out of bounds");
		}
		this.board[r][c] = 0;
	}

	@Override
	public boolean isValid(int r, int c, int nbr) {
		// TODO Auto-generated method stub
		if(r < 0 || r >= 9 || c < 0 || c >= 9 || nbr < 0 || nbr > 9) {
			throw new IllegalArgumentException("Out of bounds");
		}
		//check row, col and box
		//return !isInRow(r,nbr) && !isInCol(c,nbr) && !isInBox(r,c,nbr);
		if(timesInRow(r,nbr) > 0 || timesInCol(c,nbr) > 0 || timesInBox(r,c,nbr) > 0)
			return false;
		else return true; 
		  
	}

	@Override
	public boolean isAllValid() {
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				//leta efter dubletter 
				int rowCount = timesInRow(row, board[row][col]);
				int colCount = timesInCol(col, board[row][col]);
				int boxCount = timesInBox(row, col, board[row][col]);
				if(rowCount > 1 || colCount > 1 || boxCount > 1)
					return false;
			}
		}
		return true;
	}
	
	private int timesInRow(int row, int nbr) {
		int count = 0;
		for(int i = 0; i < 9; i++) {
			if(board[row][i] == nbr && nbr != 0)
				count++;
		}
		return count;
	}
	
	private int timesInCol(int col, int nbr) {
		int count = 0;
		for(int i = 0; i < 9; i++) {
			if(board[i][col] == nbr && nbr != 0)
				count++;
		}
		return count;
	}
	
	private int timesInBox(int row, int col, int nbr) {
		int r = row - row % 3;
		int c = col - col % 3;
		int count = 0;
		
		for(int i = r; i < r + 3; i++) {
			for(int j = c; j < c + 3; j++) {
				if(board[i][j] == nbr && nbr != 0) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public boolean solve() {
		// TODO Auto-generated method stub
		if(isAllValid()) {
			return solve(0,0);
		} 
		return false;
		
	}
	
	private boolean solve(int x, int y) {
		if(x >= 9 || y >= 9) {
			return true;
		}
		int nextX = y == 8 ? x+1 : x;
		int nextY = y == 8 ? 0 : y+1;
		if(board[x][y] == 0) {
			for(int nbr = 1; nbr <= 9; nbr++) {
				//check which nbrs are valid
				if(isValid(x,y,nbr)) {
					//assign that to board
					board[x][y] = nbr;
					//check recursivly if solution is valid
					if(solve(nextX, nextY) ) {
						return true;
					} else {
						//if not solvable, backtracking begins
						board[x][y] = 0;
					}
				}
			}
			return false;
		}
		else {
			return solve(nextX, nextY);
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				board[i][j] = 0;
			}
		}
		
	}

	@Override
	public int[][] getMatrix() {
		// TODO Auto-generated method stub
		return board.clone();
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		// TODO Auto-generated method stub
		this.board = nbrs.clone();
		
	}

}
