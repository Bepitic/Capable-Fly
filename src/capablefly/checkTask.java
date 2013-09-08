package capablefly;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import capablefly.Principal;

public class checkTask extends BukkitRunnable{
	
	private final Principal plugin;
	
	public checkTask(Principal plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {

    	for (Player p : Bukkit.getOnlinePlayers()) {
           
    		BukkitTask checktask = new checkoneusertask( plugin , p ).runTask( plugin );
    		
        }

    	plugin.saveConfig();
    
		
	}

}
