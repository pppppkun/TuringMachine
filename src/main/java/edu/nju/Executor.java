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
    State q;
    int steps = 0;
    boolean canRun = true;

    public Executor(TuringMachine tm, ArrayList<Tape> tapes) {
        this.tm = tm;
        q = tm.getInitState();
        loadTape(tapes);
    }

    /**
     * TODO
     * 1. 检查能否运行
     * 2. 调用tm.delta
     * 3. 更新磁带
     * 4. 返回下次能否执行
     *
     * @return
     */
    public Boolean execute() {
        String Z = snapShotTape();
        if (!tm.isStop(q, Z)) {
            TransitionFunction t = q.getDelta(Z);
            updateTape(t.getOutput());
            moveHeads(t.getDirection());
            q = t.getDestinationState();
            steps++;
        }
        return !tm.isStop(q, snapShotTape());
    }

    /**
     * TODO
     * 1. 检查磁带的数量是否正确 ( checkTapeNum )
     * 2. 检查磁带上的字符是否是输入符号组的 ( checkTape )
     *
     * @param tapes
     */
    public void loadTape(ArrayList<Tape> tapes) {
        canRun = canRun & tm.checkTapeNum(tapes.size());
        if (!canRun) System.err.println("Error: 2");
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
     *
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
     *
     * @return
     */
    public String snapShot() {
        StringBuilder stringBuilder = new StringBuilder();
        int maxTrackLen = 0;
        for (Tape t : tapes) {
            maxTrackLen = Math.max(maxTrackLen, t.tracks.size());
        }
        // 计算出最长的冒号在什么地方
        int colonIndex = Math.max(maxTrackLen + 5, tapes.size() + 4);
        // 先添加现在的执行的步数
        stringBuilder.append("Step").append(spaceString(colonIndex - 4)).append(":").append(" ").append(steps).append(System.lineSeparator());
        int tapeNum = 0;
        for (Tape t : tapes) {
            // 添加磁带内容
            stringBuilder.append("Tape").append(tapeNum).append(spaceString(colonIndex - ("Tape" + tapeNum).length())).append(":").append(System.lineSeparator());
            int trackNum = 0;
            for (StringBuilder sb : t.tracks) {
                // 添加磁道内容
                String track = sb.toString();
                int start = -1;
                int end = -1;
                // 找到第一个非空的字符和最后一个非空的字符
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
                // 特殊情况处理
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
                // 添加到build中
                if (trackNum == 0) {
                    if (track.length() == 1) start = end = t.getHead();
                    stringBuilder.append("Index").append(tapeNum).append(spaceString(colonIndex - ("Index" + tapeNum).length())).append(": ").append(indexHelper(start, end)).append(System.lineSeparator());
                }
                stringBuilder.append("Track").append(trackNum).append(spaceString(colonIndex - ("Track" + trackNum).length())).append(": ").append(formatTrack(track, start, end)).append(System.lineSeparator());
                trackNum++;
            }
            // 添加磁头
            stringBuilder.append("Head").append(tapeNum).append(spaceString(colonIndex - ("Head" + tapeNum).length())).append(": ").append(t.getHead()).append(System.lineSeparator());
            tapeNum++;
        }
        stringBuilder.append("State").append(spaceString(colonIndex - 5)).append(": ").append(q.getQ());
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
     *
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
     *
     * @param direction
     */
    private void moveHeads(String direction) {
        for (int i = 0; i < tapes.size(); i++) tapes.get(i).updateHead(direction.charAt(i));
    }


    public static void main(String[] args) {
        TuringMachine turingMachine = new TuringMachine("#Q = {init,add,sub,final_state}\n" +
                "#S = {0,1,2,3,4,5,6,7,8,9,+}\n" +
                "#G = {0,1,2,3,4,5,6,7,8,9,+,_}\n" +
                "#q0 = init\n" +
                "#F = {final_state}\n" +
                "#B = _\n" +
                "#N = 1\n" +
                "#D init 3 2 r add\n" +
                "#D add + + r add\n" +
                "#D add 6 7 l sub\n" +
                "#D add 7 8 l sub\n" +
                "#D add 8 9 l sub\n" +
                "#D sub + + l sub\n" +
                "#D sub 2 1 r add\n" +
                "#D sub 1 0 r add\n" +
                "#D sub 0 _ * final_state");
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("____3+6___"));
        tapes.add(new Tape(tracks, 4, '_'));
        Executor executor = new Executor(turingMachine, tapes);
        System.out.println(executor.snapShot());
        System.out.println("--------");
        boolean ret = true;
        do {
            ret = executor.execute();
            System.out.println(executor.snapShot());
            System.out.println("--------");
        } while (ret);
    }


}
