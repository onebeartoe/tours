
package org.onebeartoe.tours.lambdas.streams.cryptoquote;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Cryptoquote
{
    final List<String> allWords;

    public Cryptoquote() throws URISyntaxException, IOException 
    {
        String wordsPath = "org/onebeartoe/tours/lambdas/streams/cryptoquote/united-states-words.txt";
        
        URL url = ClassLoader.getSystemResource(wordsPath);
        
        var uri = url.toURI();
        
        Path path = Path.of(uri);
        
        try(Stream<String> stream = Files.lines(path))
        {
            allWords = stream.map(w -> w.toLowerCase())
                             .collect( Collectors.toList() );
        }
    }
    
    
    
    /**
     * This method takes two strings and finds matching starting characters.
     * 
     * The largest matching starting substring of the two arguments is 
     * used to search the dictionary for words that also start with the 
     * substring.
     * 
     * Dictionary words matching the 'starts with' substring are returned to the caller.
     */
    public List<String> decode(String str1, String str2)
    {
        List<String> allWords = allWords();
        
        int minLength = str1.length() < str2.length() ? str1.length() : str2.length();
        
        var startsWith = "";
        
        for(int i = 0; i < minLength; i++)
        {
            char c1 = str1.charAt(i);
            
            char c2 = str2.charAt(i);
            
            if(c1 == c2)
            {
                startsWith += c1;
            }
            else
            {
                break;
            }
            
            if(startsWith.length() == 0)
            {
                throw new IllegalArgumentException("string parameters do not have a common startsWith() - " + str1 + "/" + str2);
            }
        }        
        
        return new ArrayList();
    }

    /**
     * This method returns all words, in lowercase, found in the dictionary.
     * 
     * @return all words in lower case form
     */
    public List<String> allWords() 
    {
        return List.copyOf(allWords);
    }
    
    /**
     * This methods take two strings, validates the lengths and that is a common
     * startsWith() substring.
     * 
     * @return longest startsWith() string common to the both arguments
     */
    private String validate(String str1, String str2)
    {        
        int minLength = str1.length() < str2.length() ? str1.length() : str2.length();
        
        var startsWith = "";
        
        for(int i = 0; i < minLength; i++)
        {
            char c1 = str1.charAt(i);
            
            char c2 = str2.charAt(i);
            
            if(c1 == c2)
            {
                startsWith += c1;
            }
            else
            {
                break;
            }
            
            if(startsWith.length() == 0)
            {
                throw new IllegalArgumentException("string parameters do not have a common startsWith() - " + str1 + "/" + str2);
            }
        }
        
        return startsWith;
    }
}
