package common;

import searchEngine.Document;

/*
 * Modélise le résultat d'une requête : Le document et son poids
 */

public class QueryResult implements Comparable<QueryResult>{
	
	private Document document;
	private double score;
	
	
	public QueryResult(Document document, double probability) {
		this.document = document;
		this.score = probability;
	}
	
	/*******************************************************
	 ********************* GETTERS ************************
	 *******************************************************/
	
	public Document getDocument() {
		return document;
	}

	public Integer getIdDocument() {
		return document.getId();
	}
	
	public double getScore() {
		return score;
	}
	
	
	//On compare les QueryResult en fonction de leurs poids.
	//Les r�sultats seront tri�es par ordre d�croissant de poids.
	@Override
	public int compareTo(QueryResult o) {
		
		if(this == o) 
			return 0;
		if(this.score == o.score) {//Pour avoir deux documents ayant le même score
			return 1;
		}
		else//On doit multiplier le résultat par -1 pour trier par ordre décroissant
			return -Double.compare(this.score, o.score);
	}

	@Override
	public String toString() {
		String s = "Query Result : \nIdDocument = " + document.getId() + "\n";
		
		s += "Score du document : " + this.score + "\n";
		s += "Titre document : " + document.getTitle() + "\n";
		s += "-----------------------------------------------------------------------\n";
		s += "Corps du document : " + document.getText() + "\n";
		s += "-----------------------------------------------------------------------\n";
		
		return s;
	}
	
	

}
