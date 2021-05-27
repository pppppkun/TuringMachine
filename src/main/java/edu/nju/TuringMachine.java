package edu.nju;

import java.util.*;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:15
 */
public class TuringMachine {

    // 状态集合
    private Set<String> Q;
    // 输入符号集
    private Set<Character> S;
    // 磁带符号集
    private Set<Character> G;
    // 初始状态
    private String q;
    // 终止状态集
    private Set<String> F;
    // 空格符号
    private Character B;
    // 磁带数
    private Integer tapeNum;
    // 磁道数
    private ArrayList<Integer> trackNum;
    // 迁移函数集
    private Map<TransitionFunction, TransitionFunction> Delta;

    public TuringMachine(Set<String> Q, Set<Character> S, Set<Character> G, String q, Set<String> F, char B, int tapeNum, Set<TransitionFunction> Delta) {
        this.Q = Q;
        this.S = S;
        this.G = G;
        this.q = q;
        this.F = F;
        this.B = B;
        this.tapeNum = tapeNum;
        this.Delta = new HashMap<>();
        for(TransitionFunction t : Delta) {
            this.Delta.put(t, t);
        }
//        this.Delta = Delta;
    }

    //TODO
    public TuringMachine(String tm) {
        String[] var = tm.split("\n");
//        Delta = new HashSet<>();
        Delta = new HashMap<>();
        int i = 0;
        for (String s : var) {
            i++;
            s = s.trim();
            String[] res;
            if (s.length() != 0 && !Utils.IsComment(s)) {
                switch (s.substring(0, 2)) {
                    case "#Q":
                        Q = new HashSet<>();
                        res = Utils.SplitString(s);
                        if (res == null) System.err.println("Error: " + i);
                        else Q.addAll(Arrays.asList(res));
                        break;
                    case "#S":
                        S = new HashSet<>();
                        res = Utils.SplitString(s);
                        if (res == null) System.err.println("Error: " + i);
                        else Arrays.asList(res).forEach(s1 -> S.add(s1.charAt(0)));
                        break;
                    case "#G":
                        G = new HashSet<>();
                        res = Utils.SplitString(s);
                        if (res == null) System.err.println("Error: " + i);
                        else Arrays.asList(res).forEach(s1 -> G.add(s1.charAt(0)));
                        break;
                    case "#F":
                        F = new HashSet<>();
                        res = Utils.SplitString(s);
                        if (res == null) System.err.println("Error: " + i);
                        else F.addAll(Arrays.asList(res));
                        break;
                    case "#q":
                        if (s.charAt(2) != '0') System.err.println("Error: " + i);
                        else q = s.split(" ")[2];
                        break;
                    case "#B":
                        B = s.split(" ")[2].charAt(0);
                        break;
                    case "#N":
                        tapeNum = Integer.parseInt(s.split(" ")[2]);
                        break;
                    case "#D":
                        s = s.substring(3);
                        res = s.split(" ");
                        if (res[1].length() != res[2].length()) System.err.println("Error: " + i);
                        else {
                            assert Q != null;
                            if (!Q.contains(res[0])) {
                                System.err.println("Error: 7");
                                break;
                            }
                            if (!Q.contains(res[4])) {
                                System.err.println("Error: 7");
                                break;
                            }
                            if (!G.containsAll(Utils.stringToCharSet(res[1]))) {
                                System.err.println("Error: 8");
                                break;
                            }
                            if (!G.containsAll(Utils.stringToCharSet(res[2]))) {
                                System.err.println("Error: 8");
                                break;
                            }
                        }
                        TransitionFunction transitionFunction = new TransitionFunction(s);
                        Delta.put(transitionFunction, transitionFunction);
                        break;
                    default:
                        System.err.println("Error: " + i);
                        break;
                }
            }
        }
        assert F != null;
        if (!Utils.isSubSet(F, Q)) System.err.println("Error: 3");
        if (S.contains(B)) System.err.println("Error: 4");
        if (!G.contains(B)) System.err.println("Error: 5");
        if (!Utils.isSubSet(S, G)) System.err.println("Error: 6");
        if (Q == null) System.err.println("Error: lack Q");
        if (S == null) System.err.println("Error: lack S");
        if (G == null) System.err.println("Error: lack G");
        if (F == null) System.err.println("Error: lack F");
        if (q == null) System.err.println("Error: lack q0");
        if (B == null) System.err.println("Error: lack B");
        if (tapeNum == null) System.err.println("Error: lack N");
        if (Delta.size() == 0) System.err.println("Error: lack D");

    }

    public String getState() {
        return q;
    }

    //TODO
    public ArrayList<String> delta(String Z) {
        TransitionFunction t = chooseDelta(q, Z);
        q = t.getToState();
        return new ArrayList<>(Arrays.asList(t.getOutput(), t.getDirection()));
    }

    private TransitionFunction chooseDelta(String q, String Z) {
        TransitionFunction transitionFunction = new TransitionFunction();
        transitionFunction.setFromState(q);
        transitionFunction.setInput(Z);
        return Delta.get(transitionFunction);
    }

    //TODO
    public boolean isStop(String Z) {
        return F.contains(q) || chooseDelta(q, Z) == null;
    }

    public boolean checkTape(Set<Character> tape) {
        if(!G.containsAll(tape)) {
            System.err.println("Error: 1");
            return false;
        }
        return true;
    }

    public boolean checkTapeNum(int tapeNum){
        return tapeNum == this.tapeNum;
    }

    public Character getB() {
        return B;
    }

    //TODO
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.SetToString("Q", Q));
        stringBuilder.append(Utils.SetToString("S", S));
        stringBuilder.append(Utils.SetToString("G", G));
        stringBuilder.append(Utils.SetToString("F", F));
        stringBuilder.append("#q0 = ").append(q).append(System.lineSeparator());
        stringBuilder.append("#B = ").append(B).append(System.lineSeparator());
        stringBuilder.append("#N = ").append(tapeNum).append(System.lineSeparator());
//        Delta.forEach(transitionFunction -> stringBuilder.append(transitionFunction.toString()));
        Delta.values().forEach(transitionFunction -> stringBuilder.append(transitionFunction.toString()));
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
