package net.shadew.asm.descriptor;

import org.objectweb.asm.Type;

import net.shadew.util.contract.Validate;

public final class ArrayDescriptor extends TypeDescriptor {
    private final TypeDescriptor element;

    ArrayDescriptor(TypeDescriptor element) {
        TypeDescriptor root = element;
        int dimensions = 1;
        while (root.isArray()) {
            dimensions++;
            root = root.asArray().root();
        }

        this.element = element;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public char prefix() {
        return '[';
    }

    @Override
    public String toString() {
        return "[" + element.toString();
    }

    @Override
    public Type toAsm() {
        return Type.getType(this.toString());
    }

    @Override
    public String toCode() {
        return element.toCode() + "[]";
    }

    public TypeDescriptor element() {
        return element;
    }

    public TypeDescriptor root() {
        TypeDescriptor out = element;
        while (out.isArray())
            out = out.asArray().element();
        return out;
    }

    public int dimensions() {
        if (element.isArray()) return 1 + element.asArray().dimensions();
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayDescriptor that = (ArrayDescriptor) o;

        // Compare on dimensions and root to skip some recursion
        return element.equals(that.element);
    }

    @Override
    public int hashCode() {
        return element.hashCode() * 31 + 47;
    }

    public static ArrayDescriptor parse(String desc) {
        Validate.notNull(desc, "desc");
        return DescriptorParser.array(desc);
    }

    public static ArrayDescriptor of(TypeDescriptor element) {
        Validate.notNull(element, "element");
        return new ArrayDescriptor(element);
    }

    public static ArrayDescriptor of(TypeDescriptor element, int dimensions) {
        Validate.notNull(element, "element");
        Validate.positive(dimensions, "dimensions");

        ArrayDescriptor out = of(element);
        while (dimensions-- > 1)
            out = of(out);

        return out;
    }
}
