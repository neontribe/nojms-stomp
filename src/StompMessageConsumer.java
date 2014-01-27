
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;

public class StompMessageConsumer {

  public static void main(String[] args) {
    try {
      StompConnection connection = new StompConnection();
      
		connection.open("192.168.1.7", 61613);
		connection.connect("guest", "P@ssword1");
//      connection.open("orange.cloudtroopers.ro", 61613);
//      connection.connect("system", "manager");
      
      connection.subscribe("jms.queue.memberRegistration", Subscribe.AckModeValues.CLIENT);
      
      connection.begin("tx2");
      StompFrame message = connection.receive();
      System.out.println(message.getBody());
      connection.ack(message, "tx2");
      connection.commit("tx2");
      
      connection.disconnect();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}