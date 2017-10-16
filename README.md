# JLoadLibrary : LoadLibrary DLL Injector (x86/x64) Proof-of-Concept in Java
## Description
Proof-of-Concept DLL injector that uses JNA to call WINAPI in order to execute "LoadLibraryA" on the target process with our dll. Seemed like a cool project as no one had done a DLL injector (unmanaged DLLs) in Java before. Works for both x86 and x64 executables.
## Usage
Few important things:
1. Make sure you're running the java binary that has the same process architecture (x86/x64) of the target process.
2. Make sure the DLL you specify is of the same process architecture (x86/x64) of the target process.
```
java -jar JLoadLibrary.jar <Process-ID> <DLL-Path> 
```