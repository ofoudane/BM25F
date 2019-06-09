package searchEngine;

import java.io.Serializable;
import java.util.TreeMap;


public class Document implements Serializable{

    private Integer id;

    private static final long serialVersionUID = 1L;
    private String text;
    private String title;
    private String date;

    private TreeMap<String,Integer> occurrences;
    private TreeMap<String,Double> frequences;
    private TreeMap<String,Double> TFIDFfrequences;
    private TreeMap<String,Double> frequencesProb;

    public Document(Integer id){
        this.id=id;

        occurrences = new TreeMap<String,Integer>();
        frequences = new TreeMap<String,Double>();
        TFIDFfrequences = new TreeMap<String,Double>();
        frequencesProb = new TreeMap<String,Double>();
    }




    /**Getter and Setter for Id*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    /**Getter and Setter for Hotel*/
    public String getText() {
        return text;
    }

    public void setText(String texte) {
        this.text = texte;
    }


    /**Getter and Setter for Titre*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String titre) {
        this.title = titre;
    }


    /**Getter and Setter for Date*/
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }





    public void add1Occur(String mot, Integer occurr){
        occurrences.put(mot,occurr);
    }
    public Integer get1Occur(String mot){
        return occurrences.get(mot);
    }

    public boolean existsOccur(String mot){
        return occurrences.containsKey(mot);
    }



    public void add1Freq(String mot, Double frequence){
        frequences.put(mot,frequence);
    }
    public Double get1Freq(String mot){
        return frequences.get(mot);

    }
    public boolean existsFreq(String mot){
        return frequences.containsKey(mot);
    }



    public void add1TFIDFFreq(String mot, Double frequence){
        TFIDFfrequences.put(mot, frequence);
    }
    public Double get1TFIDFFreq(String mot){
        return TFIDFfrequences.get(mot);

    }
    public boolean existsTFIDFFreq(String mot){
        return TFIDFfrequences.containsKey(mot);
    }



    public void add1TFreqProb(String mot, Double frequence){
        frequencesProb.put(mot, frequence);
    }
    public Double get1FreqProb(String mot){
        return frequencesProb.get(mot);

    }
    public boolean existsFreqProb(String mot){
        return frequencesProb.containsKey(mot);
    }







    public TreeMap<String, Double> getFrequences() {
        return frequences;
    }
    public TreeMap<String, Integer> getOccurrences() {
        return occurrences;
    }
    public TreeMap<String, Double> getTFIDFFrequences() {
        return TFIDFfrequences;
    }
    public TreeMap<String, Double> getFrequencesProb() {
        return frequencesProb;
    }


}
