package net.shadew.asm.descriptor;

public final class DescriptorTests {
    private DescriptorTests() {
    }

    public static void main(String[] args) {
        System.out.println(MethodDescriptor.of(PrimitiveDescriptor.VOID, PrimitiveDescriptor.INT, new ArrayDescriptor(new ReferenceDescriptor("java/lang/String")), TypeDescriptor.reflect(int[].class)));
    }
}
