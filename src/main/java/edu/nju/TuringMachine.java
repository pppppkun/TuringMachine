package edu.nju;

import java.util.*;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:15
 */
public class TuringMachine {

    // 状态集合
    private final Map<String, State> Q;
    // 输入符号集
    private Set<Character> S;
    // 磁带符号集
    private Set<Character> G;
    // 初始状态
    private String q0;
    // State
    private State q;
    // 终止状态集
    private Set<String> F;
    // 空格符号
    private Character B;
    // 磁带数
    private Integer tapeNum;

    public TuringMachine(Set<String> Q, Set<Character> S, Set<Character> G, String q, Set<String> F, char B, int tapeNum, Set<TransitionFunction> Delta) {
        this.S = S;
        this.G = G;
        this.F = F;
        this.B = B;
        this.q0 = q;
        this.Q = new HashMap<>();
        for (String state : Q) {
            State temp = new State(state);
            temp.setQ(state);
            this.Q.put(state, temp);
        }
        this.q = this.Q.get(q0);
        this.tapeNum = tapeNum;
        for (TransitionFunction t : Delta) {
            this.Q.get(t.getFromState()).addTransitionFunction(t);
//            fsm.getState(t.getFromState()).addTransitionFunction(t);
        }
    }

    //TODO
    public TuringMachine(String tm) {
        String[] var = tm.split(System.lineSeparator());
        Q = new HashMap<>();
        int i = 0;
        for (String s : var) {
            i++;
            s = s.trim();
            if (s.length() != 0 && !Utils.IsComment(s)) {
                switch (s.substring(0, 2)) {
                    // TODO
                    case "#Q":
                        createQ(s, i);
                        break;
                    case "#S":
                        createS(s, i);
                        break;
                    case "#G":
                        createG(s, i);
                        break;
                    case "#F":
                        createF(s, i);
                        break;
                    case "#q":
                        if (s.charAt(2) != '0') System.err.println("Error: " + i);
                        else {
                            q0 = s.split(" ")[2];
                            q = Q.get(s.split(" ")[2]);
                        }
                        break;
                    case "#B":
                        B = s.split(" ")[2].charAt(0);
                        break;
                    case "#N":
                        tapeNum = Integer.parseInt(s.split(" ")[2]);
                        break;
                    case "#D":
                        s = s.substring(3);
                        resolverTransitionFunction(s, i);
                        break;
                    default:
                        System.err.println("Error: " + i);
                        break;
                }
            }
        }
        assert F != null;
        if (!Utils.isSubSet(F, Q.keySet())) System.err.println("Error: 3");
        if (S.contains(B)) System.err.println("Error: 4");
        if (!G.contains(B)) System.err.println("Error: 5");
        if (!Utils.isSubSet(S, G)) System.err.println("Error: 6");
        if (Q.size() == 0) System.err.println("Error: lack Q");
        if (S == null) System.err.println("Error: lack S");
        if (G == null) System.err.println("Error: lack G");
        if (F == null) System.err.println("Error: lack F");
        if (q == null) System.err.println("Error: lack q0");
        if (B == null) System.err.println("Error: lack B");
        if (tapeNum == null) System.err.println("Error: lack N");
        int temp = 0;
        for (State s : Q.values()) temp += s.getDeltas().size();
        if (temp == 0) System.err.println("Error: lack D");

    }

    private void createQ(String s, int lineno) {
        String[] res = Utils.SplitString(s);
        if (res == null) System.err.println("Error: " + lineno);
        else {
            for (String state : Arrays.asList(res)) {
                State temp = new State(state);
                Q.put(state, temp);
            }
        }
    }

    private void createS(String s, int lineno) {
        S = new HashSet<>();
        String[] res = Utils.SplitString(s);
        if (res == null) System.err.println("Error: " + lineno);
        else Arrays.asList(res).forEach(s1 -> S.add(s1.charAt(0)));
    }

    private void createG(String s, int lineno) {
        G = new HashSet<>();
        String[] res = Utils.SplitString(s);
        if (res == null) System.err.println("Error: " + lineno);
        else Arrays.asList(res).forEach(s1 -> G.add(s1.charAt(0)));
    }

    private void createF(String s, int lineno) {
        F = new HashSet<>();
        String[] res = Utils.SplitString(s);
        if (res == null) System.err.println("Error: " + lineno);
        else F.addAll(Arrays.asList(res));
    }


    public String getState() {
        return q.getQ();
    }

    //TODO
    public ArrayList<String> delta(String Z) {
        TransitionFunction t = q.getDelta(Z);
        q = Q.get(q.getDelta(Z).getToState());
        return new ArrayList<>(Arrays.asList(t.getOutput(), t.getDirection()));
    }

