package net.shadew.asm.descriptor;

import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.shadew.util.contract.Validate;

public final class MethodDescriptor extends Descriptor {
    private final TypeDescriptor[] parameters;
    private final TypeDescriptor returnType;

    MethodDescriptor(TypeDescriptor returnType, TypeDescriptor... parameters) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public TypeDescriptor parameter(int index) {
        return parameters[index];
    }

    public int parameterCount() {
        return parameters.length;
    }

    public TypeDescriptor[] parameterArray() {
        int len = parameters.length;
        TypeDescriptor[] out = new TypeDescriptor[len];
        System.arraycopy(parameters, 0, out, 0, len);
        return out;
    }

    public Stream<TypeDescriptor> parameters() {
        return Stream.of(parameters);
    }

    public TypeDescriptor returnType() {
        return returnType;
    }

    public int parametersSize() {
        int parsSize = 0;
        for (TypeDescriptor desc : parameters) {
            parsSize += desc.size();
        }
        return parsSize;
    }

    public int returnSize() {
        return returnType.size();
    }

    public int totalSize() {
        return parametersSize() + returnSize();
    }

    @Override
    public void accept(DescriptorVisitor visitor) {
        visitor.visitMethod(this);
    }

    @Override
    public MethodDescriptor remap(Mapper mapper) {
        int i = 0;
        TypeDescriptor[] outpars = new TypeDescriptor[parameters.length];
        for (TypeDescriptor td : parameters) {
            outpars[i] = td.remap(mapper);
            i++;
        }
        return new MethodDescriptor(returnType.remap(mapper), outpars);
    }

    @Override
    public String toString() {
        return String.format(
            "(%s)%s",
            parameters().map(Descriptor::toString).collect(Collectors.joining()),
            returnType.toString()
        );
    }

    @Override
    public Type toAsm() {
        int len = parameters.length;
        Type[] args = new Type[len];
        for (int i = 0; i < len; i++) {
            args[i] = parameters[i].toAsm();
        }
        return Type.getMethodType(returnType.toAsm(), args);
    }

    @Override
    public String toCode() {
        return String.format(
            "%s (%s)",
            returnType.toCode(),
            parameters().map(Descriptor::toCode).collect(Collectors.joining(", "))
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodDescriptor that = (MethodDescriptor) o;
        return Arrays.equals(parameters, that.parameters) && returnType.equals(that.returnType);
    }

    @Override
    public int hashCode() {
        int result = returnType.hashCode();
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    public static MethodDescriptor parse(String desc) {
        Validate.notNull(desc, "desc");
        return DescriptorParser.method(desc);
    }

    public static MethodDescriptor of(TypeDescriptor returnType, TypeDescriptor... parameters) {
        Validate.notNull(returnType, "returnType");
        Validate.notNull(parameters, "parameters");
        Validate.notNullElements(parameters, "parameters");
        return new MethodDescriptor(returnType, parameters);
    }

    public static MethodDescriptor reflect(Method method) {
        Validate.notNull(method, "method");
        Class<?>[] types = method.getParameterTypes();
        TypeDescriptor[] pars = new TypeDescriptor[types.length];
        for (int i = 0, l = types.length; i < l; i++) {
            pars[i] = TypeDescriptor.reflect(types[i]);
        }
        TypeDescriptor ret = TypeDescriptor.reflect(method.getReturnType());
        return new MethodDescriptor(ret, pars);
    }
}
