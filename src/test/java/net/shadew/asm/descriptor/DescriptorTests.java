package net.shadew.asm.descriptor;

public final class DescriptorTests {
    private DescriptorTests() {
    }

    public static void main(String[] args) {
        Mapper m = name -> name.startsWith("java/lang") ? "lava/jang" + name.substring(9) : name;
        System.out.println(
            MethodDescriptor.of(PrimitiveDescriptor.VOID, PrimitiveDescriptor.INT, new ArrayDescriptor(new ReferenceDescriptor("java/lang/String")), TypeDescriptor.reflect(int[].class))
                            .remap(m)
                            .toCode()
        );
    }
}
