package com.drtshock.playervaults.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drtshock.playervaults.human.Human;
import com.drtshock.playervaults.util.Lang;

public class DeleteNPCCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("playervaults.npc.delete")) {
				sender.sendMessage(Lang.TITLE.toString() + ChatColor.RED
						+ "No permission!");
				return true;
			}
			Human human = getHumanInSight(player);
			if (human == null) {
				player.sendMessage(Lang.TITLE.toString() + ChatColor.RED
						+ "You're not looking at an NPC.");
				return true;
			}
			human.remove();
			player.sendMessage(Lang.TITLE.toString() + ChatColor.RED
					+ "NPC deleted.");
		} else {
			sender.sendMessage(Lang.TITLE.toString() + ChatColor.RED
					+ Lang.PLAYER_ONLY);
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	private Human getHumanInSight(Player player) {
		Location eyeLoc = null;
		List<Block> locs = player.getLineOfSight(null, 4);
		for (int i = 0; i < Human.humans.size(); i++) {
			Human human = Human.humans.get(i);
			Location loc = human.getLocation();
			for (Block block : locs) {
				eyeLoc = block.getLocation();
				if (loc.getBlockX() == eyeLoc.getBlockX()
						&& loc.getBlockY() == eyeLoc.getBlockY()
						&& loc.getBlockZ() == eyeLoc.getBlockZ())
					return human;
			}
		}
		return null;
	}
}