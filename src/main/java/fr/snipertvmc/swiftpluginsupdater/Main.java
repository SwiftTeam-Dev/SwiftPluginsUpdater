package fr.snipertvmc.swiftpluginsupdater;

import fr.snipertvmc.swiftpluginsupdater.manager.ConfigurationFileManager;
import fr.snipertvmc.swiftpluginsupdater.manager.PluginListDataManager;
import fr.snipertvmc.swiftpluginsupdater.manager.UpdatesManager;
import fr.snipertvmc.swiftpluginsupdater.utilities.ConsoleLogger;
import fr.snipertvmc.swiftpluginsupdater.utilities.PluginUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	private static ConsoleLogger consoleLogger;
	private static ConfigurationFileManager configurationFileManager;
	private static PluginListDataManager pluginListDataManager;


	@Override
	public void onEnable() {

		instance = this;
		consoleLogger = new ConsoleLogger();
		configurationFileManager = new ConfigurationFileManager();
		pluginListDataManager = new PluginListDataManager();

		configurationFileManager.reloadConfigurationFile();
		pluginListDataManager.loadPluginListData();

		getConsoleLogger().info("");
		getConsoleLogger().info("§7Enabling plugin...");
		getConsoleLogger().info("§7Version: §3v" + getDescription().getVersion() + " §7- Author: §3SwiftTeam");
		getConsoleLogger().info("");

		//
		// CHECK FOR AN UPDATE
		//

		if(getSettings().checkUpdates()) {

			String latestVersion = PluginUpdater.getLatestRelease("SwiftPluginsUpdater", 114152);
			if (latestVersion.equals(getDescription().getVersion())) {
				getConsoleLogger().info("§7You have the §alatest §7version of SwiftPluginsUpdater.");
				getConsoleLogger().info("");

			} else {
				getConsoleLogger().info("§7You have an §cold §7version of SwiftPluginsUpdater.");
				getConsoleLogger().info("§7Current version: §3v" + getDescription().getVersion() + " §7- Latest version: §3v" + latestVersion);
				getConsoleLogger().info("");

				//
				// AUTOMATIC UPDATE
				//

				if(getSettings().autoUpdates()) {

					getConsoleLogger().info("§7Downloading of §elatest release §7of SwiftPluginsUpdater...");
					getConsoleLogger().info("");

					if (PluginUpdater.downloadLatestRelease(
							"SwiftPluginsUpdater",
							114152))
					{
						getConsoleLogger().info("§aSuccessful download.");
						getConsoleLogger().info("§7La mise à jour sera mise en place au prochaine redémarrage.");
						getConsoleLogger().info("");

					} else {
						getConsoleLogger().info("§cDownload failed.");
						getConsoleLogger().info("§7Please try to restart the server.");
						getConsoleLogger().info("");
						getConsoleLogger().info("§7If the problem persists, §6contact support§7:");
						getConsoleLogger().info("§7- §3https://discord.gg/fSzK79TAYf");
						getConsoleLogger().info("");
					}
				}
			}
		}

		if(pluginListDataManager.getUpdatedPlugins().isEmpty()) {
			getConsoleLogger().info("§7Aucun plugin n'a été mis à jour.");
			getConsoleLogger().info("");
		} else {
			getConsoleLogger().info("§7Liste mise à jour: §a" + pluginListDataManager.getUpdatedPlugins());
			getConsoleLogger().info("");
		}
		pluginListDataManager.clearUpdatedPlugin();

		getConsoleLogger().info("§7Une analyse des plugins va être effectuée dans §e5 secondes§7.");
		getConsoleLogger().info("");

		Bukkit.getScheduler().runTaskLater(this, UpdatesManager::checkRessourcesUpdate, (20 * 5));

		super.onEnable();
	}


	@Override
	public void onDisable() {

		if(!pluginListDataManager.getUpdatedPlugins().isEmpty()) {
			UpdatesManager.afterRestartSetup(pluginListDataManager.getUpdatedPlugins());
		}

		getConsoleLogger().console("");
		getConsoleLogger().console("§7Disabling plugin...");
		getConsoleLogger().console("");

		super.onDisable();
	}


	public static Main getInstance() {
		return instance;
	}


	public static ConsoleLogger getConsoleLogger() {
		return consoleLogger;
	}


	public static ConfigurationFileManager getSettings() {
		return configurationFileManager;
	}


	public static PluginListDataManager getPluginListData() {
		return pluginListDataManager;
	}
}