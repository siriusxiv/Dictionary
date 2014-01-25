package dictionary;

import java.util.ArrayList;

public class Word {
	String trad;
	String simp;
	String pinyin;
	ArrayList<String> defs;
	
	Word(String line){
		//System.out.println(line);
		int i = line.indexOf(' ');
		trad = line.substring(0, i);
		//System.out.println(trad);
		int j = line.indexOf(' ', i+1);
		simp = line.substring(i+1, j);
		//System.out.println(simp);
		int k = line.indexOf('[',j+1);
		int l = line.indexOf(']',k+1);
		pinyin = line.substring(k+1, l);
		//System.out.println(pinyin);
		int m = line.indexOf('/',l+1);
		int n = line.indexOf('/',m+1);
		defs = new ArrayList<String>();
		while(n!=-1){
			//System.out.println(line.substring(m+1,n));
			defs.add(line.substring(m+1,n));
			m=n;
			n=line.indexOf('/',m+1);
		}
	}
	
	@Override
	public String toString(){
		String definitions="/";
		for(String s : defs){
			definitions+=s+"/";
		}
		return trad+" "+simp+" "+"["+pinyin+"]"+definitions;
	}
}
