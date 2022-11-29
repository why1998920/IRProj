package IR.commen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This is for INFSCI-2140 in 2022
 *
 * Please add comments along with your code.
 */

public class StopWordRemover {
	//you can add essential private methods or variables.
	private Path path=new Path();
	FileReader fileReader=new FileReader(path.getStopwordDir());
	BufferedReader bufferedReader=new BufferedReader(fileReader);

	List<String> stopWordList;

	public StopWordRemover( ) throws Exception {
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
		// address of stopword.txt should be Path.StopwordDir
		stopWordList=new ArrayList<String>();
		String tmpLine;
		while((tmpLine=bufferedReader.readLine())!=null){
			stopWordList.add(tmpLine);
		}

	}

	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword( char[] word ) {
		// return true if the input word is a stopword, or false if not
		Boolean flag;
		StringBuffer tmpWord=new StringBuffer();
		for(int i=0;i<word.length;i++){
			tmpWord.append(word[i]);
		}

		flag=stopWordList.contains(tmpWord.toString());
		return flag;
	}
}
