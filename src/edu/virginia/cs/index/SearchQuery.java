package edu.virginia.cs.index;
import java.io.File;
//package edu.mit.jwi.IDictionary; 
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class SearchQuery
{
    private ArrayList<String> fields;
    private String queryText;
    private int numResults;
    private int from;
    private final static int defaultNumResults = 11000;//need to be adjusted if more results are required
    private final static String defaultField = "content";

    public SearchQuery queryText(String queryText)
    {
        this.queryText = queryText;
        return this;
    }

    public SearchQuery fields(ArrayList<String> fields)
    {
        this.fields = new ArrayList<String>(fields);
        return this;
    }

    public ArrayList<String> fields()
    {
        return fields;
    }

    public String queryText()
    {
        return queryText;
    }

    public SearchQuery fields(String field)
    {
        fields = new ArrayList<String>();
        fields.add(field);
        return this;
    }

    public int numResults()
    {
        return numResults;
    }

    public SearchQuery numResults(int numResults)
    {
        this.numResults = numResults;
        return this;
    }

    public int fromDoc()
    {
        return from;
    }

    public SearchQuery fromDoc(int fromDoc)
    {
        this.from = fromDoc;
        return this;
    }

    public SearchQuery(String queryText, ArrayList<String> fields)
    {
        this.queryText = queryText;
        this.numResults = defaultNumResults;
        this.fields = fields;
        from = 0;
    }

    public SearchQuery()
    {
        this.queryText = null;
        this.numResults = defaultNumResults;
        this.fields = new ArrayList<String>();
        fields.add(defaultField);
        from = 0;
    }

    public SearchQuery(String queryText, String field) throws IOException
    {
    	String str[] = queryText.split(" "); 
    	String wordNetDirectory = "C:\\Users\\Riya Simon\\Desktop";
    	String path = wordNetDirectory + File.separator + "dict";
    	URL url;
    	MaxentTagger tagger; 
    	try {
    		url = new URL("file", null, path);
			IDictionary dict = new Dictionary(url) ;
	    	dict.open();
    		tagger = new MaxentTagger("taggers/bidirectional-distsim-wsj-0-18.tagger");
			String tagged = tagger.tagString(queryText);
			String[] poslist = tagged.split(" "); 
			ArrayList<ISynset> listofSynonyms = new ArrayList<ISynset>(); 
			for(int i = 0; i < poslist.length; i++){
				int index = poslist[i].indexOf("/"); 
				poslist[i] = poslist[i].substring(index+1); 
				//System.out.println(poslist[i]);
				POS postag = getSynsetType(poslist[i]); 
				if (postag != null){
					getSynonyms(dict, postag, str[i]); 
				}
			}
			//System.out.println(tagged);
	
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        this.queryText = queryText;
        this.numResults = defaultNumResults;
        fields = new ArrayList<String>();
        fields.add(field);
    }

    private void getSynonyms(IDictionary dict, POS postag, String string) {
		// TODO Auto-generated method stub
    	// look up first sense of the word "dog "
    	IIndexWord idxWord = dict.getIndexWord(string, postag);
    	try{
	    	for (IWordID x: idxWord.getWordIDs()){
	    		IWord word = dict.getWord(x);
	    		ISynset synset = word.getSynset();
	    		for (IWord w: synset.getWords()){
	    			 
	    		}
	    	}
    	}
    	catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private POS getSynsetType(String string) {
		// TODO Auto-generated method stub
    	POS result = null; 
    		    if (string.startsWith("NN")) {
    		      result = POS.NOUN;
    		    } else if (string.startsWith("VB")) {
    		      result = POS.VERB;
    		    } else if (string.startsWith("RB")) {
    		      result = POS.ADVERB;
    		    } else if (string.startsWith("JJ")) {
    		      result = POS.ADJECTIVE;
    		    }
    		 
    		  
    		  return result;
	}

	@Override
    public boolean equals(Object other)
    {
        if(!(other instanceof SearchQuery))
            return false;

        SearchQuery otherQuery = (SearchQuery) other;
        return otherQuery.queryText.equals(queryText) &&
                otherQuery.fields == fields &&
                otherQuery.numResults == numResults &&
                otherQuery.from == from;
    }
}
