package the.grid.smp.commandhide.config;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import the.grid.smp.commandhide.data.Command;
import the.grid.smp.commandhide.data.Group;
import the.grid.smp.communis.config.Config;

import java.util.*;

public class CHConfig extends Config {

    private Map<String, Group> groups;

    public CHConfig(Plugin plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public void read(ConfigurationSection section) {
        this.groups = new HashMap<>();

        ConfigurationSection groupNames = section.getConfigurationSection("groups");

        for (String groupName : groupNames.getKeys(false)) {
            ConfigurationSection groupSection = groupNames.getConfigurationSection(groupName);

            Set<String> inherits = new HashSet<>(groupSection.getStringList("command"));
            List<Command> commands = new ArrayList<>();
            int priority = groupSection.getInt("priority");

            for (String cmd : groupSection.getStringList("command")) {
                commands.add(Command.parse(priority, cmd));
            }

            this.groups.put(groupName, new Group(groupName, priority, commands, inherits));
        }

        for (Group group : this.groups.values()) {
            this.resolve(group);
        }
    }

    private Group resolve(Group group) {
        for (String inherits : group.inherits()) {
            Group other = this.groups.get(inherits);
            group.inherit(this.resolve(other));
        }

        return group;
    }

    @Override
    public void write(ConfigurationSection section) {

    }

    public Group getGroup(String name) {
        return this.groups.getOrDefault(name, this.groups.get("default"));
    }

    public Group getGroup(Permissible permissible) {
        Group found = null;
        for (Group group : this.groups.values()) {
            if (permissible.hasPermission("commandhide.group." + group.name())
                    && (found == null || group.priority() > found.priority())) {
                found = group;
            }
        }

        if (found == null)
            return this.groups.get("default");

        return found;
    }

    public Collection<Group> getGroups() {
        return this.groups.values();
    }
}
