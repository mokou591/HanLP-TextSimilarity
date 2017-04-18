package test.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Article;
import main.java.model.CompareReport;
import main.java.model.CompareTask;
import main.java.model.WordFreq;
import main.java.util.TextUtil;

import org.apache.tika.exception.TikaException;
import org.junit.Test;

import com.hankcs.hanlp.seg.common.Term;

public class ProcessTest {

	private static String root = "textdata/";

	
	@Test
	/**
	 * 显示词频最高的若干项
	 */
	public void demo1() throws IOException, TikaException {
		
		Article article = TextUtil.getArticle(new File(root + "小王子.txt"));
		List<WordFreq> wfList = article.getWordFreqList();
		// output top
		System.out.println("Top frequency words:");
		for (int i = 0; i < 50; ++i) {
			System.out.println(wfList.get(i));
		}
	}

	@Test
	/**
	 * 向量相似度算法
	 */
	public void demo2() throws IOException, TikaException {
		// TODO 涉及读写可用多线程FutureTask优化。
		Article a1 = TextUtil.getArticle(new File(root + "ljb_info2.doc"));
		Article a2 = TextUtil.getArticle(new File(root + "wjh_info2.doc"));
		CompareTask task = new CompareTask(a1, a2);
		task.execute();
		CompareReport report = task.getCompareReport();
		System.out.println(report);
	}

	@Test
	/**
	 * 报告多个测试
	 */
	public void demo3() throws IOException, TikaException {
		List<CompareTask> tasks = new ArrayList<CompareTask>();

		Article a1 = TextUtil.getArticle(new File(root + "wjh_info1.doc"));
		Article b1 = TextUtil.getArticle(new File(root + "ljb_info1.doc"));
		tasks.add(new CompareTask(a1, b1));
		System.out.println("load 1");

		Article a2 = TextUtil.getArticle(new File(root + "wjh_info2.doc"));
		Article b2 = TextUtil.getArticle(new File(root + "ljb_info2.doc"));
		tasks.add(new CompareTask(a2, b2));
		System.out.println("load 2");

		Article a3 = TextUtil.getArticle(new File(root + "wjh_info3.doc"));
		Article b3 = TextUtil.getArticle(new File(root + "ljb_info3.doc"));
		tasks.add(new CompareTask(a3, b3));
		System.out.println("load 3");

		Article a4 = TextUtil.getArticle(new File(root + "wjh_info4.doc"));
		Article b4 = TextUtil.getArticle(new File(root + "ljb_info4.doc"));
		tasks.add(new CompareTask(a4, b4));
		System.out.println("load 4");

		for (CompareTask task : tasks) {
			task.execute();
			System.out.println(task.getCompareReport().getDetail());
			System.out.println();
		}
	}

	@Test
	/**
	 * 公共子序列
	 */
	public void demo4() throws IOException, TikaException {
		// 获得分词后的一整串字符
		Article a1 = TextUtil.getArticle(new File(root + "wjh_info3.doc"));
		Article a2 = TextUtil.getArticle(new File(root + "ljb_info1.doc"));
		List<Term> termList1 = a1.getSegmentList();
		List<Term> termList2 = a2.getSegmentList();
		StringBuilder segstr1 = new StringBuilder();
		StringBuilder segstr2 = new StringBuilder();

		for (Term term : termList1) {
			segstr1.append(term.word);
		}
		for (Term term : termList2) {
			segstr2.append(term.word);
		}
		// 最长公共子序列
		String lcs = "";
		String str1 = segstr1.toString();
		String str2 = segstr2.toString();

		for (int i = 1; i <= 5; ++i) {
			if (i != 1) {
				str1 = str1.replaceAll(lcs, "");
				str2 = str2.replaceAll(lcs, "");
			}
			lcs = getLCString(str1, str2);
			System.out.println("第" + i + "个最长公共子序列（长度" + lcs.length() + "）：");
			System.out.println(lcs);
			System.out.println();
		}
	}

	/**
	 * temp
	 */
	private static String getLCString(String string1, String string2) {
		int len1, len2;
		char[] str1 = string1.toCharArray();
		char[] str2 = string2.toCharArray();

		len1 = str1.length;
		len2 = str2.length;
		int maxLen = len1 > len2 ? len1 : len2;

		int[] max = new int[maxLen];// 保存最长子串长度的数组
		int[] maxIndex = new int[maxLen];// 保存最长子串长度最大索引的数组
		int[] c = new int[maxLen];

		int i, j;
		for (i = 0; i < len2; i++) {
			for (j = len1 - 1; j >= 0; j--) {
				if (str2[i] == str1[j]) {
					if ((i == 0) || (j == 0))
						c[j] = 1;
					else
						c[j] = c[j - 1] + 1;// 此时C[j-1]还是上次循环中的值，因为还没被重新赋值
				} else {
					c[j] = 0;
				}

				// 如果是大于那暂时只有一个是最长的,而且要把后面的清0
				if (c[j] > max[0]) {
					max[0] = c[j];
					maxIndex[0] = j;

					for (int k = 1; k < maxLen; k++) {
						max[k] = 0;
						maxIndex[k] = 0;
					}
				}
				// 有多个是相同长度的子串
				else if (c[j] == max[0]) {
					for (int k = 1; k < maxLen; k++) {
						if (max[k] == 0) {
							max[k] = c[j];
							maxIndex[k] = j;
							break;
						}
					}
				}
			}
		}
		// 最长子字符串
		StringBuilder lcs = new StringBuilder();
		for (j = 0; j < maxLen; j++) {
			if (max[j] > 0) {
				for (i = maxIndex[j] - max[j] + 1; i <= maxIndex[j]; i++)
					lcs.append((str1[i]));
			}
		}
		return lcs.toString();
	}
}
