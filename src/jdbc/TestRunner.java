package jdbc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import jdbc.drivers.DBDriver;

public class TestRunner {

	private static Class<?> implementation;
	private static String resourceDir;

	private static String showError(final Throwable e)
			throws IOException {
		e.printStackTrace();
		final StringBuffer buffer = new StringBuffer();
		buffer.append("\t\t\tError: " + e + " " + e.getMessage()).append("\n");
		for (final StackTraceElement trace : e.getStackTrace()) {
			buffer.append("\n" + trace.getClassName() + "."
					+ trace.getMethodName() + "(): Line "
					+ trace.getLineNumber());
		}
		return buffer.toString().replaceAll("\\n", "\n\t\t\t\t");
	}

	public static Object getImplementationInstance(){
		//		try {
		//			for(final Constructor<?> constructor : implementation.getDeclaredConstructors()){
		//				if(constructor.getParameterTypes().length == 0){
		//					constructor.setAccessible(true);
		//					return constructor.newInstance((java.lang.Object[])null);
		//				}
		//			}
		//		} catch (final Throwable e) {
		//		}
		return new DBDriver();
	}

	public static Object getImplementationInstance(final Object arg){
		try {
			for(final Constructor<?> constructor : implementation.getDeclaredConstructors()){
				if(constructor.getParameterTypes().length == 1){
					constructor.setAccessible(true);
					return constructor.newInstance(arg);
				}
			}
		} catch (final Throwable e) {
		}
		return null;
	}

	public static String getResourceDir(){
		return resourceDir;
	}

	public static String[] run(final String resourceDir, final String testPackage, final List<String> classes) throws Throwable{
		TestRunner.resourceDir = resourceDir;
		final StringBuffer log = new StringBuffer();
		final List<Class<?>> tests = new LinkedList<>();
		try {
			for(final String clazz : classes){
				final Class<?> impl = Class.forName(clazz);
				if(impl.getName().startsWith(testPackage))
					tests.add(impl);
			}
		} catch (final Throwable e) {
			log.append("{{{Failed to create the test classes! Check if we compile the correct project.").append("\n");
			log.append("Please review the 'Sources' list above for the detected source-code").append("\n");
			log.append("\t").append(showError(e)).append("\n}}}");
			return new String[]{ "Compile", log.toString(),  "" };
		}
		log.append("UnitTests: " + (tests.isEmpty() ? "none!" : Arrays.toString(tests.toArray()))).append("\n");

		log.append("Classes: " + (classes.isEmpty() ? "none!" : Arrays.toString(classes.toArray()))).append("\n");
		int failed = 0;
		int total = 0;
		final Set<String> implementations = new HashSet<>();
		for(final Class<?> testClass : tests){
			Class<?> specs = null;
			try {
				Method method = null;
				try{
					method = testClass.getMethod("getSpecifications");
				}catch(final Throwable e){
					continue;
				}
				log.append("\n\nRunning UnitTest: <<<" + testClass.getName()).append(">>>\n");
				specs = (Class<?>)method.invoke(null);
				log.append("\tInterface: " + specs.getName()).append("\n");
			} catch (final Throwable e) {
				log.append("\tFailed to find the specification interface!").append("\n");
				log.append("\t\t").append(showError(e)).append("\n");
				return new String[] { "NoSpec", log.toString(),  "" };
			}
			final List<Class<?>> impls = new LinkedList<>();
			try {
				for(final String clazz : classes){
					final Class<?> impl = Class.forName(clazz);
					for(final Class<?> i : impl.getInterfaces()){
						if(i.equals(specs)){
							impls.add(impl);
							break;
						}
					}
				}
				log.append("\tImplementations: " + (impls.isEmpty() ? "{{{none!}}}" : Arrays.toString(impls.toArray()))).append("\n");
			} catch (final Throwable e) {
				log.append("\t{{{Failed to create the implementation class!").append("\n");
				log.append("\t\t").append(showError(e)).append("}}}\n");
				return new String[] { "NoImpl", log.toString(),  "" };
			}

			for(final Class<?> impl : impls){
				log.append("\tTesting Implementation: " + impl.getName()).append("\n");
				implementations.add(impl.getName());
				implementation = impl;
				final JUnitCore junit = new JUnitCore();
				final Result result = junit.run(testClass);
				log.append("\t\t{{{Failed: " + result.getFailureCount()).append("\n");
				int i=0;
				for(final Failure f : result.getFailures()){
					log.append("\t\t\tFailure " + ++i + ":\n");
					log.append("\t\t\t" + f.getTestHeader() + "[" + f.getMessage() + "]").append("\n");
					log.append("\t\t\t" + f.getTrace().replaceAll("\\r\n|\\n\\r|\\r|\\n", "\n\t\t\t"));
					log.append("\n");
				}
				log.append("}}}");
				failed += result.getFailureCount();
				total += result.getRunCount();
				//				log.append("\t\tIgnore: " + result.getIgnoreCount()).append("\n");
				log.append("\t\t[[[Total : " + result.getRunCount()).append("]]]\n");
			}
		}
		String impls = "";
		for(final String impl : implementations)
			impls += impl + ";";
		return new String[] { (total-failed) + "/" + total, log.toString(),  impls};
	}

	public static void fail(final String message, final Throwable throwable) {
		try {
			final StringBuffer log = new StringBuffer();
			if(message!=null)
				log.append(message).append("\n");
			if(throwable!=null){
				final Throwable cause = throwable.getCause();
				if(cause!=null)
					log.append(showError(cause));
				log.append(showError(throwable));
			}
			Assert.fail(log.toString());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}