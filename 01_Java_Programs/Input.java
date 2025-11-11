import java.io.DataInputStream;
import java.io.IOException;
class Input {
	public static void main(String args[]) {
		DataInputStream i = new DataInputStream(System.in);
		String name = null;
		System.out.print("Enter your name: ");
		try {
			name = i.readLine();
		} catch(IOException io) {}
		System.out.println("Welcome " + name);
	}
}
