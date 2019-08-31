
package org.onebeartoe.tours.jdk09;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.annotations.Test;

/**
 *
 * @author Roberto Marquez
 */
public class Jdk09StreamsExamples
{
    @Test
    public void dropWhileStreams()
    {
        System.out.println();
        System.out.println("only print even numbers:");
        
        List<Integer> evens = Stream.of(1, 2, 3, 4, 5)
                .dropWhile(n -> n % 2 == 1)
                .collect(Collectors.toList());
        
        evens.forEach(e -> System.out.print(e + " "));
        
        System.out.println();
    }

    @Test
    public void takeWhileStreams()
    {
        System.out.println();
        System.out.println("only print odd numbers:");
        
//        List<Integer> odds = Stream.of(1, 2, 3, 4, 5)
//                .collect(Collectors.toList());
//        
//        odds.forEach(o -> System.out.println(o % 2));
        
        var odds = new ArrayList<Integer>();

//        List<Integer> odds = 
        Stream.of(1, 2, 3, 4, 5)
                .forEach( n -> 
                {
                    if(n % 2 == 1)
                    {
                        odds.add(n);
                    }
                });
// I guess the collect is dropping duplicates        
//                .takeWhile(n -> n % 2 == 1)
//                .collect(Collectors.toList());        
        odds.forEach(e -> System.out.print(e + " "));
    }
    
    @Test
    public void inputStreamTransferTo()
    {
        // https://docs.oracle.com/javase/9/docs/api/java/io/InputStream.html#transferTo-java.io.OutputStream-
    }    
}
