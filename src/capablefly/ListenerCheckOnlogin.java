package capablefly;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitTask;

public class ListenerCheckOnlogin implements Listener {
	private final Principal plugin;
	public ListenerCheckOnlogin( Principal plugin ){
		this.plugin = plugin;
	}
	@EventHandler
    public void normalLogin( PlayerLoginEvent event ) {
		
		//Plugin plugin;
		BukkitTask checktask = new checkoneusertask( plugin , event.getPlayer() ).runTask( plugin );

    }

}
