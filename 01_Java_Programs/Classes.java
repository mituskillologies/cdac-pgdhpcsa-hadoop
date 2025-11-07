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
}
class Classes {
	public static void main(String args[]) {
		Person p1 = new Person("Ajay", 23); // Object
		p1.display();   // method call
		Person p2 = new Person("Priya", 27); // Object
		p2.display();   // method call
	}
}
