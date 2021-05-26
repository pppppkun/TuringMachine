## 图灵机执行器

该作业作为图灵机的第二次小作业，我们将关注以下几件事情

- 两种接受方式
- 图灵机与编程

### 1. 实验背景

### 接受

上回说到，对于一个磁带，图灵机有两种接受的方式。磁带，实际上就是一个字符串，如果要严格地定义的话，**字符串**就是定义在$\Sigma$上的一个`List`，每一个元素都是$\Sigma$中的一个元素，字符串的长度就是其中元素的个数。

如果$\Sigma$=\{a, b\}， 那么定义在$\Sigma$上的一个字符串$S$可以是$aaaabbbababaabbbaba$，$\epsilon$表示一个空的字符串，长度为0，$\Sigma^*$表示所有定义在$\Sigma$上的字符串。**语言**是$\Sigma^*$的一个子集。被图灵机所接受的磁带，也就是被图灵机所接受的字符串，就是**被图灵机所定义的语言**。接下来我们讨论一下两种接受方式

实际上两种接受方式是完全等价的。如果迁移函数中有任何以FinalState开始，前往下一个状态的函数，我们将这些函数去掉，此时，对于之前所有可以接受的字符串，现在都会在新的图灵机上halts，但是等价意味着我们不能引入新的可以接受的字符串，我们还需要保证这个新的图灵机不会接受以前不能接受的字符串。

也就是说，如果现在的图灵机上存在一个状态$q$，他不是FinalState，但是$\delta(q, Z)$也是不存在的，那么这个图灵机就可以在这个状态上停机，也就是说我们引入了新的字符串，要解决这个问题很简单，**留给大家自己思考**

同理，从Halting到FinalState也可以用类似的证明思路证明他们是等价的。

### 图灵机与算法

能被图灵机所定义的语言也称为**递归可枚举语言(recursively enumerable languages)**，如果一个图灵机以FinalState来接受字符串，同时，不管这个字符串能否以FinalState的方式接受，图灵机一定会停机，那么我们就称这个图灵机为**算法**。被**算法**所定义的语言称为**递归语言**。

如果大家在wiki上搜索算法，最开始的一段话如下

> In mathematics and computer science, an **algorithm** is a **finite sequence** of well-defined, computer-implementable instructions, typically to solve **a class of problems or to perform a computation**. Algorithms are always **unambiguous** and are used as specifications for performing calculations, data processing, automated reasoning, and other tasks.

我们很容易可以把标粗的词和刚刚被我们称为算法的图灵机对应起来。有限的序列表示不会陷入死循环，它一定会停止，对应着这段话

> 不管这个字符串能否以FinalState的方式接受，它一定保证图灵机可以停下来

而一类问题则可以看成是被我们定义好的 $\Sigma$ 所产生的所有字符串

无歧义，表示每一步要么只有一个迁移函数可以选择，要么没有函数可以选择，对应着我们确定性图灵机的概念

到这里，我相信大家对图灵机的认识已经没有那么抽象了！我们通过定义，描绘出了一台机器，这台机器在加上特别的约束后就可以成为**算法**，更神奇的是，设计这台机器的过程，其实就是我们所谓的编程。

### 图灵机与编程

设计一个图灵机其实就是写代码的过程，我们的迁移函数和状态定义了我们的图灵机（也就是代码）会怎么样来运行，磁带就是我们给我们的图灵机（代码）提供的输入，这样我们就把图灵机和编程联系了起来。但是设计图灵机难度和写代码的难度是天差地别的，接下来我们会给出几个例子，并且在最后给出一些造成图灵机设计困难的原因。接下来的每个栗子，我们都会给出两种描述，一种是纯图灵机的描述，一种是和编程相关的描述。

#### 栗子1

描述1：构造一个图灵机来接受这个语言 $L = \{a^nb^n|n\ge 0\}$ ，也就是判定一个字符串是否符合这个形式

