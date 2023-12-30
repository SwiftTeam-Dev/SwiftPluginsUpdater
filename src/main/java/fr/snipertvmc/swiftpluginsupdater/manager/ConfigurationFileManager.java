package fr.snipertvmc.swiftpluginsupdater.manager;

import fr.snipertvmc.swiftpluginsupdater.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationFileManager {

	private YamlConfiguration yamlConfigurationFile;


	public void reloadConfigurationFile() {

		File configurationFile = new File(Main.getInstance().getDataFolder(), "configuration.yml");

		if(!configurationFile.exists()) {
			configurationFile.getParentFile().mkdirs();
			Main.getInstance().saveResource("configuration.yml", false);
		}

		YamlConfiguration.loadConfiguration(configurationFile);
		yamlConfigurationFile = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "configuration.yml"));
	}


	//---------------------------------------//
	//                                       //
	//  CONFIGURATION FILE - VALUES GETTERS  //
	//                                       //
	//---------------------------------------//


	public int getConfigVersion() {
		return yamlConfigurationFile.getInt("config-version");
	}


	public String getPrefix() {
		return yamlConfigurationFile.getString("prefix");
	}


	public boolean checkUpdates() {
		return yamlConfigurationFile.getBoolean("check-updates");
	}


	public boolean autoUpdates() {
		return yamlConfigurationFile.getBoolean("auto-updates");
	}
}
