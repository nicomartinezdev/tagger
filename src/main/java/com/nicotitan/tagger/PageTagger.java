package com.nicotitan.tagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author Nicolas.Martinez
 *
 */

public class PageTagger {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private static final String TXT_FILE_EXTENSION = ".txt";
    private static final String TAGGED_TEXT_FILE_SUFFIX = "taggedText-";
    private static final String TAGGER_FILE = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
    private static final MaxentTagger TAGGER = new MaxentTagger(TAGGER_FILE);
    private static Logger LOGGER = LoggerFactory.getLogger(PageTagger.class);
    
    public static void main(String[] args) throws IOException {

        String inputFilePath = getInputFilePath(args);
        URL url = new URL(inputFilePath);
        String text = getText(url);
        String taggedText = tagText(text);
        saveTaggedTextToFile(url, taggedText);
        
    }

    private static void saveTaggedTextToFile(URL url, String taggedText) throws IOException {
        
        String fileName = new StringBuilder().append(TAGGED_TEXT_FILE_SUFFIX).append(url.getHost()).append(TXT_FILE_EXTENSION).toString();
        
        FileUtils.writeStringToFile(new File(fileName), taggedText);
        
    }
    
    private static String getInputFilePath(String[] appArgs) throws IOException {

        // Fetch source file with URLs to process
        if (ArrayUtils.isEmpty(appArgs)) {
            throw new FileNotFoundException("Missing input file. Make sure to specify a valid path to load the input file with the URL to process");
        }

        String inputFilePath = appArgs[0];
        if (StringUtils.isBlank(inputFilePath)) {
            throw new IOException("Invalid input file path. Make sure to specify a valid path to load the input file with the URL to process");
        }

        return inputFilePath;
    }

    public static String tagText(String input) {

        String output = null;

        if (StringUtils.isNotEmpty(input)) {
            String[] chunks = StringUtils.split(input, null);
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<chunks.length; i++) {
                sb.append(TAGGER.tagString(chunks[i]));
            }
            output = sb.toString();
        }

        return output;
    }

    public static String getText(URL url) throws IOException {

        String output = null;

        if (url != null) {
            Document doc = Jsoup.connect(url.toString()).userAgent(USER_AGENT).get();
            if (doc != null) {
                output = doc.text();
                LOGGER.info("URL content as text: {}", output);
            }
        } else{
            LOGGER.error("The URL should not be null");
        }
        
        return output;
    }
    
}
