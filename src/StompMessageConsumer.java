
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class StompMessageConsumer {

    public static void main(String[] args) throws ParseException {
        
        Options options = new Options();
        options.addOption("h", true, "Host to connect to");
        options.addOption("p", true, "Port to connect to");
        options.addOption("u", true, "User name");
        options.addOption("P", true, "Password");
        
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse( options, args);
        
        String host = "192.168.1.7";
        String port = "61613";
        String user = "guest";
        String pass = "P@ssword1";
        
        try {
            StompConnection connection = new StompConnection();

            connection.open(host, Integer.parseInt(port));
            connection.connect(user, pass);
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
