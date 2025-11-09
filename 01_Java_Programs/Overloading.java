class Shape {					
	int calcArea(int side) {
		return side * side;
	}
	int calcArea(int height, int width) {
		return height * width;
	}
	double calcArea(float radius) {
		return Math.PI * radius * radius;
	}
}
class Overloading {
	public static void main(String args[]) {
		Shape s = new Shape();
		System.out.println("Area of :");
		System.out.println("  Square: " + s.calcArea(12));
		System.out.println("  Rect: " + s.calcArea(23,12));
		System.out.println("  Circle: " + s.calcArea(4.76f));
	}
}
