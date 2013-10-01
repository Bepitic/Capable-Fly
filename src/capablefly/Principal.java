package capablefly;

///import org.bukkit.Bukkit;
import java.io.File;

import capablefly.ListenerCheckOnlogin;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import capablefly.checkTask;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;


import org.bukkit.plugin.RegisteredServiceProvider;
//import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Principal extends JavaPlugin {
	
    public static Economy econ = null;
    
	
	public File configFile = new File(this.getDataFolder(), "config.yml");
	
//onEnable se usa cuando el plugin se carga correctamente en bukkit
@Override
public void onEnable() {
	
	if (!setupEconomy() ) {
		getLogger().severe(String.format("[%s] - No Vault ( Economy ) dependency found!", getDescription().getName()));
		getConfig().set("options.ExtendsFlyTime.Activate", false);
		getConfig().set("options.ReducesFlyTime.Activate", false);
		saveConfig();
        getServer().getPluginManager().disablePlugin(this);
        return;
    }
	
	
	saveDefaultConfig();
	
	getLogger().info(getConfig().getString("translate.enablePlugin"));
	long minutestoreload = (60*20*(getConfig().getLong("options.minutesToReload")));

		getServer().getPluginManager().registerEvents(new ListenerCheckOnlogin(this), this);
       BukkitTask checktask = new checkTask(this).runTaskTimer(this, 20, minutestoreload );
      
	
	
	getCommand("cfly").setExecutor(new gfly(this, econ));
	
	
	// TODO Auto-generated method stub
	super.onEnable();
}


//onEnable se usa cuando el plugin se desactiva en bukkit,
// ya sea manualmente o por el metodo stop ( que para el server).
@Override
	public void onDisable() {
	getLogger().info(getConfig().getString("translate.disableplugin"));
	// TODO Auto-generated method stub
		super.onDisable();
	}

private boolean setupEconomy() {
	
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
        return false;
    }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
        return false;
    }
    econ = rsp.getProvider();
    return econ != null;
}

}
