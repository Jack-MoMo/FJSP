package cn.nuaa;

import java.util.Arrays;

public class DrawGantt {
	private int[][] gantt;//甘特图中各元素的长度
	private int[][] producNumber;	//甘特图中各元素的工件号
	private int[][] processNumber;//甘特图中个元素的工序号
	public int[][] getGantt() {
		return gantt;
	}
	public void setGantt(int[][] gantt) {
		this.gantt = gantt;
	}
	public int[][] getProducNumber() {
		return producNumber;
	}
	public void setProducNumbert(int[][] producNumber) {
		this.producNumber = producNumber;
	}
	public int[][] getProcessNumber() {
		return processNumber;
	}
	public void setProcessNumber(int[][] processNumber) {
		this.processNumber = processNumber;
	}
	@Override
	public String toString() {
		String str1="";
		for(int i=0;i<gantt.length;i++) {
			str1+=Arrays.toString(gantt[i])+"\n";
		}
		String str2="";
		for(int i=0;i<gantt.length;i++) {
			str2+=Arrays.toString(producNumber[i])+"\n";
		}
		String str3="";
		for(int i=0;i<gantt.length;i++) {
			str3+=Arrays.toString(processNumber[i])+"\n";
		}
		return "DrawGantt [gantt=" + str1 +"\n"+ ", producNumber=" + str2+"\n"
				+ ", processNumber=" + str3 + "]";
	}
	
//	public static int[][] decode(Chromosome chro,int[][] Jm,int[][] T){
//		int[] OS=chro.getOS();
//		int[][] gantt=new int[RAndW.machines][0];
//		int[] productTime=new int[RAndW.products];//当前时刻各工件的结束时间
//		int[] productArray=new int[RAndW.products];//当前时刻各工件的工序号
//		
//		for(int i=0;i<OS.length;i++) {
//			int product=OS[i]-1;	//工件号,从0开始
//			int machine=Jm[product][productArray[product]]-1;	//机器号，工件号从0开始,工序号也从0开始,机器号也应该从0开始
//			int time=T[product][productArray[product]];	//对应工件工序的时间
//			/*
//			 * 确认好机器后，对甘特图中对应的机器号进行扩容
//			 */
//			gantt[machine]=Arrays.copyOf(gantt[machine], gantt[machine].length+2);
//			if(gantt[machine].length==2) {//如果当前机器上没有运行工件，则该工序的开始时间为工件的前一步的截止时间
//				gantt[machine][0]=productTime[product];
//				gantt[machine][1]=time+productTime[product];
//				productTime[product]=time+productTime[product];//每次插完工序块后，相应的工件对应的截止时间也要变化
//			}else if(gantt[machine].length==4){//如果该机器上仅运行了一个工件
//				int startTime=productTime[product];
//				int endTime=startTime+time;
//				/*
//				 * 如果能把时间插入到某个时间块之前就把他插入到某个时间块之前
//				 */
//				if(endTime<=gantt[machine][0]) {
//					System.arraycopy(gantt[machine], 0, gantt[machine], 2, 2);
//					gantt[machine][0]=startTime;
//					gantt[machine][1]=endTime;
//					productTime[product]=endTime;
//				}else {
//					/*
//					 * 否则判断当前工序的时间与当前机器上运行的最后一个机器的截止时间大小，
//					 * 选择较大的那一个时间作为该到工序的开始时间，截止时间为工序的开始时间加上工序运行时间
//					 */
//					startTime=(productTime[product]>gantt[machine][1])?productTime[product]:gantt[machine][1];
//					endTime=startTime+time;
//					gantt[machine][2]=startTime;
//					gantt[machine][3]=endTime;
//					productTime[product]=endTime;
//				}
//			}else {//如果该机器上运行了多个工件
//				/*
//				 * 判断是否可以将工序插入到该机器运行的工件之间，如果能够插入就插入，如果不能，则直接在最后一位工序块
//				 */
//				int startTime=productTime[product];
//				int endTime=startTime+time;
//				/*
//				 * 选择的方法，从最后一位先前递推，如果能够插入设置flag为true，保存当前位置，继续向前递推
//				 * 直到推到该工件的前一步的工序截止时间
//				 */
//				int j=gantt[machine].length-4;
//				/*
//				 * 此时gantt[machine][j]=最末尾的工序的起始时间，gantt[machine][j+1]=最末尾的工序的结束时间
//				 */
//				int position=-1;
//				for(;j>=0&&startTime<gantt[machine][j];j-=2) {
//					/*
//					 * 这种情况下j可能一直递推到零此时，gantt[machine][j-1]会报指针索引超出范围异常因此必须排除
//					 */
//					if(j!=0 &&(gantt[machine][j]-time>=((gantt[machine][j-1]>startTime)?gantt[machine][j-1]:startTime))
//						||
//						j==0 &&(gantt[machine][j]-time>=startTime)
//							) {
//						position=j;
//					}
//				}	
//				if(position!=-1) {
//					try {
//						System.arraycopy(gantt[machine], position, gantt[machine], position+2, gantt[machine].length-position-2);
//					}catch(Exception e){
//						System.out.println("ganttNow:");
//						for(int k=0;k<gantt.length;k++) {
//							System.out.println(Arrays.toString(gantt[k]));
//						}
//						System.out.println("machine:"+machine);
//						System.out.println("position:"+position);
//						throw new RuntimeException(e);
//					}
//					/*
//					 * 同上，如果position求出的解为0，则不存在之前的一个工序的截止时间，因此必须考虑到position为0的情况
//					 */
//					if(position !=0) {
//						gantt[machine][position]=(gantt[machine][position-1]>startTime)?gantt[machine][position-1]:startTime;
//					}else {
//						gantt[machine][position]=startTime;
//					}
//					
//					gantt[machine][position+1]=gantt[machine][position]+time;
//					productTime[product]=gantt[machine][position+1];
//					
//				}else {
//					gantt[machine][gantt[machine].length-2]=(startTime>gantt[machine][gantt[machine].length-3])?startTime:gantt[machine][gantt[machine].length-3];
//					gantt[machine][gantt[machine].length-1]=gantt[machine][gantt[machine].length-2]+time;
//					productTime[product]=endTime;
//				}	
//			}
//			productArray[product]+=1;	//工序号加一
//		}
//		return gantt;
//	}
}
