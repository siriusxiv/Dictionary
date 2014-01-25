package dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;

public class Dico {
	HashMap<String,Word> words;

	Dico() throws IOException{
		words=new HashMap<String,Word>();
		InputStreamReader reader = new InputStreamReader(new FileInputStream("cedict_ts.u8"),"UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String line;
		while((line=br.readLine())!=null){
			if(line.charAt(0)!='#'){
				String trad = line.substring(0, line.indexOf(' '));
				words.put(trad, new Word(line));
			}
		}
		br.close();
	}

	public static void main(String[] args) throws IOException{
		long tic = Calendar.getInstance().getTimeInMillis();
		Dico dic = new Dico();
		long tac = Calendar.getInstance().getTimeInMillis();
		System.out.println("Dico loaded in "+(tac-tic)+"ms");
	}
}
