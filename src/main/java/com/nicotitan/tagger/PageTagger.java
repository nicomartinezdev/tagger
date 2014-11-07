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
 * This class includes a main method to get tagged text
 * for a given input URL string.
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
    private static Logger LOG = LoggerFactory.getLogger(PageTagger.class);
    
    public static void main(String[] args) throws IOException, FileNotFoundException {

        String inputFilePath = getInputFilePath(args);
        URL url = new URL(inputFilePath);
        String text = getText(url);
        String taggedText = tagText(text);
        saveTaggedTextToFile(url, taggedText);
        
    }

    /**
     * This methods encapsulates the logic to save the output String
     * into a file using the URL host name as part of the file name.
     * 
     * @param url
     * @param taggedText
     * @throws IOException
     */
    private static void saveTaggedTextToFile(URL url, String taggedText) throws IOException {
        
        String fileName = new StringBuilder().append(TAGGED_TEXT_FILE_SUFFIX).append(url.getHost()).append(TXT_FILE_EXTENSION).toString();
        
        LOG.info("Creating new file with name: {}", fileName);
        
        FileUtils.writeStringToFile(new File(fileName), taggedText);
        
        LOG.info("File with name: {} was succesfully created", fileName);
    }
    
    /**
     * 
     * Method to encapsulate the validation logic of the input
     * string URL and throws an exception in each case.
     * 
     * @param appArgs
     * @return
     * @throws IOException
     */
    private static String getInputFilePath(String[] appArgs) throws IOException, FileNotFoundException {

        if (ArrayUtils.isEmpty(appArgs)) {
            LOG.error("No input specified.");
            throw new FileNotFoundException("Missing input file. Make sure to specify a valid path to load the input file with the URL to process");
        }

        String inputFilePath = appArgs[0];
        if (StringUtils.isBlank(inputFilePath)) {
            LOG.error("The input specified is blank.");
            throw new IOException("Invalid input file path. Make sure to specify a valid path to load the input file with the URL to process");
        }

        return inputFilePath;
    }

    /**
     * 
     * tagText utilizes MaxentTagger to tag every word
     * included in the content of the input String.
     * Since MaxentTagger doesn't handle large volumes correctly,
     * this method splits the input into chunks to avoid OOM errors.
     * 
     * @param input
     * @return
     */
    public static String tagText(String input) {

        LOG.info("Text to be tagged: {}", input);
        
        String output = null;

        if (StringUtils.isNotEmpty(input)) {
            String[] chunks = StringUtils.split(input, null);
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<chunks.length; i++) {
                sb.append(TAGGER.tagString(chunks[i]));
            }
            output = sb.toString();
        }
        
        LOG.info("Tagged text: {}", output);

        return output;
    }

    /**
     * 
     * getText uses Jsoup to connect to the given URL
     * and get its content, and then extract only the text from it.
     * 
     * @param url
     * @return
     * @throws IOException
     */
    //TODO: There are some cases, where some internal html tag (as <a...> or <img....>) are not being removed
    // by Jsoup correctly when they appear inside the <body>.
    public static String getText(URL url) throws IOException {

        String output = null;

        if (url != null) {
            LOG.info("Getting content from URL: {}", url.toString());
            Document doc = Jsoup.connect(url.toString()).userAgent(USER_AGENT).get();
            if (doc != null) {
                output = doc.text();
                LOG.info("URL content as text: {}", output);
            }
        } else{
            LOG.error("The URL should not be null");
        }
        
        return output;
    }
    
}
