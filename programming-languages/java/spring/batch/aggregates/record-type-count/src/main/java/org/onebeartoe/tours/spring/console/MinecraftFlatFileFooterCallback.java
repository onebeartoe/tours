
package org.onebeartoe.tours.spring.console;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;

/**
 *
 */
public class MinecraftFlatFileFooterCallback implements FlatFileFooterCallback
{
    PassThroughLineAggregator <Person> aggregator;
    
    public MinecraftFlatFileFooterCallback(MinecraftLineAggregator aggregator) 
    {
        this.aggregator = aggregator;
    }
    
    @Override
    public void writeFooter(Writer writer) throws IOException 
    {
        MinecraftLineAggregator a = (MinecraftLineAggregator) aggregator;
        
        Set<String> keySet = a.recordTypeMap.keySet();
        
        for(String k : keySet)
        {
            Integer count = a.recordTypeMap.get(k);
        
            writer.write(k + " : " + count + System.lineSeparator() );
        }
        
        writer.write("end of run - 222");


    }    
}
