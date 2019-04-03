package cn.nuaa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;






/**
 * �����б����й�ʹ����������̵ĳ��������㷨����ط���
 * ��Щ�������������Կ���
 * @author Administrator
 *
 */
public class Kit {
	
	/**
	 * ���ɰ���ȫ��ѡ��ĳ�ʼȾɫ��Ļ���ѡ��
	 * @return
	 */
	public static int[] GSgenerateMS() {
		int[][] matrix=RAndW.oriMatrix;
		int[] machineTime=new int[RAndW.machines];//����ʱ������
		int[] productSets=new int[RAndW.products];//������
		for(int i=0;i<productSets.length;i++) {
			productSets[i]=i;
		}

		int[] MS=new int[RAndW.productLength];//����ѡ�񲿷�  ÿ�������Ĺ��������֮�ͣ���Mk01��Ϊ55
		/*
		 * �ӹ�������ѡȡ������ͬʱѡ��ǰ�����ĵ�һ������
		 */
		int index=0;		//MS������
		while(productSets.length>0) {
//			System.out.println(Arrays.toString(productSets));
			index=0;
			//ѡ��һ������
			Random rand=new Random();
			int productIn=rand.nextInt(productSets.length);
			/*
			 * ��ѡ����һ�������󣬽��ù����ӹ�������ɾȥ
			 */
//			System.out.println(productIn);
			int pro=productSets[productIn];
			productSets[productIn]=productSets[productSets.length-1];
			productSets[productSets.length-1]=pro;
			productSets=Arrays.copyOf(productSets, productSets.length-1);
			/*
			 * ��ѡ���깤����������MS�������ù���������λ��
			 */
			for(int k=0;k<pro;k++) {
				index+=matrix[k][0];
			}
			
			for(int j=1;j<matrix[pro].length;) {
				int q=matrix[pro][j];
				int index_min=find_index_min(j,matrix[pro],machineTime);	
				/*
				 * ѡ����õĻ�������Ӧ��λ��
				 */
				MS[index]=matrix[pro][index_min];
				/*
				 * ������ʱ��������� 
				 */
//				System.out.println(matrix[pro][index_min]-1);
				machineTime[matrix[pro][index_min]-1]+=matrix[pro][index_min+1];
//				System.out.println("machineTime:"+Arrays.toString(machineTime));
				index++;
				j+=2*matrix[pro][j]+1;

			}
//			System.out.println(Arrays.toString(MS));
		}

		return MS;
	}
	
	/**
	 * ���ɰ��վֲ�ѡ��ĳ�ʼȾɫ��Ļ���ѡ��
	 * @return
	 */
	public static int[] LSgenerateMS() {
		int[][] matrix=RAndW.oriMatrix;
		int[] machineTime=new int[RAndW.machines];//����ʱ������
		int[] productSets=new int[RAndW.products];//������
		for(int i=0;i<productSets.length;i++) {
			productSets[i]=i;
		}
//		System.out.println("����Ϊ��"+r.getproductLength());
		int[] MS=new int[RAndW.productLength];//����ѡ�񲿷� ��Mk01��Ϊ55
		/*
		 * �ӹ�������ѡȡ������ͬʱѡ��ǰ�����ĵ�һ������
		 */
		int index=0;		//MS������
		while(productSets.length>0) {
//			System.out.println(Arrays.toString(productSets));
			index=0;
			//ѡ��һ������
			Random rand=new Random();
			int productIn=rand.nextInt(productSets.length);
			/*
			 * ��ѡ����һ�������󣬽��ù����ӹ�������ɾȥ
			 */
//			System.out.println(productIn);
			int pro=productSets[productIn];
			productSets[productIn]=productSets[productSets.length-1];
			productSets[productSets.length-1]=pro;
			productSets=Arrays.copyOf(productSets, productSets.length-1);
			/*
			 * ��ѡ���깤����������MS�������ù���������λ��
			 */
			for(int k=0;k<pro;k++) {
				index+=matrix[k][0];
			}
			
			for(int j=1;j<matrix[pro].length;) {
				int q=matrix[pro][j];
				int index_min=find_index_min(j,matrix[pro],machineTime);	
				/*
				 * ѡ����õĻ�������Ӧ��λ��
				 */
				MS[index]=matrix[pro][index_min];
				/*
				 * ������ʱ��������� 
				 */
//				System.out.println(matrix[pro][index_min]-1);
				machineTime[matrix[pro][index_min]-1]+=matrix[pro][index_min+1];
//				System.out.println("machineTime:"+Arrays.toString(machineTime));
				index++;
				j+=2*matrix[pro][j]+1;

			}
//			System.out.println(Arrays.toString(MS));
			/*
			 * ��ȫ��ѡ��ͬ����,����ÿһ��Ϊ��һ�����ѡ��ʱ,����ʱ������Ҫ���¹���
			 */
			machineTime=new int[RAndW.machines];
		}

		return MS;
	}
	
