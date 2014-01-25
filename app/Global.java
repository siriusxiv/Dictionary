import java.util.Calendar;
import java.io.IOException;

import play.Application;
import play.GlobalSettings;

import dictionary.Dico;

public class Global extends GlobalSettings{
	
	@Override
	public void onStart(Application app){
		try{
			long tic = Calendar.getInstance().getTimeInMillis();
			controllers.Application.dic = new Dico();
			long tac = Calendar.getInstance().getTimeInMillis();
			System.out.println("Dico loaded in "+(tac-tic)+"ms");
		}catch(IOException e){
			e.printStackTrace();
		}
	} 
}
