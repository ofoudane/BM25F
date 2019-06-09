package logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import common.QueryResult;
import searchEngine.AttributesLoader;
import searchEngine.Document;
import searchEngine.ProbabilityTerm;

public class ProbabilityRankingPrinciple {
	//Les termes de la requ�te
	private Set<String> query;
	
	//Les param�tres utiles pour le calcul de la pertinence des documents.
	private PRPParameters PRPParams;
	
	//Le r�sultat de la requ�te: Les documents tri�s de mani�re d�croissante par leur probabilit� de pertinence.
	private TreeSet<QueryResult> documentsProbability;
		
	private Map<String, Integer> sizeOfRelevantDocumentsForKeyword;
	
	public ProbabilityRankingPrinciple(Set<String> requete) {
		this.query 								= requete;
		this.sizeOfRelevantDocumentsForKeyword 	= new HashMap<>();
	}
	

	/*
	 * Attribue � chaque document, sa probabilit� de pertinence (score).
	 */
	public TreeSet<QueryResult> calculateDocumentsProbability(){
		//Le calcul ne se fait qu'une seule fois.
		if(this.documentsProbability == null) {
			
			this.documentsProbability = new TreeSet<>();
			
			//On d�finit les param�tres de la PRP
			this.definePRPParams();
			
			QueryResult documentResult;
			
			
			for(Integer idDoc : this.PRPParams.getRelevantDocuments()) {
				//Pour chaque document pertinent, on calcule sa probabilit� de pertinence.
				documentResult = new QueryResult(AttributesLoader.getDocument(idDoc), this.calculate1DocScore(idDoc));
				this.documentsProbability.add(documentResult);				
			}
						
		}
		
		return this.documentsProbability;
	}
	
	
	/**
	 * Calcule le score d'un document identifi� par idDoc, selon le mod�le BM25F
	 * @param idDoc: L'identifiant du document que l'on veut calculer son score
	 * @return Double: Le score du document
	 */
	private Double calculate1DocScore(Integer idDoc) {
		Document currentDocument = AttributesLoader.getDocument(idDoc);
		return this.query
				   .stream()
				   .filter(q -> currentDocument.existsOccur(q))
				   .map(q -> AttributesLoader.getTerm(q))
				   .collect(Collectors.summarizingDouble(keyword -> calculate1KeywordScore(keyword, currentDocument)))
				   .getSum();
	}
	
	
	
	/**
	 * Renvoie le score d'un seul terme dans le document.
	 * @param keyword : Le mot clé que l'on cherche à estimer son score.
	 * @param doc	  : Le document sur lequel on travaille.
	 * @return Double : La pertinence du terme au sein du document.
	 */
	private Double calculate1KeywordScore(ProbabilityTerm keyword, Document doc) {
		Double 	idf 		= keyword.calculateIDF(this.sizeOfRelevantDocumentsForKeyword.get(keyword.getKeyword().getTerm()), this.PRPParams.getRelevantDocuments().size());
		Integer occurence 	= keyword.getKeyword().get1Occur(doc.getId());
		Double 	docLength	= occurence / doc.get1Freq(keyword.getKeyword().getTerm()); 
		Double 	numerator 	= occurence * (PRPParameters.SCORE_K_1 + 1);
		Double 	denomirator = occurence 
							+ PRPParameters.SCORE_K_1  
							*  (1 - PRPParameters.SCORE_b * (1 - docLength / this.PRPParams.getDocumentsLengthAverage()));
			
		return idf * numerator / denomirator;
	}
	
	

	
	
		
	
		
	/**
	 * d�finit les param�tres de notre mod�le PRP
	 * Les param�tres d�finis suite � cette fonction sont:
	 * 			1- La liste des identifiants des documents pertinents
	 * 			2- La moyenne des longueurs de documents pertinents
	 */
	private void definePRPParams() {
		if(this.PRPParams == null) {
			this.PRPParams = new PRPParameters(this.getIdRelevantDocuments());
		}
	}
	
	
	/**
	 * Retourne la liste compl�te des identifiants de documents pertinents. 
	 * Un document est consid�r� pertinent s'il contient au moins l'un des termes de la requ�te.
	 */
	private Set<Integer> getIdRelevantDocuments(){
		Set<Integer> relevantDocuments = new HashSet<Integer>();
		
		this.query.stream().forEach(term->{
			if(AttributesLoader.getVocabulary().containsKey(term)) {
				Set<Integer> relevantDocumentsForKeyword = AttributesLoader.getTerm(term).getRelevantDocuments();
				relevantDocuments.addAll(relevantDocumentsForKeyword);
				sizeOfRelevantDocumentsForKeyword.put(term, relevantDocumentsForKeyword.size());
			}
		});
		
		return relevantDocuments;
	}
	
	


}
