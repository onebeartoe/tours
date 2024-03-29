package org.onebeartoe.tours.spring.console;

import lombok.Getter;
import org.springframework.batch.item.file.transform.Range;

@Getter
public enum ContactDetail {

//    CONTACT_RECORD(new Range(1, 380)),
    FIRST_NAME(new Range(1, 3)),
    LAST_NAME(new Range(4, 6))
//    ADDRESS_1(new Range(61, 90)),
//    ADDRESS_2(new Range(91, 120)),
//    ADDRESS_3(new Range(121, 150)),
//    ZIPCODE(new Range(151, 170)),
//    CITY(new Range(171, 200)),
//    STATE(new Range(201, 230)),
//    PHONE(new Range(231, 255)),
//    EMAIL(new Range(256, 305)),
//    IBAN(new Range(306, 339)),
//    SOCIAL_SECURITY_NUMBER(new Range(340, 364)),
//    DATE_OF_DEATH(new Range(365, 372)),
//    DATE_OF_BIRTH(new Range(373, 380))
    ;

    private final Range range;

    ContactDetail(Range range) {
        this.range = range;
    }

    public static Range[] getColumnRanges() {
        return new Range[]{
//                CONTACT_RECORD.getRange(),
                FIRST_NAME.getRange(),
                LAST_NAME.getRange(),
//                ADDRESS_1.getRange(),
//                ADDRESS_2.getRange(),
//                ADDRESS_3.getRange(),
//                ZIPCODE.getRange(),
//                CITY.getRange(),
//                STATE.getRange(),
//                PHONE.getRange(),
//                EMAIL.getRange(),
//                IBAN.getRange(),
//                SOCIAL_SECURITY_NUMBER.getRange(),
//                DATE_OF_DEATH.getRange(),
//                DATE_OF_BIRTH.getRange()
        };
    }
}
