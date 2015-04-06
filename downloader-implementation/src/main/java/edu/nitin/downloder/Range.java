package edu.nitin.downloder;

import java.util.Comparator;

/**
 * Created by nitin.verma on 04/04/15.
 */
public class Range {
    final long start;
    final long end;

    public Range(final long start, final long end) {
        if (start > end) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
    }

    public boolean hasOverlapWith(final Range other) {

        if (other != null) {
            if (this.start <= other.start && other.start <= this.end) {
                return true;
            }

            if (this.start <= other.end && other.end <= this.end) {
                return true;
            }

            if (other.start <= this.start && this.start <= other.end) {
                return true;
            }

            if (other.start <= this.end && this.end <= other.end) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return start + "-" + end;
    }

    public static class NoRangeOverlapComparator implements Comparator<Range> {

        @Override
        public int compare(final Range lhs, final Range rhs) {
            int compare = 0;
            if (lhs == null || rhs == null) {
                throw new RuntimeException("Can not have null ranges.");
            }
            if (lhs == rhs) {
                return 0;
            }
            if (lhs.hasOverlapWith(rhs)) {
                throw new RuntimeException("Ranges have overlap; " + lhs + "::" + rhs);
            }

            if (lhs.start < rhs.start) {
                compare = -1;
            }
            else if (lhs.start > rhs.start) {
                compare = 1;
            }

            return compare;
        }
    }
}
