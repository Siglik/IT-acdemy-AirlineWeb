/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author qqq175
 *
 */
public class Logger implements Runnable {

	private static volatile Logger instance;

	private final File file;
	private Writer out;
	private volatile boolean started = false;
	private volatile boolean stopped = false;
	private final BlockingQueue<String> logQueue = new LinkedBlockingQueue<String>();

	private SimpleDateFormat logDateFormat;

	/**
	 * private constructor
	 */
	private Logger() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String logStartedDateString = dateFormat.format(new Date());

		file = new File(Settings.getInstance().getRealPath() + Settings.getInstance().getLogDir() +"log-" +  logStartedDateString + ".log");

		/*
		 * create files if not exist dir, resources/log/ is must be exist
		 */
		try {
			file.createNewFile();
			/* create log writers */
			try {
				out = new BufferedWriter(new FileWriter(file, true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* specify logging date-time format */
		logDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	}

	/**
	 * Create and returns logger instance
	 * 
	 * @return logger instance
	 */
	public static Logger getInstance() {
		Logger localInstance = instance;
		if (localInstance == null) {
			synchronized (Logger.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new Logger();
				}
			}
		}
		return localInstance;
	}

	/**
	 * write message to  log
	 * 
	 * @param message
	 */
	public void log(String message) {
		try {
			StringBuilder logMessage = new StringBuilder("[");
			logMessage.append(logDateFormat.format(new Date())).append("] - ").append(message).append("\n");
			logQueue.put(logMessage.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!started) {
			start();
		}
	}
	
	/**
	 * write stack trace to  log
	 * 
	 * @param message
	 */
	public void log(Throwable exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		log(sw.toString()); 
	}

	/**
	 * write stack trace and message to  log
	 * 
	 * @param message
	 */
	public void log(String message, Throwable exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		log(message + "\n" + sw.toString()); 
	}

	/**
	 * close current logger and log writers
	 */
	public void close() {
		if (started) {
			this.stopped = true;
			try {
				logQueue.put("");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void start() {
		this.started = true;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (!stopped || logQueue.isEmpty()) {
			try {
				String message = logQueue.take();
				if (message != null) {
					if (!message.equals("")) {
						try {
							out.append(message);
							out.flush();
						} catch (IOException logme) {
						}
					}
				}
			} catch (InterruptedException e) {
			}
		}
		try {
			out.close();
		} catch (IOException ignore) {
		}
	}
}
