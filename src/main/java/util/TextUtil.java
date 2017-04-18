package main.java.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;

import main.java.model.Article;
import main.java.model.WordFreq;

public class TextUtil {

	/**
	 * 从文件中获取数据，封装成对象。会将词频排序，在多个任务的情况下性能得到优化
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws TikaException
	 */
	public static Article getArticle(File file) throws IOException, TikaException {
		String text = getString(file);
		List<Term> segmentList = getSegmentList(text);
		List<WordFreq> wordFreqList = getWordFrequency(segmentList);
		// 词频从高到低排序
		Comparator<WordFreq> comp = new Comparator<WordFreq>() {
			public int compare(WordFreq a, WordFreq b) {
				if (a.getFreq() < b.getFreq()) {
					return 1;
				} else if (a.getFreq() > b.getFreq()) {
					return -1;
				} else {
					return 0;
				}
			}
		};
		wordFreqList.sort(comp);
		// 封装Article
		Article article = new Article();
		article.setName(file.getName());
		article.setText(text);
		article.setSegmentList(segmentList);
		article.setWordFreqList(wordFreqList);
		return article;
	}

	/**
	 * 从指定文件读取一整个字符串
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws TikaException
	 */
	private static String getString(File file) throws IOException, TikaException {
		Tika tika = new Tika();
		tika.setMaxStringLength((int) file.length());
		String str = tika.parseToString(file);
		return str;
	}

	/**
	 * 将输入的字符串分词处理。
	 * 
	 * @param text
	 *            文本
	 * @return 切分后的单词
	 */
	private static List<Term> getSegmentList(String text) {
		List<Term> segmentList = HanLP.segment(text);
		// 过滤器
		segmentList.removeIf(new Predicate<Term>() {
			/**
			 * 过滤掉：长度为1的分词、标点符号
			 */
			public boolean test(Term term) {
				boolean flag = false;
				// 长度
				String real = term.word.trim();
				if (real.length() <= 1) {
					flag = true;
				}
				// 类型
				// 词性以w开头的，为各种标点符号
				if (term.nature.startsWith('w')) {
					flag = true;
				}
				// 过滤掉代码
				if (term.nature.equals(Nature.nx)) {// 字母专名
					flag = true;
				}
				return flag;
			}
		});
		return segmentList;
	}

	/**
	 * 根据分词集合统计词频
	 * 
	 * @param segmentList
	 *            词频集合
	 * @return
	 */
	public static List<WordFreq> getWordFrequency(List<Term> segmentList) {
		// 统计词频
		Multiset<String> wordSet = HashMultiset.create();
		for (Term term : segmentList) {// 放入词汇集合
			wordSet.add(term.word);
		}
		// 从词汇集合取出单词和频次,放入词频集合
		List<WordFreq> wfList = new ArrayList<WordFreq>();
		for (Entry<String> entry : wordSet.entrySet()) {
			wfList.add(new WordFreq(entry.getElement(), entry.getCount()));
		}
		return wfList;
	}

}
