package net.shadew.asm.descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DescriptorParserTest {
    @Test
    void testPrimitives() {
        assertEquals(PrimitiveDescriptor.parse("B").toCode(), "byte");
        assertEquals(PrimitiveDescriptor.parse("S").toCode(), "short");
        assertEquals(PrimitiveDescriptor.parse("I").toCode(), "int");
        assertEquals(PrimitiveDescriptor.parse("J").toCode(), "long");
        assertEquals(PrimitiveDescriptor.parse("F").toCode(), "float");
        assertEquals(PrimitiveDescriptor.parse("D").toCode(), "double");
        assertEquals(PrimitiveDescriptor.parse("Z").toCode(), "boolean");
        assertEquals(PrimitiveDescriptor.parse("C").toCode(), "char");

        assertThrows(DescriptorFormatException.class, () -> PrimitiveDescriptor.parse("V"));
        assertThrows(DescriptorFormatException.class, () -> PrimitiveDescriptor.parse("LClass;"));
        assertThrows(DescriptorFormatException.class, () -> PrimitiveDescriptor.parse("[LArray;"));
    }

    @Test
    void testArray() {
        assertEquals(ArrayDescriptor.parse("[I").toCode(), "int[]");
        assertEquals(ArrayDescriptor.parse("[[[I").toCode(), "int[][][]");
        assertEquals(ArrayDescriptor.parse("[[[I").dimensions(), 3);
        assertEquals(ArrayDescriptor.parse("[[[I").root(), PrimitiveDescriptor.INT);

        assertThrows(DescriptorFormatException.class, () -> ArrayDescriptor.parse("[[V"));
        assertThrows(DescriptorFormatException.class, () -> ArrayDescriptor.parse("[["));
        assertThrows(DescriptorFormatException.class, () -> ArrayDescriptor.parse("I"));
    }

    @Test
    void testReference() {
        assertEquals(ReferenceDescriptor.parse("Ljava/lang/String;").toCode(), "java.lang.String");
        assertEquals(ReferenceDescriptor.parse("Ljava/lang/String$1;").toCode(), "java.lang.String.1");
        assertEquals(ReferenceDescriptor.parse("Ljava/lang/String$Magic;").toCode(), "java.lang.String.Magic");

        assertThrows(DescriptorFormatException.class, () -> ReferenceDescriptor.parse("Ljava/lang/String"));
        assertThrows(DescriptorFormatException.class, () -> ReferenceDescriptor.parse("java/lang/String;"));
        assertThrows(DescriptorFormatException.class, () -> ReferenceDescriptor.parse("I"));
    }

    @Test
    void testMethod() {
        assertEquals(MethodDescriptor.parse("(Ljava/lang/String;)V").toCode(), "void (java.lang.String)");
        assertEquals(MethodDescriptor.parse("(IIIIJ)V").toCode(), "void (int, int, int, int, long)");
        assertEquals(MethodDescriptor.parse("(FD)Lreturn/Type;").toCode(), "return.Type (float, double)");
        assertEquals(MethodDescriptor.parse("(F[LArray;)Lreturn/Type;").toCode(), "return.Type (float, Array[])");
        assertEquals(MethodDescriptor.parse("(F[LArray;)[Lreturn/Type;").toCode(), "return.Type[] (float, Array[])");
        assertEquals(MethodDescriptor.parse("()V").toCode(), "void ()");
        assertEquals(MethodDescriptor.parse("()I").toCode(), "int ()");

        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("(Ljava/lang/String)V"));
        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("(java/lang/String;)V"));
        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("V"));
        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("[I"));
    }

    @Test
    void testType() {
        assertEquals(TypeDescriptor.parse("B").toCode(), "byte");
        assertEquals(TypeDescriptor.parse("S").toCode(), "short");
        assertEquals(TypeDescriptor.parse("I").toCode(), "int");
        assertEquals(TypeDescriptor.parse("J").toCode(), "long");
        assertEquals(TypeDescriptor.parse("F").toCode(), "float");
        assertEquals(TypeDescriptor.parse("D").toCode(), "double");
        assertEquals(TypeDescriptor.parse("Z").toCode(), "boolean");
        assertEquals(TypeDescriptor.parse("C").toCode(), "char");
        assertEquals(TypeDescriptor.parse("Ljava/lang/String;").toCode(), "java.lang.String");
        assertEquals(TypeDescriptor.parse("Ljava/lang/String$1;").toCode(), "java.lang.String.1");
        assertEquals(TypeDescriptor.parse("Ljava/lang/String$Magic;").toCode(), "java.lang.String.Magic");
        assertEquals(TypeDescriptor.parse("[I").toCode(), "int[]");
        assertEquals(TypeDescriptor.parse("[[[I").toCode(), "int[][][]");

        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("(Lthis;Lis;La;)Lmethod;"));
        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("-invalid-"));
        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("Lextra;Linput;"));
    }

    @Test
    void testDesc() {
        assertEquals(Descriptor.parse("B").toCode(), "byte");
        assertEquals(Descriptor.parse("S").toCode(), "short");
        assertEquals(Descriptor.parse("I").toCode(), "int");
        assertEquals(Descriptor.parse("J").toCode(), "long");
        assertEquals(Descriptor.parse("F").toCode(), "float");
        assertEquals(Descriptor.parse("D").toCode(), "double");
        assertEquals(Descriptor.parse("Z").toCode(), "boolean");
        assertEquals(Descriptor.parse("C").toCode(), "char");
        assertEquals(Descriptor.parse("Ljava/lang/String;").toCode(), "java.lang.String");
        assertEquals(Descriptor.parse("Ljava/lang/String$1;").toCode(), "java.lang.String.1");
        assertEquals(Descriptor.parse("Ljava/lang/String$Magic;").toCode(), "java.lang.String.Magic");
        assertEquals(Descriptor.parse("[I").toCode(), "int[]");
        assertEquals(Descriptor.parse("[[[I").toCode(), "int[][][]");
        assertEquals(Descriptor.parse("(Ljava/lang/String;)V").toCode(), "void (java.lang.String)");
        assertEquals(Descriptor.parse("(IIIIJ)V").toCode(), "void (int, int, int, int, long)");
        assertEquals(Descriptor.parse("(FD)Lreturn/Type;").toCode(), "return.Type (float, double)");
        assertEquals(Descriptor.parse("(F[LArray;)Lreturn/Type;").toCode(), "return.Type (float, Array[])");
        assertEquals(Descriptor.parse("(F[LArray;)[Lreturn/Type;").toCode(), "return.Type[] (float, Array[])");
        assertEquals(Descriptor.parse("()V").toCode(), "void ()");
        assertEquals(Descriptor.parse("()I").toCode(), "int ()");

        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("-invalid-"));
        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("Lextra;Linput;"));
    }
}
