package com.example.forisremote;
/*
 *   MainActivity-Code for ForisRemote: IR-Remote App for Eizo Foris displays with FR-14 remote.
 *   Author: Christoph Pircher
 *   Created: 07.07.2020 (DD.MM.YYYY)
 *   Last edited: 07.07.2020 (DD.MM.YYYY)
 * */


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /* IR-Patterns */
    private final static int[] POWER = {8950, 4400, 650, 500, 600, 500, 600, 1600, 600, 550, 600, 500, 600, 500, 600, 500, 650, 1550, 650, 500, 600, 1600, 650, 1550, 650, 500, 600, 500, 650, 1550, 650, 500, 600, 500, 600, 550, 550, 550, 600, 500, 600, 1600, 600, 1600, 650, 500, 600, 550, 550, 550, 550, 1650, 600, 1600, 600, 1600, 600, 550, 600, 550, 550, 1600, 600, 1650, 600, 1600, 600};
    private final static int[] HDMI = {8950, 4400, 650, 500, 600, 500, 600, 1600, 650, 500, 600, 500, 600, 500, 650, 500, 600, 1550, 650, 500, 600, 1600, 650, 1550, 650, 500, 600, 550, 600, 1550, 650, 500, 650, 500, 600, 500, 600, 1550, 650, 1600, 650, 500, 600, 500, 600, 550, 600, 1550, 650, 500, 600, 1600, 650, 500, 600, 500, 600, 1600, 650, 1550, 650, 1600, 650, 500, 600, 1550, 650};
    private final static int[] PC = {8950, 4400, 650, 500, 650, 500, 600, 1550, 650, 500, 650, 500, 600, 500, 600, 500, 600, 1600, 650, 500, 600, 1600, 650, 1550, 650, 500, 600, 500, 650, 1550, 650, 500, 600, 500, 600, 550, 600, 500, 600, 500, 600, 500, 650, 1550, 650, 500, 600, 500, 650, 500, 600, 1550, 650, 1600, 650, 1550, 650, 1600, 600, 550, 550, 1600, 650, 1600, 600, 1600, 650};
    private final static int[] MODE = {8950, 4400, 600, 550, 550, 550, 550, 1650, 600, 550, 550, 550, 550, 550, 600, 550, 550, 1600, 600, 550, 600, 1600, 600, 1600, 600, 600, 550, 550, 550, 1600, 600, 550, 600, 550, 550, 550, 550, 1650, 600, 550, 550, 550, 550, 1650, 600, 550, 550, 1650, 550, 550, 600, 1600, 600, 550, 550, 1650, 600, 1600, 600, 550, 550, 1650, 600, 550, 550, 1600, 600};
    private final static int[] UP = {8950, 4400, 600, 550, 550, 550, 600, 1550, 650, 550, 550, 550, 550, 550, 600, 500, 600, 1600, 600, 550, 550, 1650, 600, 1600, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 1650, 600, 550, 550, 550, 550, 1650, 600, 550, 550, 550, 550, 550, 550, 1650, 600, 550, 550, 1650, 600, 1600, 600, 550, 600, 1600, 600, 1600, 600};
    private final static int[] OFFTIMER = {8950, 4400, 600, 550, 600, 500, 600, 1600, 600, 550, 600, 500, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 1650, 550, 1650, 600, 550, 550, 550, 550, 1650, 600, 550, 550, 550, 550, 550, 550, 1650, 600, 550, 550, 550, 550, 1650, 600, 550, 550, 550, 550, 550, 600, 1600, 600, 550, 550, 1650, 600, 1600, 600, 550, 550, 1650, 600, 1600, 600, 1650, 600};
    private final static int[] LEFT = {8950, 4400, 600, 500, 650, 500, 600, 1600, 600, 500, 650, 450, 650, 500, 600, 500, 600, 1600, 650, 500, 600, 1600, 600, 1650, 600, 500, 600, 500, 600, 1650, 600, 500, 600, 500, 600, 1600, 650, 1600, 600, 1600, 650, 500, 600, 500, 600, 1600, 600, 550, 600, 500, 600, 500, 600, 500, 650, 500, 600, 1600, 600, 1600, 650, 500, 600, 1600, 600, 1650, 600};
    private final static int[] ENTER = {8950, 4400, 600, 500, 600, 500, 600, 1650, 600, 500, 600, 500, 600, 550, 600, 500, 600, 1600, 600, 550, 600, 1600, 600, 1600, 650, 500, 600, 500, 600, 1600, 650, 500, 600, 500, 600, 500, 600, 1650, 600, 500, 600, 1600, 600, 550, 600, 500, 600, 500, 600, 500, 650, 1600, 600, 500, 600, 1650, 600, 500, 600, 1600, 600, 1650, 600, 1600, 600, 1600, 650};
    private final static int[] RIGHT = {8950, 4400, 600, 500, 650, 450, 650, 1600, 650, 450, 650, 450, 650, 500, 600, 500, 600, 1600, 650, 500, 600, 1600, 600, 1600, 650, 500, 600, 500, 600, 1600, 650, 500, 600, 500, 600, 550, 600, 500, 600, 500, 600, 1600, 600, 500, 650, 1600, 600, 500, 650, 450, 650, 1600, 600, 1600, 650, 1600, 600, 500, 600, 1600, 650, 500, 600, 1600, 600, 1600, 650};
    private final static int[] MENU = {8950, 4400, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 550, 600, 500, 600, 550, 550, 1600, 650, 500, 600, 1650, 550, 1600, 650, 500, 600, 550, 550, 1600, 650, 500, 600, 550, 550, 550, 600, 500, 600, 1600, 600, 1600, 650, 500, 600, 500, 600, 550, 600, 500, 600, 1600, 600, 1600, 650, 500, 600, 500, 600, 1650, 600, 1600, 600, 1600, 600, 1600, 650};
    private final static int[] DOWN = {8950, 4400, 600, 550, 600, 500, 600, 1600, 600, 550, 600, 500, 600, 500, 600, 550, 550, 1650, 600, 500, 600, 1650, 550, 1650, 600, 500, 600, 550, 600, 1600, 600, 500, 650, 500, 600, 500, 600, 1600, 650, 1550, 650, 500, 600, 500, 650, 1550, 600, 550, 600, 500, 600, 1600, 650, 500, 600, 500, 650, 1550, 650, 1600, 600, 500, 600, 1600, 650, 1600, 600};
    private final static int[] RETURN = {8950, 4400, 650, 500, 600, 500, 600, 1600, 650, 500, 600, 500, 600, 500, 600, 500, 650, 1550, 650, 500, 600, 1650, 600, 1550, 650, 500, 600, 550, 600, 1550, 650, 500, 600, 500, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 550, 550, 1650, 550, 1650, 600, 550, 550, 1650, 600, 1600, 600, 550, 550, 1650, 600, 1600, 650};
    private final static int[] ECOVIEW = {8950, 4400, 550, 550, 600, 550, 550, 1600, 600, 550, 600, 550, 550, 550, 550, 550, 550, 1650, 600, 550, 550, 1650, 600, 1600, 600, 550, 550, 550, 550, 1700, 550, 550, 550, 550, 550, 1650, 600, 1600, 600, 550, 600, 1600, 600, 1600, 600, 550, 600, 1600, 600, 550, 550, 550, 600, 550, 550, 1600, 600, 550, 600, 550, 550, 1600, 650, 500, 600, 1600, 600};
    private final static int[] SIZE = {8950, 4400, 600, 500, 600, 500, 600, 1650, 600, 500, 600, 500, 600, 500, 650, 500, 600, 1600, 600, 500, 650, 1600, 600, 1600, 600, 500, 650, 500, 600, 1600, 600, 500, 650, 500, 600, 1600, 600, 500, 650, 500, 600, 1600, 600, 500, 650, 1600, 600, 500, 600, 500, 650, 500, 600, 1600, 600, 1600, 650, 500, 600, 1600, 600, 500, 650, 1600, 600, 1600, 650};
    private final static int[] VOLUP = {8950, 4400, 600, 500, 650, 500, 600, 1600, 600, 500, 600, 550, 600, 500, 600, 500, 600, 1600, 650, 500, 600, 1600, 600, 1600, 650, 500, 600, 500, 650, 1550, 650, 500, 600, 500, 600, 1600, 650, 1550, 650, 1600, 600, 1600, 650, 500, 650, 500, 550, 550, 600, 500, 600, 500, 650, 500, 550, 550, 600, 500, 600, 1600, 600, 1600, 650, 1600, 600, 1600, 650};
    private final static int[] SMART = {8950, 4400, 600, 550, 600, 500, 600, 1600, 600, 550, 550, 550, 600, 500, 600, 550, 550, 1600, 650, 500, 600, 1600, 650, 1600, 600, 500, 600, 550, 550, 1650, 600, 500, 600, 550, 550, 1600, 650, 500, 600, 1650, 550, 550, 600, 500, 600, 1600, 650, 1600, 600, 500, 600, 550, 550, 1650, 600, 500, 600, 1600, 650, 1550, 650, 500, 600, 550, 600, 1600, 600};
    private final static int[] MUTE = {8950, 4400, 600, 550, 550, 550, 600, 1550, 650, 550, 550, 550, 600, 500, 600, 500, 600, 1650, 550, 550, 600, 1600, 600, 1600, 650, 500, 600, 500, 600, 1600, 650, 500, 600, 500, 600, 550, 550, 1600, 650, 500, 600, 1600, 600, 1600, 650, 500, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 1600, 650, 500, 600, 550, 550, 1600, 650, 1600, 600, 1650, 600};
    private final static int[] VOLDOWN = {8950, 4400, 600, 550, 550, 550, 600, 1600, 600, 550, 550, 550, 600, 500, 600, 500, 600, 1600, 650, 500, 600, 1600, 600, 1600, 650, 500, 600, 550, 550, 1600, 650, 500, 600, 500, 600, 1600, 650, 1600, 650, 500, 550, 1600, 650, 500, 600, 550, 550, 550, 600, 500, 600, 500, 650, 500, 550, 1650, 600, 500, 600, 1600, 650, 1600, 600, 1600, 600, 1650, 600};

    /* Declaring Buttons */
    Button btnPower;
    Button btnHDMI;
    Button btnPC;
    Button btnMode;
    Button btnUp;
    Button btnOffTimer;
    Button btnLeft;
    Button btnEnter;
    Button btnRight;
    Button btnMenu;
    Button btnDown;
    Button btnReturn;
    Button btnEcoView;
    Button btnSize;
    Button btnVolUp;
    Button btnSmart;
    Button btnMute;
    Button btnVolDown;

    ConsumerIrManager irManager;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        irManager = (ConsumerIrManager) getSystemService(getApplicationContext().CONSUMER_IR_SERVICE);
        IRSupported();

        vibrator = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

        /* Initialize Buttons */
        btnPower = (Button) findViewById(R.id.btnPower);
        btnHDMI = (Button) findViewById(R.id.btnHDMI);
        btnPC = (Button) findViewById(R.id.btnPC);
        btnMode = (Button) findViewById(R.id.btnMode);
        btnUp = (Button) findViewById(R.id.btnUP);
        btnOffTimer = (Button) findViewById(R.id.btnOffTimer);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnRight = (Button) findViewById(R.id.btnRight);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnEcoView = (Button) findViewById(R.id.btnEcoView);
        btnSize = (Button) findViewById(R.id.btnSize);
        btnVolUp = (Button) findViewById(R.id.btnVolUp);
        btnSmart = (Button) findViewById(R.id.btnSmart);
        btnMute = (Button) findViewById(R.id.btnMute);
        btnVolDown = (Button) findViewById(R.id.btnVolDown);

        /* Set OnClickListener for all buttons */
        btnPower.setOnClickListener(this);
        btnHDMI.setOnClickListener(this);
        btnPC.setOnClickListener(this);
        btnMode.setOnClickListener(this);
        btnUp.setOnClickListener(this);
        btnOffTimer.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnDown.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        btnEcoView.setOnClickListener(this);
        btnSize.setOnClickListener(this);
        btnVolUp.setOnClickListener(this);
        btnSmart.setOnClickListener(this);
        btnMute.setOnClickListener(this);
        btnVolDown.setOnClickListener(this);
    }

    /* Checks if Device has an IR Blaster which supports 36khz. */
    private boolean IRSupported() {
        if (!irManager.hasIrEmitter()) {
            alertDialog(getString(R.string.noIRBlaster), getString(R.string.noIRBlasterTitle));
            return false;
        } else {
            ConsumerIrManager.CarrierFrequencyRange cfr[] = irManager.getCarrierFrequencies();
            for (int i = 0; i < cfr.length; ++i) {
                if (cfr[i].getMinFrequency() <= 36000 && cfr[i].getMaxFrequency() >= 36000) {
                    return true;
                }
            }
            alertDialog(getString(R.string.frequencyNotSupported), getString(R.string.frequencyNotSupportedTitle));
            return false;
        }
    }

    /*Shows an AlertDialog with an OK button */
    private void alertDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onClick(View v) {
        if (IRSupported()) {
            /* Transmit the  IR-Signal corresponding to pressed button */
            if (v == btnPower) {
                irManager.transmit(36000, POWER);
            } else if (v == btnHDMI) {
                irManager.transmit(36000, HDMI);
            } else if (v == btnPC) {
                irManager.transmit(36000, PC);
            } else if (v == btnMode) {
                irManager.transmit(36000, MODE);
            } else if (v == btnUp) {
                irManager.transmit(36000, UP);
            } else if (v == btnOffTimer) {
                irManager.transmit(36000, OFFTIMER);
            } else if (v == btnLeft) {
                irManager.transmit(36000, LEFT);
            } else if (v == btnEnter) {
                irManager.transmit(36000, ENTER);
            } else if (v == btnRight) {
                irManager.transmit(36000, RIGHT);
            } else if (v == btnMenu) {
                irManager.transmit(36000, MENU);
            } else if (v == btnDown) {
                irManager.transmit(36000, DOWN);
            } else if (v == btnReturn) {
                irManager.transmit(36000, RETURN);
            } else if (v == btnEcoView) {
                irManager.transmit(36000, ECOVIEW);
            } else if (v == btnSize) {
                irManager.transmit(36000, SIZE);
            } else if (v == btnVolUp) {
                irManager.transmit(36000, VOLUP);
            } else if (v == btnSmart) {
                irManager.transmit(36000, SMART);
            } else if (v == btnMute) {
                irManager.transmit(36000, MUTE);
            } else if (v == btnVolDown) {
                irManager.transmit(36000, VOLDOWN);
            }
        }

        /* Vibrate when any button is pressed (for feedback) */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }
    }
}