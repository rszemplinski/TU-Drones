package org.taylor.services.java.utils;

import org.taylor.services.java.core.model.Logger;

/**
 * Created by rszem_rltuyx6 on 3/29/2016.
 */
public class AppLogger implements Logger {

    private static Logger sLogger = new AppLogger();

    public static Logger getLogger() {
        return sLogger;
    }

    // Only one instance is allowed.
    private AppLogger() {
    }

    @Override
    public void logVerbose(String logTag, String verbose) {
        if (verbose != null) {
            System.out.println(logTag + " " + verbose);
        }
    }

    @Override
    public void logDebug(String logTag, String debug) {
        if (debug != null) {
            System.out.println(logTag + " " + debug);
        }
    }

    @Override
    public void logInfo(String logTag, String info) {
        if (info != null) {
            System.out.println(logTag + " " + info);
        }
    }

    @Override
    public void logWarning(String logTag, String warning) {
        if (warning != null) {
            System.err.println(logTag + " " + warning);
        }
    }

    @Override
    public void logWarning(String logTag, Exception exception) {
        if (exception != null) {
            System.err.println(logTag + " " + exception.getMessage());
        }
    }

    @Override
    public void logWarning(String logTag, String warning, Exception exception) {
        if (warning != null && exception != null) {
            System.err.println(logTag + " " + warning + " " + exception.getMessage());
        }
    }

    @Override
    public void logErr(String logTag, String err) {
        if (err != null) {
            System.err.println(logTag + " " + err);
        }
    }

    @Override
    public void logErr(String logTag, Exception exception) {
        if (exception != null) {
            System.out.println(logTag + " " + exception.getMessage());
        }
    }

    @Override
    public void logErr(String logTag, String err, Exception exception) {
        if (err != null && exception != null) {
            System.err.println(logTag + " " + err + " " + exception);
        }
    }

}
