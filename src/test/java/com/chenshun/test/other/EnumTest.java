package com.chenshun.test.other;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.*;

/**
 * User: mew <p />
 * Time: 17/8/22 08:22  <p />
 * Version: V1.0  <p />
 * Description: Enum 特殊方法测试 <p />
 * <pre>
 *           _.._        ,------------.
 *        ,'      `.    ( We want you! )
 *       /  __) __` \    `-,----------'
 *      (  (`-`(-')  ) _.-'
 *      /)  \  = /  (
 *     /'    |--' .  \
 *    (  ,---|  `-.)__`
 *     )(  `-.,--'   _`-.
 *    '/,'          (  Uu",
 *     (_       ,    `/,-' )
 *     `.__,  : `-'/  /`--'
 *       |     `--'  |
 *       `   `-._   /
 *        \        (
 *        /\ .      \.  freebuf
 *       / |` \     ,-\
 *      /  \| .)   /   \
 *     ( ,'|\    ,'     :
 *     | \,`.`--"/      }
 *     `,'    \  |,'    /
 *    / "-._   `-/      |
 *    "-.   "-.,'|     ;
 *   /        _/["---'""]
 *  :        /  |"-     '
 *  '           |      /
 *              `      |
 * </pre>
 */
public class EnumTest {

    public enum PictureType {
        BMP("bmp", 0) {
            @Override
            public String getInfo() {
                return "这是bmp格式图片";
            }

            public void getPrint() { // 没有覆盖的方法无法直接访问
                System.out.println("测试添加其他方法");
            }
        },
        JPG("jpg", 1) {
            @Override
            public String getInfo() {
                return "这是jpg格式图片";
            }
        },
        JPEG("jpeg", 2) {
            @Override
            public String getInfo() {
                return "这是jpeg格式图片";
            }
        },
        PNG("png", 3) {
            @Override
            public String getInfo() {
                return "这是png格式图片";
            }
        },
        GIF("gif", 4) {
            @Override
            public String getInfo() {
                return "这是gif格式图片";
            }
        };

        private String name;

        private int index;

        /**
         * 获取信息
         *
         * @return
         */
        public String getInfo() {
            return "";
        }

        // 构造函数不能是 public
        private PictureType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public static String getName(int index) {
            for (PictureType c : PictureType.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        public static PictureType getPictureType(String name) {
            for (PictureType c : PictureType.values()) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "name=" + this.getName() + ";index=" + this.index;
        }
    }

    public static void main(String[] args) {
        // 访问其中某一值
        System.out.println(PictureType.BMP.getName() + " => " + PictureType.BMP.getIndex());
        System.out.println(PictureType.GIF.getName() + " => " + PictureType.GIF.getIndex());

        // 遍历
        PictureType[] all = PictureType.values();
        StringBuilder sb;
        for (PictureType a : all) {
            sb = new StringBuilder();
            sb.append("name:").append(a.getName())
                    .append(" index:").append(a.getIndex())
                    .append(" ordinal:").append(a.ordinal()) // 系统给出对应的索引号 基于0
                    .append(" 描述信息:").append(a.getInfo());
            System.out.println(sb.toString());
        }

        // 修改其中的值，可以不提供 set 方法来防止数据被修改
        PictureType.GIF.setName("嘻嘻哈哈");
        System.out.println(PictureType.GIF + " => " + PictureType.GIF.getName());

        // EnumSet
        EnumSet<PictureType> ofSets = EnumSet.of(PictureType.BMP); // of 创建一个最初包含指定元素的枚举 set
        ofSets.add(PictureType.JPG);
        for (PictureType en : ofSets) {
            System.out.println("of => " + en.toString());
        }

        EnumSet<PictureType> allSets = EnumSet.allOf(PictureType.class); // allOf 创建一个包含指定元素类型的所有元素的枚举 set
        for (PictureType en : allSets) {
            System.out.println("allOf => " + en.toString());
        }

        EnumSet<PictureType> rangeSets = EnumSet.range(PictureType.BMP, PictureType.JPEG); // 创建一个指定范围 set
        for (PictureType en : rangeSets) {
            System.out.println("range => " + en.toString());
        }

        EnumSet<PictureType> noneSets = EnumSet.noneOf(PictureType.class); // 创建一个指定枚举类型的空set
        noneSets.add(PictureType.BMP);
        noneSets.add(PictureType.JPG);
        noneSets.add(PictureType.JPEG);
        Iterator<PictureType> iterable = noneSets.iterator();
        while (iterable.hasNext()) {
            System.out.println("noneOf => " + iterable.next().toString());
        }

        // EnumMap 其中的 key 是 Enum 类型因此操作速度比 HashMap 速度快，其才做方法和 HashMap 基本一样
        Map<PictureType, String> map = new EnumMap<>(PictureType.class);
        map.put(PictureType.BMP, "这是一个 BMP 格式图片");
    }



}
