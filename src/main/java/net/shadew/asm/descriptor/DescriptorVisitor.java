package net.shadew.asm.descriptor;

public abstract class DescriptorVisitor {
    public abstract void visitInternalName(String internalName);

    public void visitReference(ReferenceDescriptor desc) {
        visitInternalName(desc.internalName());
    }

    public void visitArray(ArrayDescriptor desc) {
        visitType(desc.element());
    }

    public void visitPrimitive(PrimitiveDescriptor desc) {

    }

    public void visitType(TypeDescriptor desc) {
        if (desc.isReference()) visitReference(desc.asReference());
        if (desc.isArray()) visitArray(desc.asArray());
        if (desc.isPrimitive()) visitPrimitive(desc.asPrimitive());
    }

    public void visitMethod(MethodDescriptor desc) {
        desc.parameters().forEach(this::visitType);
        visitType(desc.returnType());
    }

    public void visit(Descriptor desc) {
        if (desc.isType()) visitType(desc.asType());
        if (desc.isMethod()) visitMethod(desc.asMethod());
    }
}
