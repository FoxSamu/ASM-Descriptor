package net.shadew.asm.descriptor;

import net.shadew.util.contract.Validate;

public abstract class TypeDescriptor extends Descriptor {
    TypeDescriptor() {
    }

    @Override
    public final boolean isType() {
        return true;
    }

    public abstract int size();

    public abstract char prefix();

    @Override
    public void accept(DescriptorVisitor visitor) {
        visitor.visitType(this);
    }

    @Override
    public abstract TypeDescriptor remap(Mapper mapper);

    @Override
    public String toCode(String memberName) {
        return toCode() + " " + memberName;
    }

    public static TypeDescriptor parse(String desc) {
        Validate.notNull(desc, "desc");
        return DescriptorParser.type(desc);
    }

    public static TypeDescriptor reflect(Class<?> cls) {
        Validate.notNull(cls, "cls");
        if (cls.isPrimitive()) {
            if (cls == void.class) {
                return PrimitiveDescriptor.VOID;
            }
            if (cls == byte.class) {
                return PrimitiveDescriptor.BYTE;
            }
            if (cls == short.class) {
                return PrimitiveDescriptor.SHORT;
            }
            if (cls == int.class) {
                return PrimitiveDescriptor.INT;
            }
            if (cls == long.class) {
                return PrimitiveDescriptor.LONG;
            }
            if (cls == float.class) {
                return PrimitiveDescriptor.FLOAT;
            }
            if (cls == double.class) {
                return PrimitiveDescriptor.DOUBLE;
            }
            if (cls == boolean.class) {
                return PrimitiveDescriptor.BOOLEAN;
            }
            if (cls == char.class) {
                return PrimitiveDescriptor.CHAR;
            }
            return Validate.illegalState();
        } else if (cls.isArray()) {
            return new ArrayDescriptor(reflect(cls.getComponentType()));
        } else {
            return new ReferenceDescriptor(cls.getName().replace('.', '/'));
        }
    }

    public static TypeDescriptor type(Object obj) {
        Validate.notNull(obj, "obj");
        return reflect(obj.getClass());
    }
}
