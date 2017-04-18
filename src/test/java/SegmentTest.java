package test.java;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.junit.Test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

public class SegmentTest {

	@Test
	/**
	 * 标准分词器。
	 * 推荐使用这种HanLP静态调用的方式。
	 */
	public void demo1() {
		List<Term> list = HanLP.segment("你好，欢迎使用HanLP汉语处理包！");
		for (Term term : list) {
			System.out.println(term.word + " " + term.nature);
		}
	}

	@Test
	/*
	 * 标准分词
	 */
	public void demo2() {
		List<Term> termList = StandardTokenizer.segment("商品和服务");
		System.out.println(termList);
	}


}
