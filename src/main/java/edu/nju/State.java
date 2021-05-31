package edu.nju;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: pkun
 * @CreateTime: 2021-05-30 21:08
 */
public class State {

    private String q;
    private final Map<String, TransitionFunction> delta;

    public State(String q) {
        this.q = q;
        delta = new HashMap<>();
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Map<String, TransitionFunction> getDelta() {
        return delta;
    }

    public void addTransitionFunction(TransitionFunction tf) {
        this.delta.put(tf.getInput(), tf);
    }
}
