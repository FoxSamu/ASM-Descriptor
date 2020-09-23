# ASM-Descriptor
A basic Java library for parsing and generating binary Java descriptors.

## Installation
The artifact can be downloaded from my Maven repository. Make sure you also include the Maven Central Repository in your repositories because some transitive dependencies may not be on my Maven repository.
```groovy
repositories {
    mavenCentral()  // For transitive dependencies
    maven {
        url 'http://maven.shadew.net/'
    }
}

dependencies {
    compile 'net.shadew:descriptor:1.0'
}
```

## Descriptor syntax
To parse a descriptor, call:
```java
Descriptor.parse("(Lyour/descriptor/goes/here;)V");
```
This returns a new `Descriptor` instance. When parsing fails, a `DescriptorFormatException` is thrown.

The accepted syntax is (according to the [Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2)):
```groovy
Descriptor
    MethodDescriptor
    TypeDescriptor

MethodDescriptor
    '(' TypeDescriptor* ')' ReturnType
    
ReturnType
    TypeDescriptor
    'V'

TypeDescriptor
    PrimitiveDescriptor
    ReferenceDescriptor
    ArrayDescriptor

PrimitiveDescriptor
    'B'
    'S'
    'I'
    'J'
    'F'
    'D'
    'Z'
    'C'

ReferenceDescriptor
    'L' InternalName ';'

ArrayDescriptor
    '[' TypeDescriptor

InternalName
    /[a-zA-Z0-9$_\/]+/
```

`Descriptor` has several subclasses which resemble several kinds of descriptors. These classes cover the their respective syntax in the syntax definition above, and provide a parse function just like `Descriptor.parse` to parse a certain kind of descriptor syntax (e.g. `MethodDescriptor.parse` only allows method descriptors).
