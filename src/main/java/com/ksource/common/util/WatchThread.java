package com.ksource.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 描述：加了process.waitFor(); 
即时你的子进程结束了，程序也不执行下去？？？<br>
这个问题前不久我刚遇到过，死锁了，看下帮助文档就知道了。 <br>
javadoc :<br>
The methods that create processes may not work well for special processes on certain native platforms, such as native windowing processes, daemon processes, Win16/DOS processes on Microsoft Windows, or shell scripts. The created subprocess does not have its own terminal or console. All its standard io (i.e. stdin, stdout, stderr) operations will be redirected to the parent process through three streams (getOutputStream(), getInputStream(), getErrorStream()). The parent process uses these streams to feed input to and get output from the subprocess. Because some native platforms only provide limited buffer size for standard input and output streams, failure to promptly write the input stream or read the output stream of the subprocess may cause the subprocess to block, and even deadlock.<br> 
最后一段话，死锁的原因是子线程的输出流或输入流缓存太小，所以必须自己手动清空缓存。 <br>
在process.waitFor();之前加上一下代码 <br>
BufferedReader br = new BufferedReader(process.getInputStream()); <br>
while(br.readLine()!=null); <br>

通常这样可以解决，但是线程执行是由cpu控制的，如果process还没被执行，那么while(br.readLine()!=null);就会结束，此时如果process刚好被执行了，但由于while(br.readLine()!=null);已经结束了，process的输入流输出流还是没有被清空，到process.waitFor();时还是会造成堵塞的。 <br>

所以，一般我的做法是把上面的代码写到一个监视线程中，比如 <br>
class WatchThread extends Thread { <br>
...}<br>
然后在process.waitFor()之前，添加 <br>
WatchThread wt = new WatchThread(process); <br>
wt.start(); <br>
然后在process.waitFor()之后，添加 <br>
wt.setOver(true);
 * 
 * @author gengzi
 * @data 2012-7-18
 */
public class WatchThread extends Thread{
	Process p;
	boolean over;

	public WatchThread(Process p) {
		this.p = p;
		over = false;
	}

	public void run() {
		try {
			if (p == null)
				return;
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			while (true) {
				if (p == null || over) {
					break;
				}
				while (br.readLine() != null)
					;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOver(boolean over) {
		this.over = over;
	}
}
