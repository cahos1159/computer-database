package com.excilys.cdb.userinterface;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.excilys.cdb.controller.CdbController;

public class CdbUi {
	private InputStream in;
	private PrintStream out;
	private PrintStream err;
	private Scanner scanner;
	private String cmd;

	final CdbController cController;
	
	public CdbUi (InputStream inStream, PrintStream outStream,CdbController cController) {
		this(inStream,outStream,outStream, cController);
	}
	
	public CdbUi(InputStream inStream, PrintStream outStream, PrintStream errStream,CdbController cController) {
		this.in = inStream;
		this.out = outStream;
		this.err = errStream;
		this.scanner = new Scanner(this.in);
		this.cmd = "";
		this.cController = cController;
	}
	
	public void run() {
		this.println(this.out,"Welcome\n(Try: help / quit)");
		
		while (true) {
			this.cmd = scanner.nextLine();
			switch (this.cmd) {
				case "stop":
				case "exit":
					this.println(this.out,"Bye !");
					return;
				default:
					try {
						this.println(this.out,cController.treatMessage(cmd));
					} catch (Exception e) {
						this.println(this.err,e.getMessage());
					}
			}
			this.out.println();
		}
	}
	
	private void println(PrintStream stream, String msg) {
		stream.println(msg);
	}
}
