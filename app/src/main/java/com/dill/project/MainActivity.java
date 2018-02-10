package com.dill.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView tv, CAStv, MPtv, NStv;
    Button sol, check;
    EditText answer;
    String resp = "", url;
    String[] ans;
    Spinner spinnerLeft, spinnerRight;
    ProgressBar CASBar, MPBar, NSBar;
    int classID, correctCodesAndSum, wrongCodesAndSum, correctMultiplication, wrongMultiplication,
            correctNumberSystems, wrongNumberSystems;
    ArrayAdapter<?> adapter, adapter2;
    private static long back_pressed;
    boolean cancelled, hideMenu;
    ScrollView sw;
    SharedPreferences savedCorrectCodesAndSum, savedWrongCodesAndSum, savedCorrectMultiplication,
            savedWrongMultiplication, savedCorrectNumberSystems, savedWrongNumberSystems;
    public static final String APP_PREFERENCES = "stats";
    public static final String APP_PREFERENCES_CORRECT_CAS = "CorrectCAS";
    public static final String APP_PREFERENCES_WRONG_CAS = "WrongCAS";
    public static final String APP_PREFERENCES_CORRECT_MP = "CorrectMP";
    public static final String APP_PREFERENCES_WRONG_MP = "WrongMP";
    public static final String APP_PREFERENCES_CORRECT_NS = "CorrectNS";
    public static final String APP_PREFERENCES_WRONG_NS = "WrongNS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        back();

        savedCorrectCodesAndSum = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        correctCodesAndSum = savedCorrectCodesAndSum.getInt(APP_PREFERENCES_CORRECT_CAS, 0);
        savedWrongCodesAndSum = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        wrongCodesAndSum = savedWrongCodesAndSum.getInt(APP_PREFERENCES_WRONG_CAS, 0);

        savedCorrectMultiplication = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        correctMultiplication = savedCorrectMultiplication.getInt(APP_PREFERENCES_CORRECT_MP, 0);
        savedWrongMultiplication = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        wrongMultiplication = savedWrongMultiplication.getInt(APP_PREFERENCES_WRONG_MP, 0);

        savedCorrectNumberSystems = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        correctNumberSystems = savedCorrectNumberSystems.getInt(APP_PREFERENCES_CORRECT_NS, 0);
        savedWrongNumberSystems = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        wrongNumberSystems = savedWrongNumberSystems.getInt(APP_PREFERENCES_WRONG_NS, 0);
    }

    public void get(View view) {
        sol.setVisibility(View.INVISIBLE);
        check.setVisibility(View.INVISIBLE);
        answer.getText().clear();
        //url = "http://10.0.2.2:8081/Project2/?";
        url = "http://server-153511.appspot.com/hello?";
        url += classID + "&";
        switch (classID) {
            case 0:
                switch (spinnerLeft.getSelectedItemPosition()) {
                    case 0:
                        if (spinnerRight.getSelectedItemPosition() == 0)
                            url += 0;
                        else
                        if (spinnerRight.getSelectedItemPosition() == 1)
                            url += 1;
                        else
                            url += 2;
                        break;
                    case 1:
                        if (spinnerRight.getSelectedItemPosition() == 0)
                            url += 3;
                        else
                            url += 4;
                        break;
                    case 2:
                        if (spinnerRight.getSelectedItemPosition() == 0)
                            url += 5;
                        else
                            url += 6;
                        break;
                    case 3:
                        if (spinnerRight.getSelectedItemPosition() == 0)
                            url += 7;
                        else
                            url += 8;
                        break;
                }
                break;
            case 1:
                switch (spinnerLeft.getSelectedItemPosition()) {
                    case 0:
                        if (spinnerRight.getSelectedItemPosition() == 0)
                            url += 0;
                        else
                            url += 1;
                        break;
                    case 1:
                        url += 2;
                        break;
                }
                url += 0;
                break;
            case 2:
                switch (spinnerLeft.getSelectedItemPosition()) {
                    case 0:
                        if (spinnerRight.getSelectedItemPosition() == 0)
                            url += 0;
                        else
                        if (spinnerRight.getSelectedItemPosition() == 1)
                            url += 1;
                        else
                        if (spinnerRight.getSelectedItemPosition() == 2)
                            url += 3;
                        else
                            url += 2;
                        break;
                    case 1:
                        url += 4;
                        break;
                    case 2:
                        url += 5;
                        break;
                    case 3:
                        switch (spinnerRight.getSelectedItemPosition()) {
                            case 0:
                                url += 7;
                                break;
                            case 1:
                                url += 8;
                                break;
                            case 2:
                                url += 9;
                                break;
                            case 3:
                                url += 10;
                                break;
                            case 4:
                                url += 11;
                                break;
                            case 5:
                                url += 12;
                                break;
                            case 6:
                                url += 13;
                                break;
                            case 7:
                                url += 14;
                                break;
                            case 8:
                                url += 15;
                                break;
                            case 9:
                                url += 16;
                                break;
                            case 10:
                                url += 17;
                                break;
                            case 11:
                                url += 18;
                                break;
                            case 12:
                                url += 19;
                                break;
                            case 13:
                                url += 20;
                                break;
                        }
                        break;
                    case 4:
                        url += 6;
                        break;
                }
                break;
        }
        new con().execute();
    }

    public void check(View view) {
        check.setVisibility(View.INVISIBLE);
        sol.setVisibility(View.VISIBLE);
        if (answer.getText().toString().equals(ans[2])) {
            tv.append(getString(R.string.correct_answer));
            switch (classID) {
                case 0:
                    correctCodesAndSum++;
                    break;
                case 1:
                    correctMultiplication++;
                    break;
                case 2:
                    correctNumberSystems++;
                    break;
            }
        }
        else {
            tv.append(getString(R.string.wrong_answer));
            switch (classID) {
                case 0:
                    wrongCodesAndSum++;
                    break;
                case 1:
                    wrongMultiplication++;
                    break;
                case 2:
                    wrongNumberSystems++;
                    break;
            }
        }
    }

    public void sol(View view) {
        sol.setVisibility(View.INVISIBLE);
        tv.append(ans[1]);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                final String[] choose ={"Коды и сумма", "Произведение", "Системы счисления", "Теория", "Статистика"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Меню");

                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        classID = item;
                        choose();
                    }
                });
                builder.setCancelable(false);
                return builder.create();
            case 1:
                final String[] choose1 ={"Коды и сумма", "Произведение", "Системы счисления", "Теория", "Статистика"};
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Меню");

                builder1.setItems(choose1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        classID = item;
                        choose();
                    }
                });
                builder1.setCancelable(true);
                return builder1.create();
            default:
                return null;
        }
    }

    public void choose() {
        switch (classID) {
            case 0:
                try {
                    getSupportActionBar().setTitle(R.string.action_bar_bin_codes);
                } catch (NullPointerException e) {
                    Log.d("", "Action bar is not supported");
                }
                adapter = ArrayAdapter.createFromResource(this, R.array.codes_and_sum, android.R.layout.simple_spinner_item);
                adapter2 = ArrayAdapter.createFromResource(this, R.array.codes, android.R.layout.simple_spinner_item);
                answer.setInputType(InputType.TYPE_CLASS_DATETIME);
                answer.setKeyListener(DigitsKeyListener.getInstance("01. -"));
                break;
            case 1:
                try {
                    getSupportActionBar().setTitle(R.string.action_bar_multiplication);
                } catch (NullPointerException e) {
                    Log.d("", "Action bar is not supported");
                }
                adapter = ArrayAdapter.createFromResource(this, R.array.multiplication, android.R.layout.simple_spinner_item);
                adapter2 = ArrayAdapter.createFromResource(this, R.array.multiplication2, android.R.layout.simple_spinner_item);
                answer.setInputType(InputType.TYPE_CLASS_DATETIME);
                answer.setKeyListener(DigitsKeyListener.getInstance("01."));
                break;
            case 2:
                try {
                    getSupportActionBar().setTitle(R.string.action_bar_number_systems);
                } catch (NullPointerException e) {
                    Log.d("", "Action bar is not supported");
                }
                adapter = ArrayAdapter.createFromResource(this, R.array.number_systems, android.R.layout.simple_spinner_item);
                adapter2 = ArrayAdapter.createFromResource(this, R.array.number_systems1, android.R.layout.simple_spinner_item);
                answer.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                //answer.setKeyListener(DigitsKeyListener.getInstance("0123456789ABCDEF.,"));
                break;
            case 3:
                try {
                    getSupportActionBar().setTitle(R.string.action_bar_lecture);
                } catch (NullPointerException e) {
                    Log.d("", "Action bar is not supported");
                }
                setContentView(R.layout.lecture);
                tv = (TextView) findViewById(R.id.textView);
                spinnerLeft = (Spinner) findViewById(R.id.items);
                sw = (ScrollView) findViewById(R.id.scrollView2);
                break;
            case 4:
                try {
                    getSupportActionBar().setTitle(R.string.action_bar_statistics);
                } catch (NullPointerException e) {
                    Log.d("", "Action bar is not supported");
                }
                setContentView(R.layout.stats);
                CAStv = (TextView) findViewById(R.id.CAStv);
                MPtv = (TextView) findViewById(R.id.MPtv);
                NStv = (TextView) findViewById(R.id.NStv);
                CASBar = (ProgressBar) findViewById(R.id.CASBar);
                MPBar = (ProgressBar) findViewById(R.id.MPBar);
                NSBar = (ProgressBar) findViewById(R.id.NSBar);
                new bar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
                new bar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 1);
                new bar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 2);
                break;
        }
        if (classID != 3 && classID != 4) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLeft.setAdapter(adapter);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRight.setAdapter(adapter2);
            tv.setText(R.string.welcome_text);
            answer.getText().clear();
            sol.setVisibility(View.INVISIBLE);
            check.setVisibility(View.INVISIBLE);
        }
        else {
            try {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } catch (NullPointerException e) {
                Log.d("", "Action bar is not supported");
            }
            hideMenu = true;
            invalidateOptionsMenu();
        }
        spinnerLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                itemChanged(selectedItemPosition);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (hideMenu)
            menu.getItem(0).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                back();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void itemChanged(int pos) {
        switch(classID) {
            case 0:
                if (pos == 0) {
                    try {
                        getSupportActionBar().setTitle(R.string.action_bar_bin_codes);
                    } catch (NullPointerException e) {
                        Log.d("", "Action bar is not supported");
                    }
                    adapter2 = ArrayAdapter.createFromResource(this, R.array.codes, android.R.layout.simple_spinner_item);
                }
                else {
                    try {
                        getSupportActionBar().setTitle(R.string.action_bar_bin_sum);
                    } catch (NullPointerException e) {
                        Log.d("", "Action bar is not supported");
                    }
                    adapter2 = ArrayAdapter.createFromResource(this, R.array.sum, android.R.layout.simple_spinner_item);
                }
                break;
            case 1:
                break;
            case 2:
                if (pos == 0)
                    adapter2 = ArrayAdapter.createFromResource(this, R.array.number_systems1, android.R.layout.simple_spinner_item);
                else
                if (pos == 1 || pos == 2 || pos == 4)
                    adapter2 = ArrayAdapter.createFromResource(this, R.array.number_systems2, android.R.layout.simple_spinner_item);
                else
                    adapter2 = ArrayAdapter.createFromResource(this, R.array.number_systems3, android.R.layout.simple_spinner_item);
                break;
            case 3:
                if (pos == 0)
                    tv.setText(R.string.lecture_codes_and_sum);
                else
                if (pos == 1)
                    tv.setText(R.string.lecture_multiplication);
                else
                    tv.setText(R.string.lecture_number_systems);
                sw.scrollTo(0, 0);
                break;
        }
        spinnerRight.setAdapter(adapter2);
    }

    public void back() {
        try {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } catch (NullPointerException e) {
            Log.d("", "Action bar is not supported");
        }
        getSupportActionBar().addOnMenuVisibilityListener(new ActionBar.OnMenuVisibilityListener() {
            @Override
            public void onMenuVisibilityChanged(boolean isVisible) {
                if (isVisible) {
                    showDialog(1);
                    hideMenu = true;
                    invalidateOptionsMenu();
                    Timer timing = new Timer();
                    timing.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //closeOptionsMenu();
                            closeOptionsMenu();
                            hideMenu = false;
                            invalidateOptionsMenu();
                        }
                    }, 200);
                }
            }
        });
        cancelled = true;
        hideMenu = false;
        invalidateOptionsMenu();
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.textView);
        sol = (Button) findViewById(R.id.sol);
        check = (Button) findViewById(R.id.check);
        answer = (EditText) findViewById(R.id.answer);
        spinnerLeft = (Spinner) findViewById(R.id.items);
        spinnerRight = (Spinner) findViewById(R.id.items2);
        showDialog(0);
    }

    public void clear(View view) {
        cancelled = true;
        correctCodesAndSum = 0;
        wrongCodesAndSum = 0;
        correctMultiplication = 0;
        wrongMultiplication = 0;
        correctNumberSystems = 0;
        wrongNumberSystems = 0;
        CAStv.setText(getString(R.string.stats_codes_and_sum) + " " + correctCodesAndSum + "/" + wrongCodesAndSum);
        MPtv.setText(getString(R.string.stats_multiplication) + " " + correctMultiplication + "/" + wrongMultiplication);
        NStv.setText(getString(R.string.stats_number_systems) + " " + correctNumberSystems + "/" + wrongNumberSystems);
        CASBar.setProgress(0);
        MPBar.setProgress(0);
        NSBar.setProgress(0);
    }

    private class con extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
            Log.d("Task", "started");
            resp = "";
            tv.setText(R.string.server_answer);
        }
        protected Integer doInBackground(Integer... urls) {
            try {
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler response = new BasicResponseHandler();
                HttpGet http = new HttpGet(url);
                resp = (String) hc.execute(http, response);
            } catch (IOException e) {
                Log.d("", "Connection error");
            }
            return 1;
        }

        protected void onProgressUpdate(Integer... progress) {}

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            ans = resp.split("@");
            if (ans[0].equals(""))
                tv.setText(R.string.offline);
            else {
                tv.setText(ans[0]);
                check.setVisibility(View.VISIBLE);
            }
            Log.d("Task", "ended");
        }
    }

    private class bar extends AsyncTask<Integer, Integer, Integer> {
        TextView curtv;
        ProgressBar curBar;
        int curCorrect, curWrong;
        String curText;
        protected void onPreExecute() {
            Log.d("Task", "started");
            cancelled = false;
        }
        protected Integer doInBackground(Integer... urls) {
            switch (urls[0]) {
                case 0:
                    curtv = CAStv;
                    curBar = CASBar;
                    curCorrect = correctCodesAndSum;
                    curWrong = wrongCodesAndSum;
                    curText = getString(R.string.stats_codes_and_sum);
                    break;
                case 1:
                    curtv = MPtv;
                    curBar = MPBar;
                    curCorrect = correctMultiplication;
                    curWrong = wrongMultiplication;
                    curText = getString(R.string.stats_multiplication);
                    break;
                case 2:
                    curtv = NStv;
                    curBar = NSBar;
                    curCorrect = correctNumberSystems;
                    curWrong = wrongNumberSystems;
                    curText = getString(R.string.stats_number_systems);
                    break;
            }
            publishProgress(0);
            for (int i = 0; i < (float) curCorrect / (curWrong + curCorrect) * 100; i++) {
                if (cancelled)
                    break;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i + 1);
            }

            return 1;
        }

        protected void onProgressUpdate(Integer... progress) {
            try {
                curBar.setProgress(progress[0]);
                curtv.setText(curText + " " + curCorrect + "/" + curWrong);
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            Log.d("Task", "ended");
        }
    }

    public void onStop() {
        SharedPreferences.Editor correctCESEditor = savedCorrectCodesAndSum.edit();
        correctCESEditor.putInt(APP_PREFERENCES_CORRECT_CAS, correctCodesAndSum);
        correctCESEditor.apply();

        SharedPreferences.Editor wrongCESEditor = savedWrongCodesAndSum.edit();
        wrongCESEditor.putInt(APP_PREFERENCES_WRONG_CAS, wrongCodesAndSum);
        wrongCESEditor.apply();

        SharedPreferences.Editor correctMPEditor = savedCorrectMultiplication.edit();
        correctMPEditor.putInt(APP_PREFERENCES_CORRECT_MP, correctMultiplication);
        correctMPEditor.apply();

        SharedPreferences.Editor wrongMPEditor = savedWrongMultiplication.edit();
        wrongMPEditor.putInt(APP_PREFERENCES_WRONG_MP, wrongMultiplication);
        wrongMPEditor.apply();

        SharedPreferences.Editor correctNSEditor = savedCorrectNumberSystems.edit();
        correctNSEditor.putInt(APP_PREFERENCES_CORRECT_NS, correctNumberSystems);
        correctNSEditor.apply();

        SharedPreferences.Editor wrongNSEditor = savedWrongNumberSystems.edit();
        wrongNSEditor.putInt(APP_PREFERENCES_WRONG_NS, wrongNumberSystems);
        wrongNSEditor.apply();

        super.onStop();
    }
}