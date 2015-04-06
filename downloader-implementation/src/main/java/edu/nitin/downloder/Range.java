package edu.nitin.downloder;

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
        }
        else {
            this.start = start;
            this.end = end;
        }
    }

    @Override
    public String toString() {
        return start + "-" + end;
    }
}
