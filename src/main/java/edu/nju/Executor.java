package edu.nju;

import java.util.ArrayList;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-25 23:53
 */
public class Executor {

    ArrayList<Tape> tapes;
    TuringMachine tm;

    Executor(TuringMachine tm, ArrayList<Tape> tapes){
        this.tm = tm;
        LoadTape(tapes);
    }

    //TODO
    public ArrayList<Tape> execute() {
        return null;
    }

    //TODO
    public void LoadTape(ArrayList<Tape> tapes) {
        this.tapes = tapes;
    }



}
