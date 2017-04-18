package main.java.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 用于表示比较两篇文章的任务。 设置了任务状态。 可查看比较的结果。
 */
public class CompareTask {

	private Article a1;
	private Article a2;

	private boolean isFinished;

	private CompareReport compareReport;

	// 取高频词前若干位
	private static final int TOPMAX = 20;

	public CompareTask(Article a1, Article a2) {
		this.a1 = a1;
		this.a2 = a2;
		this.isFinished = false;
	}

	public void execute() {
		// 取高频词前若干位
		int top = TOPMAX;
		List<WordFreq> freq1 = a1.getWordFreqList().subList(0, top);
		List<WordFreq> freq2 = a2.getWordFreqList().subList(0, top);
		// System.out.println(freq1);
		// System.out.println(freq2);
		// 获取高频词并集
		ArrayList<WordFreq> union = new ArrayList<WordFreq>(freq1);
		union.addAll(freq2);
		// 生成向量
		ArrayList<WordFreq> v1 = new ArrayList<WordFreq>(freq1);
		ArrayList<WordFreq> v2 = new ArrayList<WordFreq>(freq2);
		for (WordFreq wf : union) {
			// 向量包含了高频词并集里的每个词，如果不包含则词频为0
			if (!v1.contains(wf)) {
				v1.add(new WordFreq(wf.getWord(), 0));
			}
			if (!v2.contains(wf)) {
				v2.add(new WordFreq(wf.getWord(), 0));
			}
		}
		// 根据词语排序以对齐向量，方便计算
		Comparator<WordFreq> strComp = new Comparator<WordFreq>() {
			public int compare(WordFreq a, WordFreq b) {
				return a.getWord().compareTo(b.getWord());
			}
		};
		v1.sort(strComp);
		v2.sort(strComp);
		// System.out.println(v1);
		// System.out.println(v2);
		/**
		 * 代入公式
		 */
		double vproduct = 0;
		int sumSquare1 = 0;
		int sumSquare2 = 0;
		for (int i = 0; i < v1.size(); ++i) {
			int num1 = v1.get(i).getFreq();
			int num2 = v2.get(i).getFreq();
			// 向量点积
			vproduct += num1 * num2;
			// 求向量模的过程
			sumSquare1 += num1 * num1;
			sumSquare2 += num2 * num2;
		}
		// 两向量模的乘积
		double normProduct = Math.sqrt(sumSquare1 * sumSquare2);
		// 点积除以模乘积
		double similarity = vproduct / normProduct;
		// System.out.printf("%.4f / %.4f = %.4f\n", vproduct, normProduct,
		// similarity);
		// 标记完成状态
		isFinished = true;
		makeReport(a1, a2, similarity);
	}

	private void makeReport(Article a1, Article a2, double similarity) {
		compareReport = new CompareReport(a1, a2, TOPMAX, similarity);

	}

	public boolean isFinished() {
		return isFinished;
	}

	public CompareReport getCompareReport() {
		return compareReport;
	}

}
