class TypeCasting {
	public static void main(String args[]) {
		String num1 = "123";
		String num2 = "234";
		int add;
		add =Integer.parseInt(num1) + Integer.parseInt(num2);
		System.out.println("Addition is: " + add);
		String s = num1 + 678;
		System.out.println("Result: " + s);
		
		float n1 = 78.36f, n2 = 23.89f;
		int result;
		result = (int) n1 + (int) n2;
		System.out.println("Float Addition is: " + result);
		
		int num = 56;
		String s1 = num+"";
		System.out.println("String is: " + s1);
	}
}
