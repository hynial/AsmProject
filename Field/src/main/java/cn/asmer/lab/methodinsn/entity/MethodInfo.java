package cn.asmer.lab.methodinsn.entity;

import java.util.List;
import java.util.stream.Collectors;

public class MethodInfo {
    public String binaryName;
    public String methodName;
    public List<String> methodDescList;
    public boolean keepOriginalMethodFlag = false;
    public String originalMethodRenameTo = "originalMethodRenameTo";

    public String keyData;

    @Override
    public String toString() {
        return "className:" + this.binaryName + "," + "methodName:" + this.methodName + ", methodDesc:" +
                this.methodDescList.stream().collect(Collectors.joining(",")) + ", keep:" + this.keepOriginalMethodFlag + ", originalMethodRenameTo:" + this.originalMethodRenameTo + ", " +
                "keyData:" + this.keyData;
    }
}
