package io.github.anchoretics;

public class test {
	public static void main(String[] args){
		new SocketIoThread(null,"http://127.0.0.1:3000/").start();
	}
}
