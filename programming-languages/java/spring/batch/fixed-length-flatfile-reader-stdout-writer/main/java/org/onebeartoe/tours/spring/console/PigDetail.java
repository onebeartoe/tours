package org.onebeartoe.tours.spring.console;

import lombok.Getter;
import org.springframework.batch.item.file.transform.Range;

@Getter
public enum PigDetail
{
    FIRST_NAME(new Range(1, 3)),
    LAST_NAME(new Range(8, 15)),
    DISTANCE(new Range(16, 18) );

    private final Range range;

    PigDetail(Range range) {
        this.range = range;
    }

    public static Range[] getColumnRanges()
    {
        return new Range[]
        {
            FIRST_NAME.getRange(),
            LAST_NAME.getRange(),
            DISTANCE.getRange()
        };
    }
}
