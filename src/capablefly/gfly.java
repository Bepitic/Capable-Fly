package capablefly;

import capablefly.Principal;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;


public class gfly implements CommandExecutor {
	private Principal plugin;
	public static Economy econ = null;
	public gfly(Principal plugin,Economy econ ){
		this.plugin = plugin;
		this.econ = econ;
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
		minutos = ((minutesCantFly -(minOfYear - min)) - (horas * 60) - (dias * 1440)  );




		isfling = plugin.getConfig().getBoolean( "users.isfling." + player.getDisplayName() );

		if(args.length == 0){

			if (!sender.hasPermission("capablefly.cfly")) {
				sender.sendMessage( ChatColor.DARK_RED + plugin.getConfig().getString("translate.notPermission") );
				return true;
			}



			if(canfly){
				if(settimefly || plugin.getConfig().getInt("users.minuteOfYear." + player.getDisplayName()) == 0 )
				{
					plugin.getConfig().set("users.minuteOfYear." + player.getDisplayName() , minOfYear);
					plugin.getConfig().set("users.settimefly." + player.getDisplayName(), false);

				}



				if(!(isfling)){
					player.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("translate.onCmdcflyOn"));
					plugin.getConfig().set("users.isfling." + player.getDisplayName() , true);
					player.setAllowFlight(true);
					plugin.saveConfig();


				}else{
					player.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("translate.onCmdcflyOff"));
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
					sender.sendMessage( ChatColor.DARK_RED + plugin.getConfig().getString("translate.notPermision"));
					return true;
				}

				BukkitTask checktask = new checkoneusertask(plugin,player).runTask(plugin);


				if( (minOfYear - min) < minutesCanFly )
				{	


					canf = plugin.getConfig().getString("translate.onCmdcflyhelp");
					if(fdias != 0)
					{
						canf = canf + " " + fdias  + " " + plugin.getConfig().getString("translate.onCmdcflyhelp3");	
					}
					if(fhoras != 0)
					{
						canf = canf + " " + fhoras  + " " + plugin.getConfig().getString("translate.onCmdcflyhelp2");	
					}
					if(fminutos != 0)
					{
						canf = canf + " " + fminutos  + " " + plugin.getConfig().getString("translate.onCmdcflyhelp1");	
					}

					canf = canf + " " + plugin.getConfig().getString("translate.onCmdcflyhelp4");
					player.sendMessage(ChatColor.RED + canf	 );

					if(plugin.getConfig().getBoolean("options.ExtendsFlyTime.Activate"))
					{
						player.sendMessage(ChatColor.RED + "/cfly buy  -> " + plugin.getConfig().getInt("options.ExtendsFlyTime.Time") + " " + plugin.getConfig().getString("translate.onCmdcflyhelp1") + " <-> " + plugin.getConfig().getInt("options.ExtendsFlyTime.Price") + " " + plugin.getConfig().getString("translate.onCmdcflyBuy") );
					}else{
						player.sendMessage("asdf2");
					}




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

					player.sendMessage( ChatColor.RED + cantf );


					if(plugin.getConfig().getBoolean("options.ReducesDenyTime.Activate"))
					{
						player.sendMessage(ChatColor.RED + "/cfly buy  -> " + plugin.getConfig().getInt("options.ReducesDenyTime.Time") + " " + plugin.getConfig().getString("translate.onCmdcflyhelp1") + " <-> " + plugin.getConfig().getInt("options.ReducesDenyTime.Price") + " " + plugin.getConfig().getString("translate.onCmdcflyBuy") );
					}else{
						player.sendMessage("asdf");
					}


				}
			}else if(args[0].equalsIgnoreCase("reload")){
				if (!sender.hasPermission("capablefly.cflyreload")) {
					sender.sendMessage(  ChatColor.DARK_RED + plugin.getConfig().getString("translate.notPermision"));
					return true;
				}
				plugin.reloadConfig();


			}else if(args[0].equalsIgnoreCase("Buy")){
				if (!sender.hasPermission("capablefly.buy")) {
					sender.sendMessage(  ChatColor.DARK_RED + plugin.getConfig().getString("translate.notPermision"));
					return true;
				}

				if( (minOfYear - min) < minutesCanFly )
				{	
					if(plugin.getConfig().getBoolean("options.ExtendsFlyTime.Activate"))
					{
						if(econ.getBalance(player.getName()) >= plugin.getConfig().getInt("options.ExtendsFlyTime.Price") )
						{
							econ.bankWithdraw(player.getName(), plugin.getConfig().getInt("options.ExtendsFlyTime.Price"));
							
							AmpliarTiempo(player,  plugin.getConfig().getInt("options.ExtendsFlyTime.Price"), true);

						}
					}
				}else
				{
					if(plugin.getConfig().getBoolean("options.ReducesDenyTime.Activate"))
					{
						if(econ.getBalance(player.getName()) >= plugin.getConfig().getInt("options.ReducesDenyTime.Price") )
						{
							econ.bankWithdraw(player.getName(), plugin.getConfig().getInt("options.ReducesDenyTime.Price"));
							
							AmpliarTiempo(player,  plugin.getConfig().getInt("options.ReducesDenyTime.Price"), false);
							
						}
					}

				}

			}

			return true;	
		}


		// TODO Auto-generated method stub
		return false;
	}
	
	private void AmpliarTiempo(Player plyr, int tiempo, boolean a)
	{
		
		if(a)
		{
		plugin.getConfig().set("users.minuteOfYear."+plyr.getName(), plugin.getConfig().getInt("users.minuteOfYear."+plyr.getName()) + tiempo ) ;
		}else
		{
			plugin.getConfig().set("users.minuteOfYear."+plyr.getName(), plugin.getConfig().getInt("users.minuteOfYear."+plyr.getName()) - tiempo ) ;
		}
	}

}
