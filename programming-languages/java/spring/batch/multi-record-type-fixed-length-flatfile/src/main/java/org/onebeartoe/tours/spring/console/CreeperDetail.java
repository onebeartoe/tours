package org.onebeartoe.tours.spring.console;

import lombok.Getter;
import org.springframework.batch.item.file.transform.Range;

@Getter
public enum CreeperDetail
{
    FIRST_NAME(new Range(1, 7)),
    LAST_NAME(new Range(8, 14));

    private final Range range;

    CreeperDetail(Range range)
    {
        this.range = range;
    }

    public static Range[] getColumnRanges() 
    {
        return new Range[]
        {
            FIRST_NAME.getRange(),
            LAST_NAME.getRange()
        };
    }
}
