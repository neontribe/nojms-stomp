package uk.co.neontribe.g4.stomp;

/**
 * Published under a Creative Commons Attribution-ShareAlike 4.0 International License.
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class StompMessagePublisher {

	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption("h", true, "Host to connect to");
		options.addOption("p", true, "Port to connect to");
		options.addOption("u", true, "User name");
		options.addOption("P", true, "Password");
		options.addOption("d", true, "JMS Destination");
		options.addOption("j", true, "JSON to send");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		String host = "localhost";
		String port = "61613";
		String user = "guest";
		String pass = "P@ssword1";

		String destination = Installer.MEMBER_REGISTRATION_JMS_DESTINATION;
		String json = Installer.MEMBER_REGISTRATION_JSON;

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

		File source = new File(json);
		if (source.exists()) {
			byte[] data = Files.readAllBytes(source.toPath());
			json = new String(data);
		}

		new StompMessagePublisher().sendMessage(host, port, user, pass, json, destination);
	}

	public void sendMessage(String host, String port, String user, String pass, String json, String destination) throws IOException, Exception {

		StompConnection connection = new StompConnection();

		System.out.println("Json: " + json);
		connection.open(host, Integer.parseInt(port));
		connection.connect(user, pass);

		// I aasume this loop is to test multiple messages.
		// By embedding this here we make the tool less useful for other people
		// Use a file system loop or a macro to call this many times.
//            for (int i = 0; i < 1; i++) {
//                System.out.println(" msg " + i);
//                String newJson = json.replaceAll(""+FBC_ACCT_ID,""+(FBC_ACCT_ID+i));
//                send(host, port, user, pass, destination, newJson, connection);
//            }
		send(host, port, user, pass, destination, json, connection);
		connection.disconnect();
		connection.close();
	}

	private static void send(String host, String port, String user, String pass, String destination, String json,
			StompConnection connection) throws Exception {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(Stomp.Headers.CONTENT_LENGTH, String.valueOf(json.length()));

		connection.send(destination, json, null, headers);
	}

}
