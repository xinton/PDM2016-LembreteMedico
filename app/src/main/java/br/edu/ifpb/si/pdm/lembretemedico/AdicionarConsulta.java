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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.edu.ifpb.si.pdm.lembretemedico.bd.BancoHelper;
import br.edu.ifpb.si.pdm.lembretemedico.bd.ProfissionalDAO;

public class AdicionarConsulta extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private Button btnAddAlarm;
    private static TextView tvChangeTime;
    private static TextView tvChangeDate;
   // private EditText etMedico;
    private Spinner profissionais_spinner;

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

        instanciaComponentesInterface();
        defineListeners();

        // Loading spinner data from database
        loadSpinnerData();

       btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm();
                Intent it = new Intent();
                String profissional = profissionais_spinner.getSelectedItem().toString();
                //String medico = etMedico.getText().toString();
                it.putExtra("PROFISSIONAL",profissional);
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

    private void instanciaComponentesInterface(){
        tvChangeDate = (TextView) findViewById(R.id.tvChangeDate);
        tvChangeTime = (TextView) findViewById(R.id.tvChangeTime);
        //etMedico = (EditText) findViewById(R.id.etMedico);
        profissionais_spinner = (Spinner) findViewById(R.id.profissionais_spinner);
        btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);

    }

    private void defineListeners(){
//        this.btAdd.setOnClickListener(new OnClickBotao());
//        this.lvProfissionais.setOnItemLongClickListener(new OnLongClickList());
    }

    // ## SPINNER
    private void loadSpinnerData() {
        // database handler
        ProfissionalDAO pd = new ProfissionalDAO(getApplicationContext());

        // Spinner Drop down elements
        List<Profissional> profissionais = pd.get();

        // Creating adapter for spinner
        ArrayAdapter<Profissional> dataAdapter = new ArrayAdapter<Profissional>(this,
                android.R.layout.simple_spinner_item, profissionais);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        profissionais_spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // ## SPINNER end

    public void addAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(AdicionarConsulta.year, AdicionarConsulta.month, AdicionarConsulta.day, AdicionarConsulta.hour,AdicionarConsulta.minutes, 0);
        startTime = calendar.getTimeInMillis();

        //#####

        Intent intent = new Intent("ALARM_ACTION");
        String medico = profissionais_spinner.getSelectedItem().toString();
        //String medico = etMedico.getText().toString();
        intent.putExtra("MEDICO",medico);
        intent.putExtra("DATA",startTime);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) (new Date()).getTime(), intent, 0);
        // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
        alarms.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + ( startTime - System.currentTimeMillis()),
                pendingIntent);

        this.intentArray.add(pendingIntent);

        //####

//        btnAddAlarm.setText(String.valueOf(startTime));
//        teste = (TextView) findViewById(R.id.teste);
//        teste.setText(String.valueOf(System.currentTimeMillis()));
//        Log.i("TESTE","addalarm");
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
                    .append(":").append(minute)
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
                    .append(day).append("/").append(month + 1).append("/")
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
