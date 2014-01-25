package controllers;

import java.util.ArrayList;

import dictionary.Dico;
import dictionary.Word;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	public static Dico dic;
	
    public static Result index() {
        return ok(index.render("", new ArrayList<Word>()));
    }
    
    public static Result search(){
    	DynamicForm info = Form.form().bindFromRequest();
    	String filter = info.get("filter");
    	ArrayList<Word> words = dic.findWordsMatching(filter);
    	System.out.println(filter);
    	return ok(index.render(filter,words));
    }

}
