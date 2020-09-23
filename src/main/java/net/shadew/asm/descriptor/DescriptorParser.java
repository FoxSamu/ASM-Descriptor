package net.shadew.asm.descriptor;

import java.util.ArrayList;

final class DescriptorParser {
    private DescriptorParser() {
    }

    private static PrimitiveDescriptor determinePrimitive(char c, String input, int[] index) {
        index[0]++;
        switch (c) {
            case 'B': return PrimitiveDescriptor.BYTE;
            case 'S': return PrimitiveDescriptor.SHORT;
            case 'I': return PrimitiveDescriptor.INT;
            case 'J': return PrimitiveDescriptor.LONG;
            case 'F': return PrimitiveDescriptor.FLOAT;
            case 'D': return PrimitiveDescriptor.DOUBLE;
            case 'Z': return PrimitiveDescriptor.BOOLEAN;
            case 'C': return PrimitiveDescriptor.CHAR;
        }
        index[0]--;
        throw new DescriptorFormatException("No such descriptor for prefix '" + c + "'", input, index[0]);
    }

    private static PrimitiveDescriptor primitive(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Expected primitive descriptor", input, index.length);

        char c = input.charAt(index[0]);
        return determinePrimitive(c, input, index);
    }

    private static ReferenceDescriptor reference(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Expected reference descriptor", input, index.length);

        char c = input.charAt(index[0]);
        if (c == 'L') {
            index[0]++;
            return readReference(input, index);
        }
        throw new DescriptorFormatException("Expected 'L' to start reference", input, index[0]);
    }

    private static ArrayDescriptor array(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Expected array descriptor", input, index.length);

        char c = input.charAt(index[0]);
        if (c == '[') {
            index[0]++;
            return new ArrayDescriptor(type(input, index));
        }
        throw new DescriptorFormatException("Expected '[' to start reference", input, index[0]);
    }

    private static ReferenceDescriptor readReference(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Unfinished reference descriptor", input, index.length);

        int start = index[0];
        int end = start;
        int max = input.length();
        char c;
        while ((c = input.charAt(end)) != ';') {
            end++;
            if (end >= max) {
                throw new DescriptorFormatException("Unfinished reference descriptor", input, index[0]);
            }
            if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '$' || c == '_' || c == '/')) {
                throw new DescriptorFormatException("Illegal character in internal name: '" + c + "'", input, index[0]);
            }
        }
        String name = input.substring(start, end);
        index[0] = end + 1;
        return new ReferenceDescriptor(name);
    }

    private static TypeDescriptor type(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Expected type descriptor", input, index.length);

        char c = input.charAt(index[0]);
        if (c == 'L') {
            index[0]++;
            return readReference(input, index);
        }
        if (c == '[') {
            index[0]++;
            return new ArrayDescriptor(type(input, index));
        }
        return determinePrimitive(c, input, index);
    }

    private static TypeDescriptor returnType(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Expected return type descriptor", input, index.length);

        char c = input.charAt(index[0]);
        if (c == 'V') {
            index[0]++;
            return PrimitiveDescriptor.VOID;
        }
        return type(input, index);
    }

    private static TypeDescriptor[] parameters(String input, int[] index) {
        ArrayList<TypeDescriptor> list = new ArrayList<>();
        int len = input.length();
        while (index[0] < len && input.charAt(index[0]) != ')') {
            list.add(type(input, index));
        }
        if (index[0] >= len) {
            throw new DescriptorFormatException("Unfinished list of parameter types", input, index.length);
        }
        return list.toArray(new TypeDescriptor[0]);
    }

    private static MethodDescriptor method(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Expected '(' in method descriptor", input, index.length);

        char c = input.charAt(index[0]);
        if (c != '(') throw new DescriptorFormatException("Expected '(' in method descriptor", input, index[0]);
        index[0]++;

        TypeDescriptor[] params = parameters(input, index);

        c = input.charAt(index[0]);
        if (c != ')') throw new DescriptorFormatException("Expected ')' in method descriptor", input, index[0]);
        index[0]++;

        TypeDescriptor ret = returnType(input, index);
        return new MethodDescriptor(ret, params);
    }

    private static Descriptor descriptor(String input, int[] index) {
        if (index[0] >= input.length())
            throw new DescriptorFormatException("Expected descriptor", input, index.length);

        char c = input.charAt(index[0]);
        if (c == '(') {
            return method(input, index);
        }
        return type(input, index);
    }

    private static void check(String input, int[] index) {
        if (index[0] < input.length()) {
            throw new DescriptorFormatException("Extra input", input, index[0]);
        }
    }

    static PrimitiveDescriptor primitive(String input) {
        int[] index = {0};
        PrimitiveDescriptor out = primitive(input, index);
        check(input, index);
        return out;
    }

    static ArrayDescriptor array(String input) {
        int[] index = {0};
        ArrayDescriptor out = array(input, index);
        check(input, index);
        return out;
    }

    static ReferenceDescriptor reference(String input) {
        int[] index = {0};
        ReferenceDescriptor out = reference(input, index);
        check(input, index);
        return out;
    }

    static MethodDescriptor method(String input) {
        int[] index = {0};
        MethodDescriptor out = method(input, index);
        check(input, index);
        return out;
    }

    static TypeDescriptor type(String input) {
        int[] index = {0};
        TypeDescriptor out = type(input, index);
        check(input, index);
        return out;
    }

    static Descriptor descriptor(String input) {
        int[] index = {0};
        Descriptor out = descriptor(input, index);
        check(input, index);
        return out;
    }
}
