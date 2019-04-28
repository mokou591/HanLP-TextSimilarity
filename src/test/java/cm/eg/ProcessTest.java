package cm.eg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cm.eg.model.Article;
import cm.eg.model.CompareReport;
import cm.eg.model.CompareTask;
import cm.eg.model.WordFreq;
import cm.eg.util.TextUtil;

import org.apache.tika.exception.TikaException;
import org.junit.Test;

import com.hankcs.hanlp.seg.common.Term;

/**
 * 文本相似度处理测试。
 */
public class ProcessTest {

	private static String docDir = "doc/";

	private static String txtDir = "txt/";

	/**
	 * 显示词频最高的若干项
	 */
	@Test
	public void demo1() throws IOException, TikaException {
		Article article = TextUtil.getArticle(new File(txtDir + "小王子.txt"));
		List<WordFreq> wfList = article.getWordFreqList();

		System.out.println("高频词排名:");
		for (int i = 0; i < 50; ++i) {
			System.out.println(wfList.get(i));
		}
	}


	/**
	 * 向量相似度算法
	 */
	@Test
	public void demo2() throws IOException, TikaException {
		// TODO 涉及读写可用多线程FutureTask优化。
		Article a1 = TextUtil.getArticle(new File(docDir + "Li_info2.doc"));
		Article a2 = TextUtil.getArticle(new File(docDir + "Wu_info2.doc"));
		CompareTask task = new CompareTask(a1, a2);
		task.execute();
		CompareReport report = task.getCompareReport();
		System.out.println(report);
	}


	/**
	 * 报告多个测试
	 */
	@Test
	public void demo3() throws IOException, TikaException {
		List<CompareTask> tasks = new ArrayList<CompareTask>();

		Article a1 = TextUtil.getArticle(new File(docDir + "Wu_info1.doc"));
		Article b1 = TextUtil.getArticle(new File(docDir + "Li_info1.doc"));
		tasks.add(new CompareTask(a1, b1));
		System.out.println("load 1");

		Article a2 = TextUtil.getArticle(new File(docDir + "Wu_info2.doc"));
		Article b2 = TextUtil.getArticle(new File(docDir + "Li_info2.doc"));
		tasks.add(new CompareTask(a2, b2));
		System.out.println("load 2");

		Article a3 = TextUtil.getArticle(new File(docDir + "Wu_info3.doc"));
		Article b3 = TextUtil.getArticle(new File(docDir + "Li_info3.doc"));
		tasks.add(new CompareTask(a3, b3));
		System.out.println("load 3");

		Article a4 = TextUtil.getArticle(new File(docDir + "Wu_info4.doc"));
		Article b4 = TextUtil.getArticle(new File(docDir + "Li_info4.doc"));
		tasks.add(new CompareTask(a4, b4));
		System.out.println("load 4");

		for (CompareTask task : tasks) {
			task.execute();
			System.out.println(task.getCompareReport().getConsoleDetail());
			System.out.println();
		}
	}


	/**
	 * 公共子序列
	 */
	@Test
	public void demo4() throws IOException, TikaException {
		// 获得分词后的一整串字符
		Article a1 = TextUtil.getArticle(new File(docDir + "Wu_info3.doc"));
		Article a2 = TextUtil.getArticle(new File(docDir + "Li_info1.doc"));
		List<Term> termList1 = a1.getSegmentList();
		List<Term> termList2 = a2.getSegmentList();
		StringBuilder segStr1 = new StringBuilder();
		StringBuilder segStr2 = new StringBuilder();

		for (Term term : termList1) {
			segStr1.append(term.word);
		}
		for (Term term : termList2) {
			segStr2.append(term.word);
		}
		// 最长公共子序列
		String lcs = "";
		String str1 = segStr1.toString();
		String str2 = segStr2.toString();

		for (int i = 1; i <= 5; ++i) {
			if (i != 1) {
				str1 = str1.replaceAll(lcs, "");
				str2 = str2.replaceAll(lcs, "");
			}
			lcs = TextUtil.getLCString(str1, str2);
			System.out.println("第" + i + "个最长公共子序列（长度" + lcs.length() + "）：");
			System.out.println(lcs);
			System.out.println();
		}
	}

}
