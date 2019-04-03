package cn.nuaa;

import java.util.Arrays;
import java.util.Random;

public class Population {
	private Chromosome[] pops;
	private int capacity;	//种群大小
	
	public Population(int capacity,double a1,double a2,double a3){
		this.capacity=capacity;
		int num1=(int)(capacity*a1);
		int num2=(int)(capacity*a2);
		int num3=capacity-num1-num2;
		pops=new Chromosome[capacity];
		for(int i=0;i<capacity;i++){
			if(i<num1){
				pops[i]=new Chromosome("GS");
			}else if(i<num1+num2){
				pops[i]=new Chromosome("LS");
			}else{
				pops[i]=new Chromosome("RS");
			}
		}
		//打乱
		for(int i=0;i<capacity;i++){
			Random rand=new Random();
			int temp=rand.nextInt(capacity);
			Chromosome tempChro=pops[i].clone();
			pops[i]=pops[temp].clone();
			pops[temp]=tempChro;
		}
		
	}
	
	public void crossover(double pc){
		Chromosome[] newPop=new Chromosome[capacity];
		for(int i=0;i<capacity;i+=2){
			Random rand=new Random();
			if(rand.nextDouble()<pc){
				int temp1=rand.nextInt(capacity);
				int temp2=rand.nextInt(capacity);
				Chromosome[] youngerChro=Kit.getYoungchros(pops[temp1], pops[temp2]);
				newPop[i]=youngerChro[0];
				newPop[i+1]=youngerChro[1];
			}else{
				newPop[i]=pops[i];
				newPop[i+1]=pops[i+1];
			}
			
		}
		pops=newPop;
	}
	
	public void selection(){
		Chromosome[] newpops=new Chromosome[capacity];
		for(int i=0;i<newpops.length;i++){
			Random rand=new Random();
			int temp1=rand.nextInt(pops.length);
			int temp2=rand.nextInt(pops.length);
			newpops[i]=(Kit.objectFunctionValue(pops[temp1])<Kit.objectFunctionValue(pops[temp2]))?pops[temp1].clone():pops[temp2].clone();
		}
		pops=newpops;
	}
	
	public void mutation(double pm){
		for(int i=0;i<pops.length;i++){
			Random rand=new Random();
			if(rand.nextDouble()<pm){
				pops[i]=Kit.mutationChromosomeMS(pops[i]);
				pops[i]=Kit.mutationChromosomeOS(pops[i]);
			}
		}
	}
	
	public Chromosome getBestChromosome(){
		return Kit.getBestChromosome(pops);
	}

	@Override
	public String toString() {
		String str="";
		for(int i=0;i<pops.length;i++){
			str+=Kit.objectFunctionValue(pops[i])+",";
		}
		return str;
	}
	
	
}
