package com.nicotitan.tagger;

import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author Nicolas.Martinez
 *
 */

public class PageTagger {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Initialize the tagger
        MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");

        // The sample string
        String sample = "This is a sample text";

        // The tagged string
        String tagged = tagger.tagString(sample);

        // Output the result
        System.out.println(tagged);
    }
    
    public String tagText (String input) {
        
        String output = null;
        
       // if ()
            
        return output;
    }
}
