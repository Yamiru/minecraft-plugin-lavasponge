package me.yamiru.lavasponge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class LavaSponge extends JavaPlugin {

    private static final String GITHUB_URL = "https://github.com/Yamiru/minecraft-plugin-lavasponge";
    private SpongeListener spongeListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        spongeListener = new SpongeListener(this);
        getServer().getPluginManager().registerEvents(spongeListener, this);
        getLogger().info("LavaSponge enabled! Version: " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        getLogger().info("LavaSponge disabled!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("lavasponge.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§eUsage: /" + label + " <reload|info>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                reloadConfig();
                spongeListener.reloadSettings();
                sender.sendMessage("§aLavaSponge configuration reloaded!");
                break;

            case "info":
                sendInfo(sender);
                break;

            default:
                sender.sendMessage("§eUsage: /" + label + " <reload|info>");
                break;
        }

        return true;
    }

    private void sendInfo(CommandSender sender) {
        sender.sendMessage("§6§l⚡ LavaSponge §r§6v" + getDescription().getVersion());
        sender.sendMessage("§7Author: §fYamiru");
        sender.sendMessage("§7Description: §fAbsorb lava with sponges");
        sender.sendMessage("§7GitHub: §b" + GITHUB_URL);
        sender.sendMessage("§7Commands: §f/lavasponge <reload|info>");
        sender.sendMessage("§7Permissions: §flavasponge.use, lavasponge.admin");
    }
}
