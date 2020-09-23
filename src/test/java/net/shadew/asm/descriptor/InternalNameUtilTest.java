package net.shadew.asm.descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InternalNameUtilTest {

    @Test
    void packageName() {
        assertEquals(InternalNameUtil.packageName("test/class/in/Package"), "test/class/in");
        assertEquals(InternalNameUtil.packageName("Package"), "");
    }

    @Test
    void className() {
        assertEquals(InternalNameUtil.className("test/class/in/Package"), "Package");
        assertEquals(InternalNameUtil.className("Package"), "Package");
        assertEquals(InternalNameUtil.className("test/class/with/Inner$Class"), "Inner$Class");
    }

    @Test
    void simpleName() {
        assertEquals(InternalNameUtil.simpleName("test/class/in/Package"), "Package");
        assertEquals(InternalNameUtil.simpleName("Package"), "Package");
        assertEquals(InternalNameUtil.simpleName("test/class/with/Inner$Class"), "Class");
    }

    @Test
    void isInnerClass() {
        assertEquals(InternalNameUtil.isInnerClass("test/class/in/Package"), false);
        assertEquals(InternalNameUtil.isInnerClass("test/class/with/Inner$Class"), true);
        assertEquals(InternalNameUtil.isInnerClass("test/class/with/Anonymous$Class$1"), true);
    }

    @Test
    void outerClass() {
        assertThrows(IllegalArgumentException.class, () -> InternalNameUtil.outerClass("test/class/in/Package"));
        assertEquals(InternalNameUtil.outerClass("test/class/with/Inner$Class"), "test/class/with/Inner");
        assertEquals(InternalNameUtil.outerClass("test/class/with/Anonymous$Class$1"), "test/class/with/Anonymous$Class");
    }

    @Test
    void rootClass() {
        assertEquals(InternalNameUtil.rootClass("test/class/in/Package"), "test/class/in/Package");
        assertEquals(InternalNameUtil.rootClass("test/class/with/Inner$Class"), "test/class/with/Inner");
        assertEquals(InternalNameUtil.rootClass("test/class/with/Anonymous$Class$1"), "test/class/with/Anonymous");
        assertEquals(InternalNameUtil.rootClass("test/class/with/Many$Very$Many$Inner$Classes"), "test/class/with/Many");
    }

    @Test
    void isAnonymous() {
        assertEquals(InternalNameUtil.isAnonymous("test/class/in/Package"), false);
        assertEquals(InternalNameUtil.isAnonymous("test/class/with/Inner$Class"), false);
        assertEquals(InternalNameUtil.isAnonymous("test/class/with/Anonymous$Class$1"), true);
    }

    @Test
    void inMainPackage() {
        assertEquals(InternalNameUtil.inMainPackage("test/class/in/Package"), false);
        assertEquals(InternalNameUtil.inMainPackage("Package"), true);
    }

    @Test
    void isValid() {
        assertEquals(InternalNameUtil.isValid("test/class/in/Package"), true);
        assertEquals(InternalNameUtil.isValid("test/class/with/Inner$Class"), true);
        assertEquals(InternalNameUtil.isValid("test/class/with/Anonymous$Class$1"), true);
        assertEquals(InternalNameUtil.isValid("test/class/with/1"), false);
        assertEquals(InternalNameUtil.isValid("test/3/in/Package"), false);
        assertEquals(InternalNameUtil.isValid("test/invalid.class/in/Package"), false);
    }

    @Test
    void toCode() {
        assertEquals(InternalNameUtil.toCode("Package"), "Package");
        assertEquals(InternalNameUtil.toCode("test/class/in/Package"), "test.class.in.Package");
        assertEquals(InternalNameUtil.toCode("test/class/with/Inner$Class"), "test.class.with.Inner.Class");
        assertEquals(InternalNameUtil.toCode("test/class/with/Anonymous$Class$1"), "test.class.with.Anonymous.Class.1");
        assertEquals(InternalNameUtil.toCode("test/class/with/Many$Very$Many$Inner$Classes"), "test.class.with.Many.Very.Many.Inner.Classes");
    }

    @Test
    void wrapPackage() {
        assertEquals(InternalNameUtil.wrapPackage("this/is/a/package", "Class"), "this/is/a/package/Class");
        assertEquals(InternalNameUtil.wrapPackage("", "Class"), "Class");
    }

    @Test
    void wrapClass() {
        assertEquals(InternalNameUtil.wrapClass("this/is/a/Class", "Class"), "this/is/a/Class$Class");
    }

    @Test
    void nameOf() {
        assertEquals(InternalNameUtil.nameOf(String.class), "java/lang/String");
        assertEquals(InternalNameUtil.nameOf(PrimitiveDescriptor.class), "net/shadew/asm/descriptor/PrimitiveDescriptor");
        assertEquals(InternalNameUtil.nameOf(TestInner.class), "net/shadew/asm/descriptor/InternalNameUtilTest$TestInner");
    }

    static class TestInner {
    }
}
