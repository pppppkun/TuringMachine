package edu.nju;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-30 21:08
 */
public class FiniteStateMachine {

    private final Map<String, State> states;
    private final Set<TransitionFunction> transitionFunctions;

    public FiniteStateMachine() {
        states = new HashMap<>();
        transitionFunctions = new HashSet<>();
    }

    public void putState(State state) {
        states.put(state.getQ(), state);
    }

    public State getState(String q) {
        return states.get(q);
    }

    public void putTransitionFunction(TransitionFunction tf) {
        this.getState(tf.getFromState()).addTransitionFunction(tf);
        this.transitionFunctions.add(tf);
    }

    public Set<TransitionFunction> getTransitionFunctions() {
        return transitionFunctions;
    }
}
