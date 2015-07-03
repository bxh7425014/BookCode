package com.guo.gongjiao;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class CharacterEnding {

	private static String encode = "UTF-8";

	public static String getFileEncode(InputStream in) {

		// Initalize the nsDetector() ;
		int lang = 3;
		nsDetector det = new nsDetector(lang);

		// Set an observer...
		// The Notify() will be called when a matching charset is found.

		det.Init(new nsICharsetDetectionObserver() {

			public void Notify(String charset) {
				HtmlCharsetDetector.found = true;
				encode = new String(charset);
				System.out.println("CHARSET = " + charset);
			}
		});

		BufferedInputStream imp = new BufferedInputStream(in);

		byte[] buf = new byte[1024];
		int len;
		boolean done = false;
		boolean isAscii = true;
		boolean found = false;
		try {
			while ((len = imp.read(buf, 0, buf.length)) != -1) {

				// Check if the stream is only ascii.
				if (isAscii)
					isAscii = det.isAscii(buf, len);

				// DoIt if non-ascii and not done yet.
				if (!isAscii && !done)
					done = det.DoIt(buf, len, false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		det.DataEnd();

		if (isAscii) {
			encode = "ASCII";
			System.out.println("CHARSET = ASCII");
			found = true;
		}

		if (!found) {
			String prob[] = det.getProbableCharsets();
			for (int i = 0; i < prob.length; i++) {
				System.out.println("Probable Charset = " + prob[i]);
			}
		}
		return encode;
	}
}