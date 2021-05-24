package edu.nju;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 16:15
 */
public class TuringMachine {

    // 状态集合
    Set<String> Q;
    // 输入符号集
    Set<Character> S;
    // 磁带符号集
    Set<Character> G;
    // 初始状态
    String q;
    // 终止状态集
    Set<String> F;
    // 空格符号
    Character B;
    // 磁带数
    Integer tapeNum;
    // 迁移函数集
    Set<TransitionFunction> Delta;

    public TuringMachine(Set<String> Q, Set<Character> S, Set<Character> G, String q, Set<String> F, char B, int tapeNum, Set<TransitionFunction> Delta) {
        this.Q = Q;
        this.S = S;
        this.G = G;
        this.q = q;
        this.F = F;
        this.B = B;
        this.tapeNum = tapeNum;
        this.Delta = Delta;
    }

    //TODO
    public TuringMachine(String tm) {
        String[] var = tm.split("\n");
        Delta = new HashSet<>();
        int i = 0;
        for (String s : var) {
            i++;
            s = s.trim();
            String[] res;
            if (s.length() != 0 && !IOUtils.IsComment(s)) {
                switch (s.substring(0, 2)) {
                    case "#Q":
                        Q = new HashSet<>();
                        res = IOUtils.SplitString(s);
                        if(res == null) System.err.println("Error: " + i);
                        else Q.addAll(Arrays.asList(res));
                        break;
                    case "#S":
                        S = new HashSet<>();
                        res = IOUtils.SplitString(s);
                        if(res == null) System.err.println("Error: " + i);
                        else Arrays.asList(res).forEach(s1 -> S.add(s1.charAt(0)));
                        break;
                    case "#G":
                        G = new HashSet<>();
                        res = IOUtils.SplitString(s);
                        if(res == null) System.err.println("Error: " + i);
                        else Arrays.asList(res).forEach(s1 -> G.add(s1.charAt(0)));
                        break;
                    case "#F":
                        F = new HashSet<>();
                        res = IOUtils.SplitString(s);
                        if(res == null) System.err.println("Error: " + i);
                        else F.addAll(Arrays.asList(res));
                        break;
                    case "#q":
                        if(s.charAt(2) != '0') System.err.println("Error: "+ i);
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
                        if(res[1].length() != res[2].length()) System.err.println("Error: "+i);
                        else Delta.add(new TransitionFunction(s));
                        break;
                    default:
                        System.err.println("Error: " + i);
                        break;
                }
            }
        }
        if(Q==null) System.err.println("Error: lack Q");
        if(S==null) System.err.println("Error: lack S");
        if(G==null) System.err.println("Error: lack G");
        if(F==null) System.err.println("Error: lack F");
        if(q==null) System.err.println("Error: lack q0");
        if(B==null) System.err.println("Error: lack B");
        if(tapeNum==null) System.err.println("Error: lack N");
        if(Delta.size()==0) {
            System.err.println("Error: lack D");
        }
        else {
            int a = Delta.size();
        }

    }

    //TODO
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(IOUtils.SetToString("Q", Q));
        stringBuilder.append(IOUtils.SetToString("S", S));
        stringBuilder.append(IOUtils.SetToString("G", G));
        stringBuilder.append(IOUtils.SetToString("F", F));
        stringBuilder.append("#q0 = ").append(q).append("\n");
        stringBuilder.append("#B = ").append(B).append("\n");
        stringBuilder.append("#N = ").append(tapeNum).append("\n");
        Delta.forEach(transitionFunction -> stringBuilder.append(transitionFunction.toString()));
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }
}
