class Person {					// class declaration
	String name;				// class variables
	int age;
	Person(String n, int a) {   // constructor
		name = n;
		age = a;
	}
	void display() {			// class method
		System.out.println("Name: " + name);
		System.out.println("Age : " + age);
	}
	class Player {				// inner class
		String sport;
		float rating;
	}
	Player ply = new Player();
}
class InnerClass {
	public static void main(String args[]) {
		Person p1 = new Person("Ajay", 23); // Object
		p1.ply.sport = "Football";
		System.out.println("Sport : " + p1.ply.sport);
	}
}
