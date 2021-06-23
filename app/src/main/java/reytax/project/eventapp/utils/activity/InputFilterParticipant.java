package reytax.project.eventapp.utils.activity;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterParticipant implements InputFilter {

    private final int MIN_PARTICIPANTS = 1, MAX_PARTICIPANTS = 1000;

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            String newVal = dest.subSequence(0, dstart) + source.subSequence(start, end).toString() + dest.subSequence(dend, dest.length());
            int input = Integer.parseInt(newVal);
            if (isInRange(MIN_PARTICIPANTS, MAX_PARTICIPANTS, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
