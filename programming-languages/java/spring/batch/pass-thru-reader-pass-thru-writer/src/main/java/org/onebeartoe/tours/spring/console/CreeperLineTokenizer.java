
package org.onebeartoe.tours.spring.console;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreeperLineTokenizer 
{
    @Bean
    public FixedLengthTokenizer createLineTokenizer()
    {
        final var lineTokenizer = new FixedLengthTokenizer();
    
        lineTokenizer.setNames(CreeperFieldnames.getFieldnames());
        lineTokenizer.setColumns(CreeperDetail.getColumnRanges());
        lineTokenizer.setStrict(false);
        
        return lineTokenizer;
    }
}
