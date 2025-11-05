class Exc {
	public static void main(String args[]) {
		int num1 = 0, num2 = 0;
		try {
			num1 = Integer.parseInt(args[0]);
			num2 = Integer.parseInt(args[1]);
		}
		catch (ArrayIndexOutOfBoundsException a) {
			System.out.println("Missing Arguments: " + a.getMessage());
			System.exit(0);
		}
		catch (NumberFormatException n) {
			System.out.println("Enter integers only! " + n.getMessage());
			System.exit(0);
		}
			if (num1 > num2)
				System.out.println("Larger:"+ num1);
			else
				System.out.println("Larger:"+ num2);
	}
}
