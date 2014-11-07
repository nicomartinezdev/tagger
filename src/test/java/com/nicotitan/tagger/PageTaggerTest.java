/**
 * 
 */
package com.nicotitan.tagger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author nicolas.martinez
 *
 */
public class PageTaggerTest {

    private static final String TEST_TEXT = "Test text";
    private static final String TAGGED_TEXT = "Test_NN text_NN ";
    private static final String GUMGUM_TEST_URL = "http://gumgum.com/";
    private static final String POPCRUNCH_TEST_URL = "http://www.popcrunch.com/jimmy-kimmel-engaged/";
    private static final String GUMGUM_PUBLIC_TEST_URL = "http://gumgum-public.s3.amazonaws.com/numbers.html";
    private static final String WINDINGROAD_TEST_URL = "http://www.windingroad.com/articles/reviews/quick-drive-2012-bmw-z4-sdrive28i/";
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#main(java.lang.String[])}.
     * 
     * Testing Main method creating a taggedText file for http://gumgum.com/ 
     * 
     * @throws IOException 
     */
    @Test
    public final void testMain_gumgum() throws IOException {
        
        String[] args = {GUMGUM_TEST_URL};
        PageTagger.main(args);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#main(java.lang.String[])}.
     * 
     * Testing Main method creating a taggedText file for http://www.popcrunch.com/jimmy-kimmel-engaged/
     * 
     * @throws IOException 
     */
    @Test
    public final void testMain_popcrunch() throws IOException {
        
        String[] args = {POPCRUNCH_TEST_URL};
        PageTagger.main(args);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#main(java.lang.String[])}.
     * 
     * Testing Main method creating a taggedText file for http://gumgum-public.s3.amazonaws.com/numbers.html
     * 
     * @throws IOException 
     */
    @Test
    @Ignore
    public final void testMain_gumgumPublic() throws IOException {
        
        String[] args = {GUMGUM_PUBLIC_TEST_URL};
        PageTagger.main(args);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#main(java.lang.String[])}.
     * 
     * Testing Main method creating a taggedText file for http://www.windingroad.com/articles/reviews/quick-drive-2012-bmw-z4-sdrive28i/
     * 
     * @throws IOException 
     */
    @Test
    public final void testMain_windingRoad() throws IOException {
        
        String[] args = {WINDINGROAD_TEST_URL};
        PageTagger.main(args);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#main(java.lang.String[])}.
     * 
     * Test Main method with no args (this is a corner case, just in case the main is called from inside the app
     * 
     * @throws IOException 
     */
    @Test (expected = FileNotFoundException.class)
    public final void testMain_nullArgs() throws IOException {
        
        PageTagger.main(null);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#main(java.lang.String[])}.
     * 
     * Test Main method with blank URL
     * 
     * @throws IOException 
     */
    @Test (expected = IOException.class)
    public final void testMain_blankURL() throws IOException {
        
        String[] args = {" "};
        PageTagger.main(args);
        
    }

    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#tagText(java.lang.String)}.
     * 
     * Testing tagText method with a null String input parameter.
     * 
     */
    @Test
    public final void testTagText_nullInput() {
        
        //Initialize
        
        String input = null;
        
        //Exercise
        
        String actual = PageTagger.tagText(input);
        
        //Assertions
        
        assertNull(actual);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#tagText(java.lang.String)}.
     * 
     * Testing tagText method with a test String input parameter.
     * 
     */
    @Test
    public final void testTagText() {
        
        //Initialize
        
        String input = TEST_TEXT;
        
        //Exercise
        
        String actual = PageTagger.tagText(input);
        
        //Assertions
        
        assertNotNull(actual);
        assertEquals(TAGGED_TEXT, actual);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#getText(java.net.URL)}.
     * 
     * Testing getText method with a null URL input parameter.
     * 
     * @throws IOException 
     */
    @Test
    public final void testGetText_nullURL() throws IOException {

        //Initialize
        
        URL url = null;
        
        //Exercise
        
        String actual = PageTagger.getText(url);
        
        //Assertions
        
        assertNull(actual);
        
    }
    
    /**
     * Test method for {@link com.nicotitan.tagger.PageTagger#getText(java.net.URL)}.
     * 
     * Testing getText method happy path.
     * 
     * @throws IOException 
     */
    @Test
    public final void testGetText() throws IOException {

        //Initialize
        
        URL url = new URL("http://google.com");
        
        //Exercise
        
        String actual = PageTagger.getText(url);
        
        //Assertions
        
        assertNotNull(actual);
        
    }
    
}
