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
	private final int WIDTH=20;//��ͼʱ�ĵ�λ���
	private final int HEIGHT=30;//��ͼʱ�ĵ�λ�߶�
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
//		frame.add(gantt);//�������ӵ�������
//		
//		
//		
//		frame.setSize(700, 500);
//		frame.setAlwaysOnTop(true);	//����һֱ��������
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����Ĭ�Ϲرղ������رմ���ʱ�رղ�����
//		frame.setLocationRelativeTo(null);//���ô��ھ�����ʾ
//		frame.setVisible(true);		//1.���ô��ڿɼ�	2.�������paint()����
//	}
	
	public void draw(Chromosome chro) {
		
		JFrame frame=new JFrame("Fly");
		GanttFigure gantt=new GanttFigure(chro);
		frame.add(gantt);//�������ӵ�������
		
		
		
		frame.setSize(700, 500);
		frame.setAlwaysOnTop(true);	//����һֱ��������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����Ĭ�Ϲرղ������رմ���ʱ�رղ�����
		frame.setLocationRelativeTo(null);//���ô��ھ�����ʾ
		frame.setVisible(true);		//1.���ô��ڿɼ�	2.�������paint()����


	}

	
	
	
	
}
