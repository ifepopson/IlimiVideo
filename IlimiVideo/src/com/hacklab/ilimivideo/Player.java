package com.hacklab.ilimivideo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.VideoView;

public class Player extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		
		VideoView v = (VideoView) findViewById(R.id.videoView1);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(v);
		

		v.setMediaController(mc);
		
		String path = (String) getIntent().getCharSequenceExtra("path");
		v.setVideoPath(path);
		v.start();
	}

}
