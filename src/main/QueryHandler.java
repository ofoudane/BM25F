package main;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import common.QueryResult;
import logic.ProbabilityRankingPrinciple;

public class QueryHandler {
	private Set<String> query;
	private TreeSet<QueryResult> results;
	
	public QueryHandler(String queryText) {
		this.query = Arrays.asList(queryText.trim().toLowerCase().split(" ")).stream().collect(Collectors.toSet());
	}
	
	public TreeSet<QueryResult> fetchResults(){
		
		if(this.results == null) {
			this.processQuery();
		}
		
		return this.results;
	}
		
	private void processQuery() {
		
		ProbabilityRankingPrinciple modele = new ProbabilityRankingPrinciple(this.query);
		
		this.results = modele.calculateDocumentsProbability();
	}
	
}
