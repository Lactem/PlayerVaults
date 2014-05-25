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

public class SetVaultCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("playervaults.npc.vault")) {
				sender.sendMessage(Lang.TITLE.toString() + ChatColor.RED
						+ "No permission!");
				return true;
			}
			if (args.length < 1) {
				player.sendMessage(Lang.TITLE.toString() + ChatColor.RED
						+ "Incorrect usage! " + ChatColor.GREEN
						+ "/pvnpcset <number>");
				return true;
			}
			int number = 1;
			try {
				number = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				player.sendMessage(Lang.TITLE.toString() + ChatColor.RED
						+ "You didn't give a number. " + ChatColor.GREEN
						+ "/pvnpcset <number>");
				return true;
			}
			Human human = getHumanInSight(player);
			if (human == null) {
				player.sendMessage(Lang.TITLE.toString() + ChatColor.RED
						+ "You're not looking at an NPC.");
				return true;
			}
			Human.humans.remove(human);
			human.setNumber(number);
			Human.humans.add(human);
			player.sendMessage(Lang.TITLE.toString() + ChatColor.DARK_GREEN
					+ "Vault number set.");
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