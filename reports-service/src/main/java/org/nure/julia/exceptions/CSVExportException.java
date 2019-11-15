package org.nure.julia.exceptions;

public class CSVExportException extends RuntimeException {

    public CSVExportException() {}

    public CSVExportException(String msg) {
        super(msg);
    }

    public CSVExportException(String msg, Throwable t) {
        super(msg, t);
    }
}
