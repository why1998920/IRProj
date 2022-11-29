package IR.commen;

/**
 * This is for INFSCI-2140 in 2022
 *
 * Please add comments along with your code.
 */
public class WordNormalizer {
	//you can add essential private methods or variables

	// YOU MUST IMPLEMENT THIS METHOD
	public char[] lowercase( char[] chars ) {
		//transform the uppercase characters in the word to lowercase
		for(int i=0;i<chars.length;i++){
			if((int)chars[i]>64&&(int)chars[i]<91) chars[i]=(char)((int)chars[i]+32);
		}
		return chars;
	}

	public String stem(char[] chars)
	{
		//use the stemmer in Classes package to do the stemming on input word, and return the stemmed word
		String str="";
		Stemmer s = new Stemmer();
		s.add(chars, chars.length);
		s.stem();
		str=s.toString();
		return str;
	}

}
