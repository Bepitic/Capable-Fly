package capablefly;

import capablefly.Principal;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;


public class gfly implements CommandExecutor {
	private Principal plugin;
	public gfly(Principal plugin){
		this.plugin = plugin;
	}
   
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		 
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		final Calendar cal = new GregorianCalendar();
		final int minOfYear = cal.get(Calendar.MINUTE) + ((cal.get(Calendar.HOUR_OF_DAY) + (cal.get(Calendar.DAY_OF_YEAR) * 24))*60);
		int minutesCanFly = plugin.getConfig().getInt("options.MinutesToAllowFly");
		int minutesCantFly = plugin.getConfig().getInt("options.MinutesToDenyFly") + minutesCanFly ;
		int dias,horas,minutos;
		int fdias,fhoras,fminutos;
		boolean canfly = plugin.getConfig().getBoolean( "users.canfly." + player.getDisplayName() ); 
		boolean isfling = plugin.getConfig().getBoolean( "users.isfling." + player.getDisplayName() );
		boolean settimefly = plugin.getConfig().getBoolean( "users.settimefly." + player.getDisplayName() );
		int min;
		String canf,cantf; 
		min = plugin.getConfig().getInt("users.minuteOfYear." + player.getDisplayName());				
		fdias = ( minutesCanFly-(minOfYear - min) )/ 1440;
		fhoras = (( minutesCanFly-(minOfYear - min)) - (fdias * 1440))/60;
		fminutos = (( minutesCanFly-(minOfYear - min) ) - (fhoras * 60)- (fdias * 1440)  );	
		dias = (minutesCantFly -(minOfYear - min) )/ 1440;
		horas = ((minutesCantFly -(minOfYear - min)) - (dias * 1440))/60;
		minutos = ((minutesCantFly -(minOfYear - min)) - (horas * 60)- (dias * 1440)  );
		
		
		 
		 
		 isfling = plugin.getConfig().getBoolean( "users.isfling." + player.getDisplayName() );
		 
		if(args.length == 0){
			
			if (!sender.hasPermission("capablefly.cfly")) {
	             sender.sendMessage(plugin.getConfig().getString("translate.notPermission"));
	             return true;
	         }
			
			
			if(canfly){
				if(settimefly || plugin.getConfig().getInt("users.minuteOfYear." + player.getDisplayName()) == 0 )
				{
					plugin.getConfig().set("users.minuteOfYear." + player.getDisplayName() , minOfYear);
					plugin.getConfig().set("users.settimefly." + player.getDisplayName(), false);
					
				}
				
				 
				
			if(!(isfling)){
				player.sendMessage(""+ plugin.getConfig().getString("translate.onCmdcflyOn"));
				plugin.getConfig().set("users.isfling." + player.getDisplayName() , true);
				player.setAllowFlight(true);
				plugin.saveConfig();
			
			
			}else{
				player.sendMessage(""+ plugin.getConfig().getString("translate.onCmdcflyOff"));
				plugin.getConfig().set("users.isfling." + player.getDisplayName() , false);
				player.setAllowFlight(false);
				plugin.saveConfig();
			}
			
			}
			
		return true;
		
		}else if(args.length == 1 )
		{
			if( args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?") ){
				
				
				
				if (!sender.hasPermission("capablefly.cflyhelp")) {
		             sender.sendMessage(plugin.getConfig().getString("translate.notPermision"));
		             
		             return true;
		         }
				
				
				
				BukkitTask checktask = new checkoneusertask(plugin,player).runTask(plugin);
				
				
				if( (minOfYear - min) < minutesCanFly )
				{	
					
					
					canf = plugin.getConfig().getString("translate.onCmdcflyhelp");
					if(fdias != 0)
					{
					canf = canf + " " + fdias + plugin.getConfig().getString("translate.onCmdcflyhelp3");	
					}
					if(fhoras != 0)
					{
					canf = canf + " " + fhoras + plugin.getConfig().getString("translate.onCmdcflyhelp2");	
					}
					if(fminutos != 0)
					{
					canf = canf + " " + fminutos + plugin.getConfig().getString("translate.onCmdcflyhelp1");	
					}
					
					canf = canf + " " + plugin.getConfig().getString("translate.onCmdcflyhelp4");
					
					
					
					player.sendMessage(""+ canf	 );
				}
				else{
					
					cantf = plugin.getConfig().getString("translate.onCmdcflyhelp");
					if( dias != 0)
					{
						cantf = cantf + " " + dias + " " + plugin.getConfig().getString("translate.onCmdcflyhelp3");	
					}
					if( horas != 0)
					{
						cantf = cantf + " " + horas + " " + plugin.getConfig().getString("translate.onCmdcflyhelp2");	
					}
					
					if( minutos != 0 )
					{
						cantf = cantf + " " + minutos + " " + plugin.getConfig().getString("translate.onCmdcflyhelp1");	
					}
					cantf = cantf + " " + plugin.getConfig().getString("translate.onCmdcflyhelp5");
					
					
					
					player.sendMessage(""+ cantf );
							
				}
			}else if(args[0].equalsIgnoreCase("reload")){
				if (!sender.hasPermission("capablefly.cflyreload")) {
		             sender.sendMessage(plugin.getConfig().getString("translate.notPermision"));
		             return true;
		         }
					plugin.reloadConfig();
				}
		
			return true;	
		}
		
		
		// TODO Auto-generated method stub
		return false;
	}

}
