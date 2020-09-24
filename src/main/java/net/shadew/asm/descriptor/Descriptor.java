package net.shadew.asm.descriptor;

import org.objectweb.asm.Type;

import net.shadew.util.contract.Validate;

public abstract class Descriptor {
    Descriptor() {
    }

    public boolean isMethod() {
        return false;
    }

    public boolean isType() {
        return false;
    }

    public boolean isReference() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean isVoid() {
        return this == PrimitiveDescriptor.VOID;
    }

    public TypeDescriptor asType() {
        return (TypeDescriptor) this;
    }

    public MethodDescriptor asMethod() {
        return (MethodDescriptor) this;
    }

    public ReferenceDescriptor asReference() {
        return (ReferenceDescriptor) this;
    }

    public ArrayDescriptor asArray() {
        return (ArrayDescriptor) this;
    }

    public PrimitiveDescriptor asPrimitive() {
        return (PrimitiveDescriptor) this;
    }

    public abstract String toString();
    public abstract Type toAsm();
    public abstract String toCode();
    public abstract String toCode(String memberName);

    public abstract Descriptor remap(Mapper mapper);

    public void accept(DescriptorVisitor visitor) {
        visitor.visit(this);
    }

    public static Descriptor parse(String desc) {
        Validate.notNull(desc, "desc");
        return DescriptorParser.descriptor(desc);
    }

    public static Descriptor asm(Type type) {
        Validate.notNull(type, "type");
        int sort = type.getSort();
        if (sort == Type.BYTE) {
            return PrimitiveDescriptor.BYTE;
        }
        if (sort == Type.SHORT) {
            return PrimitiveDescriptor.SHORT;
        }
        if (sort == Type.INT) {
            return PrimitiveDescriptor.INT;
        }
        if (sort == Type.LONG) {
            return PrimitiveDescriptor.LONG;
        }
        if (sort == Type.FLOAT) {
            return PrimitiveDescriptor.FLOAT;
        }
        if (sort == Type.DOUBLE) {
            return PrimitiveDescriptor.DOUBLE;
        }
        if (sort == Type.BOOLEAN) {
            return PrimitiveDescriptor.BOOLEAN;
        }
        if (sort == Type.CHAR) {
            return PrimitiveDescriptor.CHAR;
        }
        if (sort == Type.VOID) {
            return PrimitiveDescriptor.VOID;
        }
        if (sort == Type.OBJECT) {
            return new ReferenceDescriptor(type.getInternalName());
        }
        if (sort == Type.ARRAY) {
            return new ArrayDescriptor(asm(Type.getType(type.getDescriptor().substring(1))).asType());
        }
        if (sort == Type.METHOD) {
            Type[] args = type.getArgumentTypes();
            TypeDescriptor[] pars = new TypeDescriptor[args.length];
            for (int i = 0, l = args.length; i < l; i++) {
                pars[i] = asm(args[i]).asType();
            }
            TypeDescriptor ret = asm(type.getReturnType()).asType();
            return new MethodDescriptor(ret, pars);
        }
        return Validate.illegalState();
    }
}
