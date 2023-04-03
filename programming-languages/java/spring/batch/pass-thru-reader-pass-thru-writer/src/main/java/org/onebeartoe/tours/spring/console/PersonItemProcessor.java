
package org.onebeartoe.tours.spring.console;

import com.example.batchprocessing.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person>
{
    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception
    {
        Person transformedPerson = null;
        
        boolean transform = false;
        
        if(transform)
        {                     
            final String firstName = person.getFirstName().toUpperCase();
            final String lastName = person.getLastName().toUpperCase();

            transformedPerson = new Person(firstName, lastName);
            
            log.info("Converted (" + person + ") into (" + transformedPerson + ")");
        }
        else
        {
            transformedPerson = person;
        }

        return transformedPerson;
    }
}
