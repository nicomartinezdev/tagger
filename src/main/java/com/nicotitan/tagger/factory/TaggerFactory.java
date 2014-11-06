package com.nicotitan.tagger.factory;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TaggerFactory {
	
	private static final String TAGGER_FILE = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
	
	public static MaxentTagger create(){
		return new MaxentTagger(TAGGER_FILE);
	}

}
