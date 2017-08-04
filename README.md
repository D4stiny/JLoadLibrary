# JLoadLibrary : LoadLibrary DLL Injector (x86) Proof-of-Concept in Java
## Description
Proof-of-Concept DLL injector that uses JNA to call WINAPI in order to execute "LoadLibraryA" on the target process with our dll. Seemed likke a cool project as no one had done a DLL injector (unmanaged DLLs) in Java before.
## Usage
```
JLoadLibrary <Process-ID> <DLL-Path> 
```