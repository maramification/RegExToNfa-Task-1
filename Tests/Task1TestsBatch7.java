package csen1002.tests.task1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Timeout.ThreadMode;

import csen1002.main.task1.RegExToNfa;

@Timeout(value = 5, unit = TimeUnit.SECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
public class Task1TestsBatch7 {

	@Test
	public void testRegEx1() {
		RegExToNfa regExToNfa= new RegExToNfa("a;f;h;t#ah.f|t|");
		assertEquals("0;1;3;4;5;6;7;8;9;10;11#a;f;h;t#0,a,1;1,h,3;3,e,7;4,f,5;5,e,7;6,e,0;6,e,4;7,e,11;8,t,9;9,e,11;10,e,6;10,e,8#10#11", regExToNfa.toString());
	}

	@Test
	public void testRegEx2() {
		RegExToNfa regExToNfa= new RegExToNfa("i;j;n#in.ij|.");
		assertEquals("0;1;3;4;5;6;7;9#i;j;n#0,i,1;1,n,3;3,e,4;3,e,6;4,i,5;5,e,9;6,j,7;7,e,9#0#9", regExToNfa.toString());
	}

	@Test
	public void testRegEx3() {
		RegExToNfa regExToNfa= new RegExToNfa("d;f;i;w#ifdw.|*.");
		assertEquals("0;1;2;3;4;5;7;8;9;11#d;f;i;w#0,i,1;1,e,8;1,e,11;2,f,3;3,e,9;4,d,5;5,w,7;7,e,9;8,e,2;8,e,4;9,e,8;9,e,11#0#11", regExToNfa.toString());
	}

	@Test
	public void testRegEx4() {
		RegExToNfa regExToNfa= new RegExToNfa("b;f;k;m#bkfk|.m.|");
		assertEquals("0;1;2;3;4;5;6;7;9;11;12;13#b;f;k;m#0,b,1;1,e,13;2,k,3;3,e,4;3,e,6;4,f,5;5,e,9;6,k,7;7,e,9;9,m,11;11,e,13;12,e,0;12,e,2#12#13", regExToNfa.toString());
	}

	@Test
	public void testRegEx5() {
		RegExToNfa regExToNfa= new RegExToNfa("a;t;v#v*vt.e|.a.");
		assertEquals("0;1;2;3;4;5;7;8;9;11;13#a;t;v#0,v,1;1,e,0;1,e,3;2,e,0;2,e,3;3,e,4;3,e,8;4,v,5;5,t,7;7,e,11;8,e,9;9,e,11;11,a,13#2#13", regExToNfa.toString());
	}

	@Test
	public void testRegEx6() {
		RegExToNfa regExToNfa= new RegExToNfa("c;i;r;v;y#yc|ir..v.");
		assertEquals("0;1;2;3;4;5;7;9;11#c;i;r;v;y#0,y,1;1,e,5;2,c,3;3,e,5;4,e,0;4,e,2;5,i,7;7,r,9;9,v,11#4#11", regExToNfa.toString());
	}

	@Test
	public void testRegEx7() {
		RegExToNfa regExToNfa= new RegExToNfa("b;y#yby||e*.");
		assertEquals("0;1;2;3;4;5;6;7;8;9;10;11;13#b;y#0,y,1;1,e,9;2,b,3;3,e,7;4,y,5;5,e,7;6,e,2;6,e,4;7,e,9;8,e,0;8,e,6;9,e,10;9,e,13;10,e,11;11,e,10;11,e,13#8#13", regExToNfa.toString());
	}

	@Test
	public void testRegEx8() {
		RegExToNfa regExToNfa= new RegExToNfa("c;f;u;v;z#ufzv..*|c|");
		assertEquals("0;1;2;3;5;7;8;9;10;11;12;13;14;15#c;f;u;v;z#0,u,1;1,e,11;2,f,3;3,z,5;5,v,7;7,e,2;7,e,9;8,e,2;8,e,9;9,e,11;10,e,0;10,e,8;11,e,15;12,c,13;13,e,15;14,e,10;14,e,12#14#15", regExToNfa.toString());
	}

	@Test
	public void testRegEx9() {
		RegExToNfa regExToNfa= new RegExToNfa("m;p;s;u#ps.m.u|");
		assertEquals("0;1;3;5;6;7;8;9#m;p;s;u#0,p,1;1,s,3;3,m,5;5,e,9;6,u,7;7,e,9;8,e,0;8,e,6#8#9", regExToNfa.toString());
	}

	@Test
	public void testRegEx10() {
		RegExToNfa regExToNfa= new RegExToNfa("g;i;v;y#yi|ev*g..|");
		assertEquals("0;1;2;3;4;5;6;7;8;9;11;13;14;15#g;i;v;y#0,y,1;1,e,5;2,i,3;3,e,5;4,e,0;4,e,2;5,e,15;6,e,7;7,e,8;7,e,11;8,v,9;9,e,8;9,e,11;11,g,13;13,e,15;14,e,4;14,e,6#14#15", regExToNfa.toString());
	}

}