    //TODO
    public boolean isStop(String Z) {
        return F.contains(q.getQ()) || q.getDelta(Z) == null;
    }

    public boolean checkTape(Set<Character> tape) {
        tape.remove(B);
        if (!S.containsAll(tape)) {
            System.err.println("Error: 1");
            return false;
        }
        return true;
    }

    public boolean checkTapeNum(int tapeNum) {
        return tapeNum == this.tapeNum;
    }

    public Character getB() {
        return B;
    }

    private void resolverTransitionFunction(String s, int lineno) {
        ArrayList<StringBuilder> inputs;
        String[] outputPattern;
        String[] var = s.split(" ");
        String var2 = var[1];
        inputs = resolverInput(var2);
        var2 = var[2];
        outputPattern = resolverOutputPattern(var2).split(System.lineSeparator());
        for (StringBuilder input : inputs) {
            String tf = getTransitionFunctionString(var, outputPattern, input.toString());
            checkTransitionFunction(tf, lineno);
            TransitionFunction transitionFunction = new TransitionFunction(tf);
            if (Q.get(transitionFunction.getFromState()).containDelta(transitionFunction)) {
                TransitionFunction temp = Q.get(transitionFunction.getToState()).getDelta(transitionFunction.getInput());
                String originalOutput = temp.getOutput();
                String originalToState = temp.getToState();
                String originalDirection = temp.getDirection();
                if (!originalOutput.equals(transitionFunction.getOutput())
                        || !originalToState.equals(transitionFunction.getToState())
                        || !originalDirection.equals(transitionFunction.getDirection())) {
                    System.err.println("Error: 9");
                }
            }
            Q.get(transitionFunction.getFromState()).addTransitionFunction(transitionFunction);
        }
    }

    private int eval(int o1, int o2, char op) {
        switch (op) {
            case '+':
                return o1 + o2;
            case '-':
                return o1 - o2;
            case '*':
                return o1 * o2;
            case '/':
                return o1 / o2;
            default:
                return 0;
        }
    }

    private ArrayList<StringBuilder> resolverInput(String var2){
        ArrayList<StringBuilder> input = new ArrayList<>();
        input.add(new StringBuilder());
        for (int i = 0; i < var2.length(); i++) {
            if (var2.charAt(i) == '!') {
                if (var2.charAt(i + 1) == '{') {
                    int j = i + 2;
                    for (; j < var2.length(); j++) {
                        if (var2.charAt(j) == '}') {
                            break;
                        }
                    }
                    String[] chars = var2.substring(i + 2, j).split(",");
                    Set<Character> temp_set = new HashSet<>(G);
                    i = j;
                    for (j = 0; j < chars.length; j++) temp_set.remove(chars[j].charAt(0));
                    input = resolverExpect(input, temp_set);
                } else {
                    char expect_char = var2.charAt(i + 1);
                    input = resolverExpect(expect_char, input);
                    i = i + 1;
                }
            } else if (var2.charAt(i) == '[') {
                char operand1 = var2.charAt(i + 1);
                char op = var2.charAt(i + 2);
                char operand2 = var2.charAt(i + 3);
                i = i + 4;
                for (StringBuilder sb : input)
                    sb.append(eval(Integer.parseInt(operand1 + ""), Integer.parseInt(operand2 + ""), op));
            } else if (var2.charAt(i) == '{') {
                int j = i + 1;
                for (; j < var2.length(); j++) {
                    if (var2.charAt(j) == '}') {
                        break;
                    }
                }
                String[] chars = var2.substring(i + 1, j).split(",");
                i = j;
                Set<Character> temp_set = new HashSet<>();
                for (String c : chars) temp_set.add(c.charAt(0));
                input = resolverExpect(input, temp_set);
            } else {
                for (StringBuilder sb : input) sb.append(var2.charAt(i));
            }
        }
        return input;
    }

