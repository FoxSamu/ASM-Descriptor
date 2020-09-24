package net.shadew.asm.descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InternalNameUtilTest {

    @Test
    void packageName() {
        assertEquals("test/class/in", InternalNameUtil.packageName("test/class/in/Package"));
        assertEquals("", InternalNameUtil.packageName("Package"));
    }

    @Test
    void className() {
        assertEquals("Package", InternalNameUtil.className("test/class/in/Package"));
        assertEquals("Package", InternalNameUtil.className("Package"));
        assertEquals("Inner$Class", InternalNameUtil.className("test/class/with/Inner$Class"));
    }

    @Test
    void simpleName() {
        assertEquals("Package", InternalNameUtil.simpleName("test/class/in/Package"));
        assertEquals("Package", InternalNameUtil.simpleName("Package"), "Package");
        assertEquals("Class", InternalNameUtil.simpleName("test/class/with/Inner$Class"));
    }

    @Test
    void isInnerClass() {
        assertEquals(false, InternalNameUtil.isInnerClass("test/class/in/Package"));
        assertEquals(true, InternalNameUtil.isInnerClass("test/class/with/Inner$Class"));
        assertEquals(true, InternalNameUtil.isInnerClass("test/class/with/Anonymous$Class$1"));
    }

    @Test
    void outerClass() {
        assertThrows(IllegalArgumentException.class, () -> InternalNameUtil.outerClass("test/class/in/Package"));
        assertEquals("test/class/with/Inner", InternalNameUtil.outerClass("test/class/with/Inner$Class"));
        assertEquals("test/class/with/Anonymous$Class", InternalNameUtil.outerClass("test/class/with/Anonymous$Class$1"));
    }

    @Test
    void rootClass() {
        assertEquals("test/class/in/Package", InternalNameUtil.rootClass("test/class/in/Package"));
        assertEquals("test/class/with/Inner", InternalNameUtil.rootClass("test/class/with/Inner$Class"));
        assertEquals("test/class/with/Anonymous", InternalNameUtil.rootClass("test/class/with/Anonymous$Class$1"));
        assertEquals("test/class/with/Many", InternalNameUtil.rootClass("test/class/with/Many$Very$Many$Inner$Classes"));
    }

    @Test
    void isAnonymous() {
        assertEquals(false, InternalNameUtil.isAnonymous("test/class/in/Package"));
        assertEquals(false, InternalNameUtil.isAnonymous("test/class/with/Inner$Class"));
        assertEquals(true, InternalNameUtil.isAnonymous("test/class/with/Anonymous$Class$1"));
    }

    @Test
    void inMainPackage() {
        assertEquals(false, InternalNameUtil.inMainPackage("test/class/in/Package"));
        assertEquals(true, InternalNameUtil.inMainPackage("Package"));
    }

    @Test
    void isValid() {
        assertEquals(true, InternalNameUtil.isValid("test/class/in/Package"));
        assertEquals(true, InternalNameUtil.isValid("test/class/with/Inner$Class"));
        assertEquals(true, InternalNameUtil.isValid("test/class/with/Anonymous$Class$1"));
        assertEquals(false, InternalNameUtil.isValid("test/class/with/1"));
        assertEquals(false, InternalNameUtil.isValid("test/3/in/Package"));
        assertEquals(false, InternalNameUtil.isValid("test/invalid.class/in/Package"));
    }

    @Test
    void toCode() {
        assertEquals("Package", InternalNameUtil.toCode("Package"));
        assertEquals("test.class.in.Package", InternalNameUtil.toCode("test/class/in/Package"));
        assertEquals("test.class.with.Inner.Class", InternalNameUtil.toCode("test/class/with/Inner$Class"));
        assertEquals("test.class.with.Anonymous.Class.1", InternalNameUtil.toCode("test/class/with/Anonymous$Class$1"));
        assertEquals("test.class.with.Many.Very.Many.Inner.Classes", InternalNameUtil.toCode("test/class/with/Many$Very$Many$Inner$Classes"));
    }

    @Test
    void wrapPackage() {
        assertEquals("this/is/a/package/Class", InternalNameUtil.wrapPackage("this/is/a/package", "Class"));
        assertEquals("Class", InternalNameUtil.wrapPackage("", "Class"));
    }

    @Test
    void wrapClass() {
        assertEquals("this/is/a/Class$Class", InternalNameUtil.wrapClass("this/is/a/Class", "Class"));
    }

    @Test
    void nameOf() {
        assertEquals("java/lang/String", InternalNameUtil.nameOf(String.class));
        assertEquals("net/shadew/asm/descriptor/PrimitiveDescriptor", InternalNameUtil.nameOf(PrimitiveDescriptor.class));
        assertEquals("net/shadew/asm/descriptor/InternalNameUtilTest$TestInner", InternalNameUtil.nameOf(TestInner.class));
    }

    @Test
    void inPackage() {
        assertEquals(true, InternalNameUtil.inPackage("java/lang/String", "java"));
        assertEquals(true, InternalNameUtil.inPackage("java/lang/String", "java/lang"));
        assertEquals(false, InternalNameUtil.inPackage("java/lang/String", "java/la"));
        assertEquals(false, InternalNameUtil.inPackage("java/lang/String", "java/lang/Str"));
        assertEquals(true, InternalNameUtil.inPackage("java/lang/String", ""));
    }

    @Test
    void inClass() {
        assertEquals(false, InternalNameUtil.inClass("java/lang/String", "java"));
        assertEquals(false, InternalNameUtil.inClass("java/lang/String", "java/lang"));
        assertEquals(false, InternalNameUtil.inClass("some/te$ting/Magic", "some/te"));
        assertEquals(true, InternalNameUtil.inClass("some/more/Testing$Magic", "some/more/Testing"));
        assertEquals(false, InternalNameUtil.inClass("some/more/Testing$Magic", "some/more/Te"));
    }

    @Test
    void renamePackage() {
        assertEquals("c/lang/String", InternalNameUtil.renamePackage("java/lang/String", "java", "c"));
        assertEquals("lava/jang/String", InternalNameUtil.renamePackage("java/lang/String", "java/lang", "lava/jang"));
        assertEquals("java/lang/String", InternalNameUtil.renamePackage("java/lang/String", "java/la", "lava/ja"));
        assertEquals("java/lang/String", InternalNameUtil.renamePackage("java/lang/String", "java/lang/Str", "lava/jang/Str"));
        assertEquals("java/lang/String", InternalNameUtil.renamePackage("java/lang/String", "java/lang/String", "lava/jang/String"));
        assertEquals("wrapped/java/lang/String", InternalNameUtil.renamePackage("java/lang/String", "", "wrapped"));
    }

    @Test
    void renameClass() {
        assertEquals("java/lang/String", InternalNameUtil.renameClass("java/lang/String", "java", "c"));
        assertEquals("java/lang/String", InternalNameUtil.renameClass("java/lang/String", "java/lang", "lava/jang"));
        assertEquals("lava/jang/String", InternalNameUtil.renameClass("java/lang/String", "java/lang/String", "lava/jang/String"));
        assertEquals("some/te$ting/Magic", InternalNameUtil.renameClass("some/te$ting/Magic", "some/te", "no/te"));
        assertEquals("some/more/Renaming$Magic", InternalNameUtil.renameClass("some/more/Testing$Magic", "some/more/Testing", "some/more/Renaming"));
        assertEquals("some/more/Testing$Magic", InternalNameUtil.renameClass("some/more/Testing$Magic", "some/more/Te", "some/more/Gue"));
    }


    static class TestInner {
    }
}
