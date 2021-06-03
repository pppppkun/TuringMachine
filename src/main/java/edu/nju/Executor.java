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

    /**
     * TODO
     * 1. 检查能否运行
     * 2. 调用tm.delta
     * 3. 更新磁带
     * 4. 返回下次能否执行
     * @return
     */
    public Boolean execute() {
        String Z = snapShotTape();
        if (!tm.isStop(Z)) {
            TransitionFunction t = tm.delta(Z);
            updateTape(t.getOutput());
            moveHeads(t.getDirection());
            steps++;
        }
        return !tm.isStop(snapShotTape());
    }

    /**
     * TODO
     * 1. 检查磁带的数量是否正确 ( checkTapeNum )
     * 2. 检查磁带上的字符是否是输入符号组的 ( checkTape )
     * @param tapes
     */
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

    /**
     * TODO
     * 获取所有磁带的快照，也就是把每个磁带上磁头指向的字符全都收集起来
     * @return
     */
    private String snapShotTape() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tape tape : tapes) stringBuilder.append(tape.snapShot());
        return stringBuilder.toString();
    }

    /**
     * TODO
     * 按照README给出当前图灵机和磁带的快照
     * @return
     */
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

    /**
     * TODO
     * 不断切割newTapes，传递给每个Tape的updateTape方法
     * @param newTapes
     */
    private void updateTape(String newTapes) {
        int start = 0;
        for (Tape t : tapes) {
            t.updateTape(newTapes.substring(start, start + t.tracks.size()));
            start = start + t.tracks.size();
        }
    }

    /**
     * TODO
     * 将每个direction里的char都分配给Tape的updateHead方法
     * @param direction
     */
    private void moveHeads(String direction) {
        for (int i = 0; i < tapes.size(); i++) tapes.get(i).updateHead(direction.charAt(i));
    }


    public static void main(String[] args) {
        TuringMachine turingMachine = new TuringMachine("; This example program checks if the input string is a a_nb_n.\n" +
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
        tracks.add(new StringBuilder("___aaabb___"));
        tapes.add(new Tape(tracks, 3, '_'));
        Executor executor = new Executor(turingMachine, tapes);
        System.out.println(executor.snapShot());
        boolean ret = true;
        do {
            ret = executor.execute();
            System.out.println(executor.snapShot());
        }while (ret);    }


}
