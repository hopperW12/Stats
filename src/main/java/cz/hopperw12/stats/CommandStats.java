package cz.hopperw12.stats;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandStats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] label) {

        if (!(sender instanceof Player))
            return false;

        Player player = (Player) sender;

        HashMap<String, Integer> players = new HashMap<>();

        for (OfflinePlayer offlinePlayer : Bukkit.getWhitelistedPlayers())
            players.put(offlinePlayer.getName(), offlinePlayer.getStatistic(Statistic.PLAY_ONE_MINUTE));

        sortByComparator(players, false).forEach((key, value) ->
                player.sendMessage(String.format("%s - %s", key, convert(value))));

        return false;
    }

    private String convert(long ticks) {
        long seconds = ticks/20;
        long minutes = 0;
        while(seconds > 60) {
            seconds-=60;
            minutes++;
        }
        long hours = 0;
        while(minutes > 60) {
            minutes-=60;
            hours++;
        }
        if (minutes > 0) {
            if (hours > 0) {
                return "§e" + hours + " Hodin " + minutes + " Minut " + seconds + " Sekund";
            } else {
                return "§e" + minutes + " Minut " + seconds + " Sekund";
            }
        } else {
            return "§e" + seconds + " Sekund";
        }
    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, (o1, o2) -> {
            if (order) {
                return o1.getValue().compareTo(o2.getValue());
            } else {
                return o2.getValue().compareTo(o1.getValue());

            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
