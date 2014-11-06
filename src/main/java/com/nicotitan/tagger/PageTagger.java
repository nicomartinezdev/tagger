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

import com.nicotitan.tagger.factory.TaggerFactory;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author Nicolas.Martinez
 *
 */

public class PageTagger {

    private static final String TXT_FILE_EXTENSION = ".txt";
    private static final String TAGGED_TEXT_FILE_SUFFIX = "taggedText-";
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
            
            MaxentTagger tagger = TaggerFactory.create();
            output = tagger.tagString(input);
            
        }

        return output;
    }

    public static String getText(URL url) throws IOException {

        String output = null;

        if (url != null) {
            Document doc = Jsoup.connect(url.toString()).get();
            if (doc != null) {
                output = tagText(doc.text());               
                LOGGER.info("Tagged text: {}", output);
            }
        } else{
            LOGGER.error("The URL should not be null");
        }
        
        return output;
    }
    
}
