package com.billdemirkapi.jll;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD.DWORD_PTR;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.Pointer;

public class Controller {
	
    static Kernel32 kernel32 = (Kernel32) Native.loadLibrary("kernel32.dll", Kernel32.class, W32APIOptions.ASCII_OPTIONS);
    static PsapiExt psapi = (PsapiExt) Native.loadLibrary("psapi", PsapiExt.class, W32APIOptions.UNICODE_OPTIONS);
    
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("JLoadLibrary <Process-ID> <DLL-Path>");
			System.exit(0);
		}
		
		try {
           	boolean injectResult = inject(Integer.valueOf(args[0]), args[1]);

           	if(injectResult) 
           		System.out.println("Injection successful!");
           	else
           		System.out.println("Injection failed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static boolean inject(int processID, String dllName) {
		DWORD_PTR processAccess = new DWORD_PTR(0x43A);
		
		HANDLE hProcess = kernel32.OpenProcess(processAccess, new BOOL(false), new DWORD_PTR(processID));
		if(hProcess == null) {
			System.out.println("Handle was NULL! Error: " + kernel32.GetLastError());
			return false;
		}
		
		DWORD_PTR loadLibraryAddress = kernel32.GetProcAddress(kernel32.GetModuleHandle("KERNEL32"), "LoadLibraryA");
		if(loadLibraryAddress.intValue() == 0) {
			System.out.println("Could not find LoadLibrary! Error: " + kernel32.GetLastError());
			return false;
		}
		
		LPVOID dllNameAddress = kernel32.VirtualAllocEx(hProcess, null, (dllName.length() + 1), new DWORD_PTR(0x3000), new DWORD_PTR(0x4));
		if(dllNameAddress == null) {
			System.out.println("dllNameAddress was NULL! Error: " + kernel32.GetLastError());
			return false;
		}

		Pointer m = new Memory(dllName.length() + 1);
		m.setString(0, dllName); 

		boolean wpmSuccess = kernel32.WriteProcessMemory(hProcess, dllNameAddress, m, dllName.length(), null).booleanValue();
		if(!wpmSuccess) {
			System.out.println("WriteProcessMemory failed! Error: " + kernel32.GetLastError());
			return false;
		}
		
		DWORD_PTR threadHandle = kernel32.CreateRemoteThread(hProcess, 0, 0, loadLibraryAddress, dllNameAddress, 0, 0);			
		if(threadHandle.intValue() == 0) {
			System.out.println("threadHandle was invalid! Error: " + kernel32.GetLastError());
			return false;
		}
		
		kernel32.CloseHandle(hProcess);
		
		return true;
	}

}
