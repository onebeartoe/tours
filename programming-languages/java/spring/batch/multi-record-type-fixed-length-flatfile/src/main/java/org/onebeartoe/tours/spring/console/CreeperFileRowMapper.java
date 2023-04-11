
package org.onebeartoe.tours.spring.console;

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
        var line = fieldSet.readString(CreeperFieldnames.CONTACT_RECORD);
        var first = fieldSet.readString(CreeperFieldnames.FIRST_NAME);
        var last = fieldSet.readString(CreeperFieldnames.LAST_NAME);
        
        Person p = new Person(line, first, last);
        
        return p;
    }
}
