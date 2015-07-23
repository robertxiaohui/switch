package com.example.switchdoor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 SwitchDoor   mToggleView = (SwitchDoor) findViewById(R.id.stv);
	        // 设置背景图片资源
	        mToggleView.setSlideBackground(R.drawable.switch_background);
	        //设置滑动图片资源
	        mToggleView.setSlideImage(R.drawable.slide_button_background);

	        //给滑动图片设置监听
	        mToggleView.setOnToggleListener(new SwitchDoor.OnToggleListener() {

	            @Override
	            public void onToggleChanged(SwitchDoor view, boolean isOpened) {

	                Toast.makeText(MainActivity.this, isOpened ? "打开" : "关闭",
	                        Toast.LENGTH_SHORT).show();

	                //

	            }
	        });
	}

}
