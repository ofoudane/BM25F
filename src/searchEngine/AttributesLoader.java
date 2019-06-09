package searchEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import logic.PRPParameters;
import stopWords.StopWords;


public class AttributesLoader {

	   private static final String pathDocs= "/Indexed_Docs/documents.data";;
	   private static final String pathVoc = "/Indexed_Docs/vocabulary.data";

	   private static TreeMap<String,ProbabilityTerm> vocabulary;
	   private static TreeMap<Integer,Document> documents;

	   static {
	        vocabulary = new TreeMap<String,ProbabilityTerm>();
	        documents = new TreeMap<Integer,Document>();
	    }


	   
	   
		/*******************************************************
		 ************************ ADD **************************
		 *******************************************************/

	    public static void addKeyword(String key, Keyword keyword){
	        vocabulary.put(key, new ProbabilityTerm(keyword));
	    }


	    public static void addDocument(Integer id, Document doc){
	        documents.put(id, doc);
	    }
	    
		/*******************************************************
		 *************** DOCUMENTS OPERATIONS ******************
		 *******************************************************/
	    /**
	     * Retourne le nombre de termes dans un document
	     * Frequence = Occurences / longueur => longueur = Occurences / Frequence 
	     * @param idDoc : L'identifiant du document que l'on veut savoir sa longueur
	     * @return Integer: La longueur du document
	     */
	    public static Integer get1DocumentLength(Integer idDoc) {
	    	
	    	Document selectedDocument = documents.get(idDoc);
	    	Integer  documentLength	  = 0;
	    	
	    	if(selectedDocument != null) {
	    		try {
	    			String cle = selectedDocument.getFrequences().firstKey();
	    			documentLength = (int) Math.round(selectedDocument.get1Occur(cle) / selectedDocument.get1Freq(cle));
	    		}catch(NoSuchElementException e) {}
	    	}
	    	
	    	return documentLength;
	    }

	    
	    
	    
	    
	    
	    
	    
		/*******************************************************
		 ********************* GETTERS ************************
		 *******************************************************/
	    public static Document getDocument(Integer id){
	        return documents.get(id);
	    }
	    
	    public static ProbabilityTerm getTerm(String termKey) {
	    	return vocabulary.get(termKey);
	    }

	    public static TreeMap<String,ProbabilityTerm> getVocabulary(){
	    	return vocabulary;
	    }

	    public static TreeMap<Integer,Document> getDocuments(){
	    	return documents;
	    }

	    public static Set<Integer> getIdDocuments(){
	    	return documents.keySet();
	    }
	   
	    //Retourne la taille de la liste des documents
	    public static Integer getDocumentsSize() {
	    	return documents.size();
	    }


	    /** Afin d'appliquer le modèle BM25F, on aura besoin d'ajouter les occurences du titre aux documents
	     * Pour le moment, la classe document nous fournit pas l'information suivant: 
	     * 	Est-ce que le poids du titre est pris en compte dans le document ou pas ?
	     * On est donc obliger de le recalculer à chaque fois et on ne doit jamais sauvegarder cette modification 
	     * (Sauf si on modifie la classe Document)
	     */
	    public static void addTitleWeightForAllDocuments() {
	    	for(Document d : documents.values()) {
	    		add1DocumentTitleWeight(d);
	    	}
	    }
	    
	    
	    /*
	     * On met à jour le document en ajoutant le poids du titre aux fréquences et aux occurences
	     */
	    private static void add1DocumentTitleWeight(Document document) {
	    	String title = document.getTitle();
	    	if(title != null) {
	    		//On retire les stop words
	    		String[] titleWords = StopWords.cleanFromStopWords(title.toLowerCase()).trim().toLowerCase().split(" ");
	    		Integer newOccurence = 0;
	    		for (String word : titleWords) {
	    			if(document.existsOccur(word)) 
	    				newOccurence = document.get1Occur(word) + PRPParameters.TITLE_WEIGHT;
	    			else
	    				newOccurence = PRPParameters.TITLE_WEIGHT;
	    			
	    			//On ajoute le poids du titre au nombre d'occurences du mot dans le corps
					document.add1Occur(word, newOccurence );
					safeUpdateTermOccurenceIn1Doc(word, document.getId(), newOccurence);
	    		}
	    		//On remet à jour la longueur des documents pour que ça soit 
	    		update1DocumentFrequency(document);
	    	}
	    }
	    
