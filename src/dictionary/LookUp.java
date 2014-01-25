package dictionary;

public class LookUp {
	public static boolean match(String word, String filter){
		if(word.isEmpty()){
			return filter.isEmpty() || filter.charAt(0)=='*';
		}else{
			if(filter.isEmpty())
				return false;
			else{
				if(filter.charAt(0)=='?' || filter.charAt(0)=='£¿')
					return match(word.substring(1),filter.substring(1));
				else if(filter.charAt(0)=='*'){
					int j = 1;
					while(j<filter.length() && filter.charAt(j)=='*'){
						j++;
					}
					if(j==filter.length())
						return true;
					else{
						int i = word.indexOf(filter.charAt(j));
						if(i==-1)
							return false;
						else
							return match(word.substring(i+1),filter.substring(j+1));
					}
				}else if(filter.charAt(0)==word.charAt(0))
					return match(word.substring(1),filter.substring(1));
				else
					return false;
			}
		}
	}
	
	public static void main(String[] args){
		System.out.println(match("aav", "a*"));
	}
}
