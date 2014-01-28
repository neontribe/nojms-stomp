
/**
 * Published under a Creative Commons Attribution-ShareAlike 4.0 International
 * License.
 */

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class StompMessagePublisher {

    public static void main(String[] args) throws ParseException {

        Options options = new Options();
        options.addOption("h", true, "Host to connect to");
        options.addOption("p", true, "Port to connect to");
        options.addOption("u", true, "User name");
        options.addOption("P", true, "Password");
        options.addOption("j", true, "JSON to send");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);

        String host = "127.0.0.1";
        String port = "61613";
        String user = "guest";
        String pass = "P@ssword1";
        String json = "{\"loyalty_account_id\":\"5\", \"fbc_unique_acct_id\":\"1010028956904358\", \"points\":\"120000\", \"sign\":\"-\", \"offer_name\":\"PR0141\", \"forfeit_flag\":\"N\", \"parsing_batch\":\"1390320657_1853233996\", \"channel_id\":1, \"token\":\"ffef34234\"}";

        if (cmd.hasOption("h")) {
            host = cmd.getOptionValue("h");
        }
        if (cmd.hasOption("p")) {
            port = cmd.getOptionValue("p");
        }
        if (cmd.hasOption("u")) {
            user = cmd.getOptionValue("u");
        }
        if (cmd.hasOption("P")) {
            pass = cmd.getOptionValue("P");
        }
        if (cmd.hasOption("j")) {
            json = cmd.getOptionValue("j");
        }

        try {
            StompConnection connection = new StompConnection();

            connection.open(host, Integer.parseInt(port));
            connection.connect(user, pass);

			// connection to pink.cloudtroopers.com
            // connection.open("192.168.1.7", 61613);
            // connection.connect("guest", "P@ssword1");
            // memberRegistration
            // String json = "{\"fbc_unique_acct_id\":\"4234567890123413\",\"last_name\":\"Fallon\",\"first_name\":\"Cally\",\"middle_initial\":\" \",\"prefix\":\"AMiss\",\"suffix\":\"Ph.D\",\"full_name\":\"Fallon Cally\",\"address_line_1\":\"2165 Libero.Rd.\",\"address_line_2\":\"Ap #875-3531 Et Rd.\",\"city\":\"Jandrain-Jandrenouille\",\"state\":\"AK\",\"zip_code\":\".20083\",\"primary_phone_number\":\"136567581\",\"secondary_phone_number\":\"6125669026\",\"email\":\"0Cally.Fallon@email.com\",\"allegiant_customer_id\":\"allegiant_customer_Id111\",\"parsing_batch\":\"1390473784_298389220\",\"channel_id\":1,\"token\":\"myToken\"}";
            // memberUpdate
            // String json = "{\"loyalty_account_id\":\"5\",\"fbc_unique_acct_id\":\"1010028956904358\",\"full_name\":\"UPDATED\",\"address_line_1\":\"UPDATED DODGE ST\",\"address_line_2\":\"\",\"city\":\"UPDATED\",\"state\":\"NE\",\"zip_code\":\"681970003\",\"email\":\"UPDATED@asd.com\",\"parsing_batch\":\"1390320657_1853233996\", \"token\":\"myToken\"}";
            // rewardPoints
            // bytes message
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put(Stomp.Headers.CONTENT_LENGTH, String.valueOf(json.length()));

            connection.send("jms.queue.rewardPoints", json, null, headers);

            System.out.println("message sent!");
            connection.disconnect();
            connection.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
