package cm.eg;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.junit.Test;

import java.util.List;

/**
 * 分词测试。
 */
public class SegmentTest {

	/**
	 * 标准分词器。
	 * 推荐使用这种HanLP静态调用的方式。
	 */
	@Test
	public void demo1() {
		List<Term> list = HanLP.segment("你好，欢迎使用HanLP汉语处理包！");
		for (Term term : list) {
			System.out.println(term.word + " " + term.nature);
		}
	}

	/**
	 * 标准分词
	 */
	@Test
	public void demo2() {
		List<Term> termList = StandardTokenizer.segment("商品和服务");
		System.out.println(termList);
	}


}
