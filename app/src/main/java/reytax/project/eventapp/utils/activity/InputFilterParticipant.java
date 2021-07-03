package reytax.project.eventapp.utils.activity;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterParticipant implements InputFilter {

    private final int MIN_PARTICIPANTS = 1, MAX_PARTICIPANTS = 1000;

    @Override
    public CharSequence filter(CharSequence charSequence, int first, int last, Spanned spanned, int datafist, int datalast) {
        try {
            String value = spanned.subSequence(0, datafist) + charSequence.subSequence(first, last).toString() + spanned.subSequence(datalast, spanned.length());
            int input = Integer.parseInt(value);
            if (isInRange(MIN_PARTICIPANTS, MAX_PARTICIPANTS, input))
                return null;
        } catch (NumberFormatException nfe) {

        }

        return "";
    }

    private boolean isInRange(int firstValue, int secondValue, int input) {

        return secondValue > firstValue ? input >= firstValue && input <= secondValue : input >= secondValue && input <= firstValue;

    }
}
