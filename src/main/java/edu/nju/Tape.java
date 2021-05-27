package edu.nju;

import java.util.ArrayList;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 19:37
 */
public class Tape {

    ArrayList<StringBuilder> tracks;
    private char B;
    private int head;

    public Tape(ArrayList<StringBuilder> tracks, int head) {
        this.tracks = tracks;
        this.head = head;
    }

    public String snapShot() {
        StringBuilder stringBuilder = new StringBuilder();
        for (StringBuilder s : tracks) stringBuilder.append(s.charAt(head));
        return stringBuilder.toString();
    }

    public void updateHead(char c) {
        if (c == 'l') head--;
        else if (c == 'r') head++;
        if(head == -1) {
            head = 0;
            for(StringBuilder s : tracks) s.reverse().append(B).reverse();
        }
        else {
            for(StringBuilder s : tracks) {
                if(head == s.length()-1) s.append(B);
            }
        }
    }

    public int getHead() {
        return head;
    }

    public void updateTape(String newTape) {
        for (int i = 0; i < tracks.size(); i++) {
            tracks.get(i).replace(head, head + 1, newTape.charAt(i) + "");
        }
    }


}
