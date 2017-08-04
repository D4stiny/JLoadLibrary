package com.billdemirkapi.jll;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.BOOL;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.win32.StdCallLibrary;

public interface PsapiExt extends StdCallLibrary
{
  
	public BOOL EnumProcessModulesEx(HANDLE hProcess, DWORD[] hMods, int i, Pointer lpcbNeeded, DWORD dwFilterFlag);
	public DWORD GetModuleFileNameEx(HANDLE hProcess, DWORD hModule, char[] szModName, DWORD nSize);


}