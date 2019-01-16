package com.ksource.common.util;

/**
 * Function: simHash 判断文本相似度，该示例程支持中文<br/>
 * date: 2013-8-6 上午1:11:48 <br/>
 * @author june
 * @version 0.1
 */
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class SimHash {

	private String tokens;

	private BigInteger intSimHash;

	private String strSimHash;

	private int hashbits = 64;

	public SimHash(String tokens) throws IOException {
		this.tokens = tokens;
		this.intSimHash = this.simHash();
	}

	public SimHash(String tokens, int hashbits) throws IOException {
		this.tokens = tokens;
		this.hashbits = hashbits;
		this.intSimHash = this.simHash();
	}

	HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

	public BigInteger simHash() throws IOException {
		// 定义特征向量/数组
		int[] v = new int[this.hashbits];
		// 英文分词
		// StringTokenizer stringTokens = new StringTokenizer(this.tokens);
		// while (stringTokens.hasMoreTokens()) {
		// String temp = stringTokens.nextToken();
		// }
		// 1、中文分词，分词器采用 IKAnalyzer3.2.8 ，仅供演示使用，新版 API 已变化。
		StringReader reader = new StringReader(this.tokens);
		// 当为true时，分词器进行最大词长切分
		IKSegmentation ik = new IKSegmentation(reader, true);
		Lexeme lexeme = null;
		String word = null;
		String temp = null;
		while ((lexeme = ik.next()) != null) {
			word = lexeme.getLexemeText();
			// 注意停用词会被干掉
			// System.out.println(word);
			// 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
			BigInteger t = this.hash(word);
			for (int i = 0; i < this.hashbits; i++) {
				BigInteger bitmask = new BigInteger("1").shiftLeft(i);
				// 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
				// 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
				// 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
				if (t.and(bitmask).signum() != 0) {
					// 这里是计算整个文档的所有特征的向量和
					// 这里实际使用中需要 +- 权重，比如词频，而不是简单的 +1/-1，
					v[i] += 1;
				} else {
					v[i] -= 1;
				}
			}
		}

		BigInteger fingerprint = new BigInteger("0");
		StringBuffer simHashBuffer = new StringBuffer();
		for (int i = 0; i < this.hashbits; i++) {
			// 4、最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
			if (v[i] >= 0) {
				fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
				simHashBuffer.append("1");
			} else {
				simHashBuffer.append("0");
			}
		}
		this.strSimHash = simHashBuffer.toString();
		System.out.println(this.strSimHash + " length " + this.strSimHash.length());
		return fingerprint;
	}

	private BigInteger hash(String source) {
		if (source == null || source.length() == 0) {
			return new BigInteger("0");
		} else {
			char[] sourceArray = source.toCharArray();
			BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
			BigInteger m = new BigInteger("1000003");
			BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
			for (char item : sourceArray) {
				BigInteger temp = BigInteger.valueOf((long) item);
				x = x.multiply(m).xor(temp).and(mask);
			}
			x = x.xor(new BigInteger(String.valueOf(source.length())));
			if (x.equals(new BigInteger("-1"))) {
				x = new BigInteger("-2");
			}
			return x;
		}
	}

	public int hammingDistance(SimHash other) {

		BigInteger x = this.intSimHash.xor(other.intSimHash);
		int tot = 0;

		// 统计x中二进制位数为1的个数
		// 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，
		// 对吧，然后，n&(n-1)就相当于把后面的数字清0，
		// 我们看n能做多少次这样的操作就OK了。

		while (x.signum() != 0) {
			tot += 1;
			x = x.and(x.subtract(new BigInteger("1")));
		}
		return tot;
	}

	public int getDistance(String str1, String str2) {
		int distance;
		if (str1.length() != str2.length()) {
			distance = -1;
		} else {
			distance = 0;
			for (int i = 0; i < str1.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) {
					distance++;
				}
			}
		}
		return distance;
	}

	public List subByDistance(SimHash simHash, int distance) {
		// 分成几组来检查
		int numEach = this.hashbits / (distance + 1);
		List characters = new ArrayList();

		StringBuffer buffer = new StringBuffer();

		int k = 0;
		for (int i = 0; i < this.intSimHash.bitLength(); i++) {
			// 当且仅当设置了指定的位时，返回 true
			boolean sr = simHash.intSimHash.testBit(i);

			if (sr) {
				buffer.append("1");
			} else {
				buffer.append("0");
			}

			if ((i + 1) % numEach == 0) {
				// 将二进制转为BigInteger
				BigInteger eachValue = new BigInteger(buffer.toString(), 2);
				System.out.println("----" + eachValue);
				buffer.delete(0, buffer.length());
				characters.add(eachValue);
			}
		}

		return characters;
	}

	public static void main(String[] args) throws IOException {
		String s = "①生产者、销售者在产品中掺杂、掺假，以假充真，以次充好或者以不合格产品冒充合格产品，伪劣产品销售金额5万元以上的；②或者尚未销售的伪劣产品，货值金额15万元以上的" ;
		SimHash hash1 = new SimHash(s, 64);
		//System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
		// 计算 海明距离 在 3 以内的各块签名的 hash 值
		hash1.subByDistance(hash1, 3);

		// 删除首句话，并加入两个干扰串
		s = "江岸区卫生计生于2014年7月24日办理的吴么舫未取得《医疗机构执业许可证》擅自开展诊疗活动案件，因为吴么舫非法行医被卫生行政部门行政处罚两次以后再次非法行医，根据《最高人民检察院、公安部关于公安机关管辖的刑事案件立案追诉标准的规定（一）》第五十七条第一款第（四）项的规定应当移送公安机关处理。";
		SimHash hash2 = new SimHash(s, 64);
		//System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
		hash1.subByDistance(hash2, 3);

		int dis = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
		System.out.println(hash1.hammingDistance(hash2) + " " + dis);
	}
}