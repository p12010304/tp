package seedu.address.model.timepoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TimeComparatorTest {
    private static final TimePoint STRING = TimePoint.of("A");
    private static final TimePoint DATE = TimePoint.of(LocalDate.of(2025, 1, 1));
    private static final TimePoint DATETIME = TimePoint.of(LocalDateTime.of(2025, 1, 1, 1, 0));

    @Test
    public void stringLastTestCompare() {
        List<TimePoint> timePointList = Arrays.asList(STRING, DATE);
        Comparator<TimePoint> comparator =
                TimePointComparator.stringTimePointLast(new NeutralComparator());
        timePointList.sort(comparator);
        List<TimePoint> expectedList = Arrays.asList(DATE, STRING);
        assertEquals(expectedList, timePointList);
        List<TimePoint> timePointList2 = Arrays.asList(DATE, STRING);
        timePointList2.sort(comparator);
        assertEquals(expectedList, timePointList2);
    }

    @Test
    public void dateTimeLaterTestCompare() {
        List<TimePoint> timePointList = Arrays.asList(DATETIME, DATE);
        Comparator<TimePoint> comparator =
                TimePointComparator.ifSameDayDateTimePointFirst(new NeutralComparator());
        timePointList.sort(comparator);
        List<TimePoint> expectedList = Arrays.asList(DATE, DATETIME);
        assertEquals(expectedList, timePointList);
        List<TimePoint> timePointList2 = Arrays.asList(DATE, DATETIME);
        timePointList2.sort(comparator);
        assertEquals(expectedList, timePointList2);
    }

    @Test
    public void dateTimeLaterTestCompareSameDates() {
        List<TimePoint> timePointList = Arrays.asList(DATE, DATE);
        Comparator<TimePoint> comparator =
                TimePointComparator.ifSameDayDateTimePointFirst(new NeutralComparator());
        timePointList.sort(comparator);
        List<TimePoint> expectedList = Arrays.asList(DATE, DATE);
        assertEquals(expectedList, timePointList);
    }

    private static class NeutralComparator implements Comparator<TimePoint> {
        @Override
        public int compare(TimePoint o1, TimePoint o2) {
            return 0;
        }
    }
}
