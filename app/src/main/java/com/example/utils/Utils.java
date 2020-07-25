package com.example.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Workouts;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.security.AccessController.getContext;

public class Utils {

    public static Pattern FIRST_AND_LAST_NAME_PATTERN = Pattern.compile("[a-zA-Z]*[\\s][a-zA-Z].*");

    //at least 6 characters with at least 1 digit, 1 lower case letter, 1 upper case letter, and no whitespaces.
    public static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");

    public static boolean isFirstAndLastNameValid(String firstAndLastNameInput, TextInputEditText firstAndLastName) {
        if (firstAndLastNameInput.isEmpty()) {
            firstAndLastName.setError("Field can't be empty!");
            return false;
        } else if (!FIRST_AND_LAST_NAME_PATTERN.matcher(firstAndLastNameInput).matches()) {
            firstAndLastName.setError("Field is not valid! Please enter your name like \"First Last\"!");
            return false;
        }
        return true;
    }

    public static boolean isEmailAddressValid(String emailAddressInput, TextInputEditText emailAddress) {

        if (emailAddressInput.isEmpty()) {
            emailAddress.setError("Field can't be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressInput).matches()) {
            emailAddress.setError("Please enter a valid email address!");
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(String passwordInput, String confirmPasswordInput, TextInputEditText password, TextInputEditText confirmPassword) {
        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak! It should contain at least 6 characters with at least one digit, one lower case letter, one upper case letter, and now whitespaces.");
            return false;
        } else if (confirmPasswordInput.isEmpty()) {
            confirmPassword.setError("Pleare repeat your password here!");
            return false;
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            confirmPassword.setError("Passwords are not equal!");
            return false;
        }

        return true;
    }

    public static boolean isPasswordValid(String passwordInput, TextInputEditText password) {
        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty!");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak! It should contain at least 6 characters with at least one digit, one lower case letter, one upper case letter, and now whitespaces.");
            return false;
        }
        return true;
    }

    public static boolean isGenderValid(RadioButton radioFemale, RadioButton radioMale, TextView gender) {
        if (!(radioFemale.isChecked() || radioMale.isChecked())) {
            gender.setError("Please choose your gender!");
            return false;
        }
        gender.setError(null);
        return true;
    }

    public static boolean isPrimarySportValid (Spinner spinner){
        String choice = spinner.getSelectedItem().toString();
        if (choice.equals("Select your favorite sport:")){
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError("Select your primary sport!");
            return false;
        }
        return true;
    }

    public static boolean isSecondarySportValid (Spinner spinner){
        String choice = spinner.getSelectedItem().toString();
        if (choice.equals("Select your favorite sport:")){
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError("Select your secondary sport!");
            return false;
        }
        return true;
    }
    public static boolean isHeightValid(String heightInput, TextInputEditText height) {
        if (heightInput.isEmpty()) {
            height.setError("Field can't be empty!");
            return false;
        }
        try {
            double heightNumber = Double.parseDouble(heightInput);
            if (heightNumber < 80.0) {
                height.setError("Height is not valid! Please enter your height in cm.");
                return false;
            }
        } catch (NumberFormatException e) {
            height.setError("Height is not valid! Please enter your height in cm.");
            return false;
        }

        return true;
    }

    public static boolean isWeightValid(String weightInput, TextInputEditText weight) {
        if (weightInput.isEmpty()) {
            weight.setError("Field can't be empty!");
            return false;
        }

        try {
            double weightNumber = Double.parseDouble(weightInput);
        } catch (NumberFormatException e) {
            weight.setError("Weight is not valid! Please enter your weight in kg.");
            return false;
        }

        return true;
    }

    public static boolean isAgeValid(String ageInput, TextInputEditText age) {

        if (ageInput.isEmpty()) {
            age.setError("Field can't be empty!");
            return false;
        }

        try {
            int ageNumber = Integer.parseInt(ageInput);
        } catch (NumberFormatException e) {
            age.setError("Age is not valid!");
            return false;
        }
        return true;
    }

    public static void setCircleAvatar(Activity activity, Drawable image, ImageView destination){
        Glide.with(activity)
                .load(image)
                .apply(new RequestOptions().circleCrop())
                .into(destination);
    }

    public static List<Drawable> getAvatars(Context context) {
        List<Drawable> avatars = new ArrayList<>();
        avatars.add(ContextCompat.getDrawable(context, R.drawable.avatar_1));
        avatars.add(ContextCompat.getDrawable(context, R.drawable.avatar_2));
        avatars.add(ContextCompat.getDrawable(context, R.drawable.avatar_3));
        avatars.add(ContextCompat.getDrawable(context, R.drawable.avatar_4));
        avatars.add(ContextCompat.getDrawable(context, R.drawable.avatar_5));
        return avatars;
    }

    public static List<Drawable> getEventsPictures(Context context) {
        List<Drawable> eventsPictures = new ArrayList<>();
        eventsPictures.add(ContextCompat.getDrawable(context, R.drawable.img_running));
        eventsPictures.add(ContextCompat.getDrawable(context, R.drawable.img_biking));
        eventsPictures.add(ContextCompat.getDrawable(context, R.drawable.img_tennis));
        eventsPictures.add(ContextCompat.getDrawable(context, R.drawable.img_running_1));
        eventsPictures.add(ContextCompat.getDrawable(context, R.drawable.img_motors));
        return eventsPictures;
    }
}
