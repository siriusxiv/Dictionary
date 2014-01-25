package dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class Dico {
	HashMap<String,Word> wordsTrad;
	HashMap<String,Word> wordsSimp;
	Set<String> setTrad;
	Set<String> setSimp;
	
	Dico() throws IOException{
		wordsTrad=new HashMap<String,Word>();
		wordsSimp=new HashMap<String,Word>();
		InputStreamReader reader = new InputStreamReader(new FileInputStream("cedict_ts.u8"),"UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String line;
		while((line=br.readLine())!=null){
			if(line.charAt(0)!='#'){
				int i = line.indexOf(' ');
				int j = line.indexOf(' ',i+1);
				String trad = line.substring(0, i);
				Word w = new Word(line);
				wordsTrad.put(trad, w);
				String simp = line.substring(i+1, j);
				wordsSimp.put(simp, w);
			}
		}
		br.close();
		setTrad=wordsTrad.keySet();
		setSimp=wordsSimp.keySet();
	}
	
	ArrayList<Word> findWordsMatching(String filter){
		ArrayList<Word> result = new ArrayList<Word>();
		for(String word : this.setSimp){
			if(LookUp.match(word,filter))
				result.add(wordsSimp.get(word));
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException{
		long tic = Calendar.getInstance().getTimeInMillis();
		Dico dic = new Dico();
		long tac = Calendar.getInstance().getTimeInMillis();
		System.out.println("Dico loaded in "+(tac-tic)+"ms");
		System.out.println("Type your search:");
		while(true){
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String filter = bufferRead.readLine();
			if(filter.equals("q"))
				break;
			ArrayList<Word> words = dic.findWordsMatching(filter);
			for(Word w : words){
				System.out.println(w);
			}
		}
		
	}
}
