class ArrayAdd{
	public static void main(String args[]) {
		int num[] = {56,12,32,54,86,24,38};
		int add = 0;
		for (int i = 0; i < num.length; i++) {
			add = add + num[i];
		}	
		System.out.println("Addition is: " + add);
	}
}
