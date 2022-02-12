package facebook4j.internal.org.json;

import java.io.Reader;

public class JSONTokenerData {
	public int character;
	public boolean eof;
	public int index;
	public int line;
	public char previous;
	public Reader reader;
	public boolean usePrevious;

	public JSONTokenerData() {
	}

	public boolean end(XMLTokener xmlTokener) {
		// TODO Auto-generated method stub
		return false;
	}
}