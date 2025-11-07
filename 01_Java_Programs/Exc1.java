class Exc1 {
	public static void main(String args[]) {
		int num1 = 0, num2 = 0;
		try {
			num1 = Integer.parseInt(args[0]);
			num2 = Integer.parseInt(args[1]);
		}
		catch (Exception a) {
			System.out.println(a.getMessage());
			System.exit(0);
		}
			if (num1 > num2)
				System.out.println("Larger:"+ num1);
			else
				System.out.println("Larger:"+ num2);
	}
}
