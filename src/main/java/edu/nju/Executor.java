package edu.nju;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-25 23:53
 */
public class Executor {

    ArrayList<Tape> tapes;
    TuringMachine tm;
    int steps = 0;
    boolean canRun = true;

    public Executor(TuringMachine tm, ArrayList<Tape> tapes) {
        this.tm = tm;
        loadTape(tapes);
    }

    //TODO
    public Boolean execute() {
        String Z = snapShotTape();
        if (!tm.isStop(Z)) {
            ArrayList<String> ret = tm.delta(Z);
            updateTape(ret.get(0));
            moveHeads(ret.get(1));
            steps++;
        }
        return !tm.isStop(snapShotTape());
    }

    //TODO
    public void loadTape(ArrayList<Tape> tapes) {
        canRun = canRun & tm.checkTapeNum(tapes.size());
        if(!canRun) System.err.println("Error: 2");
        for (Tape t : tapes) {
            HashSet<Character> set = new HashSet<>();
            for (StringBuilder s : t.tracks) {
                for (char c : s.toString().toCharArray()) set.add(c);
            }
            canRun = canRun & tm.checkTape(set);
        }
        this.tapes = tapes;
    }

    //TODO
    private String snapShotTape() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tape tape : tapes) stringBuilder.append(tape.snapShot());
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
                int start = -1;
                int end = -1;
                for (int i = 0; i < track.length(); i++) {
                    if (track.charAt(i) != tm.getB()) {
                        start = i;
                        break;
                    }
                }
                for (int i = track.length() - 1; i >= 0; i--) {
                    if (track.charAt(i) != tm.getB()) {
                        end = i;
                        break;
                    }
                }
                if (start == end && start == -1) track = "";
                else track = track.substring(start, end + 1);
                if (t.snapShot().charAt(trackNum) == tm.getB()) {
                    if (t.getHead() > end) {
                        track += tm.getB();
                        end += 1;
                    } else {
                        track = tm.getB() + track;
                        start -= 1;
                    }
                }
                if (trackNum == 0) {
                    if (track.length() == 1) start = end = t.getHead();
                    stringBuilder.append("Index").append(tapeNum).append(spaceString(colonIndex - ("Index" + tapeNum).length())).append(": ").append(indexHelper(start, end)).append(System.lineSeparator());
                }
                stringBuilder.append("Track").append(trackNum).append(spaceString(colonIndex - ("Track" + trackNum).length())).append(": ").append(formatTrack(track, start, end)).append(System.lineSeparator());
                trackNum++;
            }
            stringBuilder.append("Head").append(tapeNum).append(spaceString(colonIndex - ("Head" + tapeNum).length())).append(": ").append(t.getHead()).append(System.lineSeparator());
            tapeNum++;
        }
        stringBuilder.append("State").append(spaceString(colonIndex - 5)).append(": ").append(tm.getState());
        return stringBuilder.toString();
    }

    private String spaceString(int numOfSpace) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numOfSpace; i++) stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    private String indexHelper(int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i != end) stringBuilder.append(i).append(" ");
            else stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

    private String formatTrack(String track, int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i != end) stringBuilder.append(track.charAt(i - start)).append(spaceString((i + "").length()));
            else stringBuilder.append(track.charAt(i - start));
        }
        return stringBuilder.toString();
    }

    private void updateTape(String newTapes) {
        int start = 0;
//        for (int i = 0; i < tapes.size(); i++) tapes.get(i).updateTape(newTapes.substring(start, start + ));
        for (Tape t : tapes) {
            t.updateTape(newTapes.substring(start, start + t.tracks.size()));
            start = start + t.tracks.size();
        }
    }

    private void moveHeads(String direction) {
        for (int i = 0; i < tapes.size(); i++) tapes.get(i).updateHead(direction.charAt(i));
    }

    public static void main(String[] args) {
        TuringMachine tm = new TuringMachine("#Q = {1,2,3,4,5}\n" +
                "\n" +
                "#S = {a , b , 1}\n" +
                "\n" +
                "#G = {a , b , 1, _ }\n" +
                "\n" +
                "#q0 = 1\n" +
                "\n" +
                "#F = {5}\n" +
                "\n" +
                "#B = _\n" +
                "\n" +
                "#N = 2\n" +
                "\n" +
                "#D 1 a_ _1 rr 1\n" +
                "#D 1 b_ __ rl 2\n" +
                "#D 2 b1 b_ *l 3\n" +
                "#D 3 b1 __ rl 3\n" +
                "#D 3 __ __ ** 5");
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("____aaabbb___"));
        tapes.add(new Tape(tracks, 4, '_'));
        tracks = new ArrayList<>();
        tracks.add(new StringBuilder("____________"));
        tapes.add(new Tape(tracks, 1, '_'));
        Executor executor = new Executor(tm, tapes);
        System.out.println(executor.snapShot());
        while (!tm.isStop(executor.snapShotTape())) {
            executor.execute();
            System.out.println(executor.snapShot());
        }
    }


}
