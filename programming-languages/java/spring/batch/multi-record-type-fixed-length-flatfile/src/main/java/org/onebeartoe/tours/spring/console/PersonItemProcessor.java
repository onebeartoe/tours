
package org.onebeartoe.tours.spring.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person>
{
    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception
    {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        String line = person.getLine();

        final Person transformedPerson = new Person(line, firstName, lastName);

        log.info("Converting (" + person + ") into (" + transformedPerson + ")" + 
                System.lineSeparator() + "line is: " + line);

        return transformedPerson;
    }
}
