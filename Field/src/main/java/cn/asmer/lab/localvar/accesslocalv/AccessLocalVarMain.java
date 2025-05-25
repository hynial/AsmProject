package cn.asmer.lab.localvar.accesslocalv;

import cn.asmer.lab.common.CommonUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.List;

/**
 * 读取方法的局部变量表 并进行方法调用(instructions.add 不生效NotWorking)
 */
public class AccessLocalVarMain {
    public static void main(String[] args) throws Exception {
        String someClass = "cn.jarinst.JarTest";
        final ClassReader reader = new ClassReader(CommonUtil.getBytecode(someClass));
        final ClassNode classNode = new ClassNode();
        reader.accept(classNode, ClassReader.EXPAND_FRAMES);
        for (final MethodNode mn : (List<MethodNode>) classNode.methods) {
            if (mn.name.equalsIgnoreCase("testLocals")) {
                final InsnList list = new InsnList();

                for (final LocalVariableNode local : (List<LocalVariableNode>) mn.localVariables) {
                    System.out.println("Local Variable: " + local.name + " : " + local.desc + " : " + local.signature + " : " + local.index);
                    if (local.desc.contains("String")) {
                        mn.visitVarInsn(Opcodes.ALOAD, local.index);
                        final VarInsnNode node = new VarInsnNode(Opcodes.ALOAD, 1);

                        list.add(node);
                        System.out.println("added local var '" + local.name + "'");
                    }
                }

                final MethodInsnNode insertion = new MethodInsnNode(Opcodes.INVOKESTATIC, "cn.asmer.lab.methodinsn.around.PrintLocal".replaceAll("[.]", "/"), "printLocalString", "(Ljava/lang/String;)V", false);
                list.add(insertion);
//                mn.instructions.insert(list);
                mn.instructions.add(list);

            }
        }
// -noverify
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);

//        Class some = JarLoader.defineClassAndLoad(writer.toByteArray(), someClass);
//        //some = Class.forName(someClass);
//        some.getDeclaredMethod("testLocals", boolean.class).invoke(null, true);

        CommonUtil.writeBytesToClassFile(writer.toByteArray(), "JarTest");
    }
}
