package team.choodoo.orm.model;

import java.time.LocalDate;

public class TestData {
    public static final TestBean t1 = new TestBean(1, "Qw", 2001);
    public static final TestBean t2 = new TestBean(2, "We", 2002);
    public static final TestBean t3 = new TestBean(3, "Ea", 2003);

    public static final DateBean d1 = new DateBean(1, "asd", LocalDate.MIN);
    public static final DateBean d2 = new DateBean(1, "asd", LocalDate.MAX);
}
