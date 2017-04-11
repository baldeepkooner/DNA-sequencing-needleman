import java.util.Scanner;

public class DNA {
	private String sequence;
	private int id;

	public DNA(String sequence, int id) {
		Scanner scan = new Scanner(System.in);
		boolean check = check(sequence);
		for (;;) {
			if (sequence.isEmpty()) {
				sequence = scan.nextLine();
				check = check(sequence);
			} else if (check) {
				this.sequence = sequence;
				break;
			} else {
				System.out.print("Invalid strand, enter again --> ");
				sequence = scan.nextLine();
				check = check(sequence);
			}
		}
		this.id = id;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int length() {
		return sequence.length();
	}

	public boolean check(String sequence) {
		for (int i = 0; i < sequence.length(); i++) {
			if (!(sequence.charAt(i) == 'A' || sequence.charAt(i) == 'G' || sequence.charAt(i) == 'C'
					|| sequence.charAt(i) == 'T'))
				return false;
		}
		return true;
	}
}
