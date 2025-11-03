class StringOperations {
	public static void main(String args[]) {
		char ch = 'T';     // character declaration
		char name[] = {'T','u','s','h','a','r'};
		String s = new String(name); // create string
		
		System.out.println("Character: " + ch);
		System.out.println("String: " + s);
		
		String city = "Pune";
		System.out.println("City name: " + city);
		System.out.println("Concatenate: " + (city+s));
		System.out.println("Length: " + city.length());
		System.out.println("Char at: " + city.charAt(3));
		System.out.println("Starts: " + s.startsWith("t"));
		System.out.println("Lower: " + city.toLowerCase());
	}
}
