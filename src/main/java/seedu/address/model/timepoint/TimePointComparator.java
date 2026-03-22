package seedu.address.model.timepoint;

import java.util.Comparator;

/**
 * Contains methods to modify {@code TimePoint} Comparators.
 */
public abstract class TimePointComparator implements Comparator<TimePoint> {
    /**
     * Returns a StringTimePoint-friendly comparator that considers {@code StringTimePoint} to be larger than other
     * {@code TimePoint} types.
     * @param comparator a Comparator for comparing {@code TimePoint}
     */
    public static Comparator<TimePoint> stringTimePointLast(Comparator<TimePoint> comparator) {
        return new StringTimePointLast(comparator);
    }

    /**
     * Returns a comparator that considers {@code DateTimePoint} to be less than a  {@code DateTimeTimePoint} when
     * both {@code TimePoint} are on the same day.
     * @param comparator a Comparator for comparing {@code TimePoint}
     */
    public static Comparator<TimePoint> ifSameDayDateTimePointFirst(Comparator<TimePoint> comparator) {
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
            if (tp1 instanceof StringTimePoint) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class IfSameDayDateTimePointFirst extends TimePointComparator {
        private final Comparator<TimePoint> comparator;

        public IfSameDayDateTimePointFirst(Comparator<TimePoint> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(TimePoint tp1, TimePoint tp2) {
            if (tp1.isSameDayAs(tp2) && !(tp1.equals(tp2))) {
                if (tp1 instanceof DateTimePoint && tp2 instanceof DateTimeTimePoint) {
                    return -1;
                } else if (tp2 instanceof DateTimePoint && tp1 instanceof DateTimeTimePoint) {
                    return 1;
                }
            }
            return comparator.compare(tp1, tp2);
        }
    }
}
