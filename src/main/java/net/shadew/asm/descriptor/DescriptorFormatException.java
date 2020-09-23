package net.shadew.asm.descriptor;

public class DescriptorFormatException extends RuntimeException {
    public DescriptorFormatException() {
    }

    public DescriptorFormatException(String message, String input, int index) {
        super(message + " (input: '" + input + "' index: " + index + ")");
    }
}
