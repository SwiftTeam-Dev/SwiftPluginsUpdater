package fr.snipertvmc.swiftpluginsupdater.manager;

import fr.snipertvmc.swiftpluginsupdater.Main;
import fr.snipertvmc.swiftpluginsupdater.utilities.PluginUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdatesManager {


	public static void afterRestartSetup(List<String> pluginList) {

		for (String pluginName : pluginList) {

			if (RessourcesDatabaseManager.getRessourceList().contains(pluginName)) {
				String rawSourcePath = Main.getInstance().getDataFolder() + "/temp/" + pluginName + ".jar";
				Path sourcePath = Paths.get(rawSourcePath);

				String rawDestinationPath = Main.getInstance().getDataFolder().toString();
				Path destinationPath = Paths.get(rawDestinationPath);

				try {
					if (Files.exists(sourcePath)) {
						Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
					}

				} catch (Exception e) {
					Main.getConsoleLogger().error(e.getMessage());
					Main.getConsoleLogger().error("");
				}

			} else {
				Main.getConsoleLogger().error("Le plugin " + pluginName + " n'est pas présent dans les données du plugin.");
				Main.getConsoleLogger().error("");
			}
		}
	}


	public static void checkRessourcesUpdate() {

		Main.getConsoleLogger().info("§7Analyse des plugins...");
		Main.getConsoleLogger().info("");

		List<Plugin> pluginsList = Arrays.asList(Bukkit.getPluginManager().getPlugins());
		List<String> ressourcesToUpdate = new ArrayList<>();

		for (Plugin plugin : pluginsList) {
			if (isUpToDate(plugin, RessourcesDatabaseManager.getRessourceID(plugin.getName()))) {
				ressourcesToUpdate.add(plugin.getName());
			}
		}

		if (ressourcesToUpdate.isEmpty()) {
			Main.getConsoleLogger().info("§a§lBravo! §7Tous vos plugins sont à jour.");
			Main.getConsoleLogger().info("");

		} else {
			Main.getConsoleLogger().info("§c§lOups! §7Nous avons trouvé §6" + ressourcesToUpdate.size() + " plugins à mettre à jour.");
			Main.getConsoleLogger().info("");

			Main.getConsoleLogger().info("§7Mise à jour des plugins...");
			updateRessources(ressourcesToUpdate);
		}
	}


	private static void updateRessources(List<String> ressourcesList) {

		PluginListDataManager pluginListDataManager = new PluginListDataManager();

		for (String ressourceName : ressourcesList) {

			int ressourceID = RessourcesDatabaseManager.getRessourceID(ressourceName);
			String ressourceNotUpdatedVersion = Bukkit.getPluginManager().getPlugin(ressourceName).getDescription().getVersion();

			if(PluginUpdater.downloadLatestRelease(ressourceName, ressourceID)) {
				String ressourceLatestRelease = PluginUpdater.getLatestRelease(ressourceName, ressourceID);
				pluginListDataManager.addPluginName(ressourceName);
				Main.getConsoleLogger().info("§7- §6" + ressourceName + "§7: §c" + ressourceNotUpdatedVersion + " §7» §a" + ressourceLatestRelease);

			} else {
				Main.getConsoleLogger().info("§7- §6" + ressourceName + "§7: §cError while updating... §7Preserved version: §e" + ressourceNotUpdatedVersion);
			}
		}
	}


	private static boolean isUpToDate(Plugin plugin, int ressourceID) {
		String versionInstalled = plugin.getDescription().getVersion();
		String latestVersion = PluginUpdater.getLatestRelease(plugin.getName(), ressourceID);
		return versionInstalled.equals(latestVersion);
	}
}
