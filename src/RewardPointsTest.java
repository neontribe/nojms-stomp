/**
 * Published under a Creative Commons Attribution-ShareAlike 4.0 International
 * License.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;

public class RewardPointsTest {

    private static final Random RAND = new Random();

    // registration constants
    private static final long EXTERNAL_PROVIDER_ID = 7891956190123333L; // fnbo
    private static final long CUSTOMER_ACCT_ID = 22222222; // allegiant

    // reward points constants
    private static final long LOYALTY_ACCT_ID = 6761023031987L;
    private static final long POINTS = 1000;
    private static final String SIGN = "+";

    private static final String FNBO_CHANNEL_NAME = "FNBO Premium Card";
    private static final String XXX_CHANNEL_NAME = "XXX Channel";

    public static final String REWARD_POINTS_JSON = "{\"recordId\":\"11123\""
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
            + ", \"transactionComment\":\"coment\", \"transactionTime\":1389782282, \"operatorId\":-1, \"token\":\"8eb0a18834d37b94e398e64677bcd851\"}";

    public static final String REWARD_POINTS_JMS_DESTINATION = "jms.queue.loyalty.exported.rewardPoints";

    public static void main(String[] args) {

        String host = "localhost";
        String port = "61613";
        String user = "guest";
        String pass = "P@ssword1";

        String destination = REWARD_POINTS_JMS_DESTINATION;

        StompConnection connection = new StompConnection();
        try {
            connection.open(host, Integer.parseInt(port));
            connection.connect(user, pass);

            performTest(host, port, user, pass, destination, connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.disconnect();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void performTest(String host, String port, String user, String pass, String destination,
            StompConnection connection) throws Exception {

        List<Integer> pointsList = new ArrayList<Integer>();
        int generalTotal = 0;

        for (int i = 0; i < 1000; i++) {
            int signum = (randInt(4) > 3) ? -1 : 1;
            int points = randInt(500) * signum;

            generalTotal = generalTotal + points;
            pointsList.add(points);

            System.out.println(" msg " + i);
            String json = REWARD_POINTS_JSON.replaceAll(String.valueOf(POINTS), String.valueOf(Math.abs(points)));
            if (signum < 0) {
                json = json.replaceAll("\\+", "-");
            }
            send(host, port, user, pass, destination, json, connection);
        }

        System.out.println(generalTotal);
        System.out.println(pointsList);
    }

    public static int randInt(int max) {

        int randomNum = RAND.nextInt(max) + 1;

        return randomNum;
    }

    private static void send(String host, String port, String user, String pass, String destination, String json,
            StompConnection connection) throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(Stomp.Headers.CONTENT_LENGTH, String.valueOf(json.length()));

        connection.send(destination, json, null, headers);
    }

}
