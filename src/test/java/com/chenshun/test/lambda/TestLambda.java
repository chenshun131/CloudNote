package com.chenshun.test.lambda;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

/**
 * User: chenshun131 <p />
 * Time: 17/9/12 22:58  <p />
 * Version: V1.0  <p />
 * Description:
 * 一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符 <br />
 * 箭头操作符将 Lambda 表达式拆分成两部分： <br />
 * <br />
 * 左侧：Lambda 表达式的参数列表 <br />
 * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体 <br />
 * <br />
 * 语法格式一：无参数，无返回值 <br />
 * <pre>
 * 		() -> System.out.println("Hello Lambda!");
 * </pre>
 * 语法格式二：有一个参数，并且无返回值 <br />
 * <pre>
 * 		(x) -> System.out.println(x) <br />
 * </pre>
 * 语法格式三：若只有一个参数，小括号可以省略不写 <br />
 * <pre>
 * 		x -> System.out.println(x) <br />
 * </pre>
 * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句 <br />
 * <pre>
 * 		Comparator<Integer> com = (x, y) -> {
 * 			System.out.println("函数式接口");
 * 			return Integer.compare(x, y);
 *        };
 * </pre>
 * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写 <br />
 * <pre>
 * 		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
 * </pre>
 * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断” <br />
 * <pre>
 * 		(Integer x, Integer y) -> Integer.compare(x, y);
 * </pre>
 * 上联：左右遇一括号省 <br />
 * 下联：左侧推断类型省 <br />
 * 横批：能省则省 <br />
 * <p>
 * 二、Lambda 表达式需要“函数式接口”的支持 <br />
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰 <br />
 * 可以检查是否是函数式接口 <p />
 */
public class TestLambda {

    @Test
    public void test1() {
        int num = 0; // 1.7 及其之前必须是 final，从 1.8 开始默认会添加 final
        // 匿名内部类
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world!" + num);
            }
        };
        runnable1.run();

        System.out.println("-------------------------");
        // Lambda表达式
        Runnable runnable2 = () -> System.out.println("Hello Lambda!");
        runnable2.run();
    }

    @Test
    public void test2() {
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("啦啦啦，我是卖报的小行家");
    }

    @Test
    public void test3() {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }

    @Test
    public void test4() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
    }

    @Test
    public void test5() {
//		String[] strs;
//		strs = {"aaa", "bbb", "ccc"};

        List<String> list = new ArrayList<>();

        show(new HashMap<>());
    }

    public void show(Map<String, Integer> map) {
    }

    // 需求：对一个数进行运算
    @Test
    public void test6() {
        Integer num = operation(100, (x) -> x * x);
        System.out.println(num);

        System.out.println(operation(200, (y) -> y + 200));
    }

    private Integer operation(Integer num, MyFun mf) {
        return mf.getValue(num);
    }

}
