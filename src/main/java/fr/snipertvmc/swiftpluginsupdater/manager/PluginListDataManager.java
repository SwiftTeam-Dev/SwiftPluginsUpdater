package fr.snipertvmc.swiftpluginsupdater.manager;

import fr.snipertvmc.swiftpluginsupdater.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PluginListDataManager {

	private YamlConfiguration yamlPluginListDataFile;


	public void loadPluginListData() {

		File pluginListDataFile = new File(Main.getInstance().getDataFolder(), "data.yml");

		if(!pluginListDataFile.exists()) {
			pluginListDataFile.getParentFile().mkdirs();
			Main.getInstance().saveResource("data.yml", false);
		}

		YamlConfiguration.loadConfiguration(pluginListDataFile);
		yamlPluginListDataFile = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "data.yml"));
	}

	public void savePluginListData() {

		File pluginListDataFile = new File(Main.getInstance().getDataFolder(), "data.yml");

		try {
			yamlPluginListDataFile.save(pluginListDataFile);

		} catch (IOException e) {
			Main.getConsoleLogger().error(e.getMessage());
		}
	}


	//---------------------------------------//
	//                                       //
	//  CONFIGURATION FILE - VALUES GETTERS  //
	//                                       //
	//---------------------------------------//


	public List<String> getUpdatedPlugins() {
		return yamlPluginListDataFile.getStringList("updatedPlugins");
	}


	public void clearUpdatedPlugin() {
		yamlPluginListDataFile.set("updatedPlugins", new ArrayList<>());
	}


	public void addPluginName(String pluginName) {
		yamlPluginListDataFile.set("updatedPlugins", getUpdatedPlugins().add(pluginName));
	}


	public void removePluginName(String pluginName) {
		yamlPluginListDataFile.set("updatedPlugins", getUpdatedPlugins().remove(pluginName));
	}
}
