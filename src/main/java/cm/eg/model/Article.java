package cm.eg.model;

import java.util.List;

import com.hankcs.hanlp.seg.common.Term;

public class Article {
	private String name;

	private String text;

	private List<Term> segmentList;
	private List<WordFreq> wordFreqList;

	public List<Term> getSegmentList() {
		return segmentList;
	}

	public void setSegmentList(List<Term> segmentList) {
		this.segmentList = segmentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public List<WordFreq> getWordFreqList() {
		return wordFreqList;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setWordFreqList(List<WordFreq> wordFreqList) {
		this.wordFreqList = wordFreqList;
	}

}
