package seedu.address.model.timepoint;

import java.util.Comparator;

/**
 * Contains methods to modify {@code TimePoint} Comparators.
 */
public abstract class TimePointComparator implements Comparator<TimePoint> {
    public static Comparator<TimePoint> StringTimePointLast(Comparator<TimePoint> comparator) {
        return new StringTimePointLast(comparator);
    }

    public static Comparator<TimePoint> IfSameDayDateTimePointFirst(Comparator<TimePoint> comparator) {
        return new IfSameDayDateTimePointFirst(comparator);
    }

    private static class StringTimePointLast extends TimePointComparator {
        private final Comparator<TimePoint> comparator;

        public StringTimePointLast(Comparator<TimePoint> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(TimePoint tp1, TimePoint tp2) {
            if (tp1 instanceof StringTimePoint == tp2 instanceof StringTimePoint) {
                return comparator.compare(tp1, tp2);
            }
            if (tp1 instanceof StringTimePoint)
            {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static class IfSameDayDateTimePointFirst extends TimePointComparator {
        private final Comparator<TimePoint> comparator;

        public IfSameDayDateTimePointFirst(Comparator<TimePoint> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(TimePoint tp1, TimePoint tp2) {
            if (tp1.isSameDayAs(tp2) && !(tp1.equals(tp2))) {
                if (tp1 instanceof DateTimePoint) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return comparator.compare(tp1, tp2);
        }
    }
}