	/**
	 * ���ɰ������ѡ��ĳ�ʼȾɫ��Ļ���ѡ��
	 * @return
	 */

	public static int[] RSgenerateMS() {
		int[] MS=new int[RAndW.productLength];	//����ѡ�񲿷�
		int[][] matrix=RAndW.oriMatrix;
		int index=0;
		for(int i=0;i<matrix.length;i++) {
			int j=1;
			for(;j<matrix[i].length;) {
				Random rand=new Random();
				int k=rand.nextInt(matrix[i][j])+1;
				MS[index]=matrix[i][j+k*2-1];
				index++;
				j+=2*matrix[i][j]+1;
			}
		}
		return MS;
	}
	
	/**
	 * ������ɳ�ʼȾɫ��Ĺ���ѡ��
	 * @return
	 */
	
	public static int[] generateOS() {
		int[] OS=new int[RAndW.productLength];
		int[][] matrix=RAndW.oriMatrix;
		int index=0;
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[i][0];j++) {
				OS[index]=i+1;
				index++;
			}
		}
		
		/*
		 * ��OS�е�Ԫ���������
		 */
		for(int i=0;i<OS.length;i++) {
			Random rand=new Random();
			int temp=rand.nextInt(OS.length);
			int temp1=OS[i];
			OS[i]=OS[temp];
			OS[temp]=temp1;
		}
		
		return OS;
		
	}
	
	/**
	 * ��ȡȾɫ��Ļ���ѭ�����
	 * @param chro Ⱦɫ��
	 * @return
	 */
	public static int[][] getJm(Chromosome chro){
		int[] MS=chro.getMS();
		int[][] oriMatrix=RAndW.oriMatrix;
		int[][] Jm;
		int[] products=new int[RAndW.products];//�������ÿ̨�����Ĺ�����
		for(int i=0;i<oriMatrix.length;i++) {
			int[] single=oriMatrix[i];
			int index=0;
			for(int j=1;j<single.length;) {
				if(single[j]!=0) {
					index++;
					j+=single[j]*2+1;
				}
			}
			products[i]=index;
		}
		//System.out.println(Arrays.toString(products));
		
		int maxProduct=products[0];
		for(int i=1;i<products.length;i++) {
			if(products[i]>maxProduct) {
				maxProduct=products[i];
			}
		}
		//System.out.println(maxProduct);
		Jm=new int[RAndW.products][];
		int index=0;
		for(int i=0;i<Jm.length;i++) {
			Jm[i]=new int[products[i]];
			for(int j=0;j<products[i];j++) {
				
				Jm[i][j]=MS[index];
				index++;
			}
		}
		return Jm;	
	}
	
	
	/**
	 * ��ȡȾɫ���ʱ��˳�����
	 * @param chro Ⱦɫ��
	 * @param Jm Ҫ����ʱ����󣬱����Ȼ�û���˳�����
	 * @return
	 */
	public static int[][] getT(Chromosome chro,int[][] Jm){
		int[][] T=new int[Jm.length][];
		int[][] oriMatrix=RAndW.oriMatrix;
		for(int i=0;i<T.length;i++) {
			T[i]=new int[Jm[i].length];
			int index=1;
			for(int j=0;j<Jm[i].length;j++) {
				/*
				 * Ҫ���ʱ��˳������ǰ��ջ��������ÿһ��ֵ����õ�
				 */
				int num=Jm[i][j];
				int number=oriMatrix[i][index];
				boolean flag=false;
				for(int k=index+1;k<index+2*number+1;k+=2) {
					if(oriMatrix[i][k]==num) {
						T[i][j]=oriMatrix[i][k+1];
						flag=true;
						break;
					}
				}
				if(!flag) {
					System.out.println("������,��ʱindexΪ"+index+",��ʱkΪ��");
					System.out.println(chro);
				}
				index+=number*2+1;

			}
		}
		return T;
	}
	
	/**
	 * �ú��������ҵ�һ�������е�ĳ�������ڻ���ʱ����������Сʱ��
	 * @param j
	 * @param singleMatrix
	 * @return
	 */
	public static int find_index_min(int j,int[] singleMatrix,int[]MachineTime) {
		/*
		 * ���ڷ����ͷ���֮���ǲ��й�ϵ�����Ա���Ҫ��static���Σ����������ᵼ�²�����MachinTime����ɾ�̬������
		 * Ϊ�˽��������⣬ֻ�����½�һ����ͬ�����飬Ȼ�������������Ρ�
		 */
		int[] machineTime=new int[MachineTime.length];
		machineTime=Arrays.copyOf(MachineTime, MachineTime.length);
		//System.out.println("j:"+j+",machineTime"+Arrays.toString(machineTime));
		int index_min=j+1;		//����ѡ��Ļ�������singleMatrix�е�λ��  j=2
		for(int s=0;s<singleMatrix[j];s++) {
			machineTime[singleMatrix[j+s*2+1]-1]+=singleMatrix[j+s*2+2];
			if(machineTime[singleMatrix[j+s*2+1]-1]<machineTime[singleMatrix[index_min]-1]) {
				index_min=j+s*2+1;
			}
		}
		return index_min;
	}	


	/**
	 * �����������ȣ������ɻ����Լ���������ɵ�Ganttͼ
	 * @param chro Ⱦɫ��
	 * @param Jm ����˳�����
	 * @param T	ʱ��˳�����
	 * @return
	 */
	public static int[][] decode(Chromosome chro,int[][] Jm,int[][] T){
		int[] OS=chro.getOS();
		int[][] gantt=new int[RAndW.machines][0];
		int[] productTime=new int[RAndW.products];//��ǰʱ�̸������Ľ���ʱ��
		int[] productArray=new int[RAndW.products];//��ǰʱ�̸������Ĺ����
		
		for(int i=0;i<OS.length;i++) {
			int product=OS[i]-1;	//������,��0��ʼ

			int machine=Jm[product][productArray[product]]-1;	//�����ţ������Ŵ�0��ʼ,�����Ҳ��0��ʼ,������ҲӦ�ô�0��ʼ
			int time=T[product][productArray[product]];	//��Ӧ���������ʱ��
			/*
			 * ȷ�Ϻû����󣬶Ը���ͼ�ж�Ӧ�Ļ����Ž�������
			 */
			gantt[machine]=Arrays.copyOf(gantt[machine], gantt[machine].length+2);
			
			if(gantt[machine].length==2) {//�����ǰ������û�����й�������ù���Ŀ�ʼʱ��Ϊ������ǰһ���Ľ�ֹʱ��
				gantt[machine][0]=productTime[product];
				gantt[machine][1]=time+productTime[product];
				productTime[product]=time+productTime[product];//ÿ�β��깤������Ӧ�Ĺ�����Ӧ�Ľ�ֹʱ��ҲҪ�仯
				
				
				
			}else if(gantt[machine].length==4){//����û����Ͻ�������һ������
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * ����ܰ�ʱ����뵽ĳ��ʱ���֮ǰ�Ͱ������뵽ĳ��ʱ���֮ǰ
				 */
				if(endTime<=gantt[machine][0]) {
					System.arraycopy(gantt[machine], 0, gantt[machine], 2, 2);
					gantt[machine][0]=startTime;
					gantt[machine][1]=endTime;
					productTime[product]=endTime;
					
					
				}else {
					/*
					 * �����жϵ�ǰ�����ʱ���뵱ǰ���������е����һ�������Ľ�ֹʱ���С��
					 * ѡ��ϴ����һ��ʱ����Ϊ�õ�����Ŀ�ʼʱ�䣬��ֹʱ��Ϊ����Ŀ�ʼʱ����Ϲ�������ʱ��
					 */
					startTime=(productTime[product]>gantt[machine][1])?productTime[product]:gantt[machine][1];
					endTime=startTime+time;
					gantt[machine][2]=startTime;
					gantt[machine][3]=endTime;
					productTime[product]=endTime;
					
					
					
					
						
				}
			}else {//����û����������˶������
				/*
				 * �ж��Ƿ���Խ�������뵽�û������еĹ���֮�䣬����ܹ�����Ͳ��룬������ܣ���ֱ�������һλ�����
				 */
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * ѡ��ķ����������һλ��ǰ���ƣ�����ܹ���������flagΪtrue�����浱ǰλ�ã�������ǰ����
				 * ֱ���Ƶ��ù�����ǰһ���Ĺ����ֹʱ��
				 */
				int j=gantt[machine].length-4;
				/*
				 * ��ʱgantt[machine][j]=��ĩβ�Ĺ������ʼʱ�䣬gantt[machine][j+1]=��ĩβ�Ĺ���Ľ���ʱ��
				 */
				int position=-1;
				for(;j>=0&&startTime<gantt[machine][j];j-=2) {
					/*
					 * ���������j����һֱ���Ƶ����ʱ��gantt[machine][j-1]�ᱨָ������������Χ�쳣��˱����ų�
					 */
					if(j!=0 &&(gantt[machine][j]-time>=((gantt[machine][j-1]>startTime)?gantt[machine][j-1]:startTime))
						||
						j==0 &&(gantt[machine][j]-time>=startTime)
							) {
						position=j;
					}
				}	
				if(position!=-1) {
					try {
						System.arraycopy(gantt[machine], position, gantt[machine], position+2, gantt[machine].length-position-2);
						
					}catch(Exception e){
						System.out.println("ganttNow:");
						for(int k=0;k<gantt.length;k++) {
							System.out.println(Arrays.toString(gantt[k]));
						}
						System.out.println("machine:"+machine);
						System.out.println("position:"+position);
						throw new RuntimeException(e);
					}
					/*
					 * ͬ�ϣ����position����Ľ�Ϊ0���򲻴���֮ǰ��һ������Ľ�ֹʱ�䣬��˱��뿼�ǵ�positionΪ0�����
					 */
					if(position !=0) {
						gantt[machine][position]=(gantt[machine][position-1]>startTime)?gantt[machine][position-1]:startTime;
						
					}else {
						gantt[machine][position]=startTime;
						
					}
					
					
					gantt[machine][position+1]=gantt[machine][position]+time;
					productTime[product]=gantt[machine][position+1];
					
				}else {
					gantt[machine][gantt[machine].length-2]=(startTime>gantt[machine][gantt[machine].length-3])?startTime:gantt[machine][gantt[machine].length-3];
					gantt[machine][gantt[machine].length-1]=gantt[machine][gantt[machine].length-2]+time;
					productTime[product]=gantt[machine][gantt[machine].length-1];
					
				}	
			}
			productArray[product]+=1;	//����ż�һ
			
			
			
			
			
			
			
		}
		return gantt;
	}

	/**
	 * �����������ȣ������ɻ�����������ɵ�Ganttͼ���ڲ������Զ����� ����˳������ʵ��˳����������������
	 * @param chro Ⱦɫ��
	 * @return
	 */
	public static int[][] decode(Chromosome chro){
		int[][] Jm=getJm(chro);
		int[][] T=getT(chro,Jm);
		return decode(chro,Jm,T);
	}

	/**
	 * ��ø�Ⱦɫ���Ŀ�꺯��ֵ��ʵ����Ҳ��ͨ��ganttͼ����õ�
	 * @param chro Ⱦɫ��
	 * @return	��Ⱦɫ���Ŀ�꺯��ֵ
	 */
	public static int objectFunctionValue(Chromosome chro) {
		int[][] gantt=decode(chro);
		int maxvalue=0;
		for(int i=0;i<gantt.length;i++) {
			if(gantt[i].length>0) {//�����п��ܴ���һ̨������û�мӹ����κ��������˱�������ж�
				if(maxvalue<gantt[i][gantt[i].length-1]) {
					maxvalue=gantt[i][gantt[i].length-1];
				}
			}
		}
		return maxvalue;
	}

	/**
	 * �Ƚ�Ⱦɫ��1��Ⱦɫ��2��Ŀ�꺯��ֵ����r�ĸ��ʷ���һ��Ŀ�꺯��ֵ�ϴ��Ⱦɫ��
	 * ��1-r�ĸ��ʷ���һ��Ŀ�꺯��ֵ��С��Ⱦɫ��
	 * @param chro1	Ⱦɫ��1
	 * @param chro2	Ⱦɫ��2
	 * @param r	����ֵ
	 * @return	����һ��Ⱦɫ��
	 */
	public static Chromosome choosebetterChromosome(Chromosome chro1,Chromosome chro2,double r) {
		Random rand=new Random();
		if(rand.nextDouble()<r) {
			return (objectFunctionValue(chro1)>objectFunctionValue(chro2))?chro1.clone():chro2.clone();
		}else {
			return (objectFunctionValue(chro1)<objectFunctionValue(chro2))?chro1.clone():chro2.clone();
		}
	}
	
	/**
	 * ������Ⱦɫ����н�������������ظ�Ⱦɫ����Ž����Ⱦɫ����Ž⡣
	 * @param chrotemp1
	 * @param chrotemp2
	 * @return
	 */
	public static Chromosome[] crossover(Chromosome chrotemp1,Chromosome chrotemp2) {
		Chromosome chroOld1=chrotemp1.clone();
		Chromosome chroOld2=chrotemp2.clone();
		Chromosome[] chroYoung=getYoungchros(chrotemp1,chrotemp2);
		
		Chromosome[] bestChro=new Chromosome[2];
		bestChro[0]=(objectFunctionValue(chroOld1)<objectFunctionValue(chroOld2))?chroOld1.clone():chroOld2.clone();
		bestChro[1]=(objectFunctionValue(chroYoung[0])<objectFunctionValue(chroYoung[1]))?chroYoung[0].clone():chroYoung[1].clone();
		
		return bestChro;
	}
	/**
	 * ������Ⱦɫ����н����������������Ⱦɫ�塣
	 * @param chrotemp1
	 * @param chrotemp2
	 * @return
	 */
	public static Chromosome[] getYoungchros(Chromosome chrotemp1,Chromosome chrotemp2) {
		Chromosome chroOld1=chrotemp1.clone();
		Chromosome chroOld2=chrotemp2.clone();
		Chromosome[] youngChro=new Chromosome[2];
		int[][] MS=MSCrossover(chroOld1.getMS(),chroOld2.getMS());
		int[][] OS=OSCrossover(chroOld1.getOS(),chroOld2.getOS());
		youngChro[0]=new Chromosome(MS[0],OS[0]);
		youngChro[1]=new Chromosome(MS[1],OS[1]);
		return youngChro;
	}
	/**
	 * ���Ⱦɫ��Ļ������򲿷ֽ��н�������������ؽ����Ļ������򲿷�
	 * @param MS1	Ⱦɫ��1�Ļ������򲿷�
	 * @param MS2	Ⱦɫ��2�Ļ������򲿷�
	 * @return
	 */
	public static int[][] MSCrossover(int[] MS1,int[] MS2) {
		int len=MS1.length;
		int[][] MSmatrix=new int[2][len];
		boolean[] flags=new boolean[MS1.length];
		for(int i=0;i<flags.length;i++) {
			Random rand=new Random();
			flags[i]=rand.nextBoolean();
		}
		
		for(int i=0;i<flags.length;i++) {
			if(flags[i]) {
				MSmatrix[0][i]=MS2[i];
				MSmatrix[1][i]=MS1[i];
			}
			else {
				MSmatrix[0][i]=MS1[i];
				MSmatrix[1][i]=MS2[i];
			}
		}
		
		return MSmatrix;
	}
	/**
	 * ���Ⱦɫ��Ĺ������򲿷ֽ��н�������������ؽ����Ĺ������򲿷�
	 * @param OS1	Ⱦɫ��1�Ĺ������򲿷�
	 * @param OS2	Ⱦɫ��2�Ĺ������򲿷�
	 * @return
	 */
	public static int[][] OSCrossover(int[] OS1,int[] OS2){
		int[][] OSmatrix=new int[2][OS1.length];
		boolean[] flags=new boolean[RAndW.products];
		for(int i=0;i<flags.length;i++) {
			Random rand=new Random();
			flags[i]=rand.nextBoolean();
		}
		for(int i=0;i<OS1.length;i++) {
			if(flags[OS1[i]-1]) {
				OSmatrix[0][i]=OS1[i];
			}
			if(!flags[OS2[i]-1]) {
				OSmatrix[1][i]=OS2[i];
			}
		}
		
		int index=0;
		for(int i=0;i<OSmatrix[0].length;i++) {
			
			if(OSmatrix[0][i]==0) {
				for(;index<OS2.length;) {
					if(!flags[OS2[index]-1]) {
						OSmatrix[0][i]=OS2[index];
						index++;
						break;
					}
					index++;
				}
			}
		}
		index=0;
		for(int i=0;i<OSmatrix[1].length;i++) {
			if(OSmatrix[1][i]==0) {
				for(;index<OS1.length;) {
					if(flags[OS1[index]-1]) {
						OSmatrix[1][i]=OS1[index];
						index++;
						break;
					}
					index++;
				}
			}
		}
		return OSmatrix;
	
	}
	/**
	 * ���Ⱦɫ����й��򲿷ֵı��죬�����ر�����Ⱦɫ��
	 * ��չ�Ĳ��������������ڻ��ڹ�������Ⱦɫ�������ѡ��һ�������Ĺ���(����)���������뵽һ�������λ�ã�
	 * �������й��������������Ĳ��䡣
	 * @param chro
	 * @return
	 */
	public static Chromosome mutationChromosomeOS(Chromosome chro) {
		int[] MS=chro.clone().getMS();
		int[] OS=chro.clone().getOS();
		Random rand=new Random();
		int index1=rand.nextInt(OS.length);//ȡ��index1����ֵ��������

		int num=OS[index1];
		if(index1!=OS.length-1) {
			System.arraycopy(OS, index1+1, OS, index1, OS.length-index1-1);
		}
		OS=Arrays.copyOf(OS, OS.length-1);
		int index2=rand.nextInt(OS.length+1);//��ȡҪ�����λ�ã�index2�����ݣ�����,�п���ֱ�ӷ������һ��λ��
		OS=Arrays.copyOf(OS, OS.length+1);
		if(index2!=OS.length-1) {
			System.arraycopy(OS, index2, OS, index2+1, OS.length-index2-1);
		}
		OS[index2]=num;
		return new Chromosome(MS,OS);
			
	}
	
	/**
	 * ���Ⱦɫ����л���ѡ�񲿷ֵı��죬�����ر�����Ⱦɫ��
	 * ����ԭ��Ϊ�����ѡ����ڻ�������Ⱦɫ���һ�������ڹ���ļӹ������������ѡȡ��������û���
	 * @param chro
	 * @return
	 */
	public static Chromosome mutationChromosomeMS(Chromosome chro) {
		int[] MS=chro.clone().getMS();
		int[] OS=chro.clone().getOS();
		Random rand=new Random();
		int index=rand.nextInt(MS.length);
		int[] MSProduct=RAndW.MSProduct;
		int[] MSj=RAndW.MSj;
		int productNumber=MSProduct[index]-1;
	
		int jNumber=MSj[index];
		int nums=RAndW.oriMatrix[productNumber][jNumber];

		if(nums!=1){//��ֻ��nums=1ʱ��ζ�Ÿù���ֻ����һ̨�����ӹ����޷���
			int[] list=new int[nums-1];
			int indextemp=0;
			for(int i=0;i<nums;i++) {
				if(MS[index]!=RAndW.oriMatrix[productNumber][jNumber+1+2*i]) {
					list[indextemp]=RAndW.oriMatrix[productNumber][jNumber+1+2*i];
					indextemp++;
				}
				
			}
			MS[index]=list[rand.nextInt(list.length)];	
		}
		
		return new Chromosome(MS,OS).clone();
		
	}
	
	
	
	
	/**
	 * ���ɸ���ͼ�а�����Ӧ�Ĺ����б������б��Լ�����ͼ����Ϣ
	 * @param chro ��Ҫ���ɵ�Ⱦɫ��
	 */
	public static DrawGantt drawingGantt(Chromosome chro) {
		int[][] Jm=getJm(chro);
		int[][] T=getT(chro,Jm);
		int[] OS=chro.getOS();
		int[][] gantt=new int[RAndW.machines][0];
		int[][] productNumber=new int[RAndW.machines][0];//����ͼ�и�Ԫ�صĹ�����
		int[][] processNumber=new int[RAndW.machines][0];//����ͼ�и�Ԫ�صĹ����
		int[] productTime=new int[RAndW.products];//��ǰʱ�̸������Ľ���ʱ��
		int[] productArray=new int[RAndW.products];//��ǰʱ�̸������Ĺ����
		int[] appearTime=new int[RAndW.products];//�������ֵĴ���
		
		
		for(int i=0;i<OS.length;i++) {
			int product=OS[i]-1;	//������,��0��ʼ
			appearTime[product]+=1;
			int machine=Jm[product][productArray[product]]-1;	//�����ţ������Ŵ�0��ʼ,�����Ҳ��0��ʼ,������ҲӦ�ô�0��ʼ
			int time=T[product][productArray[product]];	//��Ӧ���������ʱ��
			/*
			 * ȷ�Ϻû����󣬶Ը���ͼ�ж�Ӧ�Ļ����Ž�������
			 */
			gantt[machine]=Arrays.copyOf(gantt[machine], gantt[machine].length+2);
			productNumber[machine]=Arrays.copyOf(productNumber[machine], productNumber[machine].length+1);
			processNumber[machine]=Arrays.copyOf(processNumber[machine], processNumber[machine].length+1);
			if(gantt[machine].length==2) {//�����ǰ������û�����й�������ù���Ŀ�ʼʱ��Ϊ������ǰһ���Ľ�ֹʱ��
				gantt[machine][0]=productTime[product];
				gantt[machine][1]=time+productTime[product];
				productTime[product]=time+productTime[product];//ÿ�β��깤������Ӧ�Ĺ�����Ӧ�Ľ�ֹʱ��ҲҪ�仯
				
				productNumber[machine][0]=product+1;
				processNumber[machine][0]=appearTime[product];
				
				
			}else if(gantt[machine].length==4){//����û����Ͻ�������һ������
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * ����ܰ�ʱ����뵽ĳ��ʱ���֮ǰ�Ͱ������뵽ĳ��ʱ���֮ǰ
				 */
				if(endTime<=gantt[machine][0]) {
					System.arraycopy(gantt[machine], 0, gantt[machine], 2, 2);
					gantt[machine][0]=startTime;
					gantt[machine][1]=endTime;
					productTime[product]=endTime;
					
					System.arraycopy(productNumber[machine], 0, productNumber[machine], 1, 1);
					productNumber[machine][0]=product+1;
					
					System.arraycopy(processNumber[machine], 0, processNumber[machine], 1, 1);
					processNumber[machine][0]=appearTime[product];
				}else {
					/*
					 * �����жϵ�ǰ�����ʱ���뵱ǰ���������е����һ�������Ľ�ֹʱ���С��
					 * ѡ��ϴ����һ��ʱ����Ϊ�õ�����Ŀ�ʼʱ�䣬��ֹʱ��Ϊ����Ŀ�ʼʱ����Ϲ�������ʱ��
					 */
					startTime=(productTime[product]>gantt[machine][1])?productTime[product]:gantt[machine][1];
					endTime=startTime+time;
					gantt[machine][2]=startTime;
					gantt[machine][3]=endTime;
					productTime[product]=endTime;
					
					productNumber[machine][1]=product+1;
					processNumber[machine][1]=appearTime[product];
					
					
						
				}
			}else {//����û����������˶������
				/*
				 * �ж��Ƿ���Խ�������뵽�û������еĹ���֮�䣬����ܹ�����Ͳ��룬������ܣ���ֱ�������һλ�����
				 */
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * ѡ��ķ����������һλ��ǰ���ƣ�����ܹ���������flagΪtrue�����浱ǰλ�ã�������ǰ����
				 * ֱ���Ƶ��ù�����ǰһ���Ĺ����ֹʱ��
				 */
				int j=gantt[machine].length-4;
				/*
				 * ��ʱgantt[machine][j]=��ĩβ�Ĺ������ʼʱ�䣬gantt[machine][j+1]=��ĩβ�Ĺ���Ľ���ʱ��
				 */
				int position=-1;
				for(;j>=0&&startTime<gantt[machine][j];j-=2) {
					/*
					 * ���������j����һֱ���Ƶ����ʱ��gantt[machine][j-1]�ᱨָ������������Χ�쳣��˱����ų�
					 */
					if(j!=0 &&(gantt[machine][j]-time>=((gantt[machine][j-1]>startTime)?gantt[machine][j-1]:startTime))
						||
						j==0 &&(gantt[machine][j]-time>=startTime)
							) {
						position=j;
					}
				}	
				if(position!=-1) {
					try {
						System.arraycopy(gantt[machine], position, gantt[machine], position+2, gantt[machine].length-position-2);
						System.arraycopy(productNumber[machine], position/2, productNumber[machine], position/2+1, productNumber[machine].length-position/2-1);
						System.arraycopy(processNumber[machine], position/2, processNumber[machine], position/2+1, processNumber[machine].length-position/2-1);
					}catch(Exception e){
						System.out.println("ganttNow:");
						for(int k=0;k<gantt.length;k++) {
							System.out.println(Arrays.toString(gantt[k]));
						}
						System.out.println("machine:"+machine);
						System.out.println("position:"+position);
						throw new RuntimeException(e);
					}
					/*
					 * ͬ�ϣ����position����Ľ�Ϊ0���򲻴���֮ǰ��һ������Ľ�ֹʱ�䣬��˱��뿼�ǵ�positionΪ0�����
					 */
					if(position !=0) {
						gantt[machine][position]=(gantt[machine][position-1]>startTime)?gantt[machine][position-1]:startTime;
						
					}else {
						gantt[machine][position]=startTime;
						
					}
					productNumber[machine][position/2]=product+1;
					processNumber[machine][position/2]=appearTime[product];
					
					
					gantt[machine][position+1]=gantt[machine][position]+time;
					productTime[product]=gantt[machine][position+1];
					
				}else {
					gantt[machine][gantt[machine].length-2]=(startTime>gantt[machine][gantt[machine].length-3])?startTime:gantt[machine][gantt[machine].length-3];
					gantt[machine][gantt[machine].length-1]=gantt[machine][gantt[machine].length-2]+time;
					productTime[product]=gantt[machine][gantt[machine].length-1];
					
					productNumber[machine][productNumber[machine].length-1]=product+1;
					processNumber[machine][processNumber[machine].length-1]=appearTime[product];
				}	
			}
			productArray[product]+=1;	//����ż�һ
			
			
			
			
			
			
			
		}
		
		
		DrawGantt DG=new DrawGantt();
		DG.setGantt(gantt);
		DG.setProducNumbert(productNumber);
		DG.setProcessNumber(processNumber);
		
		return DG;
	}
	

	
	/**
	 * �ú�����������һ��Ⱦɫ�弯���е����Ⱦɫ�壬ע���Ⱦɫ��û��ʹ��clone����������������ǳ��¡��֮���bestChromosome�Ĳ���
	 * ����Ӱ�쵽ԭ��colony�е�Ԫ�ء���Ҫ����ICA�ĵ۹���ֳ��ؼ���ж��뽻����
	 * ���벻Ӱ�죬�ڵ���֮��ʹ��.clone()������ʹ�û�õ����Ⱦɫ����ԭȾɫ�弯��û�й�ϵ��
	 * @param colony Ⱦɫ��ļ���
	 * @return	Ⱦɫ�弯���е����Ⱦɫ�壬ע��û��ʹ�����¡�ķ���
	 */
	public static Chromosome getBestChromosome(Chromosome[] colony){
		Chromosome bestChromosome=colony[0];
		int value=Kit.objectFunctionValue(bestChromosome);
		for(int i=1;i<colony.length;i++){
			Chromosome tempChro=colony[i];
			if(Kit.objectFunctionValue(tempChro)<value){
				bestChromosome=tempChro;
				value=Kit.objectFunctionValue(tempChro);
			}
		}
		return bestChromosome;
	}
	

}