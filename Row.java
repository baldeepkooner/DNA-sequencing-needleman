import java.util.ArrayList;

public class row {
	// creates a row of (n) squares
	private ArrayList<ArraySquare> rowList;

	public row(int n) {
		rowList = new ArrayList<ArraySquare>();
		for (int i = 0; i < n; i++) {
			rowList.add(new ArraySquare());
		}
	}

	public int getSize() {
		return rowList.size();
	}

	// gets score of rowList square at (n) index
	public double getVal(int n) {
		return rowList.get(n).getScore();
	}

	// sets score in rowList square at (n) index
	public void setVal(int n, double val) {
		rowList.get(n).setScore(val);
	}

	public ArraySquare getSquare(int n) {
		return rowList.get(n);
	}
}
