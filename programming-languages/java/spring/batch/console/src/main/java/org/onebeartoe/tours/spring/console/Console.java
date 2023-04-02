
package org.onebeartoe.tours.spring.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a sample Spring Batch application using a fixed length flatfile reader 
 * and a database writer.
 * 
 * The starting point for this application was this example
 * 
 *          https://spring.io/guides/gs/batch-processing/
 */
@SpringBootApplication
public class Console {

    public static void main(String[] args) 
    {
        System.out.println("Hello World!");
        
        System.exit(
                SpringApplication.exit(
                        SpringApplication.run(
                                Console.class, args)));
    }
}
