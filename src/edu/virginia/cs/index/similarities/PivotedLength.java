package edu.virginia.cs.index.similarities;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

public class PivotedLength extends SimilarityBase {
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
        double s = 0.75; 
        float navg = stats.getAvgFieldLength();
        float n = docLength; 
        long N = stats.getNumberOfDocuments(); 
    	long df = stats.getDocFreq(); 
    	
    	float term1 = (float) ((1+Math.log(1+Math.log(termFreq)))/(1-s+(s*n/navg)));   
    	float term2 = 1;
    	float term3 = (float) Math.log((N+1)/df);
    	float retValue = (term1*term3); 
    	return retValue;
    }

    @Override
    public String toString() {
        return "Pivoted Length Normalization";
    }

}
