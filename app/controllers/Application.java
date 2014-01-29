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
	public static Dico dic = null;

	public static Result index() {
		if(dic==null)
			try{
				long tic = Calendar.getInstance().getTimeInMillis();
				dic = new Dico();
				long tac = Calendar.getInstance().getTimeInMillis();
				System.out.println("Dico loaded in "+(tac-tic)+"ms");
			}catch(IOException e){
				e.printStackTrace();
			}
		return ok(index.render("", new ArrayList<Word>()));
	}

	public static Result search(){
		DynamicForm info = Form.form().bindFromRequest();
		String filter = info.get("filter").toLowerCase();
		ArrayList<Word> words;
		if(isChinese(filter))
			words = dic.findWordsMatching(filter);
		else
			words = dic.findWordsMatchingEnglish(filter);
		System.out.println(filter);
		return ok(index.render(filter,words));
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
