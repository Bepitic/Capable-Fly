package capablefly;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import capablefly.Principal;

public class checkTask extends BukkitRunnable{
	
	private final Principal plugin;
	
	public checkTask(Principal plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		
		int minutesCanFly = plugin.getConfig().getInt("options.MinutesToAllowFly");
		int minutesCantFly = plugin.getConfig().getInt("options.MinutesToDenyFly") + minutesCanFly;
		final Calendar cal = new GregorianCalendar();
		final int minOfYear = cal.get(Calendar.MINUTE) + ((cal.get(Calendar.HOUR_OF_DAY) + (cal.get(Calendar.DAY_OF_YEAR) * 24))*60);

    	for (Player p : Bukkit.getOnlinePlayers()) {
           
    		if( (minOfYear -(plugin.getConfig().getInt("users.minuteOfYear." + p.getDisplayName()))) < minutesCanFly){
    			plugin.getConfig().set("users.canfly." + p.getDisplayName() , true);	
    		}else if( (minOfYear -(plugin.getConfig().getInt("users.minuteOfYear." + p.getDisplayName()))) >= minutesCantFly){
    			plugin.getConfig().set("users.canfly." + p.getDisplayName() , true);	
    			plugin.getConfig().set("users.settimefly." + p.getDisplayName() , true);
    		}
    		else 
    		{
    			plugin.getConfig().set("users.canfly." + p.getDisplayName() , false);
    			plugin.getConfig().set("users.isfling." + p.getDisplayName() , false);
    			p.setAllowFlight(false);
    			
    		}
    		
    		
        }

    	plugin.saveConfig();
    
		
	}

}