    private String resolverOutputPattern(String var2) {
        StringBuilder outputPattern = new StringBuilder();
        for (int i = 0; i < var2.length(); i++) {
            if (var2.charAt(i) == '$') {
                outputPattern.append("$ ").append(var2, i + 2, var2.indexOf("}", i + 1));
                i = i + var2.indexOf("}", i + 1);
            } else if (var2.charAt(i) == '[') {
                int flag1 = 0;
                int flag2 = 0;
                // resolver first operand
                String operand1 = var2.charAt(i + 1) + "";
                if (var2.charAt(i + 1) == '$') {
                    operand1 = var2.substring(i + 3, var2.indexOf("}", i + 1));
                    flag1 = 1;
                    i = var2.indexOf("}", i + 1) + 1;
                } else i = i + 2;

                char op = var2.charAt(i);
                // resolver second operand
                String operand2 = var2.charAt(i + 1) + "";
                if (var2.charAt(i + 1) == '$') {
                    operand2 = var2.substring(i + 3, var2.indexOf("}", i + 1));
                    flag2 = 1;
                    i = var2.indexOf("}", i + 1) + 1;
                } else i = i + 2;

                int res;
                if (flag1 == 1 || flag2 == 1) {
                    outputPattern.append("c ");
                    if (flag1 == 1) outputPattern.append("$ ").append(operand1).append(" ");
                    else outputPattern.append(operand1).append(" ");
                    outputPattern.append(op).append(" ");
                    if (flag2 == 1) outputPattern.append("$ ").append(operand2).append(" ");
                    else outputPattern.append(operand2).append(" ");
                } else {
                    res = eval(Integer.parseInt(operand1), Integer.parseInt(operand2), op);
                    outputPattern.append(res);
                }
            } else {
                outputPattern.append(var2.charAt(i));
            }
            if (i != var2.length()) outputPattern.append(System.lineSeparator());
        }
        return outputPattern.toString();
    }

    private String getTransitionFunctionString(String[] var, String[] outputPattern, String input) {
        StringBuilder out = new StringBuilder();
        out.append(var[0]).append(" ");
        out.append(input).append(" ");
        for (String pattern : outputPattern) {
            out.append(matchOutputPattern(input, pattern));
        }
        out.append(" ").append(var[3]).append(" ").append(var[4]);
        return out.toString();
    }

    private String matchOutputPattern(String input, String pattern) {
        StringBuilder out = new StringBuilder();
        String[] v = pattern.split(" ");
        char c = v[0].charAt(0);
        if (c == '$') out.append(input.charAt(Integer.parseInt(v[1])));
        else if (c == 'c') {
            ArrayList<Integer> operand = new ArrayList<>();
            char op = ' ';
            for (int i = 1; i < v.length; i++) {
                if (v[i].charAt(0) == '$') {
                    operand.add(Integer.parseInt(input.charAt(Integer.parseInt(v[i + 1])) + ""));
                    i = i + 1;
                } else if (v[i].charAt(0) == '+' || v[i].charAt(0) == '-' || v[i].charAt(0) == '*' || v[i].charAt(0) == '/') {
                    op = v[i].charAt(0);
                } else operand.add(Integer.parseInt(v[i]));
            }
            out.append(eval(operand.get(0), operand.get(1), op));
        } else out.append(c);
        return out.toString();
    }

    private void checkTransitionFunction(String transitionFunction, int lineno) {
        String[] res = transitionFunction.split(" ");
        if (res[1].length() != res[2].length()) {
            System.err.println("Error: " + lineno);
        }
        assert Q != null;
        if (!Q.containsKey(res[0])) {
            System.err.println("Error: 7");
        }
        if (!Q.containsKey(res[4])) {
            System.err.println("Error: 7");
        }
        if (!G.containsAll(Utils.stringToCharSet(res[1]))) {
            System.err.println("Error: 8");
        }
        if (!G.containsAll(Utils.stringToCharSet(res[2]))) {
            System.err.println("Error: 8");
        }
    }

    private ArrayList<StringBuilder> resolverExpect(char expect_char, ArrayList<StringBuilder> have) {
        Set<Character> temp_set = new HashSet<>(G);
        temp_set.remove(expect_char);
        return resolverExpect(have, temp_set);
    }

    private ArrayList<StringBuilder> resolverExpect(ArrayList<StringBuilder> have, Set<Character> temp_set) {
        ArrayList<StringBuilder> copy_input = new ArrayList<>();
        for (StringBuilder builder : have) {
            for (char c : temp_set) {
                StringBuilder stringBuilder = new StringBuilder(builder);
                stringBuilder.append(c);
                copy_input.add(stringBuilder);
            }
        }
        return copy_input;
    }


    //TODO
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.SetToString("Q", Q.keySet()));
        stringBuilder.append(Utils.SetToString("S", S));
        stringBuilder.append(Utils.SetToString("G", G));
        stringBuilder.append(Utils.SetToString("F", F));
        stringBuilder.append("#q0 = ").append(q0).append(System.lineSeparator());
        stringBuilder.append("#B = ").append(B).append(System.lineSeparator());
        stringBuilder.append("#N = ").append(tapeNum).append(System.lineSeparator());
        for (State s : Q.values())
            s.getDeltas().forEach(transitionFunction -> stringBuilder.append(transitionFunction.toString()));
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

}
