package com.billdemirkapi.jll;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.DWORD;
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
           	boolean injectResult = inject(Integer.valueOf(args[1]), args[2]);

           	if(injectResult) 
           		System.out.println("Injection successful!");
           	else
           		System.out.println("Injection failed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static boolean inject(int processID, String dllName) {
		DWORD processAccess = new DWORD(0x43A);
		
		HANDLE hProcess = kernel32.OpenProcess(processAccess, new BOOL(false), new DWORD(processID));
		if(hProcess == null) {
			System.out.println("Handle was NULL! Error: " + kernel32.GetLastError());
			return false;
		}
		
		int loadLibraryDifference = kernel32.GetProcAddress(kernel32.GetModuleHandle("KERNEL32"), "LoadLibraryA").hashCode() - kernel32.GetModuleHandle("KERNEL32").hashCode();
		if(loadLibraryDifference == 0x0) {
			System.out.println("loadLibraryDifference was NULL! Error: " + kernel32.GetLastError());
			return false;
		}
		
		DWORD[] hMods = new DWORD[1024];
		Pointer cbNeeded = new Memory(4);
		DWORD loadLibraryAddress = new DWORD(0x0);
		
		BOOL EnumProcMods = psapi.EnumProcessModulesEx(hProcess, hMods, 1024, cbNeeded, new DWORD(0x1));
		
		if(EnumProcMods == null) {
			System.out.println("EnumProcMods was NULL! Error: " + kernel32.GetLastError());
			return false;
		}
		
		for(int i = 0; i < (cbNeeded.getInt(0) - 0x4); i++) {
			char[] szModName = new char[260];
			
			DWORD getName = psapi.GetModuleFileNameEx(hProcess, hMods[i], szModName, new DWORD(0x104));
			if(getName.intValue() != 0x0) {
				String sName = String.valueOf(szModName);
				if(sName.indexOf("kernel32") != -1) {
					loadLibraryAddress = new DWORD((hMods[i].intValue() + loadLibraryDifference));
				}				
			} else {
				 
			}
		}

		LPVOID dllNameAddress = kernel32.VirtualAllocEx(hProcess, null, (dllName.length() + 1), 0x3000, 0x4);
		if(dllNameAddress == null) {
			System.out.println("dllNameAddress was NULL! Error: " + kernel32.GetLastError());
			return false;
		}

		Pointer m = new Memory(dllName.length() + 1);
		m.setString(0, dllName); 

		kernel32.WriteProcessMemory(hProcess, dllNameAddress, m, dllName.length(), null);
		kernel32.CreateRemoteThread(hProcess, 0, 0, loadLibraryAddress, dllNameAddress, 0, 0);			
		
		kernel32.CloseHandle(hProcess);
		
		return true;
	}

}
