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
 * 该类中保留有关使用面向对象编程的车间排序算法的相关方法
 * 这些方法，经过测试可用
 * @author Administrator
 *
 */
public class Kit {
	
	/**
	 * 生成按照全局选择的初始染色体的机器选择
	 * @return
	 */
	public static int[] GSgenerateMS() {
		int[][] matrix=RAndW.oriMatrix;
		int[] machineTime=new int[RAndW.machines];//机器时间数组
		int[] productSets=new int[RAndW.products];//工件数
		for(int i=0;i<productSets.length;i++) {
			productSets[i]=i;
		}

		int[] MS=new int[RAndW.productLength];//机器选择部分  每件工件的工序加起来之和，在Mk01中为55
		/*
		 * 从工件集中选取工件，同时选择当前工件的第一道工序
		 */
		int index=0;		//MS的索引
		while(productSets.length>0) {
//			System.out.println(Arrays.toString(productSets));
			index=0;
			//选择一个工件
			Random rand=new Random();
			int productIn=rand.nextInt(productSets.length);
			/*
			 * 当选择完一个工件后，将该工件从工件集中删去
			 */
//			System.out.println(productIn);
			int pro=productSets[productIn];
			productSets[productIn]=productSets[productSets.length-1];
			productSets[productSets.length-1]=pro;
			productSets=Arrays.copyOf(productSets, productSets.length-1);
			/*
			 * 在选择完工件后，索引在MS中跳到该工件所属的位置
			 */
			for(int k=0;k<pro;k++) {
				index+=matrix[k][0];
			}
			
			for(int j=1;j<matrix[pro].length;) {
				int q=matrix[pro][j];
				int index_min=find_index_min(j,matrix[pro],machineTime);	
				/*
				 * 选择最好的机器所对应的位置
				 */
				MS[index]=matrix[pro][index_min];
				/*
				 * 将机器时间数组更新 
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
	 * 生成按照局部选择的初始染色体的机器选择
	 * @return
	 */
	public static int[] LSgenerateMS() {
		int[][] matrix=RAndW.oriMatrix;
		int[] machineTime=new int[RAndW.machines];//机器时间数组
		int[] productSets=new int[RAndW.products];//工件集
		for(int i=0;i<productSets.length;i++) {
			productSets[i]=i;
		}
//		System.out.println("长度为："+r.getproductLength());
		int[] MS=new int[RAndW.productLength];//机器选择部分 在Mk01中为55
		/*
		 * 从工件集中选取工件，同时选择当前工件的第一道工序
		 */
		int index=0;		//MS的索引
		while(productSets.length>0) {
//			System.out.println(Arrays.toString(productSets));
			index=0;
			//选择一个工件
			Random rand=new Random();
			int productIn=rand.nextInt(productSets.length);
			/*
			 * 当选择完一个工件后，将该工件从工件集中删去
			 */
//			System.out.println(productIn);
			int pro=productSets[productIn];
			productSets[productIn]=productSets[productSets.length-1];
			productSets[productSets.length-1]=pro;
			productSets=Arrays.copyOf(productSets, productSets.length-1);
			/*
			 * 在选择完工件后，索引在MS中跳到该工件所属的位置
			 */
			for(int k=0;k<pro;k++) {
				index+=matrix[k][0];
			}
			
			for(int j=1;j<matrix[pro].length;) {
				int q=matrix[pro][j];
				int index_min=find_index_min(j,matrix[pro],machineTime);	
				/*
				 * 选择最好的机器所对应的位置
				 */
				MS[index]=matrix[pro][index_min];
				/*
				 * 将机器时间数组更新 
				 */
//				System.out.println(matrix[pro][index_min]-1);
				machineTime[matrix[pro][index_min]-1]+=matrix[pro][index_min+1];
//				System.out.println("machineTime:"+Arrays.toString(machineTime));
				index++;
				j+=2*matrix[pro][j]+1;

			}
//			System.out.println(Arrays.toString(MS));
			/*
			 * 与全局选择不同的是,其在每一次为另一个零件选择时,机器时间数组要重新归零
			 */
			machineTime=new int[RAndW.machines];
		}

		return MS;
	}
	
	/**
	 * 生成按照随机选择的初始染色体的机器选择
	 * @return
	 */

	public static int[] RSgenerateMS() {
		int[] MS=new int[RAndW.productLength];	//机器选择部分
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
	 * 随机生成初始染色体的工序选择
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
		 * 对OS中的元素随机打乱
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
	 * 获取染色体的机器循序矩阵
	 * @param chro 染色体
	 * @return
	 */
	public static int[][] getJm(Chromosome chro){
		int[] MS=chro.getMS();
		int[][] oriMatrix=RAndW.oriMatrix;
		int[][] Jm;
		int[] products=new int[RAndW.products];//保存的是每台工件的工序数
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
	 * 获取染色体的时间顺序矩阵
	 * @param chro 染色体
	 * @param Jm 要想获得时间矩阵，必须先获得机器顺序矩阵
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
				 * 要获得时间顺序矩阵是按照机器矩阵的每一个值来获得的
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
					System.out.println("有问题,此时index为"+index+",此时k为：");
					System.out.println(chro);
				}
				index+=number*2+1;

			}
		}
		return T;
	}
	
	/**
	 * 该函数用于找到一个工件中的某道工序在机器时间数组中最小时间
	 * @param j
	 * @param singleMatrix
	 * @return
	 */
	public static int find_index_min(int j,int[] singleMatrix,int[]MachineTime) {
		/*
		 * 由于方法和方法之间是并列关系，所以必须要加static修饰，但是这样会导致参数（MachinTime）变成静态变量，
		 * 为了解决这个问题，只能再新建一个不同的数组，然后对数组进行修饰。
		 */
		int[] machineTime=new int[MachineTime.length];
		machineTime=Arrays.copyOf(MachineTime, MachineTime.length);
		//System.out.println("j:"+j+",machineTime"+Arrays.toString(machineTime));
		int index_min=j+1;		//返回选择的机器所在singleMatrix中的位置  j=2
		for(int s=0;s<singleMatrix[j];s++) {
			machineTime[singleMatrix[j+s*2+1]-1]+=singleMatrix[j+s*2+2];
			if(machineTime[singleMatrix[j+s*2+1]-1]<machineTime[singleMatrix[index_min]-1]) {
				index_min=j+s*2+1;
			}
		}
		return index_min;
	}	


	/**
	 * 生成主动调度，返回由机器以及工序所组成的Gantt图
	 * @param chro 染色体
	 * @param Jm 机器顺序矩阵
	 * @param T	时间顺序矩阵
	 * @return
	 */
	public static int[][] decode(Chromosome chro,int[][] Jm,int[][] T){
		int[] OS=chro.getOS();
		int[][] gantt=new int[RAndW.machines][0];
		int[] productTime=new int[RAndW.products];//当前时刻各工件的结束时间
		int[] productArray=new int[RAndW.products];//当前时刻各工件的工序号
		
		for(int i=0;i<OS.length;i++) {
			int product=OS[i]-1;	//工件号,从0开始

			int machine=Jm[product][productArray[product]]-1;	//机器号，工件号从0开始,工序号也从0开始,机器号也应该从0开始
			int time=T[product][productArray[product]];	//对应工件工序的时间
			/*
			 * 确认好机器后，对甘特图中对应的机器号进行扩容
			 */
			gantt[machine]=Arrays.copyOf(gantt[machine], gantt[machine].length+2);
			
			if(gantt[machine].length==2) {//如果当前机器上没有运行工件，则该工序的开始时间为工件的前一步的截止时间
				gantt[machine][0]=productTime[product];
				gantt[machine][1]=time+productTime[product];
				productTime[product]=time+productTime[product];//每次插完工序块后，相应的工件对应的截止时间也要变化
				
				
				
			}else if(gantt[machine].length==4){//如果该机器上仅运行了一个工件
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * 如果能把时间插入到某个时间块之前就把他插入到某个时间块之前
				 */
				if(endTime<=gantt[machine][0]) {
					System.arraycopy(gantt[machine], 0, gantt[machine], 2, 2);
					gantt[machine][0]=startTime;
					gantt[machine][1]=endTime;
					productTime[product]=endTime;
					
					
				}else {
					/*
					 * 否则判断当前工序的时间与当前机器上运行的最后一个机器的截止时间大小，
					 * 选择较大的那一个时间作为该到工序的开始时间，截止时间为工序的开始时间加上工序运行时间
					 */
					startTime=(productTime[product]>gantt[machine][1])?productTime[product]:gantt[machine][1];
					endTime=startTime+time;
					gantt[machine][2]=startTime;
					gantt[machine][3]=endTime;
					productTime[product]=endTime;
					
					
					
					
						
				}
			}else {//如果该机器上运行了多个工件
				/*
				 * 判断是否可以将工序插入到该机器运行的工件之间，如果能够插入就插入，如果不能，则直接在最后一位工序块
				 */
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * 选择的方法，从最后一位先前递推，如果能够插入设置flag为true，保存当前位置，继续向前递推
				 * 直到推到该工件的前一步的工序截止时间
				 */
				int j=gantt[machine].length-4;
				/*
				 * 此时gantt[machine][j]=最末尾的工序的起始时间，gantt[machine][j+1]=最末尾的工序的结束时间
				 */
				int position=-1;
				for(;j>=0&&startTime<gantt[machine][j];j-=2) {
					/*
					 * 这种情况下j可能一直递推到零此时，gantt[machine][j-1]会报指针索引超出范围异常因此必须排除
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
					 * 同上，如果position求出的解为0，则不存在之前的一个工序的截止时间，因此必须考虑到position为0的情况
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
			productArray[product]+=1;	//工序号加一
			
			
			
			
			
			
			
		}
		return gantt;
	}

	/**
	 * 生成主动调度，返回由机器及工序组成的Gantt图，内部处理自动生成 机器顺序矩阵和实践顺序矩阵，无需额外生成
	 * @param chro 染色体
	 * @return
	 */
	public static int[][] decode(Chromosome chro){
		int[][] Jm=getJm(chro);
		int[][] T=getT(chro,Jm);
		return decode(chro,Jm,T);
	}

	/**
	 * 获得该染色体的目标函数值，实际上也是通过gantt图来获得的
	 * @param chro 染色体
	 * @return	该染色体的目标函数值
	 */
	public static int objectFunctionValue(Chromosome chro) {
		int[][] gantt=decode(chro);
		int maxvalue=0;
		for(int i=0;i<gantt.length;i++) {
			if(gantt[i].length>0) {//由于有可能存在一台机器上没有加工过任何零件，因此必须加以判断
				if(maxvalue<gantt[i][gantt[i].length-1]) {
					maxvalue=gantt[i][gantt[i].length-1];
				}
			}
		}
		return maxvalue;
	}

	/**
	 * 比较染色体1和染色体2的目标函数值，有r的概率返回一个目标函数值较大的染色体
	 * 有1-r的概率返回一个目标函数值较小的染色体
	 * @param chro1	染色体1
	 * @param chro2	染色体2
	 * @param r	概率值
	 * @return	返回一个染色体
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
	 * 将两个染色体进行交叉操作，并返回父染色体较优解和子染色体较优解。
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
	 * 将两个染色体进行交叉操作，并返回子染色体。
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
	 * 针对染色体的机器排序部分进行交叉操作，并返回交叉后的机器排序部分
	 * @param MS1	染色体1的机器排序部分
	 * @param MS2	染色体2的机器排序部分
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
	 * 针对染色体的工序排序部分进行交叉操作，并返回交叉后的工序排序部分
	 * @param OS1	染色体1的工序排序部分
	 * @param OS2	染色体2的工序排序部分
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
	 * 针对染色体进行工序部分的变异，并返回变异后的染色体
	 * 扩展的插入变异操作，即在基于工序编码的染色体中随机选择一个工件的工序(基因)，将它插入到一个随机的位置，
	 * 保持所有工件工序分配机器的不变。
	 * @param chro
	 * @return
	 */
	public static Chromosome mutationChromosomeOS(Chromosome chro) {
		int[] MS=chro.clone().getMS();
		int[] OS=chro.clone().getOS();
		Random rand=new Random();
		int index1=rand.nextInt(OS.length);//取出index1处的值，并缩容

		int num=OS[index1];
		if(index1!=OS.length-1) {
			System.arraycopy(OS, index1+1, OS, index1, OS.length-index1-1);
		}
		OS=Arrays.copyOf(OS, OS.length-1);
		int index2=rand.nextInt(OS.length+1);//获取要放入的位置，index2，扩容，放入,有可能直接放入最后一个位置
		OS=Arrays.copyOf(OS, OS.length+1);
		if(index2!=OS.length-1) {
			System.arraycopy(OS, index2, OS, index2+1, OS.length-index2-1);
		}
		OS[index2]=num;
		return new Chromosome(MS,OS);
			
	}
	
	/**
	 * 针对染色体进行机器选择部分的变异，并返回变异后的染色体
	 * 具体原理为：随机选择基于机器编码染色体的一个基因，在工序的加工机器集中随机选取机器代替该基因。
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

		if(nums!=1){//当只有nums=1时意味着该工序只能由一台机器加工，无法换
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
	 * 生成甘特图中包含对应的工件列表、工序列表、以及甘特图的信息
	 * @param chro 所要生成的染色体
	 */
	public static DrawGantt drawingGantt(Chromosome chro) {
		int[][] Jm=getJm(chro);
		int[][] T=getT(chro,Jm);
		int[] OS=chro.getOS();
		int[][] gantt=new int[RAndW.machines][0];
		int[][] productNumber=new int[RAndW.machines][0];//甘特图中各元素的工件号
		int[][] processNumber=new int[RAndW.machines][0];//甘特图中各元素的工序号
		int[] productTime=new int[RAndW.products];//当前时刻各工件的结束时间
		int[] productArray=new int[RAndW.products];//当前时刻各工件的工序号
		int[] appearTime=new int[RAndW.products];//工件出现的次数
		
		
		for(int i=0;i<OS.length;i++) {
			int product=OS[i]-1;	//工件号,从0开始
			appearTime[product]+=1;
			int machine=Jm[product][productArray[product]]-1;	//机器号，工件号从0开始,工序号也从0开始,机器号也应该从0开始
			int time=T[product][productArray[product]];	//对应工件工序的时间
			/*
			 * 确认好机器后，对甘特图中对应的机器号进行扩容
			 */
			gantt[machine]=Arrays.copyOf(gantt[machine], gantt[machine].length+2);
			productNumber[machine]=Arrays.copyOf(productNumber[machine], productNumber[machine].length+1);
			processNumber[machine]=Arrays.copyOf(processNumber[machine], processNumber[machine].length+1);
			if(gantt[machine].length==2) {//如果当前机器上没有运行工件，则该工序的开始时间为工件的前一步的截止时间
				gantt[machine][0]=productTime[product];
				gantt[machine][1]=time+productTime[product];
				productTime[product]=time+productTime[product];//每次插完工序块后，相应的工件对应的截止时间也要变化
				
				productNumber[machine][0]=product+1;
				processNumber[machine][0]=appearTime[product];
				
				
			}else if(gantt[machine].length==4){//如果该机器上仅运行了一个工件
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * 如果能把时间插入到某个时间块之前就把他插入到某个时间块之前
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
					 * 否则判断当前工序的时间与当前机器上运行的最后一个机器的截止时间大小，
					 * 选择较大的那一个时间作为该到工序的开始时间，截止时间为工序的开始时间加上工序运行时间
					 */
					startTime=(productTime[product]>gantt[machine][1])?productTime[product]:gantt[machine][1];
					endTime=startTime+time;
					gantt[machine][2]=startTime;
					gantt[machine][3]=endTime;
					productTime[product]=endTime;
					
					productNumber[machine][1]=product+1;
					processNumber[machine][1]=appearTime[product];
					
					
						
				}
			}else {//如果该机器上运行了多个工件
				/*
				 * 判断是否可以将工序插入到该机器运行的工件之间，如果能够插入就插入，如果不能，则直接在最后一位工序块
				 */
				int startTime=productTime[product];
				int endTime=startTime+time;
				/*
				 * 选择的方法，从最后一位先前递推，如果能够插入设置flag为true，保存当前位置，继续向前递推
				 * 直到推到该工件的前一步的工序截止时间
				 */
				int j=gantt[machine].length-4;
				/*
				 * 此时gantt[machine][j]=最末尾的工序的起始时间，gantt[machine][j+1]=最末尾的工序的结束时间
				 */
				int position=-1;
				for(;j>=0&&startTime<gantt[machine][j];j-=2) {
					/*
					 * 这种情况下j可能一直递推到零此时，gantt[machine][j-1]会报指针索引超出范围异常因此必须排除
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
					 * 同上，如果position求出的解为0，则不存在之前的一个工序的截止时间，因此必须考虑到position为0的情况
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
			productArray[product]+=1;	//工序号加一
			
			
			
			
			
			
			
		}
		
		
		DrawGantt DG=new DrawGantt();
		DG.setGantt(gantt);
		DG.setProducNumbert(productNumber);
		DG.setProcessNumber(processNumber);
		
		return DG;
	}
	

	
	/**
	 * 该函数用来返回一个染色体集合中的最佳染色体，注意该染色体没有使用clone方法，即在这里是浅克隆，之后对bestChromosome的操作
	 * 都会影响到原本colony中的元素。主要用于ICA的帝国和殖民地间的判断与交换。
	 * 若想不影响，在调用之后，使用.clone()方法，使得获得的最佳染色体与原染色体集合没有关系。
	 * @param colony 染色体的集合
	 * @return	染色体集合中的最佳染色体，注意没有使用深克隆的方法
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