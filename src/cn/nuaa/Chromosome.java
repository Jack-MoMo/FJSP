package cn.nuaa;

import java.util.Arrays;
/**
 * ���ౣ����ǵ۹������㷨�еĵ������ҵ���Ϣ������ֱ��ʹ���Ŵ��㷨�еĵ���Ⱦɫ����Ϊ������Ϣ
 * @author Administrator
 *
 */
public class Chromosome implements Cloneable{
	private int[] MS;		//��������
	private int[] OS;		//��������
	
	/**
	 * ֱ�������������͹�������
	 * @param MS
	 * @param OS
	 */
	public Chromosome(int[] MS,int[] OS) {
		this.MS=MS;
		this.OS=OS;
	}
	/**
	 * ����֮���Ժ�������
	 */
	public Chromosome() {
		
	}
	/**
	 * ͨ��Kit�������еķ���������
	 * 
	 * @param method
	 * method����Ϊ"GS��LS��RS"���ֱ���ζ������ȫ��ѡ���Ⱦɫ�壬���ɾֲ�ѡ���Ⱦɫ�壬���������ѡ���Ⱦɫ��
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
	 * ��дtoString����
	 */
	@Override
	public String toString() {
		return "Chromosome [MS=" + Arrays.toString(MS) + ", OS=" + Arrays.toString(OS) + "]"+"value:"+Kit.objectFunctionValue(new Chromosome(MS,OS));
//		return "Chromosome [MS=" + Arrays.toString(MS) + ", OS=" + Arrays.toString(OS) + "]";
	}

	
	
	
	/**
	 * �����Ŀ�¡���������¡��
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
