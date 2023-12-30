package fr.snipertvmc.swiftpluginsupdater.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.snipertvmc.swiftpluginsupdater.Main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RessourcesDatabaseManager {

	private final static String pluginsDatabaseUrl = "https://raw.githubusercontent.com/SwiftTeam-Dev/SwiftPluginsUpdater/Main/ressourcesDatabase.json";


	public static List<String> getRessourceList() {

		List<String> names = new ArrayList<>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject;

		try {
			jsonObject = jsonParser.parse(new InputStreamReader(new URL(pluginsDatabaseUrl).openStream())).getAsJsonObject();

		} catch (IOException e) {
			Main.getConsoleLogger().error(e.getMessage());
			Main.getConsoleLogger().error("");
			return null;
		}

		Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
		for (Map.Entry<String, com.google.gson.JsonElement> entry : entrySet) {
			names.add(entry.getKey());
		}

		return names;
	}


	public static int getRessourceID(String ressourceName) {

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject;

		try {
			jsonObject = jsonParser.parse(new InputStreamReader(new URL(pluginsDatabaseUrl).openStream())).getAsJsonObject();
			return jsonObject.getAsJsonObject(ressourceName).get("ressourceID").getAsInt();

		} catch (IOException e) {
			Main.getConsoleLogger().error(e.getMessage());
			Main.getConsoleLogger().error("");
			return -1;
		}
	}
}