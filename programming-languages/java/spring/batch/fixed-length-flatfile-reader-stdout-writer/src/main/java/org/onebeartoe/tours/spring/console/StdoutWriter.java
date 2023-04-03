
package org.onebeartoe.tours.spring.console;

import java.util.List;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;

public class StdoutWriter<Person> implements ItemWriter<Person> {

    LineAggregator<Person> lineAggregator;

    public void setLineAggregator(LineAggregator<Person> lineAggregator) 
    {
        this.lineAggregator = lineAggregator;
    }

//    @Override
//    public Person write(List<? extends Person> items) throws Exception {
//        for (Person item : items) {
//            System.out.println(lineAggregator.aggregate(item));
//        }
//        
//        return null;
//    }

    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception 
    {
        List<? extends Person> items = chunk.getItems();
        
        for (Person item : items) 
        {
            System.out.println("PlooP: " + lineAggregator.aggregate(item));
        }        
    }

}
