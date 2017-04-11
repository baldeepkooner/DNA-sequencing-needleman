import java.util.ArrayList;

public class column {
	// contains a list of (m) row objects
	private ArrayList<row> colList;

	public column(int n, int m) {
		colList = new ArrayList<row>();

		for (int i = 0; i < m; i++) {
			colList.add(new row(n));
		}
	}

	public int getSize() {
		return colList.size();
	}

	public ArrayList<row> getColList() {
		return colList;
	}

	// gets n x m element score
	public double getElementVal(int n, int m) {
		return colList.get(m).getVal(n);
	}

	// sets n x m element score
	public void setElementVal(int n, int m, double val) {
		String temp = Double.toString(val);
		String temp2 = temp.substring(0, temp.indexOf(46) + 2);
		colList.get(m).setVal(n, Double.parseDouble(temp2));
	}

	public ArraySquare getSquare(int n, int m) {
		return colList.get(m).getSquare(n);
	}

	public int getNumOfDig() {
		double max = 0;
		double min = 0;
		for (int i = 0; i < this.getSize(); i++) {
			for (int j = 0; j < this.colList.get(i).getSize(); j++) {
				if (getElementVal(j, i) > max) {
					max = getElementVal(j, i);
				}
				if (getElementVal(j, i) < min) {
					min = getElementVal(j, i);
				}
			}
		}

		return Math.max(Double.toString(max).length(), Double.toString(min).length());
	}

	public void printArray(DNA strand1, DNA strand2) {
		// count used for formatting
		int count = 0;
		int max = getNumOfDig();
		for (int j = 0; j < 2 * max + 2 - (max - 1); j++) {
			System.out.print(" ");
		}
		for (int i = 0; i < strand1.getSequence().length(); i++) {
			for (int j = 0; j < max - 1; j++) {
				System.out.print(" ");
			}
			System.out.print(strand1.getSequence().charAt(i) + " ");
		}

		System.out.println();
		for (int j = 0; j < max - 1; j++) {
			System.out.print(" ");
		}
		for (int m = 0; m < colList.size(); m++) {
			if (count > 0) {
				System.out.print(strand2.getSequence().charAt(m - 1) + " ");
				for (int j = 0; j < max - 1; j++) {
					System.out.print(" ");
				}
			}
			if (count == 0)
				System.out.print("  ");
			for (int n = 0; n < colList.get(m).getSize(); n++) {
				System.out.print(getElementVal(n, m) + " ");
				for (int j = 0; j < max - Double.toString(getElementVal(n, m)).length(); j++) {
					System.out.print(" ");
				}
			}
			System.out.println();
			count++;
		}
	}

	public void initialize(double gap) {
		getSquare(0, 0).setMtrace(-1);
		getSquare(0, 0).setNtrace(-1);
		for (int i = 1; i < getSize(); i++) {
			for (int j = 1; j < colList.get(0).getSize(); j++) {
				setElementVal(j, i, 0);
			}
		}

		for (int m = 1; m < this.getSize(); m++) {
			setElementVal(0, m, gap * m);
			getSquare(0, m).setMtrace(m - 1);
			getSquare(0, m).setSelect(3);
		}
		for (int n = 1; n < this.colList.get(0).getSize(); n++) {
			setElementVal(n, 0, gap * n);
			getSquare(n, 0).setNtrace(n - 1);
			getSquare(n, 0).setSelect(2);
		}
	}

	public void needleman(DNA strand1, DNA strand2, double match, double mismatch, double gap) {
		double score = 0;
		for (int i = 1; i < this.getSize(); i++) {
			for (int j = 1; j < colList.get(i).getSize(); j++) {
				if (strand1.getSequence().charAt(j - 1) == strand2.getSequence().charAt(i - 1)) {
					score = match;
				} else
					score = mismatch;
				// setElementVal(j,i,Math.max(Math.max(getElementVal(j-1,i-1) +
				// score, getElementVal(j-1,i) + gap), getElementVal(j,i-1) +
				// gap));
				if (getElementVal(j - 1, i - 1) + score >= getElementVal(j - 1, i) + gap
						&& getElementVal(j - 1, i - 1) + score >= getElementVal(j, i - 1) + gap) {
					setElementVal(j, i, getElementVal(j - 1, i - 1) + score);
					getSquare(j, i).setNtrace(j - 1);
					getSquare(j, i).setMtrace(i - 1);
					getSquare(j, i).setSelect(1);
				} else if (getElementVal(j - 1, i - 1) + score <= getElementVal(j - 1, i) + gap
						&& getElementVal(j - 1, i - 1) + score >= getElementVal(j, i - 1) + gap) {
					setElementVal(j, i, getElementVal(j - 1, i) + gap);
					getSquare(j, i).setNtrace(j - 1);
					getSquare(j, i).setMtrace(i);
					getSquare(j, i).setSelect(2);
				} else {
					setElementVal(j, i, getElementVal(j, i - 1) + gap);
					getSquare(j, i).setNtrace(j);
					getSquare(j, i).setMtrace(i - 1);
					getSquare(j, i).setSelect(3);
				}
			}
		}
	}

	public void Align(DNA strand1, DNA strand2) {
		int temp = 0;
		int n = strand2.length();
		int m = strand1.length();
		// System.out.println(getSquare(strand1.length(),strand2.length()).getScore()
		// + " " + n + " " + m);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (;;) {
			// System.out.println(getSquare(m,n).getScore() + " " + n + " " +
			// m);
			if (n == 0 && m == 0)
				break;
			if (getSquare(m, n).getSelect() == 1) {
				temp = m;
				m = getSquare(m, n).getNtrace();
				n = getSquare(temp, n).getMtrace();
				arr.add(1);
			} else if (getSquare(m, n).getSelect() == 2) {
				temp = m;
				m = getSquare(m, n).getNtrace();
				n = getSquare(temp, n).getMtrace();
				arr.add(2);
			} else if (getSquare(m, n).getSelect() == 3) {
				temp = m;
				m = getSquare(m, n).getNtrace();
				n = getSquare(temp, n).getMtrace();
				arr.add(3);
			}
		}
		// for (int i=0; i< arr.size(); i++){
		// System.out.println(arr.get(i));
		// }
		int i = 0;
		for (int s = arr.size() - 1; s >= 0; s--) {
			if (arr.get(s) == 1) {
				System.out.print(strand1.getSequence().charAt(i) + " ");
				i++;
			} else if (arr.get(s) == 2) {
				System.out.print(strand1.getSequence().charAt(i) + " ");
				i++;
			} else if (arr.get(s) == 3) {
				System.out.print("_ ");
			}
		}
		int j = 0;
		System.out.println();
		for (int s = arr.size() - 1; s >= 0; s--) {
			if (arr.get(s) == 1) {
				System.out.print(strand2.getSequence().charAt(j) + " ");
				j++;
			} else if (arr.get(s) == 2) {
				System.out.print("_ ");
			} else if (arr.get(s) == 3) {
				System.out.print(strand2.getSequence().charAt(j) + " ");
				j++;
			}
		}

	}
}
