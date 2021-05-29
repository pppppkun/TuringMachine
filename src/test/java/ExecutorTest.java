import edu.nju.Executor;
import edu.nju.Tape;
import edu.nju.TuringMachine;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @Author: pkun & yihui wang
 * @CreateTime: 2021-05-25 23:56
 */
public class ExecutorTest {

    String[] expects = new String[] {
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6 7 8 9\n" +
                    "Track0: a a a b b b\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 1\n" +
                    "Track0: _\n" +
                    "Head1 : 1\n" +
                    "State : 1\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 5 6 7 8 9\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 5\n" +
                    "Tape1 :\n" +
                    "Index1: 1 2\n" +
                    "Track0: 1 _\n" +
                    "Head1 : 2\n" +
                    "State : 1\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 6 7 8 9\n" +
                    "Track0: a b b b\n" +
                    "Head0 : 6\n" +
                    "Tape1 :\n" +
                    "Index1: 1 2 3\n" +
                    "Track0: 1 1 _\n" +
                    "Head1 : 3\n" +
                    "State : 1\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 7 8 9\n" +
                    "Track0: b b b\n" +
                    "Head0 : 7\n" +
                    "Tape1 :\n" +
                    "Index1: 1 2 3 4\n" +
                    "Track0: 1 1 1 _\n" +
                    "Head1 : 4\n" +
                    "State : 1\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 8 9\n" +
                    "Track0: b b\n" +
                    "Head0 : 8\n" +
                    "Tape1 :\n" +
                    "Index1: 1 2 3\n" +
                    "Track0: 1 1 1\n" +
                    "Head1 : 3\n" +
                    "State : 2\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 8 9\n" +
                    "Track0: b b\n" +
                    "Head0 : 8\n" +
                    "Tape1 :\n" +
                    "Index1: 1 2\n" +
                    "Track0: 1 1\n" +
                    "Head1 : 2\n" +
                    "State : 3\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 9\n" +
                    "Track0: b\n" +
                    "Head0 : 9\n" +
                    "Tape1 :\n" +
                    "Index1: 1\n" +
                    "Track0: 1\n" +
                    "Head1 : 1\n" +
                    "State : 3\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 10\n" +
                    "Track0: _\n" +
                    "Head0 : 10\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : 3\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 10\n" +
                    "Track0: _\n" +
                    "Head0 : 10\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : 4",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6 7 8 9\n" +
                    "Track0: a a a b b b\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : 1\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 5 6 7 8 9\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 5\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1\n" +
                    "Track0: 1 _\n" +
                    "Head1 : 1\n" +
                    "State : 1\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 6 7 8 9\n" +
                    "Track0: a b b b\n" +
                    "Head0 : 6\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 _\n" +
                    "Head1 : 2\n" +
                    "State : 1\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 7 8 9\n" +
                    "Track0: b b b\n" +
                    "Head0 : 7\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3\n" +
                    "Track0: 1 1 1 _\n" +
                    "Head1 : 3\n" +
                    "State : 1\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 8 9\n" +
                    "Track0: b b\n" +
                    "Head0 : 8\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 1\n" +
                    "Head1 : 2\n" +
                    "State : 2\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 8 9\n" +
                    "Track0: b b\n" +
                    "Head0 : 8\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1\n" +
                    "Track0: 1 1\n" +
                    "Head1 : 1\n" +
                    "State : 3\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 9\n" +
                    "Track0: b\n" +
                    "Head0 : 9\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: 1\n" +
                    "Head1 : 0\n" +
                    "State : 3\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 10\n" +
                    "Track0: _\n" +
                    "Head0 : 10\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : 3\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 10\n" +
                    "Track0: _\n" +
                    "Head0 : 10\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : 4",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a a a a a b b b\n" +
                    "Head0 : 0\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : 1\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a a a a b b b\n" +
                    "Head0 : 1\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1\n" +
                    "Track0: 1 _\n" +
                    "Head1 : 1\n" +
                    "State : 1\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4 5 6 7 8\n" +
                    "Track0: a a a a b b b\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 _\n" +
                    "Head1 : 2\n" +
                    "State : 1\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8\n" +
                    "Track0: a a a b b b\n" +
                    "Head0 : 3\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3\n" +
                    "Track0: 1 1 1 _\n" +
                    "Head1 : 3\n" +
                    "State : 1\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6 7 8\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3 4\n" +
                    "Track0: 1 1 1 1 _\n" +
                    "Head1 : 4\n" +
                    "State : 1\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 5 6 7 8\n" +
                    "Track0: a b b b\n" +
                    "Head0 : 5\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3 4 5\n" +
                    "Track0: 1 1 1 1 1 _\n" +
                    "Head1 : 5\n" +
                    "State : 1\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 6 7 8\n" +
                    "Track0: b b b\n" +
                    "Head0 : 6\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3 4 5 6\n" +
                    "Track0: 1 1 1 1 1 1 _\n" +
                    "Head1 : 6\n" +
                    "State : 1\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 7 8\n" +
                    "Track0: b b\n" +
                    "Head0 : 7\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3 4 5\n" +
                    "Track0: 1 1 1 1 1 1\n" +
                    "Head1 : 5\n" +
                    "State : 2\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 7 8\n" +
                    "Track0: b b\n" +
                    "Head0 : 7\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3 4\n" +
                    "Track0: 1 1 1 1 1\n" +
                    "Head1 : 4\n" +
                    "State : 3\n" +
                    "Step  : 9\n" +
                    "Tape0 :\n" +
                    "Index0: 8\n" +
                    "Track0: b\n" +
                    "Head0 : 8\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3\n" +
                    "Track0: 1 1 1 1\n" +
                    "Head1 : 3\n" +
                    "State : 3\n" +
                    "Step  : 10\n" +
                    "Tape0 :\n" +
                    "Index0: 9\n" +
                    "Track0: _\n" +
                    "Head0 : 9\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 1\n" +
                    "Head1 : 2\n" +
                    "State : 3",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 3 + 6\n" +
                    "Head0 : 2\n" +
                    "State : init\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 2 + 6\n" +
                    "Head0 : 3\n" +
                    "State : add\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 2 + 6\n" +
                    "Head0 : 4\n" +
                    "State : add\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 2 + 7\n" +
                    "Head0 : 3\n" +
                    "State : sub\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 2 + 7\n" +
                    "Head0 : 2\n" +
                    "State : sub\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 1 + 7\n" +
                    "Head0 : 3\n" +
                    "State : add\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 1 + 7\n" +
                    "Head0 : 4\n" +
                    "State : add\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 1 + 8\n" +
                    "Head0 : 3\n" +
                    "State : sub\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 1 + 8\n" +
                    "Head0 : 2\n" +
                    "State : sub\n" +
                    "Step  : 9\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 0 + 8\n" +
                    "Head0 : 3\n" +
                    "State : add\n" +
                    "Step  : 10\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 0 + 8\n" +
                    "Head0 : 4\n" +
                    "State : add\n" +
                    "Step  : 11\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 0 + 9\n" +
                    "Head0 : 3\n" +
                    "State : sub\n" +
                    "Step  : 12\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 0 + 9\n" +
                    "Head0 : 2\n" +
                    "State : sub\n" +
                    "Step  : 13\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: _ + 9\n" +
                    "Head0 : 2\n" +
                    "State : final_state",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 3 + 6\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : init\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 2 + 6\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1\n" +
                    "Track0: 1 _\n" +
                    "Head1 : 1\n" +
                    "State : get_num\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 1 + 6\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 _\n" +
                    "Head1 : 2\n" +
                    "State : get_num\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 0 + 6\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3\n" +
                    "Track0: 1 1 1 _\n" +
                    "Head1 : 3\n" +
                    "State : get_num\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: + 6\n" +
                    "Head0 : 3\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2 3\n" +
                    "Track0: 1 1 1 _\n" +
                    "Head1 : 3\n" +
                    "State : get_num\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 6\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 1\n" +
                    "Head1 : 2\n" +
                    "State : add\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 7\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1\n" +
                    "Track0: 1 1\n" +
                    "Head1 : 1\n" +
                    "State : add\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 8\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: 1\n" +
                    "Head1 : 0\n" +
                    "State : add\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 9\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : add\n" +
                    "Step  : 9\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 9\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : final_state"
    };

