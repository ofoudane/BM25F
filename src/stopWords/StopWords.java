package stopWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StopWords {
	private final static String STOP_WORDS_PATH = "/stopWords/stopwords.txt";
	
	private static Collection<String> stopWords;
	
	static {
		try {
			System.out.println("Loading stopwords...");
			loadStopWords();
		} catch (Exception e) {
			System.out.println("ERROR LOADING STOP_WORDS !!!!!!!!");
		}
	}
	
	
	
	public static void loadStopWords() throws Exception{
		stopWords = new HashSet<String>();
		File stopWordFile = new File(StopWords.class.getResource(STOP_WORDS_PATH).toURI().getPath());
		BufferedReader reader = new BufferedReader(new FileReader(stopWordFile));
		String line = null;
		while((line = reader.readLine()) != null){
			stopWords.add(line.trim());
		}
		reader.close();
	}
	
	public static void cleanFromStopWords(Set<String> initialTextStream) {
		initialTextStream.removeAll(stopWords);
	}
	
	public static String cleanFromStopWords(String text) {
		String result = text;
		for(String word: stopWords) {
			result = result.replaceAll(word, "");
		}
		return result;
	}
}
