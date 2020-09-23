package net.shadew.asm.descriptor;

import org.objectweb.asm.Type;

import net.shadew.util.contract.Validate;

public final class ReferenceDescriptor extends TypeDescriptor {
    private final String internalName;

    ReferenceDescriptor(String internalName) {
        this.internalName = internalName;
    }

    @Override
    public boolean isReference() {
        return true;
    }

    @Override
    public String toString() {
        return "L" + internalName + ";";
    }

    @Override
    public Type toAsm() {
        return Type.getObjectType(internalName);
    }

    @Override
    public String toCode() {
        return InternalNameUtil.toCode(internalName);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public char prefix() {
        return 'L';
    }

    public String internalName() {
        return internalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceDescriptor that = (ReferenceDescriptor) o;
        return internalName.equals(that.internalName);
    }

    @Override
    public int hashCode() {
        return internalName.hashCode();
    }

    public static ReferenceDescriptor parse(String desc) {
        Validate.notNull(desc, "desc");
        return DescriptorParser.reference(desc);
    }

    public static ReferenceDescriptor of(String internalName) {
        Validate.notNull(internalName, "internalName");
        return new ReferenceDescriptor(internalName);
    }
}
