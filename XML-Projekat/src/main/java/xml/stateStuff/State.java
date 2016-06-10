package xml.stateStuff;


/**
 * Created by Daniel on 6/10/2016.
 */
public class State {
    private String state;

    public State(){

    }

    public State(String state){
        this.state = state.trim();
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }
}