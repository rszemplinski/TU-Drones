package services.java.utils.file.IO;

import android.content.Context;
import org.droidplanner.services.android.utils.file.FileUtils;

import java.io.PrintStream;

public class ExceptionWriter {
	private final Context context;

	public ExceptionWriter(Context context) {
		this.context = context;
	}

	public void saveStackTraceToSD(Throwable exception) {
        if(exception == null)
            return;

		try {
			PrintStream out = new PrintStream(FileUtils.getExceptionFileStream(context));
			exception.printStackTrace(out);
			out.close();
		} catch (Exception excep) {
			excep.printStackTrace();
		}
	}
}
