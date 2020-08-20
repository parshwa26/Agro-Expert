package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.Utility;

public class ActivitySelectLanguage extends Activity {

    String language[] = {
            "ગુજરાતી",
            "હિન્દી",
            "English"};

    Button btn_nxt;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_language);

            btn_nxt = findViewById(R.id.btn_nxt);
            btn_nxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent refresh = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(refresh);
                    finish();
                }
            });


            Spinner spinner = findViewById(R.id.spinner);
            spinner.setTextAlignment(getApplicationContext().getResources().getColor(R.color.white));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch(i){
                        case 0:
                             Utility.set_lang(getApplicationContext(),"gu",1);
                             Log.e("finish","gu");
                             break;
                        case 1:
                            Utility.set_lang(getApplicationContext(),"hi",2);
                            Log.e("finish","hi");
                            break;
                        case 2:
                            Utility.set_lang(getApplicationContext(),"en",3);
                            Log.e("finish","en");
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });

            //  ArrayAdapter aa =  new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,language);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this,android.R.layout.simple_spinner_item,language
            );
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //int spinnerPosition = aa.getPosition(0);

            spinner.setAdapter(spinnerArrayAdapter);
            //spinner.setSelection(spinnerPosition);
        }


    @Override
    public void onBackPressed() {
        finish();
    }
}
