package edu.nju;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:19
 */
public class TransitionFunction {

    private String fromState;
    private String toState;
    private String input;
    private String output;
    private String direction;

    public TransitionFunction() {}

    public TransitionFunction(String fromState, String toState, String input, String output, String direction) {
        this.fromState = fromState;
        this.toState = toState;
        this.input = input;
        this.output = output;
        this.direction = direction;
    }

    public TransitionFunction(String s) {
        String[] var = s.split(" ");
        this.fromState = var[0];
        this.input = var[1];
        this.output = var[2];
        this.direction = var[3];
        this.toState = var[4];
    }

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getToState() {
        return toState;
    }

    public String getOutput() {
        return output;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "#D " + fromState + " " + input + " " + output + " " + direction + " " + toState + System.lineSeparator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitionFunction that = (TransitionFunction) o;
        return getFromState().equals(that.getFromState()) && getInput().equals(that.getInput());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFromState(), getInput());
    }

    public static void main(String[] args) {
        TransitionFunction t = new TransitionFunction("1", "2", "3", "4", "5");
        TransitionFunction t1 = new TransitionFunction("2", "3", "4", "5", "6");
        TransitionFunction t2 = new TransitionFunction("3", "4", "5", "6", "7");
        HashMap<TransitionFunction, TransitionFunction> map = new HashMap<>();
        map.put(t, t);
        map.put(t1,t1);
        map.put(t2,t2);
        TransitionFunction g = map.get(t);
        TransitionFunction o = new TransitionFunction();
        o.setFromState("3");
        o.setInput("5");
        TransitionFunction o1 = map.get(o);
        System.out.println(o.hashCode());
        System.out.println(t2.hashCode());
    }
}
