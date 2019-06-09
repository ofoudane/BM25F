package logic;

import java.util.Set;
import java.util.stream.Collectors;

import searchEngine.AttributesLoader;

public class PRPParameters {
	
	public final static Integer DOCUMENTS_LIMIT = 100;
	public final static Double IDF_CORRECTOR 	= 0.5;
	public final static Double SCORE_K_1		= 1.5;
	public final static Double SCORE_b			= Math.pow(0.75, 2);
	public final static Integer TITLE_WEIGHT	= 3;
	
	private Set<Integer> relevantDocuments;
	private Double 		 documentsLengthAverage;
	
	
	public PRPParameters(Set<Integer> relevantDocuments) {
		this.relevantDocuments = relevantDocuments;
		this.calculateAverageDocumentsLength();
	}
	
	
	private void calculateAverageDocumentsLength() {
		this.documentsLengthAverage =  relevantDocuments.stream()
														.map(idDoc -> AttributesLoader.get1DocumentLength(idDoc))
														.collect(Collectors.averagingDouble(d -> d));
	}
	
	
	/*******************************************************
	 ********************* GETTERS ************************
	 *******************************************************/
	
	public Set<Integer> getRelevantDocuments(){
		return this.relevantDocuments;
	}
	
	public Double getDocumentsLengthAverage() {
		return this.documentsLengthAverage;
	}
	
	


	


}
