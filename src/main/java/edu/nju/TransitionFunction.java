package edu.nju;

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

    public String getInput() {
        return input;
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

}
