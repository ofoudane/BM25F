package main;

import java.util.Scanner;
import java.util.TreeSet;

import common.QueryResult;
import searchEngine.AttributesLoader;

public class Main {
		
	public static void main(String[] args) {
	
		
		AttributesLoader.loadDocuments();
		AttributesLoader.loadVocabulary();
		AttributesLoader.addTitleWeightForAllDocuments();

		Scanner sc = new Scanner(System.in);
		
		String response ;
	
		do{
			System.out.println("Type a query ? (query/n)");
			response = sc.nextLine();
			
			if(!response.equals("n")) {
				long startTime = System.nanoTime();

				//Création de la requête
				QueryHandler handler = new QueryHandler(response);
				
				//Récupération des résultats
				TreeSet<QueryResult> results = handler.fetchResults();
				
				long endTime = System.nanoTime();
				
				System.out.println("Durée de la requête: " + (endTime - startTime)/1000000 + " ms");
				
				if(!results.isEmpty()) {//On affiche et on retire le premier résultat
					displayElement("Premier document : ", results.pollFirst());
					if(!results.isEmpty())//On affiche et on supprime le deuxième résultat
						displayElement("Deuxi�me document : ", results.pollFirst());
					if(!results.isEmpty())//On affiche le dernier résultat
						displayElement("Dernier document :", results.pollLast());
				}
			}

		   
		}while(!response.equals("n")); 
		
		sc.close();
		
	}
	
	public static void displayElement(String message, QueryResult result) {
		System.out.println(message);
		System.out.println("------------------------------------------------------");
		System.out.println(result );
	}
	
	
	
	
}
