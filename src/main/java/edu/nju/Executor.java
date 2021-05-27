package edu.nju;

import java.util.ArrayList;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-25 23:53
 */
public class Executor {

    ArrayList<Tape> tapes;
    TuringMachine tm;
    int steps = 0;

    Executor(TuringMachine tm, ArrayList<Tape> tapes) {
        this.tm = tm;
        LoadTape(tapes);
    }

    //TODO
    public ArrayList<Tape> execute() {
        String Z = snapshotTape();
        if (!tm.isStop(Z)) {
            ArrayList<String> ret = tm.delta(Z);
            updateTape(ret.get(0));
            moveHeads(ret.get(1));
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
        StringBuilder stringBuilder = new StringBuilder();
        int maxTrackLen = 0;
        for (Tape t : tapes) {
            maxTrackLen = Math.max(maxTrackLen, t.tracks.size());
        }
        int colonIndex = Math.max(maxTrackLen + 5, tapes.size() + 4);
        stringBuilder.append("Step").append(spaceString(colonIndex - 4)).append(":").append(" ").append(steps).append(System.lineSeparator());
        int tapeNum = 0;
        for (Tape t : tapes) {
            stringBuilder.append("Tape").append(tapeNum).append(spaceString(colonIndex - ("Tape" + tapeNum).length())).append(":").append(System.lineSeparator());
            int trackNum = 0;
            for (StringBuilder sb : t.tracks) {
                String track = sb.toString();
                int stateRecord = 0;
                int start = -1;
                int end = -1;
                for (int j = 0; j < track.length(); j++) {
                    if(track.charAt(j) == tm.getB() && stateRecord == 0) stateRecord = 1;
                    if(track.charAt(j) != tm.getB() && stateRecord == 1) {
                        stateRecord = 2;
                        start = j;
                    }
                    if(track.charAt(j) != tm.getB() && stateRecord == 2) {
                        end = j;
                    }
                }
                track = track.substring(start , end);
                stringBuilder.append("Track").append(trackNum).append(spaceString(colonIndex - ("Track" + trackNum++).length())).append(": ").append(track).append(System.lineSeparator());
            }
            stringBuilder.append("Head").append(tapeNum).append(spaceString(colonIndex - ("Head" + tapeNum).length())).append(": ").append(t.getHead()).append(System.lineSeparator());
        }
        stringBuilder.append("State").append(spaceString(colonIndex - 5)).append(": ").append(tm.getState());
        return stringBuilder.toString();
    }

    private String spaceString(int numOfSpace) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numOfSpace; i++) stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    private void updateTape(String newTapes) {
        for (int i = 0; i < tapes.size(); i++) tapes.get(i).updateTape(newTapes);
    }

    private void moveHeads(String direction) {
        for (int i = 0; i < tapes.size(); i++) tapes.get(i).updateHead(direction.charAt(i));
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
        System.out.println(executor.snapShot());
    }


}
