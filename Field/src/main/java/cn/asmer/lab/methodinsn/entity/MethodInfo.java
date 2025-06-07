package cn.asmer.lab.methodinsn.entity;

public class MethodInfo {
    public String binaryName;
    public String methodName;
    public String methodDesc;
    public boolean keepOriginalMethodFlag = false;
    public String originalMethodRenameTo = "originalMethodRenameTo";

    @Override
    public String toString() {
        return "className:" + this.binaryName + "," + "methodName:" + this.methodName + ", methodDesc:" +
                this.methodDesc + ", keep:" + this.keepOriginalMethodFlag + ", originalMethodRenameTo:" + this.originalMethodRenameTo;
    }
}
