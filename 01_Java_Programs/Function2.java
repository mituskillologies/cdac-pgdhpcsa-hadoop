class Function2 {
	static int larger(int n1, int n2) {
		if (n1 > n2) 
			return n1;
		else
			return n2;
	}
	public static void main(String args[]) {
		int num1 = Integer.parseInt(args[0]);
		int num2 = Integer.parseInt(args[1]);
		int r = Function2.larger(num1, num2); // method call
		System.out.println("Larger:"+ r);
	}
}
