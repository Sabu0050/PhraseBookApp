package com.sabutos.dictionary.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sabutos.dictionary.R;

public class AboutActivity extends AppCompatActivity {

    TextView tLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tLink= (TextView) findViewById(R.id.textView12);
        tLink.setMovementMethod(LinkMovementMethod.getInstance());
        tLink.setText(Html.fromHtml(getResources().getString(R.string.contact)));
        tLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailintent = new Intent(android.content.Intent.ACTION_SEND);
                emailintent.setType("plain/text");
                emailintent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {"english.bangla.com@gmail.com" });
                emailintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                emailintent.putExtra(android.content.Intent.EXTRA_TEXT,"");
                startActivity(Intent.createChooser(emailintent, "Send mail..."));
            }
        });
    }
}
