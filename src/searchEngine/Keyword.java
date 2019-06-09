package searchEngine;

import java.io.Serializable;
import java.util.TreeMap;


public class Keyword  implements Serializable {


    private static final long serialVersionUID = 1L;
    private String term;
    private TreeMap<Integer,Integer> occurrences;
    private TreeMap<Integer,Double> frequences;
    private TreeMap<Integer,Double> TFIDFfrequences;
    private TreeMap<Integer,Double> frequencesProb;


    public Keyword(String mot){
        occurrences = new  TreeMap<Integer,Integer>();
        frequences = new  TreeMap<Integer,Double>();
        TFIDFfrequences = new  TreeMap<Integer,Double>();
        frequencesProb = new  TreeMap<Integer,Double>();
        this.term = mot;
    }




    /**Getter and Setter for term*/
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }



    public Integer get1Occur(Integer  idDoc) {
        return occurrences.get(idDoc);
    }

    public boolean existsOccur(Integer  idDoc) {
        return occurrences.containsKey(idDoc);
    }

    public void add1Occur(Integer idDoc, Integer occur){
        occurrences.put(idDoc,occur);
    }


    public Double get1Freq(Integer  idDoc) {
        return frequences.get(idDoc);
    }

    public void add1Freq(Integer idDoc, Double freq){
      frequences.put(idDoc,freq);

    }
    public boolean existsFreq(Integer  idDoc) {
        return occurrences.containsKey(idDoc);
    }

    public Double get1TFIDFFreq(Integer  idDoc) {
        return TFIDFfrequences.get(idDoc);
    }

    public void add1TFIDFFreq(Integer idDoc, Double freq){
        TFIDFfrequences.put(idDoc,freq);

    }
    public boolean existsTFIDFreq(Integer  idDoc) {
        return TFIDFfrequences.containsKey(idDoc);
    }



    public Double get1FreqProb(Integer  idDoc) {
        return frequencesProb.get(idDoc);
    }

    public void add1FreqProb(Integer idDoc, Double freq){
        frequencesProb.put(idDoc,freq);

    }
    public boolean existsFreqProb(Integer  idDoc) {
        return frequencesProb.containsKey(idDoc);
    }


    public TreeMap<Integer, Double> getFrequences() {
        return frequences;
    }
    public TreeMap<Integer, Integer> getOccurrences() {
        return occurrences;
    }
    public TreeMap<Integer, Double> getTFIDFFrequences() {
        return TFIDFfrequences;
    }
    public TreeMap<Integer, Double> getFrequencesProb() {
        return frequencesProb;
    }




}
