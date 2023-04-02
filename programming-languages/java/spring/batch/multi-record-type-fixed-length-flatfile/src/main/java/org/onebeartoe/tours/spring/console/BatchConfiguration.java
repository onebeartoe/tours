
package org.onebeartoe.tours.spring.console;

import com.example.batchprocessing.Person;
import com.example.batchprocessing.PersonItemProcessor;
import java.io.File;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration 
{
    /**
     * The contents of this method are from:
     * 
     *      github/Manfred73/spring-batch-demo/src/main/java/nl/craftsmen/contact/job/reader/ProcessContactsFileReaderConfig.java
     * 
     * 
     */
    @Bean
    public FlatFileItemReader<Person> reader() 
    {
            LineTokenizer lineTokenizer = new ContactsLineTokenizer().createLineTokenizer();

final var lineMapper = createLineMapper(lineTokenizer);

final var logger = new ConditionalLogger();
final var skipRecordCallback = new SkipRecordCallback(logger);

            FlatFileItemReader<Person> reader = new FlatFileItemReader<>();

File pwd = new File(".")            ;
System.out.println("pwd = " + pwd.getAbsolutePath());
File infile  = new File ("src/main/resources/sample-data.csv");
            reader.setResource( new FileSystemResource(infile) );
            
            reader.setLinesToSkip(1);
            reader.setSkippedLinesCallback(skipRecordCallback);
            reader.setLineMapper(lineMapper);
                        
            return reader;
	}            
        
    private LineMapper<Person> createLineMapper(LineTokenizer lineTokenizer) 
    {
        final var contactsFileRowMapper = new ContactsFileRowMapper();
        
        final var mapper = new DefaultLineMapper<Person>();
        mapper.setLineTokenizer(lineTokenizer);
        mapper.setFieldSetMapper(contactsFileRowMapper);
        
        return mapper;
    }        
        
        


	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource) throws SQLException 
        {
            System.out.println("dataSource = " + dataSource);
            
            JdbcBatchItemWriterBuilder<Person> jdbcBuilder = new JdbcBatchItemWriterBuilder<Person>();
            
		return jdbcBuilder
			.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
			.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
			.dataSource(dataSource)
			.build();
	}

	@Bean
	public Job importUserJob(JobRepository jobRepository,
			JobCompletionNotificationListener listener, Step step1) {
		return new JobBuilder("importUserJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.listener(listener)
			.flow(step1)
			.end()
			.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository,
			PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Person> writer) {
		return new StepBuilder("step1", jobRepository)
			.<Person, Person> chunk(10, transactionManager)
			.reader(reader())
			.processor(processor())
			.writer(writer)
			.build();
	}
}
