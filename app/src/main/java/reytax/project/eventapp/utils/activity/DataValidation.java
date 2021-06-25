package reytax.project.eventapp.utils.activity;

import reytax.project.eventapp.utils.api.CountryStateCityApi;
import reytax.project.eventapp.utils.api.ProfanityApi;

public abstract class DataValidation {

    public static boolean checkUsername(String value) {
        if(RegexVerification.isValidUsername(value))
            return true;
        return false;
    }

    public static boolean checkName(String value) {
        if(RegexVerification.isValidName(value))
            return true;
        return false;
    }

    public static boolean checkCountry(String value) {
        if(CountryStateCityApi.getCountries().contains(value))
            return true;
        return false;
    }

    public static boolean checkState(String value) {
        if(CountryStateCityApi.getStates().contains(value))
            return true;
        return false;
    }

    public static boolean checkCity(String value) {
        if(CountryStateCityApi.getCities().contains(value))
            return true;
        return false;
    }

    public static boolean checkPhonenumber(String value) {
        if(RegexVerification.isValidPhonenumber(value))
            return true;
        return false;
    }

    public static boolean checkDescription(String value) {
        checkProfanity(value);

        int timer = 0;
        while(!ProfanityApi.getIsDone() && timer < 3000)
            try {
                Thread.sleep(50);
                timer += 50;
            } catch (InterruptedException e) {
                e.printStackTrace();
        }

        if(ProfanityApi.getIsProfane())
            return false;
        return true;
    }


    public static void checkProfanity(String value) {
        value = value.replace(" ", "%20");
        System.out.println(value);
        ProfanityApi profanityApi = new ProfanityApi();
        profanityApi.execute(value);
    }
}
