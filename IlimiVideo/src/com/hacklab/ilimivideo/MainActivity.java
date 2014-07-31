package com.hacklab.ilimivideo;

import java.io.File;
import java.util.List;
import java.util.Vector;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video.Thumbnails;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener,
		Button.OnClickListener {

	static Vector names = new Vector();
	static Vector icons = new Vector();
	static String u = "";

	private SearchView mSearchView;

	protected void onCreate(Bundle savedInstanceState) {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media

			File f = Environment.getExternalStorageDirectory();
			u = f.getPath();

			// create the folder if dey dnt exist
			f = new File(u + "/Android/data/Ilimi");
			if (!f.exists()) {
				boolean b = f.mkdir();
			}

			f = new File(u + "/Android/data/Ilimi/Videos/");
			if (!f.exists()) {
				boolean b = f.mkdir();
			}

		}

		// get all the videos
		File f = new File(u + "/Android/data/Ilimi/Videos/");
		String[] tempnames = f.list();
		for (String string : tempnames) {
			String currentpath = u + "/Android/data/Ilimi/Videos/" + string;
			names.add(string);

			Bitmap icon = ThumbnailUtils.createVideoThumbnail(currentpath,
					Thumbnails.MICRO_KIND);
			icons.add(icon);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (tempnames.length == 0) {
			LinearLayout lay = (LinearLayout) findViewById(R.id.linearcenter);
			lay.removeAllViews();

			TextView v = new TextView(MainActivity.this);
			v.setLayoutParams(new LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.MATCH_PARENT));
			v.setGravity(Gravity.CENTER);
			v.setText("No Content :( \n Please put some videos into Android/data/Ilimi/Videos  ");

			lay.addView(v);

		} else {
			GridView gridview = (GridView) findViewById(R.id.gridview);
			gridview.setAdapter(new Gridadapter(this));

			gridview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View v, int arg2,
						long position) {

					// Toast.makeText(MainActivity.this,v.getTag().toString(),
					// Toast.LENGTH_LONG).show();
					Intent i = new Intent(MainActivity.this, Player.class);
					String currentpath = u + "/Android/data/Ilimi/Videos/"
							+ names.elementAt((Integer) v.getTag()).toString();

					i.putExtra("path", currentpath);
					startActivity(i);
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionmenu, menu);

		MenuItem searchItem = menu.findItem(R.id.search);
		mSearchView = (SearchView) searchItem.getActionView();

		return super.onCreateOptionsMenu(menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.refresh) {
			// get all the videos
			names.removeAllElements();
			icons.removeAllElements();

			File f = new File(u + "/Android/data/Ilimi/Videos/");
			String[] tempnames = null;
			tempnames = f.list();

			for (String string : tempnames) {
				String currentpath = u + "/Android/data/Ilimi/Videos/" + string;
				names.add(string);

				Bitmap icon = ThumbnailUtils.createVideoThumbnail(currentpath,
						Thumbnails.MICRO_KIND);
				icons.add(icon);
			}

			LinearLayout lay = (LinearLayout) findViewById(R.id.linearcenter);
			lay.removeAllViews();

			GridView gridview = new GridView(this);
			gridview.setAdapter(new Gridadapter(MainActivity.this));
			gridview.setNumColumns(5);

			gridview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View v, int arg2,
						long position) {

					Intent i = new Intent(MainActivity.this, Player.class);
					String currentpath = u + "/Android/data/Ilimi/Videos/"
							+ names.elementAt((Integer) v.getTag()).toString();

					i.putExtra("path", currentpath);
					startActivity(i);
				}
			});

			lay.addView(gridview);
			gridview.refreshDrawableState();
			lay.refreshDrawableState();

			// lay.re

		}
		return true;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	private void setupSearchView() {
		mSearchView.setIconifiedByDefault(true);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchables = searchManager
					.getSearchablesInGlobalSearch();
			// Try to use the "applications" global search provider
			SearchableInfo info = searchManager
					.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchables) {
				if (inf.getSuggestAuthority() != null
						&& inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			mSearchView.setSearchableInfo(info);
		}
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setOnCloseListener(this);
	}

}
