package space.zyzy.dubhe.aop.asmdemo;

import org.springframework.asm.*;

import java.io.FileInputStream;

public class RunManualByteCode {

    public static void main(String[] args) throws Exception {

        // 读取字节码文件(ByteCodeDemo是.class文件)
        FileInputStream fileInputStream = new FileInputStream("E:/dubhe/src/main/resources/ByteCodeDemo");
        ClassReader classReader = new ClassReader(fileInputStream);

        ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

        //Java8选择ASM5
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5, cw) {

            /**
             * 使用field visit来访问字段信息
             */
            @Override
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                System.out.println("field --- " + name);
                return super.visitField(access, name, desc, signature, value);
            }

            /**
             * 使用method visit来访问方法信息
             */
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("method --- " + name);
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };

        // 忽略调试信息
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
    }
}
