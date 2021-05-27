package edu.nju;

import java.util.ArrayList;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-25 23:53
 */
public class Executor {

    ArrayList<Tape> tapes;
    TuringMachine tm;

    Executor(TuringMachine tm, ArrayList<Tape> tapes) {
        this.tm = tm;
        LoadTape(tapes);
    }

    //TODO
    public ArrayList<Tape> execute() {
        String Z = snapshotTape();
        while (!tm.isStop(Z)) {
            ArrayList<String> ret = tm.delta(Z);
            updateTape(ret.get(0));
            moveHeads(ret.get(1));
            Z = snapshotTape();
        }
        return tapes;
    }

    //TODO
    public void LoadTape(ArrayList<Tape> tapes) {
        this.tapes = tapes;
    }

    //TODO
    private String snapshotTape() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tapes.size(); i++) {
            stringBuilder.append(tapes.get(i).snapShot());
            if (i != tapes.size() - 1) stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    //TODO
    public String snapShot() {
        return null;
    }

    private void updateTape(String newTapes) {
        for(int i = 0;i<tapes.size();i++) tapes.get(i).updateTape(newTapes);
    }

    private void moveHeads(String direction) {
        for(int i = 0;i<tapes.size();i++) tapes.get(i).updateHead(direction.charAt(i));
    }

    public static void main(String[] args) {
        TuringMachine tm = new TuringMachine("; This example program checks if the input string is a a_nb_n.\n" +
                "; Input: a string of a's and b's, e.g. 'aaabbb'\n" +
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
                "\n" +
                "#B = _\n" +
                "\n" +
                " #N = 1\n" +
                "\n" +
                "; the transition functions\n" +
                "\n" +
                "; State 0: start state\n" +
                "#D 0 a _ r 1\n" +
                "#D 0 _ _ r 4\n" +
                "\n" +
                "; State 1:\n" +
                "#D 1 a a r 1\n" +
                "#D 1 b b r 1\n" +
                "#D 1 _ _ l 2\n" +
                "\n" +
                "; State 2:\n" +
                "#D 2 b _ l 3\n" +
                "\n" +
                "; State 3:\n" +
                "#D 3 a a l 3\n" +
                "#D 3 b b l 3\n" +
                "#D 3 _ _ r 0");
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("____aaabbb___"));
        tapes.add(new Tape(tracks, 4));
        Executor executor = new Executor(tm, tapes);
        executor.execute();
    }


}
