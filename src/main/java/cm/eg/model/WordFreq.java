package cm.eg.model;

/**
 * 词频。
 */
public class WordFreq {

	private String word;
	private int freq;

	public WordFreq(String word, int freq) {
		this.word = word;
		this.freq = freq;
	}

	public String getWord() {
		return word;
	}

	public int getFreq() {
		return freq;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	@Override
	public String toString() {
		return "[" + word + " : " + freq + "]";
	}

	@Override
	public boolean equals(Object obj) {
		WordFreq wf = (WordFreq) obj;
		return this.getWord().equals(wf.getWord());
	}
}
