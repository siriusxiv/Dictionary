package dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class Dico {
	HashMap<String,Word> wordsTrad;
	HashMap<String,Word> wordsSimp;
	Set<String> setTrad;
	Set<String> setSimp;

	private static String dicoURL = "http://dico.herokuapp.com/assets/cedict_ts.u8";
	private static String LOCAL_DICO = "dico";

	public Dico(boolean withProperName) throws IOException{
		wordsTrad=new HashMap<String,Word>();
		wordsSimp=new HashMap<String,Word>();
		
		ReadableByteChannel in=Channels.newChannel(new URL(dicoURL).openStream());
		FileOutputStream fos = new FileOutputStream(LOCAL_DICO);
		FileChannel out = fos.getChannel();
		out.transferFrom(in, 0, Long.MAX_VALUE);
		fos.close();
		
		InputStreamReader reader = new InputStreamReader(new FileInputStream(LOCAL_DICO),"UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String line;
		while((line=br.readLine())!=null){
			if(line.charAt(0)!='#'){
				int i = line.indexOf(' ');
				int j = line.indexOf(' ',i+1);
				String trad = line.substring(0, i);
				Word w = new Word(line);
				if(withProperName || !w.properName){
					wordsTrad.put(trad, w);
					String simp = line.substring(i+1, j);
					wordsSimp.put(simp, w);
				}
			}
		}
		br.close();
		setTrad=wordsTrad.keySet();
		setSimp=wordsSimp.keySet();
	}

	public ArrayList<Word> findWordsMatching(String filter){
		ArrayList<Word> result = new ArrayList<Word>();
		for(String word : this.setSimp){
			if(LookUp.match(word,filter))
				result.add(wordsSimp.get(word));
		}
		return result;
	}

	public static void main(String[] args) throws IOException{
		long tic = Calendar.getInstance().getTimeInMillis();
		Dico dic = new Dico(true);
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

	public ArrayList<Word> findWordsMatchingEnglish(String filter) {
		ArrayList<Word> result = new ArrayList<Word>();
		for(String word : this.setSimp){
			if(LookUp.containsEnglish(this,word,filter))
				result.add(wordsSimp.get(word));
		}
		return result;
	}
}
