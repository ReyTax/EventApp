package reytax.project.eventapp.utils.api.places.structure;

import java.util.ArrayList;

public class CityStructure extends ArrayList<CityStructure.Container> {

    public class Container {

        private String city_name;

        public Container(String state_name) {
            this.city_name = state_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String state_name) {
            this.city_name = state_name;
        }
    }
}
