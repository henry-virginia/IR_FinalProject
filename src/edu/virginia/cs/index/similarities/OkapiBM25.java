package edu.virginia.cs.index.similarities;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

public class OkapiBM25 extends SimilarityBase {
    /**
     * Returns a score for a single term in the document.
     *
     * @param stats
     *            Provides access to corpus-level statistics
     * @param termFreq
     * @param docLength
     */
	
	
    @Override
    protected float score(BasicStats stats, float termFreq, float docLength) { 
    	//double k2 = 750;
    	//double b = 1.0;  
    	//long N = stats.getNumberOfDocuments(); 
    	//long df = stats.getDocFreq(); 
    	float navg = stats.getAvgFieldLength();
    	
    	float term1 = (float) Math.log((stats.getNumberOfDocuments()-stats.getDocFreq()+0.5)/(stats.getDocFreq()+0.5)); 
    	float term2 = (float) (((1.2+1)*termFreq)/((1.2*(1-0.75+(0.75*(docLength/navg))))+termFreq)); 
    	float retValue = (term1*term2); 
    	return retValue;
    }

    @Override
    public String toString() {
        return "Okapi BM25";
    }

}
