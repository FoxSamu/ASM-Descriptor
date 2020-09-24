package net.shadew.asm.descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DescriptorParserTest {
    @Test
    void testPrimitives() {
        assertEquals("byte", PrimitiveDescriptor.parse("B").toCode());
        assertEquals("short", PrimitiveDescriptor.parse("S").toCode());
        assertEquals("int", PrimitiveDescriptor.parse("I").toCode());
        assertEquals("long", PrimitiveDescriptor.parse("J").toCode());
        assertEquals("float", PrimitiveDescriptor.parse("F").toCode());
        assertEquals("double", PrimitiveDescriptor.parse("D").toCode());
        assertEquals("boolean", PrimitiveDescriptor.parse("Z").toCode());
        assertEquals("char", PrimitiveDescriptor.parse("C").toCode());

        assertThrows(DescriptorFormatException.class, () -> PrimitiveDescriptor.parse("V"));
        assertThrows(DescriptorFormatException.class, () -> PrimitiveDescriptor.parse("LClass;"));
        assertThrows(DescriptorFormatException.class, () -> PrimitiveDescriptor.parse("[LArray;"));
    }

    @Test
    void testArray() {
        assertEquals("int[]", ArrayDescriptor.parse("[I").toCode());
        assertEquals("int[][][]", ArrayDescriptor.parse("[[[I").toCode());
        assertEquals(3, ArrayDescriptor.parse("[[[I").dimensions());
        assertEquals(PrimitiveDescriptor.INT, ArrayDescriptor.parse("[[[I").root());

        assertThrows(DescriptorFormatException.class, () -> ArrayDescriptor.parse("[[V"));
        assertThrows(DescriptorFormatException.class, () -> ArrayDescriptor.parse("[["));
        assertThrows(DescriptorFormatException.class, () -> ArrayDescriptor.parse("I"));
    }

    @Test
    void testReference() {
        assertEquals("java.lang.String", ReferenceDescriptor.parse("Ljava/lang/String;").toCode());
        assertEquals("java.lang.String.1", ReferenceDescriptor.parse("Ljava/lang/String$1;").toCode());
        assertEquals("java.lang.String.Magic", ReferenceDescriptor.parse("Ljava/lang/String$Magic;").toCode());

        assertThrows(DescriptorFormatException.class, () -> ReferenceDescriptor.parse("Ljava/lang/String"));
        assertThrows(DescriptorFormatException.class, () -> ReferenceDescriptor.parse("java/lang/String;"));
        assertThrows(DescriptorFormatException.class, () -> ReferenceDescriptor.parse("I"));
    }

    @Test
    void testMethod() {
        assertEquals("void (java.lang.String)", MethodDescriptor.parse("(Ljava/lang/String;)V").toCode());
        assertEquals("void (int, int, int, int, long)", MethodDescriptor.parse("(IIIIJ)V").toCode());
        assertEquals("return.Type (float, double)", MethodDescriptor.parse("(FD)Lreturn/Type;").toCode());
        assertEquals("return.Type (float, Array[])", MethodDescriptor.parse("(F[LArray;)Lreturn/Type;").toCode());
        assertEquals("return.Type[] (float, Array[])", MethodDescriptor.parse("(F[LArray;)[Lreturn/Type;").toCode());
        assertEquals("void ()", MethodDescriptor.parse("()V").toCode());
        assertEquals("int ()", MethodDescriptor.parse("()I").toCode());

        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("(Ljava/lang/String)V"));
        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("(java/lang/String;)V"));
        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("V"));
        assertThrows(DescriptorFormatException.class, () -> MethodDescriptor.parse("[I"));
    }

    @Test
    void testType() {
        assertEquals("byte", TypeDescriptor.parse("B").toCode());
        assertEquals("short", TypeDescriptor.parse("S").toCode());
        assertEquals("int", TypeDescriptor.parse("I").toCode());
        assertEquals("long", TypeDescriptor.parse("J").toCode());
        assertEquals("float", TypeDescriptor.parse("F").toCode());
        assertEquals("double", TypeDescriptor.parse("D").toCode());
        assertEquals("boolean", TypeDescriptor.parse("Z").toCode());
        assertEquals("char", TypeDescriptor.parse("C").toCode());
        assertEquals("java.lang.String", TypeDescriptor.parse("Ljava/lang/String;").toCode());
        assertEquals("java.lang.String.1", TypeDescriptor.parse("Ljava/lang/String$1;").toCode());
        assertEquals("java.lang.String.Magic", TypeDescriptor.parse("Ljava/lang/String$Magic;").toCode());
        assertEquals("int[]", TypeDescriptor.parse("[I").toCode());
        assertEquals("int[][][]", TypeDescriptor.parse("[[[I").toCode());

        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("(Lthis;Lis;La;)Lmethod;"));
        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("-invalid-"));
        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("Lextra;Linput;"));
    }

    @Test
    void testDesc() {
        assertEquals("byte", Descriptor.parse("B").toCode());
        assertEquals("short", Descriptor.parse("S").toCode());
        assertEquals("int", Descriptor.parse("I").toCode());
        assertEquals("long", Descriptor.parse("J").toCode());
        assertEquals("float", Descriptor.parse("F").toCode());
        assertEquals("double", Descriptor.parse("D").toCode());
        assertEquals("boolean", Descriptor.parse("Z").toCode());
        assertEquals("char", Descriptor.parse("C").toCode());
        assertEquals("java.lang.String", Descriptor.parse("Ljava/lang/String;").toCode());
        assertEquals("java.lang.String.1", Descriptor.parse("Ljava/lang/String$1;").toCode());
        assertEquals("java.lang.String.Magic", Descriptor.parse("Ljava/lang/String$Magic;").toCode());
        assertEquals("int[]", Descriptor.parse("[I").toCode());
        assertEquals("int[][][]", Descriptor.parse("[[[I").toCode());
        assertEquals("void (java.lang.String)", Descriptor.parse("(Ljava/lang/String;)V").toCode());
        assertEquals("void (int, int, int, int, long)", Descriptor.parse("(IIIIJ)V").toCode());
        assertEquals("return.Type (float, double)", Descriptor.parse("(FD)Lreturn/Type;").toCode());
        assertEquals("return.Type (float, Array[])", Descriptor.parse("(F[LArray;)Lreturn/Type;").toCode());
        assertEquals("return.Type[] (float, Array[])", Descriptor.parse("(F[LArray;)[Lreturn/Type;").toCode());
        assertEquals("void ()", Descriptor.parse("()V").toCode());
        assertEquals("int ()", Descriptor.parse("()I").toCode());

        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("-invalid-"));
        assertThrows(DescriptorFormatException.class, () -> TypeDescriptor.parse("Lextra;Linput;"));
    }
}
