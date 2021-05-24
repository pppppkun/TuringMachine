import edu.nju.*;
import edu.nju.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @Author: pkun
 * @CreateTime: 2021-05-23 23:27
 */
public class TMTest {

    public Set<String> StringSetGenerator() {
        Set<String> set = new HashSet<>();
        int size = 0;
        while (size == 0) size = new Random().nextInt(50);
        for (int i = 0; i < size; i++) {
            set.add(RandomStringUtils.randomAlphanumeric(1, 12));
        }
        return set;
    }

    public Set<Character> CharacterSetGenerator() {
        Set<Character> set = new HashSet<>();
        int size = 0;
        while (size == 0) size = new Random().nextInt(50);
        for (int i = 0; i < size; i++) {
            int t = new Random().nextInt(10 + 26 + 26);
            if (t < 10)
                set.add((char) ('0' + t));
            else if (t < 10 + 26)
                set.add((char) ('a' + t - 10));
            else
                set.add((char) ('A' + t - 10 - 26));
        }
        return set;
    }

    public Set<TransitionFunction> TransitionFunctionGenerator(int tapeNum) {
        Set<TransitionFunction> set = new HashSet<>();
        for (int i = 0; i < new Random().nextInt(100); i++) {
            String fromState = RandomStringUtils.randomAlphanumeric(5);
            String toState = RandomStringUtils.randomAlphanumeric(5);
            String input = RandomStringUtils.randomAlphanumeric(tapeNum);
            String output = RandomStringUtils.randomAlphanumeric(tapeNum);
            String direction = RandomStringUtils.random(tapeNum, 'l', 'r', '*');
            TransitionFunction transitionFunction = new TransitionFunction(fromState, toState, input, output, direction);
            set.add(transitionFunction);
        }
        return set;
    }

    // 测试toString
    @Test
    public void testNormalCreateA() {
        int size = 50;
        while (size-- > 0) {
            int tapeNum = 0;
            while (tapeNum == 0) tapeNum = new Random().nextInt(20);
            String q0 = RandomStringUtils.randomAlphanumeric(2, 12);
            Set<String> stateSet = StringSetGenerator();
            Set<Character> inputSymbolSet = CharacterSetGenerator();
            Set<Character> tapeSymbolSet = CharacterSetGenerator();
            Set<String> finalSet = StringSetGenerator();
            Set<TransitionFunction> transitionFunctions = TransitionFunctionGenerator(tapeNum);
            TuringMachine tm = new TuringMachine(stateSet, inputSymbolSet, tapeSymbolSet, q0, finalSet, '_', tapeNum, transitionFunctions);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(IOUtils.SetToString("Q", stateSet));
            stringBuilder.append(IOUtils.SetToString("S", inputSymbolSet));
            stringBuilder.append(IOUtils.SetToString("G", tapeSymbolSet));
            stringBuilder.append(IOUtils.SetToString("F", finalSet));
            stringBuilder.append("#q0 = ").append(q0).append("\n");
            stringBuilder.append("#B = ").append('_').append("\n");
            stringBuilder.append("#N = ").append(tapeNum).append("\n");
            transitionFunctions.forEach(transitionFunction -> stringBuilder.append(transitionFunction.toString()));
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String var = stringBuilder.toString();
            ArrayList<String> expect = new ArrayList<>(Arrays.asList(var.split("\n")));
            ArrayList<String> actual = new ArrayList<>(Arrays.asList(tm.toString().split("\n")));
            if (expect.size() != actual.size()) fail("图灵机序列化结果长度错误");
            for (String s : expect) {
                if (!actual.contains(s)) {
                    fail("结果不包含: " + s);
                }
            }
        }
    }

