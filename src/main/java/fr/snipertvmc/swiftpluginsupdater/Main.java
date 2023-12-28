package fr.snipertvmc.swiftpluginsupdater;

import fr.snipertvmc.swiftpluginsupdater.utilities.ConsoleLogger;
import fr.snipertvmc.swiftpluginsupdater.utilities.PluginUpdater;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	private static ConsoleLogger consoleLogger;

	@Override
	public void onEnable() {

		instance = this;
		consoleLogger = new ConsoleLogger();

		getConsoleLogger().console("");
		getConsoleLogger().console("§7Enabling plugin...");
		getConsoleLogger().console("§7Version: §3v" + getDescription().getVersion() + " §7- Author: §3SwiftTeam");
		getConsoleLogger().console("");

		String latestVersion = PluginUpdater.getLatestRelease();

		if(latestVersion.equals(getDescription().getVersion())) {
			getConsoleLogger().console("§7You have the §alatest §7version of SwiftPluginsUpdater.");
			getConsoleLogger().console("");

		} else {
			getConsoleLogger().console("§7You have an §cold §7version of SwiftPluginsUpdater.");
			getConsoleLogger().console("§7Current version: §3v" + getDescription().getVersion() + " §7- Latest version: §3v" + latestVersion);
			getConsoleLogger().console("");
		}

		super.onEnable();
	}

	@Override
	public void onDisable() {

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
}