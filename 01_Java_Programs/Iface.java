interface Data {
	float PI = 3.14f;      // static and constant variable
	double calcArea();     // abstract method
}
interface Info {
	float radius = 12;
	void display();
}
class Shape implements Data, Info {					
	public void display() {
		System.out.println("Value of PI: "+ PI);
	}
	public double calcArea() {
		return PI * radius * radius;
	}
}
class Iface {
	public static void main(String args[]) {
		Shape s = new Shape();
		s.display();
		System.out.println(s.calcArea());
		System.out.println(Data.PI);
	}
}
