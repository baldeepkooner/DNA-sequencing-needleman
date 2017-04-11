import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) {
		column test = null;
		double matchScore = 0;
		double mismatchScore = 0;
		double gapPenalty = 0;
		int menu = 0;
		Scanner scan = new Scanner(System.in);
		DNA strand1 = null, strand2 = null;
		for (;;) {
			switch (menu) {
			case 0:
				System.out.println("Input 2 valid DNA sequences and related parameters:");
				System.out.print("Sequence 1 --> ");
				strand1 = new DNA(scan.nextLine(), 1);
				System.out.print("Sequence 2 --> ");
				strand2 = new DNA(scan.nextLine(), 2);
				for (;;) {
					System.out.print("Match score --> ");
					try {
						matchScore = scan.nextDouble();
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid entry");
					}
					scan.nextLine();
				}
				for (;;) {
					System.out.print("Mismatch score --> ");
					try {
						mismatchScore = scan.nextDouble();
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid entry");
					}
					scan.nextLine();
				}
				for (;;) {
					System.out.print("Gap penalty --> ");
					try {
						gapPenalty = scan.nextDouble();
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid entry");
					}
					scan.nextLine();
				}
				System.out.println("\nSequences verified and DNA object constructed\n");
				test = new column(strand1.length() + 1, strand2.length() + 1);
				// test.printArray(strand1, strand2);
				menu = 1;
				break;
			case 1:
				System.out.println("\nChoose an option:");
				System.out.println("1) Print both sequences");
				System.out.println("2) Print initialized matrix");
				System.out.println("3) Result of the Needleman-Wunsch (print entire matrix)");
				System.out.println("4) Print one optimal alignment");
				System.out.println("5) Enter two new sequences (return to part A)");
				System.out.println("6) Exit Program");

				do {
					try {
						menu = scan.nextInt() + 1;
						if (menu < 2 || menu > 7)
							System.out.println("Invalid entry");
					} catch (InputMismatchException e) {
						System.out.println("Invalid entry");
					}
					scan.nextLine(); // clears the buffer
				} while (menu < 2 || menu > 7);
				break;

			case 2:
				System.out.println("\n" + strand1.getSequence());
				System.out.println(strand2.getSequence());
				menu = 1;
				break;
			case 3:
				test.initialize(gapPenalty);
				test.printArray(strand1, strand2);
				menu = 1;
				break;
			case 4:
				test.initialize(gapPenalty);
				test.needleman(strand1, strand2, matchScore, mismatchScore, gapPenalty);
				test.printArray(strand1, strand2);
				menu = 1;
				break;
			case 5:
				test.initialize(gapPenalty);
				test.needleman(strand1, strand2, matchScore, mismatchScore, gapPenalty);
				test.Align(strand1, strand2);
				menu = 1;
				break;
			case 6:
				menu = 0;
				break;
			case 7:
				break;
			}

			if (menu == 7)
				break;
		}
		System.out.println("Goodbye");
		scan.close();
	}
}