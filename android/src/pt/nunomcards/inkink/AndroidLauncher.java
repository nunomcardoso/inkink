package pt.nunomcards.inkink;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new InkInkGame(), config);
	}

	private int counter = 0;
	@Override
	public void onBackPressed() {
		if(counter == 0)
			Toast.makeText(this, "Press again to Exit Game.", Toast.LENGTH_SHORT).show();
		else
			super.onBackPressed();
		counter+=1;
	}
}
