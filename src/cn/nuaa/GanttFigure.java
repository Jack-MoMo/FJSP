package cn.nuaa;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GanttFigure extends JPanel{
	private static Chromosome chro;
//	private static DisjunctiveForm1 form;
	private final int WIDTH=20;//绘图时的单位宽度
	private final int HEIGHT=30;//绘图时的单位高度
	public static BufferedImage background;
	static {
		try {
		background=ImageIO.read(GanttFigure.class.getResource("background.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		int[][] gantt;
		int[][] productNumber;
		int[][] processNumber;
		int len;

			DrawGantt DG=Kit.drawingGantt(chro);
			gantt=DG.getGantt();
			productNumber=DG.getProducNumber();
			processNumber=DG.getProcessNumber();
			len=(int)Kit.objectFunctionValue(chro);
		
		
		g.drawImage(background, 0, 0, null);
		int avglen=20*50/len;
		for(int i=0;i<gantt.length;i++) {
			for(int j=0;j<gantt[i].length/2;j++) {
				g.drawRect(gantt[i][2*j]*avglen+80, (i+1)*40, (gantt[i][2*j+1]-gantt[i][2*j])*avglen, 30);
				g.drawString(productNumber[i][j]+"."+processNumber[i][j], gantt[i][2*j]*avglen+80, (i+1)*40+20);
				g.drawRect(5, (i+1)*40, 40, 30);
				g.drawString("M:"+i, 7, (i+1)*40+20);
			}
		}
		int height=10;
		g.drawLine(0, height,len*avglen+190+30 , height);
		for(int i=0;i<len+1;i++) {
			g.drawLine(80+avglen*i, height-5, 80+avglen*i, height);
			if(i%5==0) {
				g.drawString(""+i, 80+avglen*i, height+10);
			}
		
		}
		
	}
	public GanttFigure(Chromosome chro) {
		this.chro=chro;
	}
//	public GanttFigure(DisjunctiveForm1 form){
//		this.chro=null;
//		this.form=form;
//	}
	
//	public void draw(DisjunctiveForm1 form){
//		JFrame frame=new JFrame("Fly");
//		GanttFigure gantt=new GanttFigure(form);
//		frame.add(gantt);//将面板添加到窗口上
//		
//		
//		
//		frame.setSize(700, 500);
//		frame.setAlwaysOnTop(true);	//设置一直在最上面
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置默认关闭操作（关闭窗口时关闭操作）
//		frame.setLocationRelativeTo(null);//设置窗口居中显示
//		frame.setVisible(true);		//1.设置窗口可见	2.尽快调用paint()方法
//	}
	
	public void draw(Chromosome chro) {
		
		JFrame frame=new JFrame("Fly");
		GanttFigure gantt=new GanttFigure(chro);
		frame.add(gantt);//将面板添加到窗口上
		
		
		
		frame.setSize(700, 500);
		frame.setAlwaysOnTop(true);	//设置一直在最上面
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置默认关闭操作（关闭窗口时关闭操作）
		frame.setLocationRelativeTo(null);//设置窗口居中显示
		frame.setVisible(true);		//1.设置窗口可见	2.尽快调用paint()方法


	}

	
	
	
	
}
