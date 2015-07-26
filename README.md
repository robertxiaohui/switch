# switchDoor
##功能:
	实现一个可以上下滑动的开关 


##效果展示
 ![image](https://github.com/robertxiaohui/switch/blob/master/switchDoor/yt6.gif)


##配置
1. 需要将switchDoor.java这个文件，拷贝到你的工程目录下面。

2. 在你的布局文件中,添加一下代码,当然这里的包名,需要根据你的应用进行修改，例如我的是：com.example.switchdoor.SwitchDoor.
		
		`<com.example.switchdoor.SwitchDoor
       	xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/stv"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>`

3.在你需要调用的代码中，添加这个到你的oncreat（）方法中。

    ` 		SwitchDoor   mToggleView = (SwitchDoor) findViewById(R.id.stv);
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
	        });`