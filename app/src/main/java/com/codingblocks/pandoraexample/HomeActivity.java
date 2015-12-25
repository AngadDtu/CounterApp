package com.codingblocks.pandoraexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    TextView tv;
    Button incrementButton;
    Button decrementButton;
    EditText etv;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv = (TextView) findViewById(R.id.text);
        tv.setTextColor(Color.BLACK);
       // tv.setOnLongClickListener(this);
        incrementButton = (Button) findViewById(R.id.incrementButton);
        incrementButton.setOnClickListener(this);
        incrementButton.setTextColor(Color.GREEN);
        decrementButton = (Button) findViewById(R.id.decrementButton);
        decrementButton.setTextColor(Color.RED);
        // decrementButton.setOnClickListener(this);
        Log.i("Log activity cycle", "on create");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        i = savedInstanceState.getInt("i");
        tv.setText(savedInstanceState.getString("textview_content"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("i", i);
        outState.putString("textview_content", tv.getText().toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Log activity cycle", "on start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Log activity cycle", "on resume");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            tv.setText("Settings clicked");
            return true;
        } else if (id == R.id.action_reset) {
            SharedPreferences sp = getSharedPreferences("Previous", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("data", i);
            editor.commit();
            i = 0;
            tv.setText(Integer.toString(i));

        } else if (id == R.id.action_set) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Set Value");
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.alert, null);
            etv = (EditText) v.findViewById(R.id.set);
            etv.setText("");
            b.setView(v);
            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String s = etv.getText().toString();
                    i = Integer.parseInt(s);
                    tv.setText(Integer.toString(i));
                }
            });
            b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            b.create().show();

        } else if (id == R.id.action_store) {
            SharedPreferences shared = getSharedPreferences("Previous", Context.MODE_PRIVATE);
            int k = shared.getInt("data", 0);
            Toast.makeText(this, "Previous Value Was " + k, Toast.LENGTH_LONG).show();


        } else if (id == R.id.action_email) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:angad.dtu2012@gmail.com"));
            i.putExtra(Intent.EXTRA_SUBJECT, "subject");
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }

        } else if (id == R.id.action_web) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://www.google.com"));
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void onblah(View v) {
        v.playSoundEffect(SoundEffectConstants.CLICK);
        i--;
        tv.setAlpha(tv.getAlpha() - 0.1f);
        tv.setText(Integer.toString(i));
    }

    @Override
    public void onClick(View v) {
        v.playSoundEffect(SoundEffectConstants.CLICK);
        i++;
        tv.setAlpha(tv.getAlpha() + 0.1f);
        tv.setText(Integer.toString(i));

    }
    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}