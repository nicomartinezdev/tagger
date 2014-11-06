package com.nicotitan.tagger;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author Nicolas.Martinez
 *
 */

public class PageTagger {

    private static final String TAGGER_FILE = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
    private static final MaxentTagger TAGGER = new MaxentTagger(TAGGER_FILE);
    
    public static void main(String[] args) throws IOException {

        /**
         * I could add a null check for 'args' though it's not needed cause that would be a violation of main method
         * I consider acceptable throwing a NPE if the method is called in any other section of the code without the proper input
         **/
        if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
            String tagged = tagText(getText(new URL(args[0])));
            System.out.println(tagged);
        } else {
            System.out.println("Invalid input. Please try again.");
        }
        
    }
    
    public static String tagText (String input) {
        
        String output = null;
        
        if (StringUtils.isNotEmpty(input)) {
            output = TAGGER.tagString(input);
        }
            
        return output;
    }
    
    public static String getText (URL url) throws IOException {
        
        String output = null;
        
        if (url != null) {
            Document doc = Jsoup.connect(url.toString()).get();
            if (doc != null) {
                output = doc.text();
            }
        }
        
        return output;
    }
    
}
