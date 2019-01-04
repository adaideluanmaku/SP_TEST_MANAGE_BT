package com.ch.dll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class Dll_Run_test {
	public static void main(String[] args) throws JsonProcessingException {
		// 调用dll
		int runback = PASS4Invoke.instanceDll.MDC_Init("zy","1609yanshi","");
		System.out.println("调用dll-init back->[{" + runback + "}]");

//		System.out.println(PASS4Invoke.instanceDll.MDC_GetLastError());
	}
}
