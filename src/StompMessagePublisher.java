/**
 * Published under a Creative Commons Attribution-ShareAlike 4.0 International
 * License.
 */

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class StompMessagePublisher {

    private static final String REWARD_POINTS_JSON = "{\"loyalty_account_id\":\"1\", \"fbc_unique_acct_id\":\"1010028956904358\", \"points\":\"1\", \"sign\":\"+\", \"offer_name\":\"PR0141\", \"forfeit_flag\":\"N\", \"parsing_batch\":\"1390320657_1853233996\", \"channel_id\":1, \"token\":\"ffef34234\"}";
    private static final String MEMBER_REGISTRATION_JSON = "{\"fbc_unique_acct_id\":\"4234567890123413\",\"last_name\":\"Fallon\",\"first_name\":\"Cally\",\"middle_initial\":\" \",\"prefix\":\"AMiss\",\"suffix\":\"Ph.D\",\"full_name\":\"Fallon Cally\",\"address_line_1\":\"2165 Libero.Rd.\",\"address_line_2\":\"Ap #875-3531 Et Rd.\",\"city\":\"Jandrain-Jandrenouille\",\"state\":\"AK\",\"zip_code\":\".20083\",\"primary_phone_number\":\"136567581\",\"secondary_phone_number\":\"6125669026\",\"email\":\"0Cally.Fallon@email.com\",\"allegiant_customer_id\":\"allegiant_customer_Id111\",\"parsing_batch\":\"1390473784_298389220\",\"channel_id\":1,\"token\":\"myToken\"}";
    private static final String MEMBER_UPDATE_JSON = "{\"loyalty_account_id\":\"1\",\"fbc_unique_acct_id\":\"1010028956904358\",\"full_name\":\"UPDATED\",\"address_line_1\":\"UPDATED DODGE ST\",\"address_line_2\":\"\",\"city\":\"UPDATED\",\"state\":\"NE\",\"zip_code\":\"681970003\",\"email\":\"UPDATED@asd.com\",\"parsing_batch\":\"1390320657_1853233996\", \"token\":\"myToken\"}";

    private static final String REWARD_POINTS_JMS_DESTINATION = "jms.queue.rewardPoints";
    private static final String MEMBER_REGISTRATION_JMS_DESTINATION = "jms.queue.memberRegistration";
    private static final String MEMBER_UPDATE_JMS_DESTINATION = "jms.queue.memberUpdate";

    public static void main(String[] args) throws ParseException {

        Options options = new Options();
        options.addOption("h", true, "Host to connect to");
        options.addOption("p", true, "Port to connect to");
        options.addOption("u", true, "User name");
        options.addOption("P", true, "Password");
        options.addOption("d", true, "JMS Destination");
        options.addOption("j", true, "JSON to send");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);

        String host = "127.0.0.1";
        String port = "61613";
        String user = "guest";
        String pass = "P@ssword1";
        String destination = MEMBER_REGISTRATION_JMS_DESTINATION;
        String json = MEMBER_REGISTRATION_JSON;

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
        if (cmd.hasOption("d")) {
            destination = cmd.getOptionValue("d");
        }
        if (cmd.hasOption("j")) {
            json = cmd.getOptionValue("j");
        }

        try {
            StompConnection connection = new StompConnection();

            connection.open(host, Integer.parseInt(port));
            connection.connect(user, pass);

            send(host, port, user, pass, destination, json, connection);
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

    private static void send(String host, String port, String user, String pass, String destination, String json,
            StompConnection connection) throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Stomp.Headers.CONTENT_LENGTH, String.valueOf(json.length()));

        connection.send(destination, json, null, headers);
        StompFrame stomp = connection.receive();

        System.out.println("Message sent to " + destination);
        System.out.println(user + ':' + pass + '@' + host + ":" + port);
        System.out.println(json);
        System.out.println("\n++ The server responded ++");
        System.out.println(stomp);
        System.out.println("\n");
    }

}
