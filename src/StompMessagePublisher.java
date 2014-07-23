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

    // private static final long FBC_ACCT_ID = 7891956190123332L;

    // registration constants
    private static final long EXTERNAL_PROVIDER_ID = 7891956190123333L; // fnbo
    private static final long CUSTOMER_ACCT_ID = 22222222; // allegiant

    // reward points constants
    private static final long LOYALTY_ACCT_ID = 6761023031987L;
    private static final long POINTS = 100;
    private static final String SIGN = "+";

    private static final String FNBO_CHANNEL_NAME = "FNBO Premium Card";
    private static final String XXX_CHANNEL_NAME = "XXX Channel";

    public static final String REWARD_POINTS_JSON_MERGE = "{\"recordId\":\"11123\""
            + ", \"loyaltyAccountId\":\""
            + LOYALTY_ACCT_ID
            + "\""
            + ", \"externalProviderId\":\""
            + EXTERNAL_PROVIDER_ID
            + "\""
            + ",\"fullName\":\"Mr Clayton Andrei Jr\",\"addressLine1\":\"4590 Wilkinson Street\",\"addressLine2\":\"6009 ON AAAAAAAAA\",\"city\":\"Austin\",\"state\":\"CA\",\"zipCode\":\"6186\",\"email\":\"AndreiClayton@rhyta.com\""
            + ",\"points\":\""
            + POINTS
            + "\""
            + ", \"sign\":\""
            + SIGN
            + "\""
            + ", \"offerCode\":\"PR0141\", \"forfeitFlag\":\"N\", \"parsingBatch\":\"1391079208_636438973\""
            + ", \"channelName\":\""
            + FNBO_CHANNEL_NAME
            + "\""
            + ", \"transactionComment\":\"coment\", \"transactionTime\":1389782282, \"operatorId\":-1, \"token\":\"8eb0a18834d37b94e398e64677bcd85d\"}";

    public static final String MEMBER_REGISTRATION_JSON = "{\"recordId\":\"10123\""
            + ", \"externalProviderId\":\""
            + EXTERNAL_PROVIDER_ID
            + "\""
            + ",\"lastName\":\"Fallon\",\"firstName\":\"Cally\",\"middleInitial\":\" \",\"prefix\":\"AMiss\",\"suffix\":\"Ph.D\",\"fullName\":\"Fallon Cally\",\"addressLine1\":\"2165 Libero.Rd.\",\"addressLine2\":\"Ap #875-3531 Et Rd.\",\"city\":\"Jandrain-Jandrenouille\",\"state\":\"AK\",\"zipCode\":\".20083\",\"primaryPhoneNumber\":\"136567581\",\"secondaryPhoneNumber\":\"6125669026\",\"email\":\"0Cally.Fallon@email.com\""
            + ",\"allegiantCustomerId\":\"" + CUSTOMER_ACCT_ID + "\""
            + ",\"parsingBatch\":\"1390473784_298389220\",\"channelName\":\"FNBO Premium Card\",\"token\":\"myToken\"}";

    public static final String REWARD_POINTS_JMS_DESTINATION = "jms.queue.loyalty.exported.rewardPoints";
    public static final String MEMBER_REGISTRATION_JMS_DESTINATION = "jms.queue.loyalty.exported.memberRegistration";

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

        // String host = "loyjbsms11-public.lolacloud.com";
        // String host = "pink.cloudtroopers.ro";
        String host = "localhost";
        // String host = "pink.cloudtroopers.ro";
        String port = "61613";
        String user = "guest";
        String pass = "P@ssword1";

        String destination = REWARD_POINTS_JMS_DESTINATION;
        String json = REWARD_POINTS_JSON_MERGE;

//        String destination = MEMBER_REGISTRATION_JMS_DESTINATION;
//        String json = MEMBER_REGISTRATION_JSON;

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
                System.out.println(" msg " + i);
                String newJson = json.replaceAll("" + EXTERNAL_PROVIDER_ID, "" + (EXTERNAL_PROVIDER_ID + i));
                newJson = newJson.replaceAll("" + CUSTOMER_ACCT_ID, "" + (CUSTOMER_ACCT_ID + i));
                newJson = newJson.replaceAll("" + LOYALTY_ACCT_ID, "" + (LOYALTY_ACCT_ID + i));
                send(host, port, user, pass, destination, newJson, connection);
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
