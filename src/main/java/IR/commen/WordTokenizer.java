package IR.commen;

import java.util.ArrayList;
import java.util.List;

/**
 * This is for INFSCI-2140 in 2022
 *
 * TextTokenizer can split a sequence of text into individual word tokens.
 *
 * Please add comments along with your code.
 */
public class WordTokenizer {
	//you can add essential private methods or variables
	List<char[]> tokenizedWordList=null;


	// YOU MUST IMPLEMENT THIS METHOD
	public WordTokenizer( char[] texts ) {
		// this constructor will tokenize the input texts (usually it is a char array for a whole document)
		tokenizedWordList= new ArrayList<char[]>();
		List<Character> tmpChars=new ArrayList<Character>();
		int cCnt=0;
		//travers each char within texts[]
		while(cCnt<texts.length){


			char tmpc=texts[cCnt];
			cCnt++;
			if (
					((int)tmpc>47&&(int)tmpc<58==true) || ((int)tmpc>64&&(int)tmpc<91==true) || ((int)tmpc>96&&(int)tmpc<123==true)
			)
			{
				tmpChars.add(tmpc);
			}
			else if(tmpChars.size()!=0){
				char[] tokenizedWord=new char[tmpChars.size()];
				for(int i=0;i<tmpChars.size();i++){
					tokenizedWord[i]=tmpChars.get(i);
				}
				tokenizedWordList.add(tokenizedWord);

				tmpChars= new ArrayList<Character>();
			}
		}

	}

	// YOU MUST IMPLEMENT THIS METHOD
	public char[] nextWord() {
		// read and return the next word of the document
		// or return null if it is the end of the document

		try {
			char[] word=tokenizedWordList.get(0);
			if(tokenizedWordList.size()!=0) tokenizedWordList.remove(0);
			return word;
		}
		catch (Exception e){
			return null;
		}
	}
}
