package uk.co.neontribe.g4.stomp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author tobias
 */
public class Installer {

	private static final long FBC_ACCT_ID = 7891956190123332L;

	public static final String REWARD_POINTS_JSON_MERGE = "{\"recordId\":\"11123\", \"loyaltyAccountId\":\"6761023031994\", \"externalProviderId\":\"4134361776535813\",\"fullName\":\"Mr Clayton Andrei Jr\",\"addressLine1\":\"4590 Wilkinson Street\",\"addressLine2\":\"6009 ON AAAAAAAAA\",\"city\":\"Austin\",\"state\":\"CA\",\"zipCode\":\"6186\",\"email\":\"AndreiClayton@rhyta.com\",\"points\":\"1\", \"sign\":\"+\", \"offerCode\":\"PR0141\", \"forfeitFlag\":\"N\", \"parsingBatch\":\"1391079208_636438973\", \"channelId\":1, \"transactionSourceId\":1, \"transactionComment\":\"coment\", \"transactionTime\":1389782282, \"transactionType\":\"E\", \"operatorId\":-1, \"token\":\"8eb0a18834d37b94e398e64677bcd85d\"}";
	public static final String REWARD_POINTS_JSON = "{\"loyaltyAccountId\":\"6761023031994\", \"externalProviderId\":\"4134361776535813\", \"points\":\"1\", \"sign\":\"+\", \"offerCode\":\"PR0141\", \"forfeitFlag\":\"N\", \"parsingBatch\":\"1391079208_636438973\", \"channelId\":1, \"transactionSourceId\":1, \"transactionComment\":\"coment\", \"transactionTime\":1389782282, \"transactionType\":\"E\", \"operatorId\":-1, \"token\":\"8eb0a18834d37b94e398e64677bcd85d\"}";
	public static final String MEMBER_REGISTRATION_JSON = "{\"recordId\":\"10123\", \"externalProviderId\":\"FBC_ACCT_ID\",\"lastName\":\"Fallon\",\"firstName\":\"Cally\",\"middleInitial\":\" \",\"prefix\":\"AMiss\",\"suffix\":\"Ph.D\",\"fullName\":\"Fallon Cally\",\"addressLine1\":\"2165 Libero.Rd.\",\"addressLine2\":\"Ap #875-3531 Et Rd.\",\"city\":\"Jandrain-Jandrenouille\",\"state\":\"AK\",\"zipCode\":\".20083\",\"primaryPhoneNumber\":\"136567581\",\"secondaryPhoneNumber\":\"6125669026\",\"email\":\"0Cally.Fallon@email.com\",\"allegiantCustomerId\":\"111111\",\"parsingBatch\":\"1390473784_298389220\",\"channelId\":1,\"token\":\"myToken\"}";
	public static final String MEMBER_UPDATE_JSON = "{\"loyaltyAccountId\":\"6761023031994\",\"externalProviderId\":\"4134361776535813\",\"fullName\":\"Mr Clayton Andrei Jr\",\"addressLine1\":\"4590 Wilkinson Street\",\"addressLine2\":\"6009 ON AAAAAAAAA\",\"city\":\"Austin\",\"state\":\"CA\",\"zipCode\":\"6186\",\"email\":\"AndreiClayton@rhyta.com\",\"parsingBatch\":\"1397817086_603333107\", \"token\":\"f971c633287139e384f950f58067e8c7\"}";

	public static final String REWARD_POINTS_JMS_DESTINATION = "jms.queue.loyalty.exported.rewardPoints";
	public static final String MEMBER_REGISTRATION_JMS_DESTINATION = "jms.queue.loyalty.exported.memberRegistration";
	public static final String MEMBER_UPDATE_JMS_DESTINATION = "jms.queue.loyalty.exported.memberUpdate";

	public static void main(String[] args) throws Exception {
		if (args.length == 0 || args[0].equals("write json")) {
			new Installer().writeJson();
		}
	}

	protected void writeJson() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		FileOutputStream out;

		out = new FileOutputStream("reward_points_json_merge.json");
		out.write(REWARD_POINTS_JSON_MERGE.getBytes());
		out.close();

		out = new FileOutputStream("reward_points_json.json");
		out.write(REWARD_POINTS_JSON.getBytes());
		out.close();

		out = new FileOutputStream("member_registration_json.json");
		out.write(MEMBER_REGISTRATION_JSON.getBytes());
		out.close();

		out = new FileOutputStream("member_update_json.json");
		out.write(MEMBER_UPDATE_JSON.getBytes());
		out.close();

	}
}
