package com.qa.ExcelTester;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class ParamTest {
	
	@Parameters
	public static Collection <Object[]> data() {
		return Arrays.asList(new Object[][] {{"foo","bar",1, true},{"fizz","buzz", 0, false}});
	}
	
	private String a;
	private String b;
	private int c;
	private Boolean d;

	public ParamTest(String a, String b, int c, Boolean d) {
		setA(a);
		setB(b);
		setC(c);
		setD(d);
	}

	public void setA(String a) {
		this.a = a;
	}

	public void setB(String b) {
		this.b = b;
	}

	public void setC(int c) {
		this.c = c;
	}

	public void setD(Boolean d) {
		this.d = d;
	}
	
	@Test
	public void test1() {
		System.out.println(a+ " " + b + " ");
	}
	
	@Test
	public void test2() {
		System.out.println(c + " ");
	}
	@Test
	public void test3() {
		System.out.println(d+ " ");
	}
	
}
