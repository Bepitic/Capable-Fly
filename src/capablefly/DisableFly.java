package capablefly;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DisableFly extends BukkitRunnable  {
	
	private Player p;
	public DisableFly(Player player){
		this.p = player;
	}
	
	
	@Override
	public void run() {
		p.setAllowFlight(false);
		
	}

}
