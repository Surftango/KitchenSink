package com.maa.ca.kitchensink;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ca.integration.CaMDOIntegration;

import java.util.Calendar;
import java.util.Random;


public class EventsActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    Button bStartProgressBarButton, bStopProgressBarButton, bEnterPrivateZone, bExitPrivateZone;
    ProgressBar pbSampleBar;
    CheckBox mCheckBox;
    ToggleButton mSwitch;
    RadioGroup mRadioGrp;
    Spinner mSpinner;

    DatePickerDialog.OnDateSetListener listener;

    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Intitalise();
        initializeNavigation();
        CaMDOIntegration.registerAppFeedBack(Feedback.getBroadCastReciever());

    }


    @Override
    protected void onResume() {
        super.onResume();
        CaMDOIntegration.setSessionAttribute(CaMDOIntegration.CAMAA_STRING, "Custom Event Name " + i++, "Custom Event Value " + i++);
        CaMDOIntegration.setSessionAttribute(CaMDOIntegration.CAMAA_STRING, "Header Event Name " + i++, "Header Event Value " + i++);
//        makeDummyProfileCall();
    }


    public int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // CaMDOIntegration.setSessionInfo("Header Customer Device NAme"+i++, "Header Customer Device Val "+i++,CaMDOIntegration.CAMAA_CUSTOMER_ID );

    }

    private void Intitalise() {
        bStartProgressBarButton = (Button) findViewById(R.id.bStartProgressBar);
        bStopProgressBarButton = (Button) findViewById(R.id.bStopProgressBar);
        pbSampleBar = (ProgressBar) findViewById(R.id.pbsamplebar);
        bEnterPrivateZone = (Button) findViewById(R.id.bEnterPrivateZone);
        bExitPrivateZone = (Button) findViewById(R.id.bExitPrivateZone);
        mCheckBox = (CheckBox) findViewById(R.id.mCheckbox);
        mSwitch = (ToggleButton) findViewById(R.id.sampleswitch);
        mRadioGrp = (RadioGroup)  findViewById(R.id.radio_grp);
        mSpinner = (Spinner) findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);

        bEnterPrivateZone.setOnClickListener(this);
        bExitPrivateZone.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);
        mSwitch.setOnCheckedChangeListener(this);

        bStartProgressBarButton.setOnClickListener(this);
        bStopProgressBarButton.setOnClickListener(this);
        mSpinner.setOnItemSelectedListener(this);
        pbSampleBar.setVisibility(View.GONE);


        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        new TimePickerDialog(this, this, hour, minute,
                DateFormat.is24HourFormat(this));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bEventsActivity:
                break;
            case R.id.bStartProgressBar:
                pbSampleBar.setVisibility(View.VISIBLE);
                break;
            case R.id.bStopProgressBar:
                pbSampleBar.setVisibility(View.GONE);
                break;

            case R.id.bEnterPrivateZone:
                CaMDOIntegration.enterPrivateZone();
                break;
            case R.id.bExitPrivateZone:
                CaMDOIntegration.exitPrivateZone();
                break;
            case R.id.mCheckbox:
                break;
            default:
                super.onClick(v);
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this,"Clicked "+buttonView.getText(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        Toast.makeText(this,"Clicked RadioGrp "+group.getId()+" val "+checkedId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"Selected Spinner pos "+position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view) {
    }

    public void onButtonClickedTest(View view) {
        Toast.makeText(this, "on clicked of Button", Toast.LENGTH_LONG).show();
    }

    public void onCustomButtonClickedTest(View view) {
        Toast.makeText(this, "on clicked of Custom Button", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
