package cn.nuaa;

import java.util.Arrays;
/**
 * 该类保存的是帝国竞争算法中的单个国家的信息，这里直接使用遗传算法中的单个染色体作为国家信息
 * @author Administrator
 *
 */
public class Chromosome implements Cloneable{
	private int[] MS;		//机器排序
	private int[] OS;		//工序排序
	
	/**
	 * 直接输入机器排序和工序排序
	 * @param MS
	 * @param OS
	 */
	public Chromosome(int[] MS,int[] OS) {
		this.MS=MS;
		this.OS=OS;
	}
	/**
	 * 生成之后以后再输入
	 */
	public Chromosome() {
		
	}
	/**
	 * 通过Kit方法类中的方法来生成
	 * 
	 * @param method
	 * method可以为"GS、LS、RS"，分别意味着生成全局选择的染色体，生成局部选择的染色体，和生成随机选择的染色体
	 */
	public Chromosome(String method) {
		if("GS".equals(method)) {
			MS=Kit.GSgenerateMS();
		}else if("LS".equals(method)) {
			MS=Kit.LSgenerateMS();
		}else if("RS".equals(method)) {
			MS=Kit.RSgenerateMS();
		}
		OS=Kit.generateOS();
	}
	
	public int[] getMS() {
		return MS;
	}

	public void setMS(int[] mS) {
		MS = mS;
	}

	public int[] getOS() {
		return OS;
	}

	public void setOS(int[] oS) {
		OS = oS;
	}

	/**
	 * 重写toString方法
	 */
	@Override
	public String toString() {
		return "Chromosome [MS=" + Arrays.toString(MS) + ", OS=" + Arrays.toString(OS) + "]"+"value:"+Kit.objectFunctionValue(new Chromosome(MS,OS));
//		return "Chromosome [MS=" + Arrays.toString(MS) + ", OS=" + Arrays.toString(OS) + "]";
	}

	
	
	
	/**
	 * 针对类的克隆操作，深克隆。
	 */

	public Chromosome clone() {
		Chromosome chro=null;
		try {
			chro=(Chromosome)super.clone();
		}catch(CloneNotSupportedException e) {
			
		}
		chro.MS=Arrays.copyOf(this.MS, this.MS.length);
		chro.OS=Arrays.copyOf(this.OS,this.OS.length);
		return chro;
	}
	
}
