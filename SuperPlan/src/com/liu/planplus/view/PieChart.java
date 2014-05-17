package com.liu.planplus.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.liu.planplus.toolClass.Point;

/**
 * ���ּ��п���������ת���ſ��ͼ��ʵ��ԭ��
 * 
 * С�ǣ�
 * ��ʵ�ֵĹ����У���Ҫ���õ���һЩ��ѧ������ʵ�ֽǶȺ���Ļλ������ļ���
 * ��������������֮��ĽǶȼ�������⣬һ��ʼ���˺ܾ�û�н�������һ��żȻ����⣬������
 * �����ü����������Ǽ��������������������������ĽǶȵ�ʱ�����ȣ�����
 * ÿ���������x��������ĽǶȣ��������ܿ��Խ���ת��Ϊ����ֱ�������ε��ڽǼ�������
 * �ٽ����μ���ĽǶȽ��м������㣬��ʵ���ˡ��ǲ��Ǻܼ򵥣��Ǻǣ�����������������ѧ
 * û��ѧ�õĿ�������˵��Ҳֻ����������Ϊ����
 * 
 * @author liu
 *
 */
public class PieChart extends View{
	public static final String TAG = "PieChart";
	public static final int ALPHA = 100;
	public static final int ANIMATION_DURATION = 800;
	public static final int ANIMATION_STATE_RUNNING = 1;
	public static final int ANIMATION_STATE_DOWN = 2;
	//�õ���Ļ�Ŀ�͸�
	public  WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);
	@SuppressWarnings("deprecation")
	public int width = wm.getDefaultDisplay().getWidth();
	@SuppressWarnings("deprecation")
	public int height = wm.getDefaultDisplay().getHeight();
	public int OFFSET = 70;//�����ͼ�ı߽���ʼλ��
	public int TEXTSIZE = 36;//���������С
	/**
	 * ��Ҫ�������ֵ����ô���õġ��������ͼƬ�е�һ���Բ�������Ӧ�ĳ������ĸ��ߵ�����λ��
	 * �����ֵ���Լ���Ҫ��γ��Բ������ˡ����������ǵı�ͼ����������������������
	 */
	private final RectF OVAL = new RectF(OFFSET,OFFSET,width-OFFSET,width-OFFSET);

	
	private int[] colors; //ÿ���ֵ���ɫֵ
	
	private int[] values; //ÿ���ֵĴ�С
	
	private int[] degrees; //ֵת���ɽǶ�
	
	private String[] titles; //ÿ���ֵ�����
	
	//���Ʊ�ͼ�Ļ���
	private Paint paint;
	//���Ƽ�ͷ�Ļ���
	private Paint arrowPaint;
	//���Ƽ�ͷ��·��
	private Path path;
//	private Paint maskPaint;
//	private Bitmap mask; //�������ֵ�Bitmap	
	private Paint textPaint;
	
	private Point lastEventPoint;
	
	private int currentTargetIndex = -1;
	
	private Point center; //����Ǳ�ͼ������λ��
	
	private int eventRadius = 0; //�¼������ͼ���ĵľ���
	
	//���Ե�ʱ��ʹ�õ�
	//private ChartClickListener clickListener;
	
	private int startDegree = 90; //�ó�ʼ��ʱ��Բ���ǴӼ�ͷλ�ÿ�ʼ������
	
	
	private int animState = ANIMATION_STATE_DOWN;
	
	private boolean animEnabled = false;
	
	private long animStartTime;
	
	public PieChart(Context context) {
		super(context);
		init();
	}
	
	public PieChart(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}
	
	public PieChart(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		paint = new Paint();
		
//		maskPaint = new Paint();
		
		arrowPaint = new Paint();
		arrowPaint.setColor(Color.WHITE);
		arrowPaint.setAlpha(100);
		
		path = new Path();
		
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		textPaint.setAlpha(255);
		textPaint.setTextSize(TEXTSIZE);
		
		values = new int[]{
				60,
				90,
				30,
				50,
				70
		};
		
		titles = new String[]{
				"�����Ǻ����Ĳ�",
				"�ղ�",
				"����",
				"����ʲô�������Ҳ�֪��",
				"����Ƿǳ��ǳ����Ĳ�"
		};	
		
		colors = new int[]{
			Color.argb(ALPHA, 249, 64, 64),
			Color.argb(ALPHA, 0, 255, 0),
			Color.argb(ALPHA, 255, 0, 255),
			Color.argb(ALPHA, 255, 255, 0),
			Color.argb(ALPHA, 0, 255, 255)
		};
		
		degrees = getDegrees();
		
		//Drawable d = getResources().getDrawable(R.drawable.mask);
//		mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask);
		
		//��ȡ��ʼλ�õ�ʱ���·���ͷ���ڵ�����		
		animEnabled = true; //ͬʱ����������
	}
	
