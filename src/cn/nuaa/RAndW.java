package cn.nuaa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
/**
 * �����ȡ���ݼ���������Ⱦɫ������Ĺ̶�����Ϣ
 * ������ÿ��Ⱦɫ����е���Ϣ�������������������������ֻ����һ����Ҫ��ʱ������
 * @author Administrator
 *
 */
public class RAndW {
	String  file;
	public static int products;		//������
	public static int machines;		//������
	public static int[][] oriMatrix;
	public static int productLength;//�ܹ���
	public static int[] MSProduct;	//����ѡ���Ӧ�Ĺ�����
	public static int[] MSj;			//����ѡ���Ӧ�Ĺ����Ӧ��j�����ж�һ�������м����������Խ��мӹ�����
	public static int[] singleProducts;	//ÿ��������ӵ�еĹ�����
	
	/**
	 * RAndW�Ĺ��췽��
	 */
	public RAndW() {
		this("Mk01.fjs");
	}
	public RAndW(String file) {
		super();
		this.file = file;
		BufferedReader br=null;
		try {
			FileInputStream fis=new FileInputStream(file);
			InputStreamReader isr=new InputStreamReader(fis);
			
			br=new BufferedReader(isr);
			String line=null;
			boolean row1=true;
			int index=0;
			while((line=br.readLine())!=null&&!(line.isEmpty())) {
				if(row1) {
					row1=false;
					int[] nums=stripStrength(line);
					products=nums[0];
					machines=nums[1];
					oriMatrix=new int[products][];
				}else {
					oriMatrix[index]=stripStrength(line);
					index++;
				}
				
			}
			productLength=0;
			for(int i=0;i<oriMatrix.length;i++) {
				productLength+=oriMatrix[i][0];
			}
			MSProduct=new int[productLength];
			MSj=new int[productLength];
			int productnum=0;
			for(int i=0;i<MSProduct.length;) {
				for(int j=0;j<oriMatrix[productnum][0];j++) {
					MSProduct[i]=productnum+1;
					i++;
				}
				productnum++;
			}
			int machine=0;
			int i=0;
			for(;machine<oriMatrix.length && i<MSj.length;machine++) {
				int jj=1;
				for(;jj<oriMatrix[machine].length;) {
					MSj[i]=jj;
					jj+=oriMatrix[machine][jj]*2+1;
					i++;
				}
			}
			singleProducts=new int[products];
			for(int j=0;j<oriMatrix.length;j++) {
				singleProducts[j]=oriMatrix[j][0];
			}
				
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public String toString() {
		return this.products+","+this.machines;
	}
	/**
	 * �����ո���ַ���ת��Ϊ�������
	 * @param s
	 * @return
	 */
	public static int[] stripStrength(String s) {
		String [] a=s.trim().split("[^0123456789]+");
		int[] nums=new int[a.length];
		for(int i=0;i<nums.length;i++) {
			nums[i]=Integer.parseInt(a[i]);
		}
		return nums;
	}
	
	
}
