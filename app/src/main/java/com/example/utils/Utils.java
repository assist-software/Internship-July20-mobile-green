package com.example.utils;

import android.app.Activity;
import android.content.Context;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern FIRST_AND_LAST_NAME_PATTERN = Pattern.compile("[a-zA-Z]*[\\s][a-zA-Z].*");
    //at least 6 characters with at least 1 digit, 1 lower case letter, 1 upper case letter, and no whitespaces.
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");
    private static final String EMPTY_FIELD_ERROR = "Field can't be empty!";
    private static final String FIRST_AND_LAST_NAME_ERROR = "Field is not valid! Please enter your name like \"First Last\"!";
    private static final String SELECT_FAVORITE_SPORT = "Select your favorite sport:";
    private static final String EMAIL_ADDRESS_NOT_VALID = "Please enter a valid email address!";
    private static final String PASSWORD_TOO_WEAK = "Password too weak! It should contain at least 6 characters with at least one digit, one lower case letter, one upper case letter, and now whitespaces.";
    private static final String PASSWORD_REPEAT = "Pleare repeat your password here!";
    private static final String PASSWORDS_NOT_EQUAL = "Passwords are not equal!";
    private static final String CHOOSE_GENDER = "Please choose your gender!";
    private static final String SELECT_PRIMARY_SPORT = "Select your primary sport!";
    private static final String SELECT_SECONDARY_SPORT = "Select your secondar sport!";
    private static final String EVENT_TEXT = "Event";
    private static final String SELECT_EVENT = "Select your event!";
    private static final String WORKOUT_EFFECTIVENESS_TEXT = "Workout Effectiveness";
    private static final String SELECT_WORKOUT_EFFECTIVENESS = "Select your workout effectiveness!";
    private static final String DURATION_NOT_VALID = "Duration is not valid! Please enter your duration as number of minutes.";
    private static final String HEART_RATE_NOT_VALID = "Heart rate is not valid! Please enter your heart rate as a number.";
    private static final String CALORIES_NOT_VALID = "Calories are not valid! Please enter your calories as a number.";
    private static final String AVERAGE_SPEED_NOT_VALID = "Average speed is not valid! Please enter your average speed as number of km/h.";
    private static final String DISTANCE_NOT_VALID = "Distance is not valid! Please enter distance as number of km.";
    private static final String HEIGHT_NOT_VALID = "Height is not valid! Please enter your height in cm.";
    private static final String WEIGHT_NOT_VALID = "Weight is not valid! Please enter your weight in kg.";
    private static final String AGE_NOT_VALID = "Age is not valid!";

    public static boolean isFirstAndLastNameValid(String firstAndLastNameInput, TextInputEditText firstAndLastName) {
        if (firstAndLastNameInput.isEmpty()) {
            firstAndLastName.setError(EMPTY_FIELD_ERROR);
            return false;
        } else if (!FIRST_AND_LAST_NAME_PATTERN.matcher(firstAndLastNameInput).matches()) {
            firstAndLastName.setError(FIRST_AND_LAST_NAME_ERROR);
            return false;
        }
        return true;
    }

    public static boolean isEmailAddressValid(String emailAddressInput, TextInputEditText emailAddress) {

        if (emailAddressInput.isEmpty()) {
            emailAddress.setError(EMPTY_FIELD_ERROR);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressInput).matches()) {
            emailAddress.setError(EMAIL_ADDRESS_NOT_VALID);
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(String passwordInput, String confirmPasswordInput, TextInputEditText password, TextInputEditText confirmPassword) {
        if (passwordInput.isEmpty()) {
            password.setError(EMPTY_FIELD_ERROR);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError(PASSWORD_TOO_WEAK);
            return false;
        } else if (confirmPasswordInput.isEmpty()) {
            confirmPassword.setError(PASSWORD_REPEAT);
            return false;
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            confirmPassword.setError(PASSWORDS_NOT_EQUAL);
            return false;
        }

        return true;
    }

    public static boolean isPasswordValid(String passwordInput, TextInputEditText password) {
        if (passwordInput.isEmpty()) {
            password.setError(EMPTY_FIELD_ERROR);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError(PASSWORD_TOO_WEAK);
            return false;
        }
        return true;
    }

    public static boolean isGenderValid(RadioButton radioFemale, RadioButton radioMale, TextView gender) {
        if (!(radioFemale.isChecked() || radioMale.isChecked())) {
            gender.setError(CHOOSE_GENDER);
            return false;
        }
        gender.setError(null);
        return true;
    }

    public static boolean isPrimarySportValid(Spinner spinner) {
        String choice = spinner.getSelectedItem().toString();
        if (choice.equals(SELECT_FAVORITE_SPORT)) {
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError(SELECT_PRIMARY_SPORT);
            return false;
        }
        return true;
    }

    public static boolean isSecondarySportValid(Spinner spinner) {
        String choice = spinner.getSelectedItem().toString();
        if (choice.equals(SELECT_FAVORITE_SPORT)) {
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError(SELECT_SECONDARY_SPORT);
            return false;
        }
        return true;
    }

    public static boolean isEventValid(Spinner spinner) {
        String choice = spinner.getSelectedItem().toString();
        if (choice.equals(EVENT_TEXT)) {
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError(SELECT_EVENT);
            return false;
        }
        return true;
    }

    public static boolean isWorkoutEffectivenessValid(Spinner spinner) {
        String choice = spinner.getSelectedItem().toString();
        if (choice.equals(WORKOUT_EFFECTIVENESS_TEXT)) {
            TextView errotText = (TextView) spinner.getSelectedView();
            errotText.setError(SELECT_WORKOUT_EFFECTIVENESS);
            return false;
        }
        return true;
    }

    public static boolean isDurationValid(String durationInput, TextInputEditText duration) {
        if (durationInput.isEmpty()) {
            duration.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        try {
            Double.parseDouble(durationInput);
        } catch (NumberFormatException e) {
            duration.setError(DURATION_NOT_VALID);
            return false;
        }
        return true;
    }

    public static boolean isHeartRateValid(String hearRateInput, TextInputEditText heartRate) {
        if (hearRateInput.isEmpty()) {
            heartRate.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        try {
            Double.parseDouble(hearRateInput);
        } catch (NumberFormatException e) {
            heartRate.setError(HEART_RATE_NOT_VALID);
            return false;
        }
        return true;
    }

    public static boolean isCaloriesValid(String caloriesInput, TextInputEditText calories) {
        if (caloriesInput.isEmpty()) {
            calories.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        try {
            Double.parseDouble(caloriesInput);
        } catch (NumberFormatException e) {
            calories.setError(CALORIES_NOT_VALID);
            return false;
        }
        return true;
    }

    public static boolean isAvgSpeedValid(String avgSpeedInput, TextInputEditText avgSpeed) {
        if (avgSpeedInput.isEmpty()) {
            avgSpeed.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        try {
            Double.parseDouble(avgSpeedInput);
        } catch (NumberFormatException e) {
            avgSpeed.setError(AVERAGE_SPEED_NOT_VALID);
            return false;
        }
        return true;
    }

    public static boolean isDistanceValid(String distanceInput, TextInputEditText distance) {
        if (distanceInput.isEmpty()) {
            distance.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        try {
            Double.parseDouble(distanceInput);
        } catch (NumberFormatException e) {
            distance.setError(DISTANCE_NOT_VALID);
            return false;
        }
        return true;
    }

    public static boolean isHeightValid(String heightInput, TextInputEditText height) {
        if (heightInput.isEmpty()) {
            height.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        try {
            double heightNumber = Double.parseDouble(heightInput);
            if (heightNumber < 50.0 || heightNumber > 300.0) {
                height.setError(HEIGHT_NOT_VALID);
                return false;
            }
        } catch (NumberFormatException e) {
            height.setError(HEIGHT_NOT_VALID);
            return false;
        }

        return true;
    }

    public static boolean isWeightValid(String weightInput, TextInputEditText weight) {
        if (weightInput.isEmpty()) {
            weight.setError(EMPTY_FIELD_ERROR);
            return false;
        }
        try {
            double weightNumber = Double.parseDouble(weightInput);
            if (weightNumber < 10.0 || weightNumber > 500.0) {
                weight.setError(WEIGHT_NOT_VALID);
                return false;
            }
        } catch (NumberFormatException e) {
            weight.setError(WEIGHT_NOT_VALID);
            return false;
        }

        return true;
    }

    public static boolean isAgeValid(String ageInput, TextInputEditText age) {
        if (ageInput.isEmpty()) {
            age.setError(EMPTY_FIELD_ERROR);
            return false;
        }

        try {
            int ageNumber = Integer.parseInt(ageInput);
            if (ageNumber < 0 || ageNumber > 100) {
                age.setError(AGE_NOT_VALID);
                return false;
            }
        } catch (NumberFormatException e) {
            age.setError(AGE_NOT_VALID);
            return false;
        }
        return true;
    }

    public static void setCircleAvatar(Activity activity, Drawable image, ImageView destination) {
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
