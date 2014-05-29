/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.neontribe.g4.stomp;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import static uk.co.neontribe.g4.stomp.StompMessagePublisher.MEMBER_UPDATE_JMS_DESTINATION;

/**
 *
 * @author tobias
 */
public class CreateUpdates {

	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption("h", true, "Host to connect to");
		options.addOption("p", true, "Port to connect to");
		options.addOption("u", true, "User name");
		options.addOption("P", true, "Password");
		options.addOption("j", true, "Path to JSON Folder");
		options.addOption("v", false, "Verbose");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		String host = "localhost";
		String port = "61613";
		String user = "guest";
		String pass = "P@ssword1";

		String destination = MEMBER_UPDATE_JMS_DESTINATION;
		String jsonpath = null;

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
			jsonpath = cmd.getOptionValue("j");
		}
		if (cmd.hasOption("v")) {
			StompMessagePublisher.verbose = true;
		}

		Set<String> jsonv = new HashSet<>();
		File dir = new File(jsonpath);
		if (!dir.isDirectory()) {
			System.err.println("Json path does not exists");
			System.exit(1);
		}
		if (StompMessagePublisher.verbose) {
			System.out.println("Read JSON from " + dir);
		}

		File[] listOfFiles = dir.listFiles();

		for (File listOfFile : listOfFiles) {
			if (listOfFile.isFile()) {
				String jsonfile = listOfFile.getName().toLowerCase();
				if (jsonfile.endsWith(".json")) {
					File source = listOfFile;
					if (source.exists()) {
						if (StompMessagePublisher.verbose) {
							System.out.println("Adding json from " + source);
						}
						byte[] data = Files.readAllBytes(source.toPath());
						jsonv.add(new String(data));
					}
				}
			}
		}

		for (String json : jsonv) {
			new StompMessagePublisher().sendMessage(host, port, user, pass, json, destination);
			Thread.sleep(1000);
		}
	}
}
