package the.grid.smp.commandhide;

import org.bukkit.plugin.java.JavaPlugin;
import the.grid.smp.commandhide.config.CHConfig;
import the.grid.smp.commandhide.listener.TabListener;

public final class CommandHide extends JavaPlugin {

    private CHConfig config;

    @Override
    public void onEnable() {
        this.config = new CHConfig(this);
        this.getServer().getPluginManager().registerEvents(new TabListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public CHConfig config() {
        return this.config;
    }
}
