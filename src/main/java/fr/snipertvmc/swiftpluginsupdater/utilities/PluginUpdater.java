package fr.snipertvmc.swiftpluginsupdater.utilities;

import fr.snipertvmc.swiftpluginsupdater.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PluginUpdater {

	public static String getLatestRelease() {
		try {
			URL url = new URL("https://api.spiget.org/v2/resources/114152/versions/latest");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			JSONObject response = (JSONObject) new JSONParser().parse(reader);

			return (String) response.get("name");

		} catch (Exception e) {
			Main.getConsoleLogger().warning("Erreur lors de la vérification de la version : " + e.getMessage());
			return null;
		}
	}
}
