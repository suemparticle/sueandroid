package com.sue.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Debug;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.mparticle.MParticle;
import com.mparticle.MParticleOptions;
import com.mparticle.MParticle.LogLevel;
import com.mparticle.identity.IdentityApi;
import com.mparticle.identity.IdentityApiRequest;
import com.mparticle.identity.IdentityApiResult;
import com.mparticle.identity.IdentityHttpResponse;
import com.mparticle.identity.MParticleUser;
import com.mparticle.identity.TaskFailureListener;
import com.mparticle.identity.TaskSuccessListener;
import com.mparticle.identity.BaseIdentityTask;
import com.mparticle.AttributionListener;
import com.mparticle.AttributionResult;
//import com.mparticle.kits.AppsFlyerKit;
import com.mparticle.AttributionError;

import android.net.Uri;
import com.appboy.support.AppboyLogger;
import com.mparticle.kits.AppsFlyerKit;
import com.mparticle.kits.BranchMetricsKit;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AFLogger;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;

import androidx.annotation.NonNull;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et=(EditText)findViewById(R.id.email);
        final EditText tv=(EditText)findViewById(R.id.username);

        //Log.d("userbeforelogin",MParticle.getInstance().Identity().getCurrentUser().getUserIdentities().toString());

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Logged In");

                Intent activity2Intent = new Intent(getApplicationContext(), HelloActivity.class);
                startActivity(activity2Intent);

                MParticleUser user = MParticle.getInstance().Identity().getCurrentUser();

                //Log.d("userafterlogin",user.getUserIdentities().toString());

//            IdentityApiRequest apiRequest = user == null
//                    ? IdentityApiRequest.withEmptyUser().customerId("fair5").build()
//                    : IdentityApiRequest.withUser(user).customerId("fair5").build();

                //MYTEST
                IdentityApiRequest apiRequest = IdentityApiRequest.withEmptyUser()
                        .customerId("fair6")
                        .build();

                MParticle.getInstance().Identity().login(apiRequest)
                        .addFailureListener(new TaskFailureListener() {
                            @Override
                            public void onFailure(IdentityHttpResponse identityHttpResponse) {
                                //device may be offline and request should be retried - see below.
                                Log.d("login","MParticle login failure");
                            }
                        })
                        .addSuccessListener(new TaskSuccessListener() {
                            @Override
                            public void onSuccess(IdentityApiResult identityApiResult) {
                                //Continue with login, and you can also access the new/updated user:
                                //MParticleUser user = identityApiResult.getUser()
                                Log.d("login","MParticle login success");
                            }
                        });
//                if(et.getText().toString().matches("") || tv.getText().toString().matches("")){
//                    IdentityApiRequest identityRequest = IdentityApiRequest.withEmptyUser()
//                            //the IdentityApiRequest provides several convenience methods for common identity types
//                            //.userIdentity(MParticle.IdentityType.Alias, "braze3")
//                            //.email("braze4@example.com")
//                            //.customerId("braze4")
//                            .build();
//                    MParticle.getInstance().Identity().login(identityRequest);
//                } else {
//                    IdentityApiRequest identityRequest = IdentityApiRequest.withEmptyUser()
//                            //the IdentityApiRequest provides several convenience methods for common identity types
//                            //.email(et.getText().toString())
//                            //.customerId(tv.getText().toString())
//                            //.userIdentity(MParticle.IdentityType.Facebook, "1053234")
////                            .userAliasHandler{ previousUser, newUser ->
////                            newUser.userAttributes = previousUser.userAttributes}
//                            .build();
//                    MParticle.getInstance().Identity().login(identityRequest);
//                }
            }

        });

        // FAIR //
//        private void trackIdentityUpdate(TrackEvent event) {
//            MParticleUser user = MParticle.getInstance().Identity().getCurrentUser();
//            IdentityApiRequest modifyRequest = user == null
//                    ? IdentityApiRequest.withEmptyUser()
//                    .email(event.getParameterValueByKey(EventParamKey.EMAIL))
//                    .build()
//                    : IdentityApiRequest.withUser(user)
//                    .email(event.getParameterValueByKey(EventParamKey.EMAIL))
//                    .build();
//            MParticle.getInstance().Identity().modify(modifyRequest);
//        }
//        public void trackLogin(TrackEvent loginEvent) {
//            MParticleUser user = MParticle.getInstance().Identity().getCurrentUser();
//            String accountNumber = loginEvent == null ? null : loginEvent.getParameterValueByKey(EventParamKey.FAIR_ACCOUNT_NUMBER);
//            IdentityApiRequest apiRequest = user == null
//                    ? IdentityApiRequest.withEmptyUser().customerId(accountNumber).build()
//                    : IdentityApiRequest.withUser(user).customerId(accountNumber).build();
//            MParticle.getInstance().Identity().login(apiRequest)
//                    .addFailureListener(new TaskFailureListener() {
//                        @Override
//                        public void onFailure(IdentityHttpResponse identityHttpResponse) {
//                            //device may be offline and request should be retried - see below.
//                            Log.d("logintest","MParticle login failure");
//                        }
//                    })
//                    .addSuccessListener(new TaskSuccessListener() {
//                        @Override
//                        public void onSuccess(IdentityApiResult identityApiResult) {
//                            //Continue with login, and you can also access the new/updated user:
//                            //MParticleUser user = identityApiResult.getUser()
//                            Log.d("logintest","MParticle login success");
//                        }
//                    });
//        }

        // FAIR //

        BaseIdentityTask identifyTask = new BaseIdentityTask()
                .addFailureListener(new TaskFailureListener() {
                    @Override
                    public void onFailure(IdentityHttpResponse identityHttpResponse) {
                        //handle failure - see below
                    }
                }).addSuccessListener(new TaskSuccessListener() {
                    @Override
                    public void onSuccess(IdentityApiResult identityApiResult) {
                        MParticleUser user = identityApiResult.getUser();
                    }
                });

        //Appsflyer kit attribution listener
        AttributionListener myListener = new AttributionListener() {
            @Override
            public void onResult(@NonNull AttributionResult attributionResult) {
                Log.d("mparticle-attr"," onResult = $attributionResult");
                if (attributionResult.getServiceProviderId() == MParticle.ServiceProviders.APPSFLYER) {
                    JSONObject attributionParams = attributionResult.getParameters();
                    if (attributionParams != null && attributionParams.has(AppsFlyerKit.INSTALL_CONVERSION_RESULT)) {
                        Log.d("Conversion result", attributionParams.toString());
                    } else if (attributionParams != null && attributionParams.has(AppsFlyerKit.APP_OPEN_ATTRIBUTION_RESULT)) {
                        Log.d("App open result", attributionParams.toString());
                    }
                }
            }

            @Override
            public void onError(@NonNull AttributionError attributionError) {
                Log.d("Attribution Data Error", attributionError.toString());
            }
        };

        //Braze Verbose Logging
        //AppboyLogger.setLogLevel(Log.VERBOSE);

        //Debug.startMethodTracing("sample");

        //AppsFlyerLib.getInstance().setLogLevel(AFLogger.LogLevel.INFO);
        // Branch logging for debugging
        //Branch.enableDebugMode();
        // Branch object initialization
        //Branch.getAutoInstance(this);

        MParticleOptions options = MParticleOptions.builder(this)
                .credentials("APIKEY","APISECRET")
                .environment(MParticle.Environment.Development)
                .logLevel(MParticle.LogLevel.VERBOSE)
                .attributionListener(myListener)
                .uploadInterval(60)
                //.dataplan("plan2", 1)
                .build();
        MParticle.start(options);

    }


}