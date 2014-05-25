package com.drtshock.playervaults.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drtshock.playervaults.human.Human;
import com.drtshock.playervaults.util.Lang;

public class NPCCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("playervaults.npc.create")) {
				sender.sendMessage(Lang.TITLE.toString() + ChatColor.RED
						+ "No permission!");
				return true;
			}
			String name = "Villager";
			if (args.length > 0) {
				name = "";
				for (String string : args) {
					name += string + " ";
				}
			}
			@SuppressWarnings("unused")
			Human human = new Human(player.getWorld(), name,
					new Random().nextInt(100000), player.getLocation(), 54);
			player.sendMessage(Lang.TITLE.toString() + ChatColor.DARK_GREEN
					+ "NPC created!");
		} else {
			sender.sendMessage(Lang.TITLE.toString() + ChatColor.RED
					+ Lang.PLAYER_ONLY);
		}
		return true;
	}
}