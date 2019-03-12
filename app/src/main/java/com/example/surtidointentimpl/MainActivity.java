package com.example.surtidointentimpl;


import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button btn1 = findViewById(R.id.button1);
		Button btn2 = findViewById(R.id.button2);
		Button btn3 = findViewById(R.id.button3);
		Button btn4 = findViewById(R.id.button4);
		Button btn5 = findViewById(R.id.button5);
		Button btn6 = findViewById(R.id.button6);
		Button btnMarcar = findViewById(R.id.buttonMarcar);
		Button btnSMS = findViewById(R.id.buttonSMS);
		Button btnMail = findViewById(R.id.buttonMail);
		Button btnImatge = findViewById(R.id.buttonGallery);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btnMarcar.setOnClickListener(this);
		btnSMS.setOnClickListener(this);
		btnMail.setOnClickListener(this);
		btnImatge.setOnClickListener(this);


		if (Build.VERSION.SDK_INT >= 23)
			if (! ckeckPermissions())
				requestPermissions();
	}

	public void onClick (View v) {
		Intent in;
		final String lat = "41.60788";
		final String lon = "0.623333";
		final String url = "http://www.eps.udl.cat/";
		final String adressa = "Carrer de Jaume II, 69, Lleida";
		final String textoABuscar = "escuela politecnica superior";

		switch (v.getId()) {
			case R.id.button1:
				Toast.makeText(this, getString(R.string.opcio1), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + ',' + lon));
				startActivity(in);
				break;
			case R.id.button2:
				Toast.makeText(this, getString(R.string.opcio2), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + adressa));
				startActivity(in);
				break;
			case R.id.button3:
				Toast.makeText(this, getString(R.string.opcio3), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(in);
				break;
			case R.id.button4:
				Toast.makeText(this, getString(R.string.opcio4), Toast.LENGTH_LONG).show();
				//in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + "escola politecnica superior UdL"));
				in = new Intent(Intent.ACTION_WEB_SEARCH);
				in.putExtra(SearchManager.QUERY, textoABuscar);
				startActivity(in);
				break;
			case R.id.button5:
				Toast.makeText(this, getString(R.string.opcio5), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
				startActivity(in);
				break;
			case R.id.button6:
				Toast.makeText(this, getString(R.string.opcio6), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_PICK);
				in.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
				startActivityForResult(in, 5);
				break;
			case R.id.buttonMarcar:
				Toast.makeText(this, getString(R.string.opcioMarcar), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.telef)));
				startActivity(in);
				break;
			case R.id.buttonSMS:
				Toast.makeText(this, getString(R.string.opcioSMS), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + getString(R.string.contact)));
				in.putExtra("sms_body",getString(R.string.Message));
				startActivity(in);
				break;
			case R.id.buttonMail:
				Toast.makeText(this, getString(R.string.opcioMail), Toast.LENGTH_LONG).show();
				in = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + getString(R.string.correu)));
				in.putExtra(Intent.EXTRA_SUBJECT, "demo");
				in.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail));
				startActivity(in);
				break;
			case R.id.buttonGallery:
                Toast.makeText(this, getString(R.string.opcioImage), Toast.LENGTH_LONG).show();
                in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(in,10);
                break;
		}
	}

	private boolean ckeckPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (ActivityCompat.checkSelfPermission(getApplicationContext(),
					Manifest.permission.CALL_PHONE) ==
					PackageManager.PERMISSION_GRANTED)
				return true;
			else
				return false;
		}
		else
			return true;
	}

	private void requestPermissions() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.CALL_PHONE},
				0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && null != data) {
            TextView text = (TextView) findViewById(R.id.textView);
            Uri uri = data.getData();
            String[] proj = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int numIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                text.setText(cursor.getString(numIndex));
            }
        }

        if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            ImageView image = findViewById(R.id.imatge);
            image.setImageURI(uri);
        }
    }
}
