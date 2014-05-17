package com.liu.planplus.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

//���ScrollView֮����ʾ������
public class CanvasView  extends View {
	//���廭��
	private Paint paint = new Paint();
	//�õ���Ļ�Ŀ�͸�
	private WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);
	private int width = wm.getDefaultDisplay().getWidth();
	private int height = wm.getDefaultDisplay().getHeight();
	private RectF rel = new RectF(10, 200, 70, 230);
	private RectF rell = new RectF(10, 240, 70, 270);
	public CanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public CanvasView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// ��д�÷��������л�ͼ
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// �����Ż������Ƴɰ�ɫ
		//canvas.drawColor(Color.WHITE);
		
		// ȥ���
		paint.setAntiAlias(true);
		//������ɫ
		paint.setColor(Color.argb(255, 0,0,0));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		
		canvas.drawLine(100, 0, 100, 2000, paint);
//		for (int i = 1; i <= 12; i++)
//		{
//			canvas.drawLine(0, deltheight*i, width, deltheight*i, paint);
//		}
		/*
		// ����Բ��
		canvas.drawCircle(40, 40, 30, paint);
		// ����������
		canvas.drawRect(10, 80, 70, 140, paint);
		// ���ƾ���
		canvas.drawRect(10, 150, 70, 190, paint);
		// ����Բ�Ǿ���
		
		canvas.drawRoundRect(rel, 15, 15, paint);
		// ������Բ
		
		canvas.drawOval(rell, paint);

		// ����һ��Path���󣬷�ճ�һ��������
		Path path1 = new Path();
		path1.moveTo(10, 340);
		path1.lineTo(70, 340);
		path1.lineTo(40, 290);
		path1.close();
		// ����Path���л��ƣ�����������
		canvas.drawPath(path1, paint);
		// ����һ��Path���󣬷�ճ�һ�������
		Path path2 = new Path();
		path2.moveTo(26, 360);
		path2.lineTo(54, 360);
		path2.lineTo(70, 392);
		path2.lineTo(40, 420);
		path2.lineTo(10, 392);
		path2.close();
		// ����Path���л��ƣ����������
		canvas.drawPath(path2, paint);

		// -----�������������------
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.RED);
		// ����Բ��
		canvas.drawCircle(120, 40, 30, paint);
		// ����������
		canvas.drawRect(90, 80, 150, 140, paint);
		// ���ƾ���
		canvas.drawRect(90, 150, 150, 190, paint);
		// ����Բ�Ǿ���
		RectF re2 = new RectF(90, 200, 150, 230);
		canvas.drawRoundRect(re2, 15, 15, paint);
		// ������Բ
		RectF re21 = new RectF(90, 240, 150, 270);
		canvas.drawOval(re21, paint);
		// ����һ��Path���󣬷�ճ�һ��������
		Path path3 = new Path();
		path3.moveTo(90, 340);
		path3.lineTo(150, 340);
		path3.lineTo(120, 290);
		path3.close();
		// ����������
		canvas.drawPath(path3, paint);
		// ����һ��Path���󣬷�ճ�һ�������
		Path path4 = new Path();
		path4.moveTo(106, 360);
		path4.lineTo(134, 360);
		path4.lineTo(150, 392);
		path4.lineTo(120, 420);
		path4.lineTo(90, 392);
		path4.close();
		// ���������
		canvas.drawPath(path4, paint);

		// -----���ý����������--------
		// ΪPaint���ý�����
		Shader mShader = new LinearGradient(0, 0, 40, 60, new int[] {
				Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW }, null,
				Shader.TileMode.REPEAT);
		paint.setShader(mShader);
		// ������Ӱ
		paint.setShadowLayer(45, 10, 10, Color.GRAY);
		// ����Բ��
		canvas.drawCircle(200, 40, 30, paint);
		// ����������
		canvas.drawRect(170, 80, 230, 140, paint);
		// ���ƾ���
		canvas.drawRect(170, 150, 230, 190, paint);
		// ����Բ�Ǿ���
		RectF re3 = new RectF(170, 200, 230, 230);
		canvas.drawRoundRect(re3, 15, 15, paint);
		// ������Բ
		RectF re31 = new RectF(170, 240, 230, 270);
		canvas.drawOval(re31, paint);
		// ����һ��Path���󣬷�ճ�һ��������
		Path path5 = new Path();
		path5.moveTo(170, 340);
		path5.lineTo(230, 340);
		path5.lineTo(200, 290);
		path5.close();
		// ����������
		canvas.drawPath(path5, paint);

		// ����һ��Path���󣬷�ճ�һ�������
		Path path6 = new Path();
		path6.moveTo(186, 360);
		path6.lineTo(214, 360);
		path6.lineTo(230, 392);
		path6.lineTo(200, 420);
		path6.lineTo(170, 392);
		path6.close();
		// ���������
		canvas.drawPath(path6, paint);
		
		
		// ------�����ַ���С�����-------
		paint.setTextSize(22);
		paint.setShader(null);
		// ����7���ַ���
		canvas.drawText("Բ��", 240, 50, paint);
		canvas.drawText("������", 240, 120, paint);
		canvas.drawText("����", 240, 175, paint);
		canvas.drawText("Բ�Ǿ���", 230, 220, paint);
		canvas.drawText("��Բ", 240, 260, paint);
		canvas.drawText("������", 240, 325, paint);
		canvas.drawText("�����", 240, 390, paint);
*/
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(2000, 2000);  
	}

	
}
