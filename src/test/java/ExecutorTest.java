import edu.nju.Executor;
import edu.nju.Tape;
import edu.nju.TuringMachine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

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
                    "State : final_state",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4 5\n" +
                    "Track0: a b b a\n" +
                    "Head0 : 2\n" +
                    "State : init\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5\n" +
                    "Track0: b b a\n" +
                    "Head0 : 3\n" +
                    "State : A\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5\n" +
                    "Track0: a b a\n" +
                    "Head0 : 4\n" +
                    "State : B\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5\n" +
                    "Track0: a b a\n" +
                    "Head0 : 5\n" +
                    "State : B\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6\n" +
                    "Track0: a b b _\n" +
                    "Head0 : 6\n" +
                    "State : A\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6\n" +
                    "Track0: a b b a\n" +
                    "Head0 : 6\n" +
                    "State : f",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 2\n" +
                    "State : init\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 3\n" +
                    "State : A\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 4\n" +
                    "State : A\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 5\n" +
                    "State : A\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 6\n" +
                    "State : A\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 7\n" +
                    "State : A\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 8\n" +
                    "State : A\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 9\n" +
                    "State : A\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 10\n" +
                    "State : A\n" +
                    "Step  : 9\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 11\n" +
                    "State : A\n" +
                    "Step  : 10\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 12\n" +
                    "State : A\n" +
                    "Step  : 11\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 13\n" +
                    "State : A\n" +
                    "Step  : 12\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 14\n" +
                    "State : A\n" +
                    "Step  : 13\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 15\n" +
                    "State : A\n" +
                    "Step  : 14\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 16\n" +
                    "State : B\n" +
                    "Step  : 15\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 17\n" +
                    "State : B\n" +
                    "Step  : 16\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 18\n" +
                    "State : B\n" +
                    "Step  : 17\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 19\n" +
                    "State : B\n" +
                    "Step  : 18\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 20\n" +
                    "State : B\n" +
                    "Step  : 19\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 21\n" +
                    "State : B\n" +
                    "Step  : 20\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 22\n" +
                    "State : B\n" +
                    "Step  : 21\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 23\n" +
                    "State : B\n" +
                    "Step  : 22\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 24\n" +
                    "State : B\n" +
                    "Step  : 23\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 25\n" +
                    "State : B\n" +
                    "Step  : 24\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 26\n" +
                    "State : B\n" +
                    "Step  : 25\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 27\n" +
                    "State : B\n" +
                    "Step  : 26\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 28\n" +
                    "State : A\n" +
                    "Step  : 27\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 29\n" +
                    "State : A\n" +
                    "Step  : 28\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 30\n" +
                    "State : A\n" +
                    "Step  : 29\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 31\n" +
                    "State : A\n" +
                    "Step  : 30\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 32\n" +
                    "State : A\n" +
                    "Step  : 31\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 33\n" +
                    "State : A\n" +
                    "Step  : 32\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 34\n" +
                    "State : A\n" +
                    "Step  : 33\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 35\n" +
                    "State : A\n" +
                    "Step  : 34\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 36\n" +
                    "State : A\n" +
                    "Step  : 35\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 37\n" +
                    "State : A\n" +
                    "Step  : 36\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 38\n" +
                    "State : A\n" +
                    "Step  : 37\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 39\n" +
                    "State : A\n" +
                    "Step  : 38\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 40\n" +
                    "State : A\n" +
                    "Step  : 39\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 41\n" +
                    "State : A\n" +
                    "Step  : 40\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 42\n" +
                    "State : A\n" +
                    "Step  : 41\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 43\n" +
                    "State : A\n" +
                    "Step  : 42\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 44\n" +
                    "State : A\n" +
                    "Step  : 43\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 45\n" +
                    "State : A\n" +
                    "Step  : 44\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  _\n" +
                    "Head0 : 46\n" +
                    "State : A\n" +
                    "Step  : 45\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46\n" +
                    "Track0: a a a a a a a a  a  a  a  a  a  b  b  b  b  b  b  b  b  b  b  b  b  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a  a\n" +
                    "Head0 : 46\n" +
                    "State : f",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 2 + 4\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : init\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 1 + 4\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1\n" +
                    "Track0: 1 _\n" +
                    "Head1 : 1\n" +
                    "State : get_num\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4\n" +
                    "Track0: 0 + 4\n" +
                    "Head0 : 2\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 _\n" +
                    "Head1 : 2\n" +
                    "State : get_num\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: + 4\n" +
                    "Head0 : 3\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1 2\n" +
                    "Track0: 1 1 _\n" +
                    "Head1 : 2\n" +
                    "State : get_num\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 4\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0 1\n" +
                    "Track0: 1 1\n" +
                    "Head1 : 1\n" +
                    "State : add\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 5\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: 1\n" +
                    "Head1 : 0\n" +
                    "State : add\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 6\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : add\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4\n" +
                    "Track0: = 6\n" +
                    "Head0 : 4\n" +
                    "Tape1 :\n" +
                    "Index1: 0\n" +
                    "Track0: _\n" +
                    "Head1 : 0\n" +
                    "State : final_state",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4 5 6 7\n" +
                    "Track0: a a a b b b\n" +
                    "Head0 : 2\n" +
                    "State : 0\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 3\n" +
                    "State : 1\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 4\n" +
                    "State : 1\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 5\n" +
                    "State : 1\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 6\n" +
                    "State : 1\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 7\n" +
                    "State : 1\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7 8\n" +
                    "Track0: a a b b b _\n" +
                    "Head0 : 8\n" +
                    "State : 1\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6 7\n" +
                    "Track0: a a b b b\n" +
                    "Head0 : 7\n" +
                    "State : 2\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6\n" +
                    "Track0: a a b b\n" +
                    "Head0 : 6\n" +
                    "State : 3\n" +
                    "Step  : 9\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6\n" +
                    "Track0: a a b b\n" +
                    "Head0 : 5\n" +
                    "State : 3\n" +
                    "Step  : 10\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6\n" +
                    "Track0: a a b b\n" +
                    "Head0 : 4\n" +
                    "State : 3\n" +
                    "Step  : 11\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6\n" +
                    "Track0: a a b b\n" +
                    "Head0 : 3\n" +
                    "State : 3\n" +
                    "Step  : 12\n" +
                    "Tape0 :\n" +
                    "Index0: 2 3 4 5 6\n" +
                    "Track0: _ a a b b\n" +
                    "Head0 : 2\n" +
                    "State : 3\n" +
                    "Step  : 13\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5 6\n" +
                    "Track0: a a b b\n" +
                    "Head0 : 3\n" +
                    "State : 0\n" +
                    "Step  : 14\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6\n" +
                    "Track0: a b b\n" +
                    "Head0 : 4\n" +
                    "State : 1\n" +
                    "Step  : 15\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6\n" +
                    "Track0: a b b\n" +
                    "Head0 : 5\n" +
                    "State : 1\n" +
                    "Step  : 16\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6\n" +
                    "Track0: a b b\n" +
                    "Head0 : 6\n" +
                    "State : 1\n" +
                    "Step  : 17\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6 7\n" +
                    "Track0: a b b _\n" +
                    "Head0 : 7\n" +
                    "State : 1\n" +
                    "Step  : 18\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5 6\n" +
                    "Track0: a b b\n" +
                    "Head0 : 6\n" +
                    "State : 2\n" +
                    "Step  : 19\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5\n" +
                    "Track0: a b\n" +
                    "Head0 : 5\n" +
                    "State : 3\n" +
                    "Step  : 20\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5\n" +
                    "Track0: a b\n" +
                    "Head0 : 4\n" +
                    "State : 3\n" +
                    "Step  : 21\n" +
                    "Tape0 :\n" +
                    "Index0: 3 4 5\n" +
                    "Track0: _ a b\n" +
                    "Head0 : 3\n" +
                    "State : 3\n" +
                    "Step  : 22\n" +
                    "Tape0 :\n" +
                    "Index0: 4 5\n" +
                    "Track0: a b\n" +
                    "Head0 : 4\n" +
                    "State : 0\n" +
                    "Step  : 23\n" +
                    "Tape0 :\n" +
                    "Index0: 5\n" +
                    "Track0: b\n" +
                    "Head0 : 5\n" +
                    "State : 1\n" +
                    "Step  : 24\n" +
                    "Tape0 :\n" +
                    "Index0: 5 6\n" +
                    "Track0: b _\n" +
                    "Head0 : 6\n" +
                    "State : 1\n" +
                    "Step  : 25\n" +
                    "Tape0 :\n" +
                    "Index0: 5\n" +
                    "Track0: b\n" +
                    "Head0 : 5\n" +
                    "State : 2\n" +
                    "Step  : 26\n" +
                    "Tape0 :\n" +
                    "Index0: 4\n" +
                    "Track0: _\n" +
                    "Head0 : 4\n" +
                    "State : 3\n" +
                    "Step  : 27\n" +
                    "Tape0 :\n" +
                    "Index0: 5\n" +
                    "Track0: _\n" +
                    "Head0 : 5\n" +
                    "State : 0\n" +
                    "Step  : 28\n" +
                    "Tape0 :\n" +
                    "Index0: 6\n" +
                    "Track0: _\n" +
                    "Head0 : 6\n" +
                    "State : 4",
            "Step  : 0\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a a a a a b b b\n" +
                    "Head0 : 2\n" +
                    "State : 0\n" +
                    "Step  : 1\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ a a a b b b\n" +
                    "Head0 : 3\n" +
                    "State : 1\n" +
                    "Step  : 2\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ a a a b b b\n" +
                    "Head0 : 4\n" +
                    "State : 1\n" +
                    "Step  : 3\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ a a a b b b\n" +
                    "Head0 : 5\n" +
                    "State : 1\n" +
                    "Step  : 4\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ a a a b b b\n" +
                    "Head0 : 6\n" +
                    "State : 1\n" +
                    "Step  : 5\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ a a a b b b\n" +
                    "Head0 : 7\n" +
                    "State : 1\n" +
                    "Step  : 6\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ a a a b b b\n" +
                    "Head0 : 8\n" +
                    "State : 1\n" +
                    "Step  : 7\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8 9\n" +
                    "Track0: a a _ a a a b b b _\n" +
                    "Head0 : 9\n" +
                    "State : 1\n" +
                    "Step  : 8\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ a a a b b b\n" +
                    "Head0 : 8\n" +
                    "State : 2\n" +
                    "Step  : 9\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ a a a b b\n" +
                    "Head0 : 7\n" +
                    "State : 3\n" +
                    "Step  : 10\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ a a a b b\n" +
                    "Head0 : 6\n" +
                    "State : 3\n" +
                    "Step  : 11\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ a a a b b\n" +
                    "Head0 : 5\n" +
                    "State : 3\n" +
                    "Step  : 12\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ a a a b b\n" +
                    "Head0 : 4\n" +
                    "State : 3\n" +
                    "Step  : 13\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ a a a b b\n" +
                    "Head0 : 3\n" +
                    "State : 3\n" +
                    "Step  : 14\n" +
                    "Tape0 :\n" +
                    "Index0: -1 0 1 2 3 4 5 6 7\n" +
                    "Track0: _  a a _ a a a b b\n" +
                    "Head0 : 2\n" +
                    "State : 3\n" +
                    "Step  : 15\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ a a a b b\n" +
                    "Head0 : 3\n" +
                    "State : 0\n" +
                    "Step  : 16\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ _ a a b b\n" +
                    "Head0 : 4\n" +
                    "State : 1\n" +
                    "Step  : 17\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ _ a a b b\n" +
                    "Head0 : 5\n" +
                    "State : 1\n" +
                    "Step  : 18\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ _ a a b b\n" +
                    "Head0 : 6\n" +
                    "State : 1\n" +
                    "Step  : 19\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ _ a a b b\n" +
                    "Head0 : 7\n" +
                    "State : 1\n" +
                    "Step  : 20\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7 8\n" +
                    "Track0: a a _ _ a a b b _\n" +
                    "Head0 : 8\n" +
                    "State : 1\n" +
                    "Step  : 21\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ _ a a b b\n" +
                    "Head0 : 7\n" +
                    "State : 2\n" +
                    "Step  : 22\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6\n" +
                    "Track0: a a _ _ a a b\n" +
                    "Head0 : 6\n" +
                    "State : 3\n" +
                    "Step  : 23\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6\n" +
                    "Track0: a a _ _ a a b\n" +
                    "Head0 : 5\n" +
                    "State : 3\n" +
                    "Step  : 24\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6\n" +
                    "Track0: a a _ _ a a b\n" +
                    "Head0 : 4\n" +
                    "State : 3\n" +
                    "Step  : 25\n" +
                    "Tape0 :\n" +
                    "Index0: -1 0 1 2 3 4 5 6\n" +
                    "Track0: _  a a _ _ a a b\n" +
                    "Head0 : 3\n" +
                    "State : 3\n" +
                    "Step  : 26\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6\n" +
                    "Track0: a a _ _ a a b\n" +
                    "Head0 : 4\n" +
                    "State : 0\n" +
                    "Step  : 27\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6\n" +
                    "Track0: a a _ _ _ a b\n" +
                    "Head0 : 5\n" +
                    "State : 1\n" +
                    "Step  : 28\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6\n" +
                    "Track0: a a _ _ _ a b\n" +
                    "Head0 : 6\n" +
                    "State : 1\n" +
                    "Step  : 29\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6 7\n" +
                    "Track0: a a _ _ _ a b _\n" +
                    "Head0 : 7\n" +
                    "State : 1\n" +
                    "Step  : 30\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5 6\n" +
                    "Track0: a a _ _ _ a b\n" +
                    "Head0 : 6\n" +
                    "State : 2\n" +
                    "Step  : 31\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5\n" +
                    "Track0: a a _ _ _ a\n" +
                    "Head0 : 5\n" +
                    "State : 3\n" +
                    "Step  : 32\n" +
                    "Tape0 :\n" +
                    "Index0: -1 0 1 2 3 4 5\n" +
                    "Track0: _  a a _ _ _ a\n" +
                    "Head0 : 4\n" +
                    "State : 3\n" +
                    "Step  : 33\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2 3 4 5\n" +
                    "Track0: a a _ _ _ a\n" +
                    "Head0 : 5\n" +
                    "State : 0\n" +
                    "Step  : 34\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2\n" +
                    "Track0: a a _\n" +
                    "Head0 : 6\n" +
                    "State : 1\n" +
                    "Step  : 35\n" +
                    "Tape0 :\n" +
                    "Index0: 0 1 2\n" +
                    "Track0: a a _\n" +
                    "Head0 : 5\n" +
                    "State : 2"
    };

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

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
    public void testCorrectB() throws IOException {
        Path path = Paths.get("TuringMachine/mul.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        String res = "#Q = {init,temp,mul,final_state}\n" +
                "#S = {0,1,2,3,4,5,6,7,8,9,*}\n" +
                "#G = {*,0,1,2,3,4,5,6,7,8,9,=,_}\n" +
                "#F = {final_state}\n" +
                "#q0 = init\n" +
                "#B = _\n" +
                "#N = 2\n" +
                "#D init 5_ _5 r* mul\n" +
                "#D init 1_ _1 r* mul\n" +
                "#D mul 20 0_ ** final_state\n" +
                "#D init 9_ _9 r* mul\n" +
                "#D mul 24 8_ ** final_state\n" +
                "#D mul 23 6_ ** final_state\n" +
                "#D mul 22 4_ ** final_state\n" +
                "#D mul 21 2_ ** final_state\n" +
                "#D mul 17 7_ ** final_state\n" +
                "#D mul 16 6_ ** final_state\n" +
                "#D mul 15 5_ ** final_state\n" +
                "#D mul 14 4_ ** final_state\n" +
                "#D mul 19 9_ ** final_state\n" +
                "#D mul 18 8_ ** final_state\n" +
                "#D init 0_ _0 r* mul\n" +
                "#D init 8_ _8 r* mul\n" +
                "#D init 4_ _4 r* mul\n" +
                "#D mul 51 5_ ** final_state\n" +
                "#D mul 50 0_ ** final_state\n" +
                "#D mul 13 3_ ** final_state\n" +
                "#D mul 12 2_ ** final_state\n" +
                "#D mul 11 1_ ** final_state\n" +
                "#D mul 10 0_ ** final_state\n" +
                "#D mul 06 0_ ** final_state\n" +
                "#D mul 05 0_ ** final_state\n" +
                "#D mul 04 0_ ** final_state\n" +
                "#D mul 03 0_ ** final_state\n" +
                "#D mul 09 0_ ** final_state\n" +
                "#D mul 08 0_ ** final_state\n" +
                "#D mul 07 0_ ** final_state\n" +
                "#D init 7_ _7 r* mul\n" +
                "#D init 3_ _3 r* mul\n" +
                "#D mul 42 8_ ** final_state\n" +
                "#D mul 41 4_ ** final_state\n" +
                "#D mul 40 0_ ** final_state\n" +
                "#D mul 02 0_ ** final_state\n" +
                "#D mul 01 0_ ** final_state\n" +
                "#D mul 00 0_ ** final_state\n" +
                "#D init 6_ _6 r* mul\n" +
                "#D init 2_ _2 r* mul\n" +
                "#D mul 31 3_ ** final_state\n" +
                "#D mul 30 0_ ** final_state\n" +
                "#D mul 33 9_ ** final_state\n" +
                "#D mul 32 6_ ** final_state\n" +
                "#D mul *0 =0 r* mul\n" +
                "#D mul *4 =4 r* mul\n" +
                "#D mul *3 =3 r* mul\n" +
                "#D mul *2 =2 r* mul\n" +
                "#D mul *1 =1 r* mul\n" +
                "#D mul *8 =8 r* mul\n" +
                "#D mul *7 =7 r* mul\n" +
                "#D mul *6 =6 r* mul\n" +
                "#D mul *5 =5 r* mul\n" +
                "#D mul *9 =9 r* mul";
        ArrayList<String> expect = new ArrayList<>(Arrays.asList(res.split("\n")));
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(tm.toString().split(System.lineSeparator())));
        for (String s : expect) {
            if (!actual.contains(s)) {
                fail("结果不包含: " + s);
            }
        }
    }

    @Test
    public void testCorrectC() throws IOException {
        Path path = Paths.get("TuringMachine/--.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        String res = "#Q = {0,1}\n" +
                "#S = {0,1,2,3,4,5,6,7,8,9}\n" +
                "#G = {0,1,2,3,4,5,6,7,8,9,_}\n" +
                "#F = {1}\n" +
                "#q0 = 0\n" +
                "#B = _\n" +
                "#N = 1\n" +
                "#D 0 1 0 * 1\n" +
                "#D 0 2 1 * 1\n" +
                "#D 0 3 2 * 1\n" +
                "#D 0 4 3 * 1\n" +
                "#D 0 5 4 * 1\n" +
                "#D 0 6 5 * 1\n" +
                "#D 0 7 6 * 1\n" +
                "#D 0 8 7 * 1\n" +
                "#D 0 9 8 * 1";
        ArrayList<String> expect = new ArrayList<>(Arrays.asList(res.split("\n")));
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(tm.toString().split(System.lineSeparator())));
        for (String s : expect) {
            if (!actual.contains(s)) {
                fail("结果不包含: " + s);
            }
        }
    }

    @Test
    public void testCorrectD() throws IOException {
        Path path = Paths.get("TuringMachine/swap.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        String res = "#Q = {init,final_state,write}\n" +
                "#S = {a,b,c,d,e,f,g,h,i,j,k,l,m,n,0,1,2,3,4,5,6,7,8,9}\n" +
                "#G = {_,a,b,c,d,e,f,g,h,i,j,k,l,m,n,0,1,2,3,4,5,6,7,8,9}\n" +
                "#F = {final_state}\n" +
                "#q0 = init\n" +
                "#B = _\n" +
                "#N = 2\n" +
                "#D init 5_ _5 rr write\n" +
                "#D write 9_ _9 *l write\n" +
                "#D init 1_ _1 rr write\n" +
                "#D write 5_ _5 *l write\n" +
                "#D init 9_ _9 rr write\n" +
                "#D init f_ _f rr write\n" +
                "#D write g_ _g *l write\n" +
                "#D init b_ _b rr write\n" +
                "#D write c_ _c *l write\n" +
                "#D write 1_ _1 *l write\n" +
                "#D init n_ _n rr write\n" +
                "#D init 0_ _0 rr write\n" +
                "#D init j_ _j rr write\n" +
                "#D write k_ _k *l write\n" +
                "#D write _e e_ lr write\n" +
                "#D write _f f_ lr write\n" +
                "#D write _g g_ lr write\n" +
                "#D init 8_ _8 rr write\n" +
                "#D write 6_ _6 *l write\n" +
                "#D write _h h_ lr write\n" +
                "#D write _i i_ lr write\n" +
                "#D write _j j_ lr write\n" +
                "#D write _k k_ lr write\n" +
                "#D init 4_ _4 rr write\n" +
                "#D write 2_ _2 *l write\n" +
                "#D write _l l_ lr write\n" +
                "#D write __ __ ** final_state\n" +
                "#D write _a a_ lr write\n" +
                "#D write _b b_ lr write\n" +
                "#D write _c c_ lr write\n" +
                "#D write _d d_ lr write\n" +
                "#D init e_ _e rr write\n" +
                "#D write h_ _h *l write\n" +
                "#D init a_ _a rr write\n" +
                "#D write d_ _d *l write\n" +
                "#D init m_ _m rr write\n" +
                "#D write _m m_ lr write\n" +
                "#D write _n n_ lr write\n" +
                "#D init i_ _i rr write\n" +
                "#D write l_ _l *l write\n" +
                "#D init 7_ _7 rr write\n" +
                "#D write 7_ _7 *l write\n" +
                "#D init 3_ _3 rr write\n" +
                "#D write 3_ _3 *l write\n" +
                "#D write i_ _i *l write\n" +
                "#D init h_ _h rr write\n" +
                "#D write e_ _e *l write\n" +
                "#D init d_ _d rr write\n" +
                "#D write m_ _m *l write\n" +
                "#D init l_ _l rr write\n" +
                "#D init 6_ _6 rr write\n" +
                "#D write 8_ _8 *l write\n" +
                "#D init 2_ _2 rr write\n" +
                "#D write 4_ _4 *l write\n" +
                "#D write a_ _a *l write\n" +
                "#D write _5 5_ lr write\n" +
                "#D write _6 6_ lr write\n" +
                "#D init g_ _g rr write\n" +
                "#D write _7 7_ lr write\n" +
                "#D write f_ _f *l write\n" +
                "#D write _8 8_ lr write\n" +
                "#D write _9 9_ lr write\n" +
                "#D init c_ _c rr write\n" +
                "#D write b_ _b *l write\n" +
                "#D write 0_ _0 *l write\n" +
                "#D write n_ _n *l write\n" +
                "#D write _0 0_ lr write\n" +
                "#D write _1 1_ lr write\n" +
                "#D write _2 2_ lr write\n" +
                "#D init k_ _k rr write\n" +
                "#D write _3 3_ lr write\n" +
                "#D write j_ _j *l write\n" +
                "#D write _4 4_ lr write";
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

    @Test
    public void testCorrectJ() throws IOException {
        Path path = Paths.get("TuringMachine/add.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("__2+4__"));
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
        String[] expect = expects[7].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectK() throws IOException {
        Path path = Paths.get("TuringMachine/right_shift.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("__abba__"));
        tapes.add(new Tape(tracks, 2, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[5].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectL() throws IOException {
        Path path = Paths.get("TuringMachine/right_shift.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("__aaaaaaaaaaaaabbbbbbbbbbbbaaaaaaaaaaaaaaaaaaa__"));
        tapes.add(new Tape(tracks, 2, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[6].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectM() throws IOException {
        Path path = Paths.get("TuringMachine/anbn.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("__aaabbb__"));
        tapes.add(new Tape(tracks, 2, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[8].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    @Test
    public void testCorrectN() throws IOException {
        Path path = Paths.get("TuringMachine/anbn.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("aaaaaabbb"));
        tapes.add(new Tape(tracks, 2, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] expect = expects[9].split("\n");
        if(expect.length != actual.size()) fail();
        for(int i = 0;i<expect.length;i++) {
            assertEquals(expect[i], actual.get(i));
        }
    }

    /**
     * error 1,2
     * @throws IOException
     */
    @Test
    public void testErrorO() throws IOException {
        Path path = Paths.get("TuringMachine/error1.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("zn"));
        tapes.add(new Tape(tracks, 0, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] error = errContent.toString().split(System.lineSeparator());
        ArrayList<String> errorIndex = new ArrayList<>();
        errorIndex.add("Error: 1");
        errorIndex.add("Error: 2");
        for (String s : error) {
            if (s.length() == 0) continue;
            if (errorIndex.contains(s)) errorIndex.remove(s);
            else {
                fail();
            }
        }
        if (errorIndex.size() != 0) fail("有错误还未被检测出");
        errContent.reset();
    }

    /**
     * error 3, 4, 5, 6, 7, 8, lack D
     * @throws IOException
     */
    @Test
    public void testErrorP() throws IOException {
        Path path = Paths.get("TuringMachine/error2.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("aabb"));
        tapes.add(new Tape(tracks, 0, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] error = errContent.toString().split(System.lineSeparator());
        ArrayList<String> errorIndex = new ArrayList<>();
        errorIndex.add("Error: 3");
        errorIndex.add("Error: 4");
        errorIndex.add("Error: 5");
        errorIndex.add("Error: 6");
        errorIndex.add("Error: 8");
        errorIndex.add("Error: 8");
        errorIndex.add("Error: 8");
        errorIndex.add("Error: 8");
        errorIndex.add("Error: 8");
        errorIndex.add("Error: 8");
        errorIndex.add("Error: 7");
        errorIndex.add("Error: 7");
        errorIndex.add("Error: 7");
        for (String s : error) {
            if (s.length() == 0) continue;
            if (errorIndex.contains(s)) errorIndex.remove(s);
            else {
                fail();
            }
        }
        if (errorIndex.size() != 0) fail("有错误还未被检测出");
        errContent.reset();
    }

    @Test
    public void testErrorQ() throws IOException {
        Path path = Paths.get("TuringMachine/error3.tm");
        TuringMachine tm = new TuringMachine(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
        ArrayList<Tape> tapes = new ArrayList<>();
        ArrayList<StringBuilder> tracks = new ArrayList<>();
        tracks.add(new StringBuilder("3+4"));
        tapes.add(new Tape(tracks, 0, '_'));
        Executor executor = new Executor(tm, tapes);
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        boolean ret = true;
        do {
            ret = executor.execute();
            actual.addAll(Arrays.asList(executor.snapShot().split(System.lineSeparator())));
        }while (ret);
        String[] error = errContent.toString().split(System.lineSeparator());
        ArrayList<String> errorIndex = new ArrayList<>();
        errorIndex.add("Error: 9");
        errorIndex.add("Error: 9");
        errorIndex.add("Error: 9");
        errorIndex.add("Error: 2");
        for (String s : error) {
            if (s.length() == 0) continue;
            if (errorIndex.contains(s)) errorIndex.remove(s);
            else {
                fail();
            }
        }
        if (errorIndex.size() != 0) fail("有错误还未被检测出");
        errContent.reset();
    }


}
