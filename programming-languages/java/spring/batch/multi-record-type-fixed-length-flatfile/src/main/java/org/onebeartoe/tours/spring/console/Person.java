package org.onebeartoe.tours.spring.console;

public class Person 
{
    private String line;

	private String lastName;
	private String firstName;


	public Person(String line, String firstName, String lastName) 
        {
            this.line = line;
            this.firstName = firstName;
            this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() 
        {
		return line + System.lineSeparator() +
                            "firstName: " + firstName + ", lastName: " + lastName;
	}

}
