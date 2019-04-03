package cn.nuaa;

public class FJSPStart {
	public static void main(String[] args) {
		String[] example=new String[]{"MK01.fjs","MK02.fjs","MK03.fjs","MK04.fjs","MK05.fjs","MK06.fjs","MK07.fjs",
				"MK08.fjs","MK09.fjs","MK10.fjs",};
		RAndW r=new RAndW(example[3]);
		int maxCircle=200;
		int capacity=100;
		double pm=0.01;
		double pc=0.8;
		Population p=new Population(capacity,0.6,0.3,0.1);
		for(int i=0;i<maxCircle;i++){
			System.out.println(p);
			p.crossover(pc);
			p.mutation(pm);
			p.selection();
			System.out.println("第"+i+"次循环的值："+Kit.objectFunctionValue(p.getBestChromosome()));
		}
		Chromosome best=p.getBestChromosome();
		GanttFigure gf=new GanttFigure(best);
		gf.draw(best);
	}
	
	
}
