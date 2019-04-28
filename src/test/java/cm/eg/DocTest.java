package cm.eg;
import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * 处理文档测试。
 */
public class DocTest {

	private static String docDir = "doc/";

	@Test
	public void demo1() throws TikaException, IOException, SAXException {
		File file = new File(docDir + "Wu_info1.doc");
		System.out.println("length:" + file.length());

		Tika tika = new Tika();
		System.out.println("tika detect():" + tika.detect(file));
		String text = tika.parseToString(file);

		System.out.println("file string length:" + text.length());
	}
	
}
