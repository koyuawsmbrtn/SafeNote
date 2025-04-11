package space.koyu.safenote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class PanicReceiver extends BroadcastReceiver {
    private static final String PREFS_NAME = "SafeNotePrefs";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("info.guardianproject.panic.action.TRIGGER".equals(intent.getAction())) {
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.commit(); // Using commit() instead of apply() for immediate execution
            
            // Force stop the app
            Intent closeIntent = new Intent(Intent.ACTION_MAIN);
            closeIntent.addCategory(Intent.CATEGORY_HOME);
            closeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(closeIntent);
        }
    }
}