描述2：设计一个程序，来识别输入的字符串是否符合 $L = \{a^nb^n|n\ge 0\}$的形式。

其中每个圆圈表示一个状态，圆圈里面的不同字符表示了不同的状态，如果圆圈里面还有一个小圈，表示这个状态是FinalState，如果从状态A到状态B有箭头从A指向B，表示可以从状态A转移到状态B，箭头上面标志了这次转换需要做的事情，` a / B, R `表示目前磁头指向的磁带上的字符是a时，将它重写为B，并且磁头向右移动一格。后面的图以同样的方式解释



![img](https://pkun.oss-cn-beijing.aliyuncs.com/uPic/tsYxw0.png)

#### 栗子2

描述1：构造一个图灵机来让一个字符串集体右移一位，也就是 `abbaBB->BabbaBB`，字符串中只会出现a和b 

描述2：设计一个程序，让一个输入的字符串集体右移一位，需要在原来的字符串上修改

![img](https://pkun.oss-cn-beijing.aliyuncs.com/uPic/YswfTK.png)

#### 栗子3

描述1：构造一个图灵机来让一个字符串集体循环右移一位，也就是`abbaBB->aabbBB`，字符串中只会出现a和b 

描述2：设计一个程序，让一个输入的字符串集体循环右移一位，需要在原来的字符串上修改



![img](https://pkun.oss-cn-beijing.aliyuncs.com/uPic/jzi2QS.png)

#### 栗子4

描述1：构造一个图灵机来接受这个语言 $L = \{ ba^ib | i \ge 0 \}$ ，也就是判定一个字符串是否符合这个形式，符合的话在磁带上放下一个y，不符合放下一个n，其他字符均为Blank

描述2：设计一个程序，来识别输入的字符串是否符合  $L = \{ ba^ib | i \ge 0 \}$ ，的形式，符合的话输出y，否则输出n



![img](https://pkun.oss-cn-beijing.aliyuncs.com/uPic/Sb9YfW.png)

#### 为什么设计图灵机这么难？

在上面四个栗子中，如果大家使用C、Python这些编程语言可以非常容易解决，但是设计一个图灵机来解决却需要一些小技巧，如果让大家设计一个解决排序问题的图灵机，那恐怕要花上几个小时甚至几天的时间，最后设计出来的图灵机状态可能极其复杂。为什么会这样？

#### 极少的变量

一个比较明显的原因就是你拥有的信息太少了，在写代码的时候你可以随意的声明很多变量，但是在图灵机中，你的迁移函数中只能收到一个字符，甚至你能用的所有存储空间，不过就是一个无限长的磁带而已，你能使用的变量只有一个（尽管它无限长）。

#### 函数设计困难

在编程的时候，我们可以把一个复杂的问题分割成不同的小问题，然后编写不同的函数来处理它，但是在图灵机中，编写子函数是比较复杂的事情，因为我们没有显式的栈，如果你在一个状态 qqq 遇到两个不同的字符的时候需要前往两个不同的子函数再返回，然后根据返回值做一些操作，这样图灵机的状态可能会变得非常多，因为你可能需要连个状态来区分从不同的子函数返回，也有可能造成迁移函数非常复杂。

我们将图灵机能够接受的语言的范围，也就是设计图灵机能够解决的问题称为图灵机的表达能力。

图灵机的表达能力和现在的计算机比起来，哪个更强？我们通过上面的栗子已经看出来，在应付一些稍微麻烦的算法时，设计一个图灵机已经是一个相当困难的任务，那么图灵机的表达能力比计算机弱吗？

### 对图灵机表达能力的讨论

#### 一个磁带多个磁道的图灵机

在之前的栗子中，我们构造的图灵机都只有一个磁带，那么从考虑解决变量少的问题出发，我们可以拓展出一个磁带上有两个磁道的图灵机，看看这样图灵机的表达能力是否增强。在单磁带多磁道图灵机中，迁移函数被拓展为
$$
\delta(q, Z) = (P,Y,D) \\ Z = 10\\ Y =00\\ D = L
$$


```
TapeA
Track1 ...BBB010101101BBB...
Track2 ...BBBBBB01010BBBB...
HeadA           _
```

假设一个图灵机 $T$ 有一个磁带A和两个磁道，假设当前图灵机的状态为 $q$ ，磁带上的符号为 $a_1,a_2$ ，修改后的符号为 $b_1b_2$ ，状态转换成 $p$ ，需要注意，一个磁头是对应一个磁带的，也就是说一个磁头同时标记了两个磁道上的字符。那么我们可以构造一个单磁带单磁道的图灵机，他的状态空间和 $T$ 完全相同，同时因为磁带只有一个，我们可以把的两条磁道按照先后顺序压缩，比如符号为$a_1,a_2$ 的时候，我们可以直接把它看成一个符号 $a_1a_2$ ，这样我们就可以用**单磁带单磁道的图灵机模拟单磁带多磁道的图灵机**了。于此同时，单磁带单磁道的图灵机其实就是一个单磁带多磁道的图灵机只使用了一个磁道的结果，所以单磁带多磁道的图灵机也可以接受所有单磁带单磁道图灵机所接受的语言。

给图灵机的磁带增加多个磁道，对图灵机的表达能力没有任何改变，那如果我们增加多个磁带呢？

## 多个磁带的图灵机

在多个磁带的图灵机中，每个磁带我们都给他分配一个单独的磁头，迁移函数的形式拓展为
$$
\delta(q, Z) = (P,Y,D) \\ Z = (b,f)\\ Y =(g, d)\\ D = (L,R)​
$$


```
TapeA
Track1 ...BBB010101101BBB...
HeadA       _
TapeB
Track1  ...BBB0101011BBBBB...
HeadB           _
```

同样，假设一个图灵机 $T$ 有 $K$ 个磁带，因为磁道的个数不改变表达能力，所以我们假设这 $K$ 个磁带都只有一个磁道，现在我们构造一个一个磁带，拥有 $2K$ 个磁道的图灵机 $T'$ ，对于每个$T$中的磁带，我们都用$T'$中的一个磁道来表示，再用另一个磁道来表示这个磁带的磁头是在什么位置，对于这样的图灵机$T'$，我们可以证明$T$和$T'$他们的表达能力是等价的，这里留给大家证明(((o(*ﾟ▽ﾟ*)o))) 

给图灵机加上很多磁带、磁道，其实都完全不改变图灵机本身的表达能力，也就是说，一个变量的图灵机和一堆变量的图灵机，他们能做的事情是一样的。图灵机和现代的计算机相比，哪个表达能力更强？答案是**一样的**。也就是说，一个最简单的图灵机，和我们手中复杂的计算机，他们能解决的问题是一样多的！图灵机无法解决的问题，计算机也无法解决，计算机能解决的，图灵机一定可以解决。

> The Church-Turing Thesis
>
> Everything we can compute on a physical computer can be computed on a Turing Machine

本次实验只需要大家能够实现一个执行图灵机的程序

### 2. 实验要求

本次实验中，同学们需要实现四个方法

```
public ArrayList<Tape> execute() {} // Executor.java
public void LoadTape(ArrayList<Tape> tapes) {} // Executor.java
public ArrayList<String> delta(String q, ArrayList<String> Z) {} // TuringMachine.java 
public boolean IsStop() {} // TuringMachine.java
```

其中，只有Execute方法是必须要实现的，因为我们的测试程序只会调用execute方法和Executor类的构造方法。其他方法，如果大家不需要的话也可以不用。在Executor类的构造方法里需要能够判断出**图灵机**和**纸带**不合法的地方。Execute()方法需要让读取的图灵机在事先给好的磁带上执行。
