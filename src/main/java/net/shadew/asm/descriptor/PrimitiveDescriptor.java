package net.shadew.asm.descriptor;

import org.objectweb.asm.Type;

import net.shadew.util.contract.Validate;

public class PrimitiveDescriptor extends TypeDescriptor {
    public static final PrimitiveDescriptor BYTE = new PrimitiveDescriptor('B', 1, Type.BYTE_TYPE, "byte");
    public static final PrimitiveDescriptor SHORT = new PrimitiveDescriptor('S', 1, Type.SHORT_TYPE, "short");
    public static final PrimitiveDescriptor INT = new PrimitiveDescriptor('I', 1, Type.INT_TYPE, "int");
    public static final PrimitiveDescriptor LONG = new PrimitiveDescriptor('J', 2, Type.LONG_TYPE, "long");
    public static final PrimitiveDescriptor FLOAT = new PrimitiveDescriptor('F', 1, Type.FLOAT_TYPE, "float");
    public static final PrimitiveDescriptor DOUBLE = new PrimitiveDescriptor('D', 2, Type.DOUBLE_TYPE, "double");
    public static final PrimitiveDescriptor BOOLEAN = new PrimitiveDescriptor('Z', 1, Type.BOOLEAN_TYPE, "boolean");
    public static final PrimitiveDescriptor CHAR = new PrimitiveDescriptor('C', 1, Type.CHAR_TYPE, "char");
    public static final PrimitiveDescriptor VOID = new PrimitiveDescriptor('V', 0, Type.VOID_TYPE, "void");

    private final char prefix;
    private final int size;
    private final Type type;
    private final String code;

    private PrimitiveDescriptor(char desc, int size, Type type, String code) {
        this.prefix = desc;
        this.size = size;
        this.type = type;
        this.code = code;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public char prefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return prefix + "";
    }

    @Override
    public Type toAsm() {
        return type;
    }

    @Override
    public String toCode() {
        return code;
    }

    public static PrimitiveDescriptor parse(String desc) {
        Validate.notNull(desc, "desc");
        return DescriptorParser.primitive(desc);
    }
}
