package space.zyzy.dubhe.javastructure;

/**
 * 我们通过javap查看这个类的字节码：
 * public final class space.zyzy.dubhe.javastructure.EnumObject extends java.lang.Enum<space.zyzy.dubhe.javastructure.EnumObject> {
 *   public static final space.zyzy.dubhe.javastructure.EnumObject ONE;
 *   public static final space.zyzy.dubhe.javastructure.EnumObject TWO;
 *
 *   public static space.zyzy.dubhe.javastructure.EnumObject[] values();
 *     Code:
 *        0: getstatic     #1                  // Field $VALUES:[Lspace/zyzy/dubhe/javastructure/EnumObject;
 *        3: invokevirtual #2                  // Method "[Lspace/zyzy/dubhe/javastructure/EnumObject;".clone:()Ljava/lang/Object;
 *        6: checkcast     #3                  // class "[Lspace/zyzy/dubhe/javastructure/EnumObject;"
 *        9: areturn
 *
 *   public static space.zyzy.dubhe.javastructure.EnumObject valueOf(java.lang.String);
 *     Code:
 *        0: ldc           #4                  // class space/zyzy/dubhe/javastructure/EnumObject
 *        2: aload_0
 *        3: invokestatic  #5                  // Method java/lang/Enum.valueOf:(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
 *        6: checkcast     #4                  // class space/zyzy/dubhe/javastructure/EnumObject
 *        9: areturn
 *
 *   static {};
 *     Code:
 *        0: new           #4                  // class space/zyzy/dubhe/javastructure/EnumObject
 *        3: dup
 *        4: ldc           #7                  // String ONE
 *        6: iconst_0
 *        7: invokespecial #8                  // Method "<init>":(Ljava/lang/String;I)V
 *       10: putstatic     #9                  // Field ONE:Lspace/zyzy/dubhe/javastructure/EnumObject;
 *       13: new           #4                  // class space/zyzy/dubhe/javastructure/EnumObject
 *       16: dup
 *       17: ldc           #10                 // String TWO
 *       19: iconst_1
 *       20: invokespecial #8                  // Method "<init>":(Ljava/lang/String;I)V
 *       23: putstatic     #11                 // Field TWO:Lspace/zyzy/dubhe/javastructure/EnumObject;
 *       26: iconst_2
 *       27: anewarray     #4                  // class space/zyzy/dubhe/javastructure/EnumObject
 *       30: dup
 *       31: iconst_0
 *       32: getstatic     #9                  // Field ONE:Lspace/zyzy/dubhe/javastructure/EnumObject;
 *       35: aastore
 *       36: dup
 *       37: iconst_1
 *       38: getstatic     #11                 // Field TWO:Lspace/zyzy/dubhe/javastructure/EnumObject;
 *       41: aastore
 *       42: putstatic     #1                  // Field $VALUES:[Lspace/zyzy/dubhe/javastructure/EnumObject;
 *       45: return
 * }
 * 1、枚举类是一个final类无法被继承
 * 2、本身继承自 java.lang.Enum 类
 * 3、自动为我们生成了相关的values以及valueOf方法
 * 4、Enum是线程安全的,Enum中字段都是static类型的，因为static类型的属性会在类被加载之后被初始化，而Java类的加载和初始化过程都是线程安全的
 * 5、Enum线程安全的优势在于可以避免自己实现 序列化
 * 6、无法通过反射的方式生成Enum的实例,Constructor类的newInstance方法指明了原因
 */
public enum EnumObject {
    ONE, TWO;
}
