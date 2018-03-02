package es.usc.citius.servando.calendula;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class UserPersonalInformation {

    // Shared Preferences reference
    SharedPreferences pref;

    //Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;


    // Sharedpref file name
    private static final String PREFER_NAME = "RenalAidPref";

    // Address (make variable public to access from outside)
    public static final String KEY_ADDRESS = "address";

    // NIN (make variable public to access from outside)
    public static final String KEY_NIN = "nin";

    //URL of the of the address to acess
    public static final String KEY_PHONENUMBER = "phonenumber";

    //URL of the of the address to acess
    public static final String KEY_DOCTORINFO = "doctorinfo";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";


    public UserPersonalInformation(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserSession(String address, String nin, String phoneNumber, String doctorInfo) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_ADDRESS, address);

        // Storing email in pref
        editor.putString(KEY_NIN, nin);

        editor.putString(KEY_PHONENUMBER, phoneNumber);

        editor.putString(KEY_DOCTORINFO, doctorInfo);

        // commit changes
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));

        user.put(KEY_NIN, pref.getString(KEY_NIN, null));

        user.put(KEY_PHONENUMBER, pref.getString(KEY_PHONENUMBER, null));

        user.put(KEY_DOCTORINFO, pref.getString(KEY_DOCTORINFO, null));

        return user;
    }


    public void logoutUser() {
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }


}
