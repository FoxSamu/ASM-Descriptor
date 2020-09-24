package net.shadew.asm.descriptor;

import java.util.regex.Pattern;

import net.shadew.util.contract.Validate;

public final class InternalNameUtil {
    private static final Pattern VALID_CLASS_PATTERN = Pattern.compile("^(?:[a-zA-Z$_][a-zA-Z0-9$_]*?/)*[a-zA-Z$_][a-zA-Z0-9$_]*?(?:\\$[a-zA-Z0-9_]+?)*$");
    private static final Pattern ANONYMOUS_CLASS_PATTERN = Pattern.compile("^(.*)\\$[0-9]+$");

    private InternalNameUtil() {
    }

    public static String packageName(String name) {
        Validate.notNull(name, "name");

        int lastSlash = name.lastIndexOf('/');
        return lastSlash == -1 ? "" : name.substring(0, lastSlash);
    }

    public static String className(String name) {
        Validate.notNull(name, "name");

        int lastSlash = name.lastIndexOf('/');
        return lastSlash == -1 ? name : name.substring(lastSlash + 1);
    }

    public static String simpleName(String name) {
        Validate.notNull(name, "name");

        String clsName = className(name);
        int lastDollar = clsName.lastIndexOf('$');
        return lastDollar == -1 ? clsName : clsName.substring(lastDollar + 1);
    }

    public static boolean isInnerClass(String name) {
        Validate.notNull(name, "name");
        return className(name).lastIndexOf('$') != -1;
    }

    public static String outerClass(String name) {
        Validate.notNull(name, "name");

        int lastSlash = name.lastIndexOf('/');
        String clsName = lastSlash == -1 ? name : name.substring(lastSlash + 1);
        String pkgName = lastSlash == -1 ? "" : name.substring(0, lastSlash);

        int lastDollar = clsName.lastIndexOf('$');
        if (lastDollar == -1)
            Validate.illegalArgument("Not an inner class: '" + name + "'");

        return pkgName + "/" + clsName.substring(0, lastDollar);
    }

    public static String rootClass(String name) {
        Validate.notNull(name, "name");

        int lastSlash = name.lastIndexOf('/');
        String clsName = lastSlash == -1 ? name : name.substring(lastSlash + 1);
        String pkgName = lastSlash == -1 ? "" : name.substring(0, lastSlash);

        int dollar = clsName.indexOf('$');
        if (dollar == -1)
            return name;

        return pkgName + "/" + clsName.substring(0, dollar);
    }

    public static boolean isAnonymous(String name) {
        Validate.notNull(name, "name");
        return ANONYMOUS_CLASS_PATTERN.matcher(name).matches();
    }

    public static boolean inMainPackage(String name) {
        Validate.notNull(name, "name");

        return name.lastIndexOf('/') == -1;
    }

    public static boolean isValid(String name) {
        Validate.notNull(name, "name");
        return VALID_CLASS_PATTERN.matcher(name).matches();
    }

    public static String toCode(String name) {
        Validate.notNull(name, "name");
        if (inMainPackage(name)) return className(name).replace('$', '.');
        return packageName(name).replace('/', '.') + "." + className(name).replace('$', '.');
    }

    public static String wrapPackage(String pkg, String cls) {
        Validate.notNull(pkg, "pkg");
        Validate.notNull(cls, "cls");
        if (pkg.isEmpty()) return cls;
        return pkg + "/" + cls;
    }

    public static String wrapClass(String name, String inner) {
        Validate.notNull(name, "name");
        Validate.notNull(inner, "inner");
        return name + "$" + inner;
    }

    public static String nameOf(Class<?> cls) {
        Validate.notNull(cls, "cls");
        Validate.isFalse(cls.isArray(), "'cls' is an array");
        Validate.isFalse(cls.isPrimitive(), "'cls' is primitive");
        return cls.getName().replace('.', '/');
    }

    public static boolean inPackage(String name, String pkg) {
        Validate.notNull(name, "name");
        Validate.notNull(pkg, "pkg");
        if (pkg.isEmpty()) return true;
        int splitAt = pkg.length();
        if (splitAt >= name.length()) return false;
        String left = name.substring(0, splitAt);
        String right = name.substring(splitAt);
        return right.charAt(0) == '/' && left.equals(pkg);
    }

    public static String renamePackage(String name, String from, String to) {
        Validate.notNull(name, "name");
        Validate.notNull(from, "from");
        Validate.notNull(to, "to");
        if (from.equals(to)) return name;
        if (from.isEmpty()) return to + "/" + name;
        int splitAt = from.length();
        if (splitAt >= name.length()) return name;
        String left = name.substring(0, splitAt);
        String right = name.substring(splitAt);
        if (right.charAt(0) == '/' && left.equals(from)) {
            return to + right;
        }
        return name;
    }

    public static boolean inClass(String name, String cls) {
        Validate.notNull(name, "name");
        Validate.notNull(cls, "cls");
        int splitAt = cls.length();
        if (splitAt >= name.length()) return false;
        String left = name.substring(0, splitAt);
        String right = name.substring(splitAt);
        return right.charAt(0) == '$' && right.indexOf('/') == -1 && left.equals(cls);
    }

    public static String renameClass(String name, String from, String to) {
        Validate.notNull(name, "name");
        Validate.notNull(from, "from");
        Validate.notNull(to, "to");
        if (from.equals(to)) return name;
        int splitAt = from.length();
        if (splitAt >= name.length()) {
            if (from.equals(name))
                return to;
            return name;
        }
        String left = name.substring(0, splitAt);
        String right = name.substring(splitAt);
        if (right.charAt(0) == '$' && right.indexOf('/') == -1 && left.equals(from)) {
            return to + right;
        }
        return name;
    }
}
