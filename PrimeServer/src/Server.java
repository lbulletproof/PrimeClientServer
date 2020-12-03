
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
/**
 * @author Mike
 * 
 * 
 *<p> 
 * Server waits for client request to check if a number is prime.
 * Server check if number is prime and return result to client.
 * This application uses: 
 * java FX for GUI
 * Java IO and Java net 
 * For socket and streams.
 *    
 *
 */
public class Server extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());
        
        while (true) {
          // Receive radius from the client
          double num = inputFromClient.readInt();
  
          // Compute prime
          boolean flag = false;
          for (int i = 2; i <= num / 2; ++i) {
            // condition for nonprime number
            if (num % i == 0) {
              flag = true;
              break;
            }
          }
          	String strToClient ="";
          if (flag) {
        	  strToClient = (int)num + " is not a prime number.";
        	  System.out.println(strToClient);
          }else {
        	  strToClient = (int)num + " is a prime number.";
        	  System.out.println(strToClient);
        }
          

  
          // Send area back to the client
//          outputToClient.writeInt(num);
          outputToClient.writeChars(strToClient);
          final String str = strToClient;
          Platform.runLater(() -> {
            ta.appendText("Number received from client: " );
            ta.appendText( str + '\n'); 
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
