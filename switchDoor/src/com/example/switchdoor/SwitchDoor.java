package com.example.switchdoor;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SwitchDoor extends View {

	private final static int STATE_DOWN = 0;
	private final static int STATE_MOVE = 1;
	private final static int STATE_UP = 2;

	private Bitmap mBackground;// 滑动的背景
	private Bitmap mSlideImage;// 滑动块的图片
	private Paint paint = new Paint();//创建一个画笔

	private boolean isOpened = false;// 用来标记控件是否是打开的
	private float mCurrentY;//临时记录Y的坐标

	private int mState = STATE_UP;// 用来记录用户当前手势的状态,默认状态
	private OnToggleListener mListener;//给整个控件设置监听

	private int space=90;

	public SwitchDoor(Context context) {
		this(context, null);
	}

	public SwitchDoor(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//设置背景图片
	public void setSlideBackground(int resId) {
		mBackground = BitmapFactory.decodeResource(getResources(), resId);
	}

	//设置滑动块
	public void setSlideImage(int resId) {
		mSlideImage = BitmapFactory.decodeResource(getResources(), resId);
	}

	//测量当前控件的宽高
	@Override
	protected void onMeasure(int highMeasureSpec, int heightMeasureSpec) {

		// 设置 当前控件的实际的宽高

		if (mBackground != null) {
			int measuredWith = mBackground.getWidth();//获取背景的宽度
			int measuredHeight = mBackground.getHeight();//获取背景的高度
			int slideWidth = mSlideImage.getWidth();//获取滑动块的宽度
			//Log.d(TAG,""+measuredHeight)
			space = (measuredWith-slideWidth)/2;//获滑动块和背景之间的间距
			setMeasuredDimension(measuredWith, measuredHeight);//设置当前控件的大小
		} else {
			super.onMeasure(highMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// 画背景
		if (mBackground != null) {

			// canvas 画布，画板
			int high = 0;//画的X轴起始位置
			int top = 0;//画的Y轴起始位置

			canvas.drawBitmap(mBackground, high, top, paint);//画背景
		}

		// 画滑动块
		if (mSlideImage == null) {
			return;
		}

		// 当控件在左侧-->关闭情况
		// 如果用户手指按下时，当前的点的垂直坐标在 滑动块的左侧，就不动
		// canvas.drawBitmap(mSlideImage, 0, 0, paint);

		int slideHigh = mSlideImage.getHeight();
		int backHigh = mBackground.getHeight();

		switch (mState) {
		case STATE_DOWN:
		case STATE_MOVE:
			if (!isOpened) {
				// 当控件在上侧-->关闭情况
				if (mCurrentY < slideHigh / 2f+space) {// 当前的点的垂直坐标在 滑动块的上侧
					canvas.drawBitmap(mSlideImage, space, space, paint);
				} else {
					// 在下侧
					float high = mCurrentY - slideHigh / 2f;
					float maxhigh = backHigh - slideHigh;
					if (high > maxhigh-space) {
						high = maxhigh-space;
					}
					canvas.drawBitmap(mSlideImage, space, high, paint);
				}
			} else {
				// 在下侧--》打开的
				// 当前的点的垂直坐标在 滑动块的下侧，不动
				if (mCurrentY > backHigh - slideHigh / 2f-space) {
					// 画打开的状态
					float high = backHigh - slideHigh-space;
					canvas.drawBitmap(mSlideImage, space, high, paint);
				} else {
					//
					float high = mCurrentY - slideHigh / 2f-space;
					if (high < space) {
						high = space;
					}
					canvas.drawBitmap(mSlideImage, space, high, paint);
				}
			}
			break;
		case STATE_UP:
			if (isOpened) {
				// 打开的
				float high = backHigh - slideHigh;
				canvas.drawBitmap(mSlideImage,space, high-space, paint);
			} else {
				// 关闭的
				canvas.drawBitmap(mSlideImage, space, space, paint);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// MotionEvent--->用户触摸时的实时数据

		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下时
			// 改变状态
			mState = STATE_DOWN;
			mCurrentY = event.getY();

			break;
		case MotionEvent.ACTION_MOVE:
			// 移动
			mState = STATE_MOVE;
			mCurrentY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			// 手指抬起
			mState = STATE_UP;
			mCurrentY = event.getY();

			int high = mBackground.getHeight();

			if (mCurrentY > high / 2f && !isOpened) {
				// 打开
				isOpened = true;

				if (mListener != null) {
					mListener.onToggleChanged(this, true);
				}

			} else if (mCurrentY <= high / 2f && isOpened) {
				// 关闭
				isOpened = false;

				if (mListener != null) {
					mListener.onToggleChanged(this, false);
				}
			}
			break;
		default:
			break;
		}

		invalidate();

		// 是否出处理touch事件
		return true;// 消费掉所有的touch行为
	}

	public void setOnToggleListener(OnToggleListener listener) {
		this.mListener = listener;
	}

	public interface OnToggleListener {
		// 暴露当前是否是打开或是关闭的状态,提供数据给调用者
		void onToggleChanged(SwitchDoor view, boolean isOpened);
	}
}
