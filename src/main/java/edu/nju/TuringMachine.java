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
    char B;
    // 磁带数
    int tapeNum;
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
            if (s.length() != 0 && !IOUtils.IsComment(s)) {
                switch (s.charAt(1)) {
                    case 'Q':
                        Q = new HashSet<>();
                        Q.addAll(Arrays.asList(IOUtils.SplitString(s)));
                        break;
                    case 'S':
                        S = new HashSet<>();
                        Arrays.asList(IOUtils.SplitString(s)).forEach(s1 -> S.add(s1.charAt(0)));
                        break;
                    case 'G':
                        G = new HashSet<>();
                        Arrays.asList(IOUtils.SplitString(s)).forEach(s1 -> G.add(s1.charAt(0)));
                        break;
                    case 'F':
                        F = new HashSet<>();
                        F.addAll(Arrays.asList(IOUtils.SplitString(s)));
                        break;
                    case 'q':
                        q = s.split(" ")[2];
                        break;
                    case 'B':
                        B = s.split(" ")[2].charAt(0);
                        break;
                    case 'N':
                        tapeNum = Integer.parseInt(s.split(" ")[2]);
                        break;
                    default:
                        if (s.charAt(1) != ' ') {
                            System.err.println();
//                            throw new TMSyntaxErrorException("syntax line: " + i);
                        }
                        else Delta.add(new TransitionFunction(s));
                        break;
                }
            }
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

    public static void main(String[] args) {
        TuringMachine TM = new TuringMachine("; This example program checks if the input string is a a_nb_n.\n" +
                "; Input: a string of a's and b's, e.g. '1001001'\n" +
                "; the finite set of states\n" +
                "#Q = {0, 1, 2, 3, 4}\n" +
                "\n" +
                "; the finite set of input symbols\n" +
                "#S = {a, b}\n" +
                "\n" +
                "; the complete set of tape symbols\n" +
                "#G = {a, b, _}\n" +
                "\n" +
                "; the start state\n" +
                "#q0 = 0\n" +
                "\n" +
                "; the set of final states\n" +
                "#F = {4}\n" +
                "#B = _\n" +
                "#N = 1\n" +
                "\n" +
                "\n" +
                "; the transition functions\n" +
                "\n" +
                "; State 0: start state\n" +
                "0 a _ r 1\n" +
                "0 _ _ r 4\n" +
                "\n" +
                "; State 1:\n" +
                "1 a a r 1\n" +
                "1 b b r 1\n" +
                "1 _ _ l 2\n" +
                "\n" +
                "; State 2:\n" +
                "2 b _ l 3\n" +
                "\n" +
                "; State 3:\n" +
                "3 a a l 3\n" +
                "3 b b l 3\n" +
                "3 _ _ r 0");

        System.out.println(TM);

    }
}
