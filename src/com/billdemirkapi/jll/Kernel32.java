package com.billdemirkapi.jll;

import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.Pointer;

public interface Kernel32 extends StdCallLibrary {
	
	public HANDLE OpenProcess(DWORD dwDesiredAccess, BOOL bInheritHandle, DWORD dwProcessId);
    public LPVOID GetProcAddress(HANDLE hModule, String  lpProcName);
    public LPVOID VirtualAllocEx(HANDLE hProcess, LPVOID lpAddress, long dwSize, int flAllocationType, int flProtect);
    public BOOL WriteProcessMemory(HANDLE hProcess, LPVOID lpBaseAddress, Pointer lpBuffer, int nSize, Pointer lpNumberOfBytesWritten);
	public HANDLE CreateRemoteThread(HANDLE hProcess, int lpThreadAttributes, int dwStackSize, DWORD loadLibraryAddress, LPVOID lpParameter, int dwCreationFlags, int lpThreadId);
    public BOOL CloseHandle(HANDLE hObject);
	//public HANDLE GetModuleHandleW(WString lpModuleName);
    public int GetLastError();
	public HANDLE GetModuleHandle(String string);
	
}
