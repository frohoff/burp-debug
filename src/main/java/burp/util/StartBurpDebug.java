package burp.util;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Collections;

import privilegedaccessor.StrictPA;


public class StartBurpDebug {
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {				
		try {
			System.out.println("disable debug check");
			clearRuntimeInputArgs();
			System.out.println("disabled debug check");
		} catch (Exception e) {
			System.err.println("failed to disable burp anti-debug protection");
			e.printStackTrace();
		}		
		
		System.out.println("starting burp");
		try {
			burp.StartBurp.main(args);		
			System.out.println("started burp");			
		} catch (Throwable t) {
			throw new RuntimeException("failed to start burp", t);			
		}
	}

	protected static void clearRuntimeInputArgs() throws NoSuchFieldException, IllegalAccessException {
		RuntimeMXBean mxb = ManagementFactory.getRuntimeMXBean();
		mxb.getInputArguments(); // init args field
		StrictPA.setValue(StrictPA.getValue(mxb, "jvm"), "vmArgs", Collections.unmodifiableList(new ArrayList<Object>()));
	}

}
