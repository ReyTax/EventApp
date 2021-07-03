package reytax.project.eventapp.utils.activity;

import reytax.project.eventapp.utils.api.places.CountryStateCityApi;
import reytax.project.eventapp.utils.api.filter.ProfanityApi;

public abstract class DataValidation {

    public static boolean checkUsername(String value) {
        if (RegexVerification.isValidUsername(value))
            return true;
        return false;
    }

    public static boolean checkName(String value) {
        if (RegexVerification.isValidName(value))
            return true;
        return false;
    }

    public static boolean checkCountry(String value) {
        if (CountryStateCityApi.getCountries().contains(value))
            return true;
        return false;
    }

    public static boolean checkState(String value) {
        if (CountryStateCityApi.getStates().contains(value))
            return true;
        return false;
    }

    public static boolean checkCity(String value) {
        if (CountryStateCityApi.getCities().contains(value))
            return true;
        return false;
    }

    public static boolean checkPhonenumber(String value) {
        if (RegexVerification.isValidPhonenumber(value))
            return true;
        return false;
    }

    public static void checkDescription(String value) {
        checkProfanity(value);
    }


    public static void checkProfanity(String value) {
        value = value.replace(" ", "%20");
        ProfanityApi.setIsDone(false);
        ProfanityApi profanityApi = new ProfanityApi();
        profanityApi.execute(value);

    }
}
