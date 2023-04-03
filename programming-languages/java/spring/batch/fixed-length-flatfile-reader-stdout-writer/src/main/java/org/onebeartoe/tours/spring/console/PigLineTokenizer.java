
package org.onebeartoe.tours.spring.console;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.context.annotation.Bean;


public class PigLineTokenizer 
{
    public PigLineTokenizer() 
    {
        
    }    

    @Bean
    public FixedLengthTokenizer createLineTokenizer()
    {
        final var lineTokenizer = new FixedLengthTokenizer();
    
        lineTokenizer.setNames(PigFieldnames.getFieldnames());
        lineTokenizer.setColumns(PigDetail.getColumnRanges());
        lineTokenizer.setStrict(false);
        
        return lineTokenizer;
    }
}
