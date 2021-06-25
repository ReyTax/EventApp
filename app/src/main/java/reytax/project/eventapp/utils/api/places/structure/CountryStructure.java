package reytax.project.eventapp.utils.api.places.structure;

import java.util.ArrayList;

public class CountryStructure extends ArrayList<CountryStructure.Container> {

    public class Container {

        private String country_name;
        private String country_short_name;
        private String country_phone_code;

        public Container(String country_name,String country_short_name,String country_phone_code) {
            this.country_name = country_name;
            this.country_short_name = country_short_name;
            this.country_phone_code = country_phone_code;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getCountry_short_name() {
            return country_short_name;
        }

        public void setCountry_short_name(String country_short_name) {
            this.country_short_name = country_short_name;
        }

        public String getCountry_phone_code() {
            return country_phone_code;
        }

        public void setCountry_phone_code(String country_phone_code) {
            this.country_phone_code = country_phone_code;
        }

    }


}
