package com.example.one_fit_all;

import androidx.appcompat.app.AppCompatActivity;

//fitbit
import com.fitbit.authentication.AuthenticationHandler;
import com.fitbit.authentication.AuthenticationManager;
import com.fitbit.authentication.AuthenticationResult;
import com.fitbit.authentication.Scope;
import com.example.one_fit_all.databinding.ActivityFitbitRootBinding;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.Set;


public class fitbit_rootActivity extends AppCompatActivity implements AuthenticationHandler{

    private ActivityFitbitRootBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fitbit_root);
        Log.d("fitbit_root","---------------Got to fitbitRoot");


    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.setLoading(false);
        /**
         *  (Look in FitbitAuthApplication for Step 1)
         */
        Log.d("fitbit_root","---------------step 1");
        FitbitAuthApplication fitbitAuthApplication = new FitbitAuthApplication();
        /**
         *  2. If we are logged in, go to next activity
         *      Otherwise, display the login screen
         */
        if (AuthenticationManager.isLoggedIn()) {
            onLoggedIn();
        }
    }

    public void onLoggedIn() {
        Intent intent = fitbit_userData.newIntent(this);
        startActivity(intent);
        binding.setLoading(false);
    }

    public void onLoginClick(View view) {
        binding.setLoading(true);
        /**
         *  3. Call login to show the login UI
         */
        AuthenticationManager.login(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         *  4. When the Login UI finishes, it will invoke the `onActivityResult` of this activity.
         *  We call `AuthenticationManager.onActivityResult` and set ourselves as a login listener
         *  (via AuthenticationHandler) to check to see if this result was a login result. If the
         *  result code matches login, the AuthenticationManager will process the login request,
         *  and invoke our `onAuthFinished` method.
         *
         *  If the result code was not a login result code, then `onActivityResult` will return
         *  false, and we can handle other onActivityResult result codes.
         *
         */

        if (!AuthenticationManager.onActivityResult(requestCode, resultCode, data, this)) {
            // Handle other activity results, if needed
        }

    }

    @Override
    public void onAuthFinished(AuthenticationResult authenticationResult) {
        binding.setLoading(false);

        /**
         * 5. Now we can parse the auth response! If the auth was successful, we can continue onto
         *      the next activity. Otherwise, we display a generic error message here
         */
        if (authenticationResult.isSuccessful()) {
            onLoggedIn();
        } else {
            displayAuthError(authenticationResult);
        }
    }

    private void displayAuthError(AuthenticationResult authenticationResult) {
        String message = "";

        switch (authenticationResult.getStatus()) {
            case dismissed:
                message = "Login dismissed or no scopes selected";
                break;
            case error:
                message = authenticationResult.getErrorMessage();
                break;
            case missing_required_scopes:
                Set<Scope> missingScopes = authenticationResult.getMissingScopes();
                String missingScopesText = TextUtils.join(", ", missingScopes);
                message = "Error logging in. Missing the following required scopes: "+ missingScopesText;
                break;
        }

        new AlertDialog.Builder(this)
                .setTitle("Login")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create()
                .show();
    }


}