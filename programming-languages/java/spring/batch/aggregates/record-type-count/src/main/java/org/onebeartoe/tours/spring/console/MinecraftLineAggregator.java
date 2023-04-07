
package org.onebeartoe.tours.spring.console;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.file.transform.PassThroughLineAggregator;

/**
 * 
 */
public class MinecraftLineAggregator extends PassThroughLineAggregator <Person>
{
    public Map<String,Integer> recordTypeMap;

    public MinecraftLineAggregator() 
    {
        recordTypeMap = new HashMap<>();        
    }

    @Override
    public String aggregate(Person item)
    {
        String line = "item: " + item;
                
        String type = item.getFirstName()
                        .substring(0, 3);
        
        Integer count = recordTypeMap.get(type);
        
        if(count == null)
        {
            count = 1;
        }
        else
        {
            count++;            
        }
        
        recordTypeMap.put(type, count);
        
        System.out.println(line);
        
        return line;
    }    
}