    @Test
    public void testCorrectA() throws IOException {
        Path path = Paths.get("TuringMachine/add.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        String res = "#Q = {add,init,get_num,final_state}\n" +
                "#S = {0,1,2,3,4,5,6,7,8,9,+}\n" +
                "#G = {+,0,1,2,3,4,5,6,7,8,9,=,_}\n" +
                "#F = {final_state}\n" +
                "#q0 = init\n" +
                "#B = _\n" +
                "#N = 2\n" +
                "#D init 5_ 41 *r get_num\n" +
                "#D add 2_ 2_ ** final_state\n" +
                "#D get_num 5_ 41 *r get_num\n" +
                "#D init 7_ 61 *r get_num\n" +
                "#D add 0_ 0_ ** final_state\n" +
                "#D get_num 7_ 61 *r get_num\n" +
                "#D init 1_ 01 *r get_num\n" +
                "#D add 6_ 6_ ** final_state\n" +
                "#D get_num 9_ 81 *r get_num\n" +
                "#D init 3_ 21 *r get_num\n" +
                "#D add 4_ 4_ ** final_state\n" +
                "#D add 8_ 8_ ** final_state\n" +
                "#D init 9_ 81 *r get_num\n" +
                "#D add 11 2_ *l add\n" +
                "#D add 41 5_ *l add\n" +
                "#D add 21 3_ *l add\n" +
                "#D add 81 9_ *l add\n" +
                "#D add 61 7_ *l add\n" +
                "#D get_num +_ =_ rl add\n" +
                "#D get_num 1_ 01 *r get_num\n" +
                "#D get_num 3_ 21 *r get_num\n" +
                "#D get_num 4_ 31 *r get_num\n" +
                "#D init 6_ 51 *r get_num\n" +
                "#D add 3_ 3_ ** final_state\n" +
                "#D get_num 6_ 51 *r get_num\n" +
                "#D init 8_ 71 *r get_num\n" +
                "#D add 1_ 1_ ** final_state\n" +
                "#D get_num 8_ 71 *r get_num\n" +
                "#D init 2_ 11 *r get_num\n" +
                "#D add 7_ 7_ ** final_state\n" +
                "#D init 4_ 31 *r get_num\n" +
                "#D add 5_ 5_ ** final_state\n" +
                "#D add 9_ 9_ ** final_state\n" +
                "#D add 01 1_ *l add\n" +
                "#D add 51 6_ *l add\n" +
                "#D add 31 4_ *l add\n" +
                "#D add 71 8_ *l add\n" +
                "#D get_num 0_ __ r* get_num\n" +
                "#D get_num 2_ 11 *r get_num";
        ArrayList<String> expect = new ArrayList<>(Arrays.asList(res.split("\n")));
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(tm.toString().split(System.lineSeparator())));
        for (String s : expect) {
            if (!actual.contains(s)) {
                fail("结果不包含: " + s);
            }
        }
    }

