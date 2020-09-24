package net.shadew.asm.descriptor;

@FunctionalInterface
public interface Mapper {
    String remap(String internalName);
}