	    private static void update1DocumentFrequency(Document document) {
	    	long numberOfWords = document.getOccurrences().values().stream()
	    								 .collect(Collectors.summarizingInt(v->v))
	    								 .getSum();
	    	
	    	//On met à jour la liste des fréquences et la liste des occurrences
	    	document.getOccurrences().entrySet().stream()
	    			.forEach(entry -> {
	    				Double freq = entry.getValue() * 1.0/numberOfWords;
	    				document.add1Freq(entry.getKey(), freq);
	    				if(vocabulary.containsKey(entry.getKey())) {
	    					vocabulary.get(entry.getKey()).getKeyword().add1Freq(document.getId(), freq);
	    				}
	    			});
	    }
	    
	    
	    /**
	     * Utilisée par la méthode de redéfinition du nombre des occurences dans le modèle BM25F
	     * Cette méthode permet de vérifier si le term éxiste d'abord avant de le modifier
	     * Si le terme n'éxiste pas alors aucune création n'est faite
	     * @param term : Le term que l'on veut modifie
	     * @param idDoc: L'identfiant du document
	     * @param newOccurence : Le nouveau nombre d'occurences du terme dans le document
	     */
	   public static void safeUpdateTermOccurenceIn1Doc(String term, Integer idDoc, Integer newOccurence) {
		   if(vocabulary.containsKey(term)) {
			   vocabulary.get(term).getKeyword().add1Occur(idDoc, newOccurence);
		   }
	   }

	    
	    
	    
	    
	    
	    
	    
	    
	    
		/*******************************************************
		 ********************* FILE LOAD ***********************
		 *******************************************************/
	    
	    public static TreeMap<String,ProbabilityTerm> loadVocabulary(){
	        try{
	        	
	            System.out.println("Loading keywords..");
	            // Read from disk using FileInputStream
	             FileInputStream f_in = new FileInputStream(AttributesLoader.class.getResource(pathVoc).toURI().toURL().getPath());

	            // Read object using ObjectInputStream
	            ObjectInputStream obj_in = new ObjectInputStream (f_in);

	            // Read an object
	            TreeMap<String, Keyword> loadedVocabulary = (TreeMap<String,Keyword>)obj_in.readObject();
	            
	            loadedVocabulary.entrySet().stream().forEach(entry->{
	            	vocabulary.put(entry.getKey(), new ProbabilityTerm(entry.getValue()));
	            });
	            
	            return vocabulary;

	        }catch(Exception e){
	            e.printStackTrace();
	            System.exit(1);
	        }
	        
	        return null;
	    }
	    
	    
	    
	    public static TreeMap<Integer,Document>loadDocuments(){
	        try{
	            // Read from disk using FileInputStream
	            System.out.println("Loading Documents..");
	            FileInputStream f_in = new FileInputStream(AttributesLoader.class.getResource(pathDocs).toURI().toURL().getPath());

	            // Read object using ObjectInputStream
	            ObjectInputStream obj_in = new ObjectInputStream (f_in);

	            // Read an object
	            documents = (TreeMap<Integer,Document>)obj_in.readObject();
	            	            
	            return documents;

	        }catch(Exception e){
	            e.printStackTrace();
	            System.exit(1);
	        }
	        return null;
	    }

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
		/*******************************************************
		 ********************* DATA SAVE ***********************
		 *******************************************************/

	    public static void saveVocabulary(){


	        try{
	            File fileTemp = new File(pathVoc);
	            if (fileTemp.exists()){
	                fileTemp.delete();
	            }
	            // Write to disk with FileOutputStream
	            FileOutputStream f_out = new FileOutputStream(pathVoc);

	            // Write object with ObjectOutputStream
	            ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

	            // Write object out to disk

	            obj_out.writeObject (vocabulary);

	        }catch(IOException e){
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }




	    public static void saveDocuments(){

	        try{

	            File fileTemp = new File(pathDocs);
	            if (fileTemp.exists()){
	                fileTemp.delete();
	            }
	            // Write to disk with FileOutputStream
	            FileOutputStream f_out = new FileOutputStream(pathDocs);

	            // Write object with ObjectOutputStream
	            ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

	            // Write object out to disk

	            obj_out.writeObject (documents);

	        }catch(IOException e){
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }



}
