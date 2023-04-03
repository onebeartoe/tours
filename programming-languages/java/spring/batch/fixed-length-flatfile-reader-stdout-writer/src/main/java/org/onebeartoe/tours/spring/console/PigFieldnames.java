package org.onebeartoe.tours.spring.console;

public final class PigFieldnames 
{
    private PigFieldnames()
    {
        throw new UnsupportedOperationException("Cannot initialize utility classes");
    }

    public static final String ENTITY = "firstName";
    public static final String OWNER = "lastName";
    public static final String DISTANCE = "distance";

    public static String[] getFieldnames() 
    {
        return new String[]
        {
            ENTITY, OWNER, DISTANCE
        };
    }
}
