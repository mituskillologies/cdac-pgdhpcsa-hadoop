import java.io.DataInputStream;
import java.io.IOException;
class Input1 {
	public static void main(String args[]) 
	throws IOException {
		DataInputStream i = new DataInputStream(System.in);
		String name = null;
		System.out.print("Enter your name: ");
		name = i.readLine();
		System.out.println("Welcome " + name);
	}
}