//	public void setChartClickListener(ChartClickListener l){
//		this.clickListener = l;
//	}
	
	//�����ܺ�
	private int sum(int[] values){
		int sum = 0;
		for(int i=0; i<values.length;i++){
			sum += values[i];
		}
		
		return sum;
	}
	
	/**
	 * ����ÿ������ռ�ı�����������ÿ������������Բ����ռ�ĽǶ�
	 * ���ǣ��и�Сϸ�ڣ����Ǽ����ʱ��ע�⣬���ܲ�������������������ʱ��Ϊ��
	 * �������еĽǶȺ�С��360�ȵ���������ҽ�ʣ��Ĳ����͸�ĳ�����֣�����Ҳ��Ӱ��
	 * @return
	 */
	private int[] getDegrees(){
		int sum = this.sum(values);
		
		int[] degrees = new int[values.length];
		for(int i=0; i<values.length; i++){
			degrees[i] = (int)Math.floor((double)((double)values[i]/(double)sum)*360);
			//Log.v("Angle", angles[i]+"");
		}
		int angleSum = this.sum(degrees);
		if(angleSum != 360){
			//����ļ�����ܵ��º�С��360
			int c = 360 - angleSum;
			degrees[values.length-1] += c; //���������һ����ֵ�Դ��
		}
		
		return degrees;
	}
	
	/**
	 * ��д���������������������
	 */
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);

    	
    	if(animEnabled){
    		/**
    		 * ˵����������ʱ����Ҫ��ת�Ż�����ͼ
    		 */
    		Log.e(TAG, "anim enabled");
    		if(animState == ANIMATION_STATE_DOWN){

    			animStartTime = SystemClock.uptimeMillis();
    			animState = ANIMATION_STATE_RUNNING;
    			
    		}
    			
			final long currentTimeDiff = SystemClock.uptimeMillis() - animStartTime;
			int currentMaxDegree = (int)((float)currentTimeDiff/ANIMATION_DURATION*360f);
			
			Log.e(TAG, "��ǰ���Ķ���Ϊ:"+currentMaxDegree);
			
			if(currentMaxDegree >= 360){
				//��������״̬,ֹͣ����
				currentMaxDegree = 360;
				animState = ANIMATION_STATE_DOWN;
				animEnabled = false;
			}
			
			int[] degrees = getDegrees();
        	int startAngle = this.startDegree;
        	
        	//��ȡ��ǰʱ����������ת�ĽǶ���λ�ڵ�����
        	int maxIndex = getEventPart(currentMaxDegree);
        	
        	//���ݲ�ͬ����ɫ����ͼ
        	for(int i=0; i<= maxIndex; i++){
        		int currentDegree = degrees[i];
        		
        		if(i== maxIndex){
        			//���ڵ�ǰ���һ���������򣬿���ֻ��һ���֣���Ҫ��ȡ��ƫ����
        			currentDegree = getOffsetOfPartStart(currentMaxDegree, maxIndex);
        		}
        		
        		if(i > 0){
        			//ע�⣬ÿ�λ���ͼ���ǵü���startAngle
        			startAngle += degrees[i-1];
        		}
        		
        		paint.setColor(colors[i]);
            	canvas.drawArc(OVAL, startAngle, currentDegree, true, paint);
        	}
        	
        	if(animState == ANIMATION_STATE_DOWN){

        		//������������ˣ��������ǰ��ͷλ��������������ķ���
        		onStop();
        		
        	}else{
        		postInvalidate();    
        	} 	
    		
    	}else{

        	int[] degrees = getDegrees();
        	int startAngle = this.startDegree;
        	
        	/**
        	 * ÿ���������ɫ��ͬ����������ֻҪ���ƺ�ÿ������ĽǶȾͿ����ˣ������Ǹ�Բ
        	 */
        	for(int i=0; i<values.length; i++){
        		paint.setColor(colors[i]);
        		if(i>0){
        			startAngle += degrees[i-1];
        		}
        		canvas.drawArc(OVAL, startAngle, degrees[i], true, paint);

        	}    		
    	}
    	
    	
    	/**
    	 * ������ͼ֮�󣬻�����ͼƬ������ͼƬ��λ�ڱ�ͼ֮���ˣ��γ������ֵ�Ч��
    	 */
    //	canvas.drawBitmap(mask, 0, 0, maskPaint);
    	
    	/**
    	 * ����һ��Path���󣬷�ճ�һ�����μӼ�ͷ
    	 */
		
		path.moveTo(150, width - OFFSET + 30);
		path.lineTo(width/2 - 30, width - OFFSET + 30);
		path.lineTo(width/2, (float) (width - OFFSET + 30 - 30*1.732));
		path.lineTo(width/2 + 30, width - OFFSET + 30);
		path.lineTo(width - 150, width - OFFSET + 30);
		path.lineTo(width - 150, width - OFFSET + 130);
		path.lineTo(150, width - OFFSET + 130);
		path.close();
		// ����Path���л��ƣ�����ͼ��
		canvas.drawPath(path, arrowPaint);
    	
    	/**
    	 * ���ݵ�ǰ����õ��ļ�ͷ����������ʾ������������Ϣ
    	 */
    	if(currentTargetIndex >= 0){
    		String title = titles[currentTargetIndex];
    		textPaint.setColor(colors[currentTargetIndex]);
    		//����������,�����־�����ʾ
    		int titleWidth = title.length()*TEXTSIZE;
    		canvas.drawText(title, (width - titleWidth)/2, width - OFFSET + 90, textPaint);
    	}

    }

    
    /**
     * �����ͼ��ת��
     */
    public boolean onTouchEvent(MotionEvent event){
    	
    	if(animEnabled && animState == ANIMATION_STATE_RUNNING){
    		return super.onTouchEvent(event);
    	}
    	
    	Point eventPoint = getEventAbsoluteLocation(event);
    	computeCenter(); //������������
    	
    	//���㵱ǰλ�������x��������ĽǶ�
    	//��������������м�����eventRadius��
    	int newAngle = getEventAngle(eventPoint, center); 
    	
    	int action = event.getAction();
    	
    	switch (action) {
		case MotionEvent.ACTION_DOWN:
			
			lastEventPoint = eventPoint;
			
			if(eventRadius > getRadius()){
        		/**
        		 * ֻ�е��ڱ�ͼ�ڲ�����Ҫ����ת��,����ֱ�ӷ���
        		 */
				Log.e(TAG, "��ǰλ�ó����˰뾶��"+eventRadius+">"+getRadius());
        		return super.onTouchEvent(event);
        	}
			
			break;
		case MotionEvent.ACTION_MOVE:
			//���ﴦ����
			rotate(eventPoint, newAngle);
			
			//����֮�󣬼ǵø���lastEventPoint
			lastEventPoint = eventPoint;
			break;
			
		case MotionEvent.ACTION_UP:
			onStop();
			break;

		default:
			break;
		}
    	
    	return true;
    }    
    
    /**
     * ������ֹͣ��ת��ʱ�������ǰ�·���ͷλ��ĳ������ķ�����λ�ã���������Ҫ����
     * ƫ���������ҽ���ͷָ������λ��
     */
    private void onStop() {
		
		int targetAngle = getTargetDegree();
		currentTargetIndex = getEventPart(targetAngle);

		int offset = getOffsetOfPartCenter(targetAngle, currentTargetIndex);
		
		/**
		 * offset>0,˵����ǰ��ͷλ������λ���ұߣ���������������˳ʱ����תoffset��С�ĽǶ�
		 * offset<0,�����෴
		 */
		startDegree += offset;
		
		postInvalidateDelayed(200);
	}

	private void rotate(Point eventPoint, int newDegree) {
		
		//������һ��λ�������x��������ĽǶ�
    	int lastDegree = getEventAngle(lastEventPoint, center); 
    	

    	/**
    	 * ��ʵת�����ǲ��ϵĸ��»�Բ��ʱ�����ʼ�Ƕȣ�������ÿ�δ��µ���ʼ�Ƕ��ػ�Բ�����γ���ת����Ч��
    	 */
    	startDegree += newDegree-lastDegree;
    	
    	//ת��Ȧ��ʱ���޶�startAngleʼ����-360-360��֮��
    	if(startDegree >= 360){
    		startDegree -= 360;
    	}else if(startDegree <= -360){
    		startDegree += 360;
    	}
    	
    	Log.e(TAG, "��ǰstartAngle��"+startDegree);
    	
    	//��ȡ��ǰ�·���ͷ���ڵ�����������onDraw��ʱ��ͻ�ת����ͬ������ʾ���ǵ�ǰ�����Ӧ����Ϣ
		int targetDegree = getTargetDegree();
		currentTargetIndex = getEventPart(targetDegree);    	
    	
		//�������»��ƽ��棬����onDraw����
    	postInvalidate();

	}
    
    
    /**
     * ��ȡ��ǰ�¼�event�������Ļ������
     * @param event
     * @return
     */
    protected Point getEventAbsoluteLocation(MotionEvent event){
    	int[] location = new int[2];
    	this.getLocationOnScreen(location); //��ǰ�ؼ�����Ļ�ϵ�λ��    
    	
    	int x = (int)event.getX();
    	int y = (int)event.getY();
    	
    	x += location[0];
    	y += location[1]; //����x,y�ʹ���ǰ�¼������������Ļ������
    	
    	Point p = new Point(x, y);
    	
    	Log.v(TAG, "�¼����꣺"+p.toString());
    	
    	return p;
    }
	
    /**
     * ��ȡ��ǰ��ͼ���������꣬�������Ļ���Ͻ�
     */
	protected void computeCenter(){
		if(center == null){
			int x = (int)OVAL.left + (int)((OVAL.right-OVAL.left)/2f);
			int y = (int)OVAL.top + (int)((OVAL.bottom - OVAL.top)/2f)+50; //״̬���ĸ߶���50
			center = new Point(x,y);
			//Log.v(TAG, "�������꣺"+center.toString());			
		}
	}
	
	/**
	 * ��ȡ�뾶
	 */
	protected int getRadius(){
		int radius = (int)((OVAL.right-OVAL.left)/2f);
		//Log.v(TAG, "�뾶��"+radius);
		return radius;
	}
	
	/**
	 * ��ȡ�¼���������ڱ�ͼ������x��������ĽǶ�
	 * �����������ϵ��ת����������ʹ�ñ�ͼ��������Ϊ�������ģ��������Ǵӳ��е���ѧһֱʹ�õ�"����"����ϵ��
	 * �����漰��Բ��ת����������һ�������x������˳ʱ��������ĳ���¼�������ϵ�е�λ��
	 * @param eventPoint
	 * @param center
	 * @return
	 */
	protected int getEventAngle(Point eventPoint, Point center){
		int x = eventPoint.x - center.x;//x�᷽���ƫ����
		int y = eventPoint.y - center.y; //y�᷽���ƫ����
		
		//Log.v(TAG, "ֱ����������ֱ�߳��ȣ�"+x+","+y);
		
		double z = Math.hypot(Math.abs(x), Math.abs(y)); //��ֱ��������б�ߵĳ���
		
		//Log.v(TAG, "б�߳��ȣ�"+z);
		
		eventRadius = (int)z;
		double sinA = (double)Math.abs(y)/z;
		
		//Log.v(TAG, "sinA="+sinA);
		
		double asin = Math.asin(sinA); //���������õ���ǰ���x��ĽǶ�,����С���Ǹ�
		
		//Log.v(TAG, "��ǰ���ƫ�ƽǶȵķ����ң�"+asin);
		
		int degree = (int)(asin/3.14f*180f);
		
		//Log.v(TAG, "��ǰ���ƫ�ƽǶȣ�"+angle);
		
		//�������Ҫ����x,y�����������жϵ�ǰ���x���������ļн�
		int realDegree = 0;
		if(x<=0 && y<=0){
			//���Ϸ�������180+angle
			
			realDegree = 180+degree;
			
		}else if(x>=0 && y<=0){
			//���Ϸ�������360-angle
			realDegree = 360-degree;
		}else if(x<=0 && y>=0){
			//���·�������180-angle
			realDegree = 180-degree;
		}else{
			//���·�,ֱ�ӷ���
			realDegree = degree;
			
		}
		
		//Log.v(TAG, "��ǰ�¼��������������x�������ε�˳ʱ��ƫ�ƽǶ�Ϊ��"+realAngle);
		
		return realDegree;

	}
	
	/**
	 * ��ȡ��ǰ�·���ͷλ�������startDegree�ĽǶ�ֵ
	 * ע�⣬�·���ͷ�����x����������90��
	 * @return
	 */
	protected int getTargetDegree(){
		
		int targetDegree = -1;
		
		int tmpStart = startDegree;
		
		/**
		 * �����ǰstartAngleΪ��������ֱ��+360��ת��Ϊ��ֵ
		 */
		if(tmpStart < 0){
			tmpStart += 360;
		}
		
		
		if(tmpStart < 90){
			/**
			 * ���startAngleС��90�ȣ�����Ϊ������
			 */
			targetDegree = 90 - tmpStart;
		}else{
			/**
			 * ���startAngle����90��������ÿ�μ���startAngle��ʱ���޶��������Ϊ360�ȣ�����
			 * ֱ�ӿ��԰������¹�ʽ����
			 */
			targetDegree = 360 + 90 - tmpStart;
		}
		
		//Log.e(TAG, "Taget Angle:"+targetDegree+"startAngle:"+startAngle);
		
		return targetDegree;
	}
	
	/**
	 *�жϽǶ�Ϊdegree�����ڱ�ͼ���ĸ�����
	 *ע�⣬����ĽǶ�һ������ֵ�����Ҳ��������x�������򣬶��������startAngle
	 *���ص�ǰ���ֵ�����
	 * @param degree
	 * @return
	 */
	protected int getEventPart(int degree){
		int currentSum = 0;
		
		for(int i=0; i<degrees.length; i++){
			currentSum += degrees[i];
			if(currentSum >= degree){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * ���Ѿ���֪�˵�ǰdegreeλ��targetIndex���������£�����angle���������targetIndex��ʼλ�õ�ƫ����
	 * @param degree
	 * @param targetIndex
	 * @return
	 */
	protected int getOffsetOfPartStart(int degree, int targetIndex){
		int currentSum = 0;
		for(int i=0; i<targetIndex; i++){
			currentSum += degrees[i];
		}
		
		int offset = degree - currentSum;
		
		return offset;		
	}
	
	/**
	 * ���Ѿ���֪�˵�ǰdegreeλ��targetIndex���������£�����angle���������targetIndex����λ�õ�ƫ����
	 * ����ǵ�����ֹͣ��ת��ʱ��ͨ������ƫ��������ʹ�ü�ͷָ��ǰ���������λ��
	 * @param degree
	 * @param targetIndex
	 * @return
	 */
	protected int getOffsetOfPartCenter(int degree, int targetIndex){
		int currentSum = 0;
		for(int i=0; i<=targetIndex; i++){
			currentSum += degrees[i];
		}
		
		int offset = degree - (currentSum-degrees[targetIndex]/2); 
		
		//����һ��,��offset>0��δ����һ��,��offset<0
		return offset;
	}
	

}
