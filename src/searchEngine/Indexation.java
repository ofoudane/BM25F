package searchEngine;

import java.util.TreeMap;

public class Indexation {
	private String texte;
		
	private TreeMap<String, Integer> occurences;
		
	public Indexation(String texte) {
		if(texte == null)
			throw new IllegalArgumentException("Veuillez fournir un texte du document valid");
		this.texte = texte;
	}
	
	public TreeMap<String, Integer> calculerOccurences() {
		if(this.occurences == null) {
			this.occurences = new TreeMap<String, Integer>();
			String[] tabTermes = this.texte.trim().split(" ");
			this.setListeTermes(tabTermes);
		}
		
		return this.occurences;
	}	
	
	
	private void setListeTermes(String[] tabTermes) {
		Integer occurences_terme;
		
		this.occurences.clear();
		
		//TODO vérifier les stop-words
		
		for(String terme: tabTermes) {
			
			occurences_terme = this.occurences.get(terme);
			
			if(occurences_terme == null)
				occurences_terme = 1;
			
			this.occurences.put(terme, occurences_terme);
			
		}
	}
	
	
}
