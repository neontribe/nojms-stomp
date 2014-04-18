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

    public static final String REWARD_POINTS_JSON = "{\"loyaltyAccountId\":\"6761023031994\", \"externalProviderId\":\"4134361776535813\", \"points\":\"1\", \"sign\":\"+\", \"offerCode\":\"PR0141\", \"forfeitFlag\":\"N\", \"parsingBatch\":\"1391079208_636438973\", \"channelId\":1, \"transactionSourceId\":1, \"transactionComment\":\"coment\", \"transactionTime\":1389782282, \"transactionType\":\"E\", \"operatorId\":-1, \"token\":\"8eb0a18834d37b94e398e64677bcd85d\"}";
    public static final String MEMBER_REGISTRATION_JSON = "{\"externalProviderId\":\"4134361776535813\",\"lastName\":\"Fallon\",\"firstName\":\"Cally\",\"middleInitial\":\" \",\"prefix\":\"AMiss\",\"suffix\":\"Ph.D\",\"fullName\":\"Fallon Cally\",\"addressLine1\":\"2165 Libero.Rd.\",\"addressLine2\":\"Ap #875-3531 Et Rd.\",\"city\":\"Jandrain-Jandrenouille\",\"state\":\"AK\",\"zipCode\":\".20083\",\"primaryPhoneNumber\":\"136567581\",\"secondaryPhoneNumber\":\"6125669026\",\"email\":\"0Cally.Fallon@email.com\",\"allegiantCustomerId\":\"111111\",\"parsingBatch\":\"1390473784_298389220\",\"channelId\":1,\"token\":\"myToken\"}";
    public static final String MEMBER_UPDATE_JSON = "{\"loyaltyAccountId\":\"6761023031994\",\"externalProviderId\":\"4134361776535813\",\"fullName\":\"Mr Clayton Andrei Jr\",\"addressLine1\":\"4590 Wilkinson Street\",\"addressLine2\":\"6009 ON AAAAAAAAA\",\"city\":\"Austin\",\"state\":\"CA\",\"zipCode\":\"6186\",\"email\":\"AndreiClayton@rhyta.com\",\"parsingBatch\":\"1397817086_603333107\", \"token\":\"f971c633287139e384f950f58067e8c7\"}";

    public static final String REWARD_POINTS_JMS_DESTINATION = "jms.queue.loyalty.exported.rewardPoints";
    public static final String MEMBER_REGISTRATION_JMS_DESTINATION = "jms.queue.loyalty.exported.memberRegistration";
    public static final String MEMBER_UPDATE_JMS_DESTINATION = "jms.queue.loyalty.exported.memberUpdate";

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
        String destination = MEMBER_UPDATE_JMS_DESTINATION;
        String json = MEMBER_UPDATE_JSON;

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

            for (int i = 0; i < 1; i++) {
                send(host, port, user, pass, destination, json, connection);
            }
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
    }

}
