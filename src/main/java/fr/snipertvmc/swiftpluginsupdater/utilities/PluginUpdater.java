package fr.snipertvmc.swiftpluginsupdater.utilities;

import fr.snipertvmc.swiftpluginsupdater.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class PluginUpdater {


	public static boolean downloadLatestRelease(String ressourceName, int ressourceID) {

		try {
			downloadFile(ressourceName, ressourceID);
			Main.getPluginListData().addPluginName(ressourceName);
			return true;

		} catch (IOException e) {
			Main.getConsoleLogger().error("Error while downloading of latest update for " + ressourceName + ": " + e.getMessage());
			Main.getConsoleLogger().error("");
			return false;
		}
	}

	public static String getLatestRelease(String ressourceName, int ressourceID) {

		try {
			URL url = new URL("https://api.spiget.org/v2/resources/" + ressourceID + "/versions/latest");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			JSONObject response = (JSONObject) new JSONParser().parse(reader);

			return (String) response.get("name");

		} catch (Exception e) {
			Main.getConsoleLogger().error("");
			Main.getConsoleLogger().error("Error while checking of an update for " + ressourceName + ": " + e.getMessage());
			Main.getConsoleLogger().error("");
			return null;
		}
	}


	public static void downloadFile(String ressourceName, int ressourceID) throws IOException {

		URL url = new URL("https://api.spiget.org/v2/resources/" + ressourceID + "/download");

		File destinationDir = new File(Main.getInstance().getDataFolder() + "/temp/");
		if (!destinationDir.exists()) {
			destinationDir.mkdirs();
		}

		try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
		     FileOutputStream fileOutputStream = new FileOutputStream(
				     Main.getInstance().getDataFolder() + "/temp/" + ressourceName + "-" + getLatestRelease(ressourceName, ressourceID) + ".jar"
				     // Path Output: /plugins/SwiftPluginsUpdater/temp/PLUGIN_NAME-PLUGIN_VERSION.jar
		     )) {
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		}
	}
}
