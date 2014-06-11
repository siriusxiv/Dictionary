package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import dictionary.Dico;
import dictionary.Word;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	public static Dico dic_common_name_only = null;
	public static Dico dic_all = null;

	public static Result index() {
		if(dic_all==null)
			try{
				long tic = Calendar.getInstance().getTimeInMillis();
				dic_all = new Dico(true);
				dic_common_name_only = new Dico(false);
				long tac = Calendar.getInstance().getTimeInMillis();
				System.out.println("Dico loaded in "+(tac-tic)+"ms");
			}catch(IOException e){
				e.printStackTrace();
			}
		return ok(index.render("", new ArrayList<Word>(),"", false));
	}

	public static Result search(){
		long tic = Calendar.getInstance().getTimeInMillis();
		DynamicForm info = Form.form().bindFromRequest();
		String filter = info.get("filter").toLowerCase();
		boolean noProperName = info.get("proper") != null;
		ArrayList<Word> words;
		if(isChinese(filter))
			if(noProperName)
				words = dic_common_name_only.findWordsMatching(filter);
			else
				words = dic_all.findWordsMatching(filter);
		else
			if(noProperName)
				words = dic_common_name_only.findWordsMatchingEnglish(filter);
			else
				words = dic_all.findWordsMatchingEnglish(filter);
		System.out.println(filter);
		long tac = Calendar.getInstance().getTimeInMillis();
		return ok(index.render(filter,words,new Long(tac-tic)+"ms",noProperName));
	}

	private static boolean isChinese(String filter) {
		String any = "abcdefghijklmnopqrstuvwxyz ,()!?;:";
		for(int i = 0; i<filter.length() ; i++){
			for(int j = 0; j<any.length() ; j++){
				if(any.charAt(j)==filter.charAt(i))
					return false;
			}
		}
		return true;
	}

}
