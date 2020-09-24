package net.shadew.asm.descriptor;

public final class DescriptorTests {
    private DescriptorTests() {
    }

    public static void main(String[] args) {
        System.out.println(
            MethodDescriptor.parse("(IIILjava/lang/String;Ljava/util/Random;[Lfoo/bar/Baz;)Lsome/random/Shit;")
                            .toCode("randomMethod")
        );
    }
}
