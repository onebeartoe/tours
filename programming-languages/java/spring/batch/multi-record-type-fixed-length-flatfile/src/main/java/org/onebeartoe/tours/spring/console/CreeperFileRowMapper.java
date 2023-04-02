
package org.onebeartoe.tours.spring.console;

import com.example.batchprocessing.Person;
import lombok.NonNull;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class CreeperFileRowMapper implements FieldSetMapper<Person>
{

    @Override
    public @NonNull Person mapFieldSet(FieldSet fieldSet) 
    {
        var first = fieldSet.readString(CreeperFieldnames.FIRST_NAME);
        var last = fieldSet.readString(CreeperFieldnames.LAST_NAME);
        
        Person p = new Person(first, last);
        
        return p;
    }
}
