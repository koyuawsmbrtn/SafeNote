package space.koyu.safenote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.graphics.Typeface;
import android.view.inputmethod.EditorInfo;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SafeNotePrefs";
    private static final String NOTE_KEY = "note_content";
    private EditText noteField;
    private SharedPreferences prefs;

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Create and configure the EditText
        noteField = new IncognitoEditText(this);
        noteField.setBackgroundColor(0xFF000000);
        noteField.setTextColor(0xFFFFFFFF);
        noteField.setTypeface(Typeface.MONOSPACE);
        noteField.setTextSize(16);
        noteField.setPrivateImeOptions("nm");

        // Wrap the EditText in a ScrollView to prevent overlapping with the status bar
        android.widget.ScrollView scrollView = new android.widget.ScrollView(this);
        scrollView.addView(noteField);
        scrollView.setBackgroundColor(0xFF000000);
        scrollView.setPadding(32, 128, 32, 32);

        // Use a root layout to adjust for keyboard visibility
        android.widget.LinearLayout rootLayout = new android.widget.LinearLayout(this);
        rootLayout.setOrientation(android.widget.LinearLayout.VERTICAL);
        rootLayout.addView(scrollView);
        rootLayout.setPadding(0, 0, 0, 0);
        rootLayout.setBackgroundColor(0xFF000000);
        rootLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
        rootLayout.setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        rootLayout.setFitsSystemWindows(true);

        // Disable screenshots and recent apps preview
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(rootLayout);

        noteField.requestFocus();
        noteField.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        rootLayout.setOnClickListener(v -> {
            noteField.requestFocus();
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
            imm.showSoftInput(noteField, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
            }
        });

        // Adjust layout dynamically using window insets
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (v, insets) -> {
            int keyboardHeight = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.ime()).bottom;
            if (keyboardHeight > 0) { // Keyboard is visible
                scrollView.setPadding(32, 128, 32, keyboardHeight + 32);
            } else { // Keyboard is hidden
                scrollView.setPadding(32, 128, 32, 32);
            }
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            noteField.setCursorVisible(true);
            noteField.setHighlightColor(0x80FFFFFF); // Semi-transparent white for text selection
            noteField.setTextCursorDrawable(androidx.appcompat.R.drawable.abc_text_cursor_material);
            noteField.setTextSelectHandle(androidx.appcompat.R.drawable.abc_text_select_handle_left_mtrl);
            noteField.setTextSelectHandleRight(androidx.appcompat.R.drawable.abc_text_select_handle_right_mtrl);
            noteField.setTextSelectHandleLeft(androidx.appcompat.R.drawable.abc_text_select_handle_left_mtrl);
        }


        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        noteField.setText(loadNote());
        noteField.setSelection(noteField.getText().length());

        noteField.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveNote(s.toString());
                scrollView.post(() -> {
                    scrollView.scrollTo(0, noteField.getBottom());
                });
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void saveNote(String text) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NOTE_KEY, text);
        editor.apply();
    }

    private String loadNote() {
        return prefs.getString(NOTE_KEY, "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNote(noteField.getText().toString());
    }
}