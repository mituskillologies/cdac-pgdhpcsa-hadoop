class ArrayAddFor {
	public static void main(String args[]) {
		int num[] = {56,11,37,54,86,25,38};
		int add = 0;
		for (int n : num) {
			add = add + n;
		}	
		System.out.println("Addition is: " + add);
	}
}
