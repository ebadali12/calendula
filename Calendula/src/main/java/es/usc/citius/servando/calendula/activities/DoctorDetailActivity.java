package es.usc.citius.servando.calendula.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.HashMap;

import es.usc.citius.servando.calendula.R;
import es.usc.citius.servando.calendula.UserPersonalInformation;

import static es.usc.citius.servando.calendula.util.ScreenUtils.setWindowFlag;

public class DoctorDetailActivity extends AppCompatActivity {


    protected Toolbar toolbar;


    TextInputLayout addressTextInputLayout;
    TextInputLayout ninTextInputLayout;
    TextInputLayout phoneNumberTextInputLayout;
    TextInputLayout doctorInfoTextInputLayout;

    AppCompatEditText addressInputEditTextView;
    AppCompatEditText ninInputEditTextView;
    AppCompatEditText phoneNumberInputEditTextView;
    AppCompatEditText doctorInfoInputEditTextView;

    AppCompatButton nextActionButton;

    String address, nin, phoneNumber, doctorInfo;
    String addressP, ninP, phoneNumberP, doctorInfoP;

    UserPersonalInformation userPersonalInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        setupToolbar("Personal Information", getResources().getColor(R.color.dark_grey_home), Color.WHITE);
        setStatusBarColor(getResources().getColor(R.color.dark_grey_home));
        setUpUI();
        setUpPersonalInformation();

    }


    private void setupToolbar(String title, int color, int iconColor) {
        // set up the toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(color);
        toolbar.setNavigationIcon(getNavigationIcon(iconColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (title == null) {
            //set the back arrow in the toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(title);
        }
    }

    public void setStatusBarColor(int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(color);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        }
    }

    protected Drawable getNavigationIcon(@ColorInt int iconColor) {
        return new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back)
                .color(iconColor)
                .actionBar();
    }


    private void setUpUI() {

        addressTextInputLayout = (TextInputLayout) findViewById(R.id.addressTextInputLayout);
        ninTextInputLayout = (TextInputLayout) findViewById(R.id.ninTextInputLayout);
        phoneNumberTextInputLayout = (TextInputLayout) findViewById(R.id.phoneNumberTextInputLayout);
        doctorInfoTextInputLayout = (TextInputLayout) findViewById(R.id.doctorInfoTextInputLayout);

        addressInputEditTextView = (AppCompatEditText) findViewById(R.id.addressInputEditTextView);
        ninInputEditTextView = (AppCompatEditText) findViewById(R.id.ninInputEditTextView);
        phoneNumberInputEditTextView = (AppCompatEditText) findViewById(R.id.phoneNumberInputEditTextView);
        doctorInfoInputEditTextView = (AppCompatEditText) findViewById(R.id.doctorInfoInputEditTextView);

        nextActionButton = (AppCompatButton) findViewById(R.id.nextActionButton);

        nextActionButton.setOnClickListener(nextActionButtonListener);

    }

    private void setUpPersonalInformation() {

        userPersonalInformation = new UserPersonalInformation(getApplicationContext());
        HashMap<String, String> user = userPersonalInformation.getUserDetails();

        addressP = user.get(UserPersonalInformation.KEY_ADDRESS);
        ninP = user.get(UserPersonalInformation.KEY_NIN);
        phoneNumberP = user.get(UserPersonalInformation.KEY_PHONENUMBER);
        doctorInfoP = user.get(UserPersonalInformation.KEY_DOCTORINFO);

        addressInputEditTextView.setText(addressP);
        ninInputEditTextView.setText(ninP);
        phoneNumberInputEditTextView.setText(phoneNumberP);
        doctorInfoInputEditTextView.setText(doctorInfoP);
    }

    View.OnClickListener nextActionButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            address = addressInputEditTextView.getText().toString();
            nin = ninInputEditTextView.getText().toString();
            phoneNumber = phoneNumberInputEditTextView.getText().toString();
            doctorInfo = doctorInfoInputEditTextView.getText().toString();

            addressTextInputLayout.setError(null);
            ninTextInputLayout.setError(null);
            phoneNumberTextInputLayout.setError(null);
            doctorInfoTextInputLayout.setError(null);


            if (address.length() == 0) {
                addressTextInputLayout.setError("Please enter a valid address!");
                return;
            }

            if (nin.length() == 0 || nin.length() != 11) {
                ninTextInputLayout.setError("Please enter a valid NIN!");
                return;
            }

            if (phoneNumber.length() == 0) {
                phoneNumberTextInputLayout.setError("Please enter a valid phone number!");
                return;
            }

            if (doctorInfo.length() == 0) {
                doctorInfoTextInputLayout.setError("Please enter a valid doctor information!");
                return;
            }

            //TODO call save the data here...

            if (!address.equals(addressP) || !nin.equals(ninP) || !phoneNumber.equals(phoneNumberP) || !doctorInfo.equals(doctorInfoP)) {

                userPersonalInformation.logoutUser();
                userPersonalInformation = null;

                userPersonalInformation = new UserPersonalInformation(getApplicationContext());
                userPersonalInformation.createUserSession(address, nin, phoneNumber, doctorInfo);

                Toast.makeText(getApplicationContext(), "Personal information updated!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "No new information added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