    // 测试过滤注释
    @Test
    public void testNormalCreateB() {
        int tapeNum = 0;
        while (tapeNum == 0) tapeNum = new Random().nextInt(20);
        String q0 = RandomStringUtils.randomAlphanumeric(2, 12);
        Set<String> stateSet = StringSetGenerator();
        Set<Character> inputSymbolSet = CharacterSetGenerator();
        Set<Character> tapeSymbolSet = CharacterSetGenerator();
        Set<String> finalSet = StringSetGenerator();
        Set<TransitionFunction> transitionFunctions = TransitionFunctionGenerator(tapeNum);
        StringBuilder stringBuilderA = new StringBuilder();
        StringBuilder stringBuilderB = new StringBuilder();
        for (int i = 0; i < new Random().nextInt(20); i++)
            stringBuilderA.append("; ").append(RandomStringUtils.randomAlphanumeric(16, 256)).append("\n");
        stringBuilderA.append(IOUtils.SetToString("Q", stateSet));
        stringBuilderB.append(IOUtils.SetToString("Q", stateSet));
        for (int i = 0; i < new Random().nextInt(20); i++)
            stringBuilderA.append("; ").append(RandomStringUtils.randomAlphanumeric(16, 256)).append("\n");
        stringBuilderA.append(IOUtils.SetToString("S", inputSymbolSet));
        stringBuilderB.append(IOUtils.SetToString("S", inputSymbolSet));
        for (int i = 0; i < new Random().nextInt(20); i++)
            stringBuilderA.append("; ").append(RandomStringUtils.randomAlphanumeric(16, 256)).append("\n");
        stringBuilderA.append(IOUtils.SetToString("G", tapeSymbolSet));
        stringBuilderB.append(IOUtils.SetToString("G", tapeSymbolSet));
        for (int i = 0; i < new Random().nextInt(20); i++)
            stringBuilderA.append("; ").append(RandomStringUtils.randomAlphanumeric(16, 256)).append("\n");
        stringBuilderA.append(IOUtils.SetToString("F", finalSet));
        stringBuilderB.append(IOUtils.SetToString("F", finalSet));
        for (int i = 0; i < new Random().nextInt(20); i++)
            stringBuilderA.append("; ").append(RandomStringUtils.randomAlphanumeric(16, 256)).append("\n");
        stringBuilderA.append("#q0 = ").append(q0).append("\n");
        stringBuilderB.append("#q0 = ").append(q0).append("\n");
        stringBuilderA.append("#B = ").append('_').append("\n");
        stringBuilderB.append("#B = ").append('_').append("\n");
        for (int i = 0; i < new Random().nextInt(20); i++)
            stringBuilderA.append("; ").append(RandomStringUtils.randomAlphanumeric(16, 256)).append("\n");
        stringBuilderA.append("#N = ").append(tapeNum).append("\n");
        stringBuilderB.append("#N = ").append(tapeNum).append("\n");
        transitionFunctions.forEach(transitionFunction -> stringBuilderA.append(transitionFunction.toString()));
        stringBuilderA.deleteCharAt(stringBuilderA.length() - 1);
        stringBuilderB.deleteCharAt(stringBuilderB.length() - 1);
        String var = stringBuilderA.toString();
        TuringMachine tm = new TuringMachine(var);
        ArrayList<String> expect = new ArrayList<>(Arrays.asList(stringBuilderB.toString().split("\n")));
        ArrayList<String> actual = new ArrayList<>(Arrays.asList(tm.toString().split("\n")));
        if (expect.size() != actual.size()) fail("图灵机序列化结果长度错误");
        for (String s : expect) {
            if (!actual.contains(s)) {
                fail("结果不包含: " + s);
            }
        }
    }

    // 测试：漏写括号
    @Test
    public void testNormalCreateC() {

    }

    // 测试：出现了无法解析的内容，即不属于任何最后给出的格式的内容
    @Test
    public void testNormalCreateD() {

    }

    // 测试： 某个需要的七元组信息不在输入当中
    @Test
    public void testNormalCreateE() {

    }

    // 测试：输入的Delta函数读取的符号和写回的符号长度不同
    @Test
    public void testNormalCreateF() {

    }

}
