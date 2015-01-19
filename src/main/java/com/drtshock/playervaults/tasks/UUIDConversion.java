package com.drtshock.playervaults.tasks;

import com.drtshock.playervaults.PlayerVaults;
import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Class to convert vaults by name to vaults by UUID.
 */
public final class UUIDConversion extends BukkitRunnable {
    @Override
    public void run() {
        File newDir = PlayerVaults.getInstance().getVaultData();
        if (newDir.exists()) {
            PlayerVaults.getInstance().getLogger().log(Level.INFO, "Files already converted to UUID.");
            return;
        }

        newDir.mkdirs();

        PlayerVaults.getInstance().getLogger().log(Level.INFO, "********** Starting PlayerVault conversion to UUIDs **********");
        PlayerVaults.getInstance().getLogger().log(Level.INFO, "This might take awhile.");
        PlayerVaults.getInstance().getLogger().log(Level.INFO, "plugins/PlayerVaults/vaults will remain as a backup.");

        File vaultFolder = new File(PlayerVaults.getInstance().getDataFolder() + File.separator + "vaults");
        if (!vaultFolder.exists()) {
            PlayerVaults.getInstance().getLogger().log(Level.INFO, "********** Conversion done - nothing to convert ;D **********");
        	return;
        }
        
        for (File file : vaultFolder.listFiles()) {
            if (file.isDirectory()) continue; // backups folder.
            OfflinePlayer player = Bukkit.getOfflinePlayer(file.getName().replace(".yml", ""));
            if (player == null) {
                PlayerVaults.getInstance().getLogger().log(Level.WARNING, "Unable to convert file because player never joined the server: " + file.getName());
                break;
            }

            File newFile = new File(PlayerVaults.getInstance().getVaultData(), player.getUniqueId().toString() + ".yml");
            file.mkdirs();
            try {
                Files.copy(file, newFile);
                PlayerVaults.getInstance().getLogger().log(Level.INFO, "Successfully converted vault file for " + player.getName());
            } catch (IOException e) {
                PlayerVaults.getInstance().getLogger().log(Level.SEVERE, "Couldn't convert vault file for " + player.getName());
            }
        }

        PlayerVaults.getInstance().getLogger().log(Level.INFO, "********** Conversion done ;D **********");
    }
}
