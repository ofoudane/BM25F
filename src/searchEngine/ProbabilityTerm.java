package searchEngine;

import java.util.Set;
import java.util.stream.Collectors;

import logic.PRPParameters;
/**
 * Cette classe est un d�corateur de keyword
 * Elle permet de calculer l'IDF du keyword au sein d'un corpus.
 *
 */
public class ProbabilityTerm {
	private Keyword 	  originalKeyword;
	private Set<Integer>  relevantDocuments;
	
	public ProbabilityTerm(Keyword keyword) {
		this.originalKeyword = keyword;
		this.relevantDocuments = this.relevantDocumentsInCorpus(AttributesLoader.getIdDocuments());
	}
	
	
	/**
	 * Calcule l'IDF du terme de l'entit� dans un corpus de documents pertinents.
	 * @param r: Nombre des documents pertinents contenant ce terme.
	 * @param R: Nombre total des documents pertinents.
	 * @return Double: L'idf du terme
	 */
	public Double calculateIDF(Integer r, Integer R) {
		//Nombre total des documents dans l'ensemble. 
		Integer N = AttributesLoader.getDocumentsSize();
		//Nombre total des documents contenant ce terme
		Integer n = this.getRelevantDocumentsSize();
		//Un paramètre fixe (constante) que l'on d�finit 
		Double  k = PRPParameters.IDF_CORRECTOR;
		
		return Math.log(
				(   	(r + k)  * (N - n - R + r + k)  )  /
				(	(n - r + k)  * (R - r + k )        	)
		);
	}
	
	
	/**
	 * Retourne les termes du corpus contenant le terme contenu dans cette entit�
	 * @param corpus: Le corpus dans lequel s'effectuera la recherche
	 * @return Set<Integer> : La liste des identifiants des documents contenant ce terme dans le corpus
	 */
	public Set<Integer> relevantDocumentsInCorpus(Set<Integer> corpus) {
		return this.originalKeyword.getOccurrences()
				   .keySet().stream()
				   .filter(idDoc -> corpus.contains(idDoc))
				   .collect(Collectors.toSet());
	}
	
	public Long numberOfRelevantDocumentsInCorpus(Set<Integer> corpus) {
		return this.originalKeyword.getOccurrences()
				   .keySet().stream()
				   .filter(idDoc -> corpus.contains(idDoc))
				   .count();
	}
	

	
	
	
	
	
	
	
	
	
	/* **************************         **************************
	 * ************************** GETTERS ************************** *
	 * **************************         ************************** */

	public Keyword getKeyword() {
		return this.originalKeyword;
	}
	
	public Set<Integer> getRelevantDocuments(){
		return this.relevantDocuments;
	}
	
	public Integer getRelevantDocumentsSize() {
		return this.relevantDocuments.size();
	}
	
	
	
	
}
