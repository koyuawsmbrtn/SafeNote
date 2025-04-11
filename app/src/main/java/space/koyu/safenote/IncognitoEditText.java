package space.koyu.safenote;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;

public class IncognitoEditText extends AppCompatEditText {
    public IncognitoEditText(Context context) {
        super(context);
    }

    public IncognitoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IncognitoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection ic = super.onCreateInputConnection(outAttrs);
        if (outAttrs != null) {
            outAttrs.imeOptions |= EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING;
        }
        return ic;
    }
}
