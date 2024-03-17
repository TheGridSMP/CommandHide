package the.grid.smp.commandhide.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.TabCompleteEvent;
import the.grid.smp.commandhide.CommandHide;
import the.grid.smp.commandhide.data.Command;
import the.grid.smp.commandhide.data.Group;
import the.grid.smp.commandhide.data.Operation;

import java.util.ArrayList;
import java.util.List;

public class TabListener implements Listener {

    private final CommandHide plugin;

    public TabListener(CommandHide plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTab(TabCompleteEvent event) {
        if (!(event.getSender() instanceof Player))
            return;

        Group group = this.plugin.config().getGroup(event.getSender());
        List<String> completions = new ArrayList<>(event.getCompletions());

        System.out.println(completions);

        for (Command command : group.commands()) {
            if (command.context().equals("*") && command.operation() == Operation.SUBTRACT) {
                completions.clear();
                break;
            }
        }

        for (Command command : group.commands()) {
            String slashed = "/" + command.context();

            if (command.operation() == Operation.SUBTRACT)
                completions.removeIf(s -> s.startsWith(slashed));
            else
                completions.add(slashed);
        }

        event.setCompletions(completions);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Group group = this.plugin.config().getGroup(event.getPlayer());
        boolean disallowed = false;

        for (Command command : group.commands()) {
            if (command.context().equals("*") && command.operation() == Operation.SUBTRACT) {
                disallowed = true;
                break;
            }
        }

        for (Command command : group.commands()) {
            String slashed = "/" + command.context();
            boolean matches = event.getMessage().startsWith(slashed);

            if (!matches)
                continue;

            if (disallowed && command.operation() == Operation.ADD) {
                disallowed = false;
                break;
            } else if (!disallowed && command.operation() == Operation.SUBTRACT) {
                disallowed = true;
                break;
            }
        }

        event.setCancelled(disallowed);
    }
}
