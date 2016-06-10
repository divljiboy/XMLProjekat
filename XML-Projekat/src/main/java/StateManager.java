/**
 * Created by Daniel on 6/10/2016.
 */
public class StateManager {

    public enum States{
        PREDLAGANJE_AKATA,
        PREDLAGANJE_AMANDMANA,
        GLASANJE
    }

    public static States getState(){


        return States.GLASANJE;
    }

    public static void setState(States state){


    }

}
