
package org.onebeartoe.tours.spring.console;

import lombok.NonNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 *
 */
public class PigFileRowMapper implements FieldSetMapper<Person>
{    
    @Override
    public @NonNull Person mapFieldSet(FieldSet fieldSet) 
    {
        var first = fieldSet.readString(PigFieldnames.ENTITY);
        var last = fieldSet.readString(PigFieldnames.OWNER);
        
        Person p = new Person("line", first, last);
        
        return p;
    }
}
