import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ChatApp {

	private static int port;
	private static int user;
	private static Socket sendSocket;
	private static Socket receiveSocket;
	static ServerSocket serverSocket;

	static void sendMessage(String message, Boolean encrypt) {
		try {
			sendSocket = new Socket("localhost", user == 0 ? port : port + 1);
			System.out.println("sending message ...");
			DataOutputStream sendBuffer = new DataOutputStream(sendSocket.getOutputStream());

			sendBuffer.writeUTF(message);
			System.out.println("message sent !");

			return;
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
	}

	private static void printMessage(String message) {
		System.out.println("");
		for (int i = 0; i < (message.length() + 5); i++) {
			System.out.print("-");
		}
		System.out.println("");
		System.out.print("|");
		System.out.print("  ");
		for (int i = 0; i < (message.length()); i++) {
			System.out.print(" ");
		}
		System.out.print("  ");
		System.out.println("|");
		System.out.println("|  " + message + "  |");
		System.out.print("|");
		System.out.print("  ");

		for (int i = 0; i < (message.length()); i++) {
			System.out.print(" ");
		}
		System.out.print("  ");
		System.out.print("|");
		System.out.println("");
		for (int i = 0; i < (message.length() + 5); i++) {
			System.out.print("-");
		}
		System.out.println("");

	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hello to chat app ....");
		System.out.println("choose user");
		user = Integer.parseInt(scanner.nextLine());
		port = 6789;

		System.out.println("Loading ....");

		try {
			if (user == 0) {
				serverSocket = new ServerSocket(user == 0 ? (port + 1) : port);

				while (true) {
					System.out.println("Listening ...");
					receiveSocket = serverSocket.accept();
					DataInputStream incoming = new DataInputStream(receiveSocket.getInputStream());

					String data = (String) incoming.readUTF();
					printMessage(data);
					receiveSocket.close();

					System.out.println("Enter message :");
					String messageToSend = scanner.nextLine();
					if (!messageToSend.isEmpty()) {
						sendMessage(messageToSend, true);

					}
				}

			} else {
				serverSocket = new ServerSocket(user == 0 ? port + 1 : port);

				while (true) {

					System.out.println("Enter message :");
					String messageToSend = scanner.nextLine();
					if (!messageToSend.isEmpty()) {
						sendMessage(messageToSend, true);

					}
					System.out.println("Listening ...");
					receiveSocket = serverSocket.accept();
					DataInputStream incoming = new DataInputStream(receiveSocket.getInputStream());

					String data = (String) incoming.readUTF();
					printMessage(data);
					receiveSocket.close();

				}

			}

		} catch (Exception e) {
			System.out.println("problem is :" + e);
		}
		scanner.close();
	}
}
