import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;


import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;

public class StompMessagePublisher {

	public static void main(String[] args) {
		try {
			StompConnection connection = new StompConnection();

			connection.open("127.0.0.1", 61613);
			connection.connect("guest", "P@ssword1");
			
			// connection to pink.cloudtroopers.com
			// connection.open("192.168.1.7", 61613);
			// connection.connect("guest", "P@ssword1");
			
			// memberRegistration
			// String json = "{\"fbc_unique_acct_id\":\"4234567890123413\",\"last_name\":\"Fallon\",\"first_name\":\"Cally\",\"middle_initial\":\" \",\"prefix\":\"AMiss\",\"suffix\":\"Ph.D\",\"full_name\":\"Fallon Cally\",\"address_line_1\":\"2165 Libero.Rd.\",\"address_line_2\":\"Ap #875-3531 Et Rd.\",\"city\":\"Jandrain-Jandrenouille\",\"state\":\"AK\",\"zip_code\":\".20083\",\"primary_phone_number\":\"136567581\",\"secondary_phone_number\":\"6125669026\",\"email\":\"0Cally.Fallon@email.com\",\"allegiant_customer_id\":\"allegiant_customer_Id111\",\"parsing_batch\":\"1390473784_298389220\",\"channel_id\":1,\"token\":\"myToken\"}";
		
			// memberUpdate
			// String json = "{\"loyalty_account_id\":\"5\",\"fbc_unique_acct_id\":\"1010028956904358\",\"full_name\":\"UPDATED\",\"address_line_1\":\"UPDATED DODGE ST\",\"address_line_2\":\"\",\"city\":\"UPDATED\",\"state\":\"NE\",\"zip_code\":\"681970003\",\"email\":\"UPDATED@asd.com\",\"parsing_batch\":\"1390320657_1853233996\", \"token\":\"myToken\"}";

			// rewardPoints
			String json = "{\"loyalty_account_id\":\"5\", \"fbc_unique_acct_id\":\"1010028956904358\", \"points\":\"120000\", \"sign\":\"-\", \"offer_name\":\"PR0141\", \"forfeit_flag\":\"N\", \"parsing_batch\":\"1390320657_1853233996\", \"channel_id\":1, \"token\":\"ffef34234\"}";
			 
			
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