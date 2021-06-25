package reytax.project.eventapp.structure;

import java.util.ArrayList;

public class StateStructure extends ArrayList<StateStructure.Container> {

    public class Container {

        private String state_name;

        public Container(String state_name) {
            this.state_name = state_name;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }
    }
}
