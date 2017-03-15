package com.dill.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView tv, score;
    Button sol, check;
    EditText answer;
    String resp = "", url;
    String[] ans;
    Spinner items, items2;
    ProgressBar pbar;
    int classID, correct, wrong;
    ArrayAdapter<?> adapter, adapter2;
    private static long back_pressed;
    boolean cancelled, second = false;
    SharedPreferences savedCorrect, savedWrong;
    public static final String APP_PREFERENCES = "stats";
    public static final String APP_PREFERENCES_CORRECT = "Correct";
    public static final String APP_PREFERENCES_WRONG = "Wrong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        savedCorrect = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        correct = savedCorrect.getInt(APP_PREFERENCES_CORRECT, 0);
        savedWrong = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        wrong = savedWrong.getInt(APP_PREFERENCES_WRONG, 0);

        /*tv = (TextView) findViewById(R.id.textView);
        sol = (Button) findViewById(R.id.sol);
        check = (Button) findViewById(R.id.check);
        answer = (EditText) findViewById(R.id.answer);
        items = (Spinner) findViewById(R.id.items);
        items2 = (Spinner) findViewById(R.id.items2);
        showDialog(0);*/
        back(new View(this));
        /*items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                tv.setText(selectedItemPosition + "");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
    }

    public void get(View view) {
        sol.setVisibility(View.INVISIBLE);
        check.setVisibility(View.INVISIBLE);
        answer.getText().clear();
        //url = "http://10.0.2.2:8081/Project2/?";
        url = "http://1-dot-server-153511.appspot.com/webproject?";
        //url += classID + "&" + items.getSelectedItemPosition();
        url += classID + "&";// + items.getSelectedItemPosition();
        switch (classID) {
            case 0:
                switch (items.getSelectedItemPosition()) {
                    case 0:
                        if (items2.getSelectedItemPosition() == 0)
                            url += 0;
                        else
                        if (items2.getSelectedItemPosition() == 1)
                            url += 1;
                        else
                            url += 2;
                        break;
                    case 1:
                        if (items2.getSelectedItemPosition() == 0)
                            url += 3;
                        else
                            url += 4;
                        break;
                    case 2:
                        if (items2.getSelectedItemPosition() == 0)
                            url += 5;
                        else
                            url += 6;
                        break;
                    case 3:
                        if (items2.getSelectedItemPosition() == 0)
                            url += 7;
                        else
                            url += 8;
                        break;
                }
                break;
            case 1:
                /*switch (items.getSelectedItemPosition()) {
                }*/
                url += 0;
                break;
            case 2:
                switch (items.getSelectedItemPosition()) {
                    case 0:
                        if (items2.getSelectedItemPosition() == 0)
                            url += 0;
                        else
                        if (items2.getSelectedItemPosition() == 1)
                            url += 1;
                        else
                        if (items2.getSelectedItemPosition() == 2)
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
                        if (items2.getSelectedItemPosition() == 0)
                            url += 7;
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
        //if (!answer.getText().toString().equals("") && ans[2].contains(answer.getText().toString()))
        if (answer.getText().toString().equals(ans[2])) {
            tv.append(getString(R.string.correct_answer));
            correct++;
        }
        else {
            tv.append(getString(R.string.wrong_answer));
            wrong++;
        }
    }

    public void sol(View view) {
        sol.setVisibility(View.INVISIBLE);
        tv.append(ans[1]);
    }

    public void menu(View view) {
        showDialog(0);
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
                builder.setTitle("Выберите, что решать");

                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        classID = item;
                        choose();
                    }
                });
                builder.setCancelable(false);
                return builder.create();
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
                items = (Spinner) findViewById(R.id.items);
                break;
            case 4:
                try {
                    getSupportActionBar().setTitle(R.string.action_bar_statistics);
                } catch (NullPointerException e) {
                    Log.d("", "Action bar is not supported");
                }
                setContentView(R.layout.stats);
                score = (TextView) findViewById(R.id.score);
                pbar = (ProgressBar) findViewById(R.id.progressBar4);
                pbar.setMax(100);
                new bar().execute();
                break;
        }
        if (classID != 3 && classID != 4) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            items.setAdapter(adapter);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            items2.setAdapter(adapter2);
            tv.setText(R.string.welcome_text);
            answer.getText().clear();
            sol.setVisibility(View.INVISIBLE);
            check.setVisibility(View.INVISIBLE);
        }
        items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                itemChanged(selectedItemPosition);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                break;
        }
        items2.setAdapter(adapter2);
    }

    public void back(View view) {
        try {
            getSupportActionBar().setTitle(R.string.app_name);
        } catch (NullPointerException e) {
            Log.d("", "Action bar is not supported");
        }
        cancelled = true;
        second = false;
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.textView);
        score = (TextView) findViewById(R.id.score);
        sol = (Button) findViewById(R.id.sol);
        check = (Button) findViewById(R.id.check);
        answer = (EditText) findViewById(R.id.answer);
        items = (Spinner) findViewById(R.id.items);
        items2 = (Spinner) findViewById(R.id.items2);
        pbar = (ProgressBar) findViewById(R.id.progressBar4);
        showDialog(0);
    }

    public void clear(View view) {
        correct = 0;
        wrong = 0;
        pbar.setProgress(0);
    }

    public void btnDec(View view) {
        wrong += 10;
    }

    public void btnInc(View view) {
        correct += 10;
    }

    public int curProgress() {
        return pbar.getProgress();
    }

    private class con extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
            Log.d("Task", "started");
            resp = "";
            tv.setText(R.string.server_answer);
        }
        protected Integer doInBackground(Integer... urls) {
            /*try {
                URL url = new URL("http://10.0.2.2:8081/Project2/");
                URLConnection connection = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String returnString = "";
                while ((returnString = in.readLine()) != null)
                {
                    resp += returnString;
                }
                in.close();
            } catch (IOException e) {
                Log.d("", "Connection error");
            }*/

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
        protected void onPreExecute() {
            Log.d("Task", "started");
            cancelled = false;
            //if (correct == 0 && wrong == 0)
                //cancelled = true;//для релиза
                //score.setText("У вас 0 правильных и 0 неправильных");
        }
        protected Integer doInBackground(Integer... urls) {
            publishProgress(0);
            while (!cancelled) {
                if (!second)
                    for (int i = 0; i < (float) correct / (wrong + correct) * 100; i++) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (cancelled)
                            break;
                        publishProgress(i + 1);
                    }
                else if (curProgress() < (float) correct / (wrong + correct) * 100) {
                    for (int i = curProgress(); i < (float) correct / (wrong + correct) * 100; i++) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (cancelled)
                            break;
                        publishProgress(i + 1);
                    }
                }
                else {
                    for (int i = curProgress(); i > (float) correct / (wrong + correct) * 100; i--) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (cancelled)
                            break;
                        publishProgress(i + 1);
                    }
                }

                second = true;
            }

            /*while (true) {
                if (cancelled)
                    break;
                publishProgress(round((float)correct/(wrong+correct)*100));
            }*/

            return 1;
        }

        protected void onProgressUpdate(Integer... progress) {
            try {
                pbar.setProgress(progress[0]);
                score.setText("У вас " + correct + " правильных и " + wrong + " неправильных");
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
        SharedPreferences.Editor correctEditor = savedCorrect.edit();
        correctEditor.putInt(APP_PREFERENCES_CORRECT, correct);
        correctEditor.apply();

        SharedPreferences.Editor wrongEditor = savedWrong.edit();
        wrongEditor.putInt(APP_PREFERENCES_WRONG, wrong);
        wrongEditor.apply();

        super.onStop();
    }
}