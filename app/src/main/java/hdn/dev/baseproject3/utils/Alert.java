package hdn.dev.baseproject3.utils;

import android.app.AlertDialog;
import android.content.Context;

import hdn.dev.baseproject3.R;

public class Alert {
    public Alert(Context context,String message, int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (type == 1) {
            builder.setTitle("Success")
                    .setIcon(R.drawable.checked)
                    .setMessage(message)
                    .setPositiveButton("Done", (dialog, which) -> {
                        // Do something when OK button is clicked
                    });
        } else if (type == 0) {
            builder.setTitle("Failure")
                    .setIcon(R.drawable.cancel)
                    .setMessage(message)
                    .setPositiveButton("Done", (dialog, which) -> {
                        // Do something when OK button is clicked
                    });
        }


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
