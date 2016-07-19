package br.edu.ifpb.si.pdm.lembretemedico;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdicionarConsulta extends AppCompatActivity {
    private Button btnAddAlarm;
    private TextView teste;
    private static TextView tvChangeTime;
    private static TextView tvChangeDate;
    private EditText etMedico;
    static final int DATE_DIALOG_ID = 999;

    private String medico;
    private long startTime;

    private static int year;
    private static int month;
    private static int day;

    private static int hour;
    private static int minutes;

    private ArrayList<PendingIntent> intentArray;
    private AlarmManager alarms;
    private Receiver receiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_consulta);

        tvChangeDate = (TextView) findViewById(R.id.tvChangeDate);
        tvChangeTime = (TextView) findViewById(R.id.tvChangeTime);
        etMedico = (EditText) findViewById(R.id.etMedico);
        btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);


       btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm();
                Log.i("TESTE","Chamou addalarm");
                Intent it = new Intent();
                String medico = etMedico.getText().toString();
                it.putExtra("MEDICO",medico);
                it.putExtra("DATA", startTime);
                setResult(RESULT_OK, it);
                finish();
            }
        });

        intentArray = new ArrayList<PendingIntent>();
        alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        receiver = new Receiver();
        filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);
    }

    public void addAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(AdicionarConsulta.year, AdicionarConsulta.month, AdicionarConsulta.day, AdicionarConsulta.hour,AdicionarConsulta.minutes, 0);
        startTime = calendar.getTimeInMillis();

        //#####

        Intent intent = new Intent("ALARM_ACTION");
        String medico = etMedico.getText().toString();
        intent.putExtra("MEDICO",medico);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) (new Date()).getTime(), intent, 0);
        // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
        alarms.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (startTime - System.currentTimeMillis()),
                pendingIntent);

        this.intentArray.add(pendingIntent);

        //####

        btnAddAlarm.setText(String.valueOf(startTime));
        teste = (TextView) findViewById(R.id.teste);
        teste.setText(String.valueOf(System.currentTimeMillis()));
        Log.i("TESTE","addalarm");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            AdicionarConsulta.hour = hourOfDay;
            AdicionarConsulta.minutes = minute;
            AdicionarConsulta.tvChangeTime.setText(new StringBuilder().append(hourOfDay)
                    .append("-").append(minute)
                    .append(" "));
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            AdicionarConsulta.year = year;
            AdicionarConsulta.month = month;
            AdicionarConsulta.day = day;
            AdicionarConsulta.tvChangeDate.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(month + 1).append("-").append(day).append("-")
                    .append(year).append(" "));
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(receiver);
        super.onStop();
    }
}
