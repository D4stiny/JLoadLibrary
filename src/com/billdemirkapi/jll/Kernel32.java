package com.billdemirkapi.jll;

import com.sun.jna.platform.win32.BaseTSD.DWORD_PTR;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.Pointer;

public interface Kernel32 extends StdCallLibrary {
	
	public HANDLE OpenProcess(DWORD_PTR dwDesiredAccess, BOOL bInheritHandle, DWORD_PTR dwProcessId);
    public DWORD_PTR GetProcAddress(HANDLE hModule, String  lpProcName);
    public LPVOID VirtualAllocEx(HANDLE hProcess, LPVOID lpAddress, int dwSize, DWORD_PTR flAllocationType, DWORD_PTR flProtect);
    public BOOL WriteProcessMemory(HANDLE hProcess, LPVOID lpBaseAddress, Pointer lpBuffer, int nSize, Pointer lpNumberOfBytesWritten);
	public DWORD_PTR CreateRemoteThread(HANDLE hProcess, int lpThreadAttributes, int dwStackSize, DWORD_PTR loadLibraryAddress, LPVOID lpParameter, int dwCreationFlags, int lpThreadId);
    public BOOL CloseHandle(HANDLE hObject);
	//public HANDLE GetModuleHandleW(WString lpModuleName);
    public int GetLastError();
	public HANDLE GetModuleHandle(String string);
	
}
