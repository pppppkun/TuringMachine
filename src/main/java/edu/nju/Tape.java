package edu.nju;

import java.util.ArrayList;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 19:37
 */
public class Tape {

    ArrayList<String> tracks;
    int head;

    public Tape(ArrayList<String> tracks, int head) {
        this.tracks = tracks;
        this.head = head;
    }

    public String snapShot() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : tracks) stringBuilder.append(s.charAt(head));
        return stringBuilder.toString();
    }

}
