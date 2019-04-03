package cn.nuaa;

import java.util.Arrays;

public class DrawGantt {
	private int[][] gantt;//����ͼ�и�Ԫ�صĳ���
	private int[][] producNumber;	//����ͼ�и�Ԫ�صĹ�����
	private int[][] processNumber;//����ͼ�и�Ԫ�صĹ����
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
//		int[] productTime=new int[RAndW.products];//��ǰʱ�̸������Ľ���ʱ��
//		int[] productArray=new int[RAndW.products];//��ǰʱ�̸������Ĺ����
//		
//		for(int i=0;i<OS.length;i++) {
//			int product=OS[i]-1;	//������,��0��ʼ
//			int machine=Jm[product][productArray[product]]-1;	//�����ţ������Ŵ�0��ʼ,�����Ҳ��0��ʼ,������ҲӦ�ô�0��ʼ
//			int time=T[product][productArray[product]];	//��Ӧ���������ʱ��
//			/*
//			 * ȷ�Ϻû����󣬶Ը���ͼ�ж�Ӧ�Ļ����Ž�������
//			 */
//			gantt[machine]=Arrays.copyOf(gantt[machine], gantt[machine].length+2);
//			if(gantt[machine].length==2) {//�����ǰ������û�����й�������ù���Ŀ�ʼʱ��Ϊ������ǰһ���Ľ�ֹʱ��
//				gantt[machine][0]=productTime[product];
//				gantt[machine][1]=time+productTime[product];
//				productTime[product]=time+productTime[product];//ÿ�β��깤������Ӧ�Ĺ�����Ӧ�Ľ�ֹʱ��ҲҪ�仯
//			}else if(gantt[machine].length==4){//����û����Ͻ�������һ������
//				int startTime=productTime[product];
//				int endTime=startTime+time;
//				/*
//				 * ����ܰ�ʱ����뵽ĳ��ʱ���֮ǰ�Ͱ������뵽ĳ��ʱ���֮ǰ
//				 */
//				if(endTime<=gantt[machine][0]) {
//					System.arraycopy(gantt[machine], 0, gantt[machine], 2, 2);
//					gantt[machine][0]=startTime;
//					gantt[machine][1]=endTime;
//					productTime[product]=endTime;
//				}else {
//					/*
//					 * �����жϵ�ǰ�����ʱ���뵱ǰ���������е����һ�������Ľ�ֹʱ���С��
//					 * ѡ��ϴ����һ��ʱ����Ϊ�õ�����Ŀ�ʼʱ�䣬��ֹʱ��Ϊ����Ŀ�ʼʱ����Ϲ�������ʱ��
//					 */
//					startTime=(productTime[product]>gantt[machine][1])?productTime[product]:gantt[machine][1];
//					endTime=startTime+time;
//					gantt[machine][2]=startTime;
//					gantt[machine][3]=endTime;
//					productTime[product]=endTime;
//				}
//			}else {//����û����������˶������
//				/*
//				 * �ж��Ƿ���Խ�������뵽�û������еĹ���֮�䣬����ܹ�����Ͳ��룬������ܣ���ֱ�������һλ�����
//				 */
//				int startTime=productTime[product];
//				int endTime=startTime+time;
//				/*
//				 * ѡ��ķ����������һλ��ǰ���ƣ�����ܹ���������flagΪtrue�����浱ǰλ�ã�������ǰ����
//				 * ֱ���Ƶ��ù�����ǰһ���Ĺ����ֹʱ��
//				 */
//				int j=gantt[machine].length-4;
//				/*
//				 * ��ʱgantt[machine][j]=��ĩβ�Ĺ������ʼʱ�䣬gantt[machine][j+1]=��ĩβ�Ĺ���Ľ���ʱ��
//				 */
//				int position=-1;
//				for(;j>=0&&startTime<gantt[machine][j];j-=2) {
//					/*
//					 * ���������j����һֱ���Ƶ����ʱ��gantt[machine][j-1]�ᱨָ������������Χ�쳣��˱����ų�
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
//					 * ͬ�ϣ����position����Ľ�Ϊ0���򲻴���֮ǰ��һ������Ľ�ֹʱ�䣬��˱��뿼�ǵ�positionΪ0�����
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
//			productArray[product]+=1;	//����ż�һ
//		}
//		return gantt;
//	}
}
