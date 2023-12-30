package fr.snipertvmc.swiftpluginsupdater.utilities;

import org.bukkit.Bukkit;

public class ConsoleLogger {


	public void console(String infoMessage) {
		Bukkit.getConsoleSender().sendMessage("§8§l[§6SwiftPluginsUpdater§8§l]§7 " + infoMessage);
	}


	public void info(String infoMessage) {
		Bukkit.getLogger().info("[SwiftPluginsUpdater] " + infoMessage);
	}


	public void warning(String infoMessage) {
		Bukkit.getLogger().warning("[SwiftPluginsUpdater] " + infoMessage);
	}


	public void error(String infoMessage) {
		Bukkit.getLogger().severe("[SwiftPluginsUpdater] " + infoMessage);
	}
}