    @Test
    public void testCorrectE() throws IOException {
        Path path = Paths.get("TuringMachine/anbnTwoTape.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("____aaabbb___"));
        tapes.add(new Tape(tracks, 4, '_'));
        tracks = new ArrayList<>();
        tracks.add(new StringBuilder("____________"));
        tapes.add(new Tape(tracks, 1, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[0].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectF() throws IOException {
        Path path = Paths.get("TuringMachine/anbnTwoTape.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("____aaabbb___"));
        tapes.add(new Tape(tracks, 4, '_'));
        tracks = new ArrayList<>();
        tracks.add(new StringBuilder("_"));
        tapes.add(new Tape(tracks, 0, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[1].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectG() throws IOException {
        Path path = Paths.get("TuringMachine/anbnTwoTape.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("aaaaaabbb"));
        tapes.add(new Tape(tracks, 0, '_'));
        tracks = new ArrayList<>();
        tracks.add(new StringBuilder("_"));
        tapes.add(new Tape(tracks, 0, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[2].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectH() throws IOException {
        Path path = Paths.get("TuringMachine/3+6.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("__3+6__"));
        tapes.add(new Tape(tracks, 2, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[3].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectI() throws IOException {
        Path path = Paths.get("TuringMachine/add.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("__3+6__"));
        tapes.add(new Tape(tracks, 2, '_'));
        tracks = new ArrayList<>();
        tracks.add(new StringBuilder("_"));
        tapes.add(new Tape(tracks, 0, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[4].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

}
