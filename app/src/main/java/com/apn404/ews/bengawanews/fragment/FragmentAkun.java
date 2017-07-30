package com.apn404.ews.bengawanews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apn404.ews.bengawanews.LoginActivity;
import com.apn404.ews.bengawanews.R;
import com.apn404.ews.bengawanews.helper.SessionManager;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAkun extends Fragment {

    Button logout;
    View view;
    SessionManager session;
    protected String stremail;
    TextView textView;

    public FragmentAkun() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_akun, container, false);

        this.session = new SessionManager(getActivity());
        //ngecek login e
        this.session.checkLogin();
//betul dapet email
        stremail = this.session.getUserDetails().get(SessionManager.KEY_EMAIL);

        session = new SessionManager(getActivity().getApplicationContext());
        if (stremail!=null){
            Toast.makeText(FragmentAkun.this.getActivity(),stremail, Toast.LENGTH_SHORT).show();
        }

        if (AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }


        logout = (Button) view.findViewById(R.id.logout);
        textView = (TextView) view.findViewById(R.id.email);
        textView.setText(stremail);

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goLoginScreen();
            }
        });
        return view;
    }

    private void goLoginScreen() {
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}