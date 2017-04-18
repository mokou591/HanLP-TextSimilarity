package main.java.model;


public class CompareReport {
	private Article a1;
	private Article a2;

	private int top;

	private double similarity;

	public CompareReport(Article a1, Article a2, int top, double similarity) {
		this.a1 = a1;
		this.a2 = a2;
		this.top = top;
		this.similarity = similarity;
	}

	@Override
	/**
	 * 返回比较结果的简单报告。
	 * 可调用getDetail()方法获得详细报告。
	 */
	public String toString() {
		return String.format("%s和%s 相似度：%.0f%%", a1.getName(), a2.getName(), similarity*100);
	}

	public String getDetail() {
		StringBuilder sb = new StringBuilder();
		String ls = System.lineSeparator();
		sb.append(a1.getName() +" 长度："+a1.getText().length() +" 高频词：");
		sb.append(ls);
		sb.append(a1.getWordFreqList().subList(0, top));
		sb.append(ls);
		sb.append(a2.getName() +" 长度："+a2.getText().length() + " 高频词：");
		sb.append(ls);
		sb.append(a2.getWordFreqList().subList(0, top));
		sb.append(ls);
		sb.append("相似度：" + String.format("%.4f",similarity));
		return sb.toString();
	}
}
