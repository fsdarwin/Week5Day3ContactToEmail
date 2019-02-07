package com.example.week5day3contacttoemail;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.week5day3contacttoemail.managers.ContactsManager;
import com.example.week5day3contacttoemail.managers.PermissionsManager;
import com.example.week5day3contacttoemail.pojos.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionsManager.IPermissionManager,
        ContactsManager.IContractManager {
    PermissionsManager permissionsManager;
    EditText etVName;
    TextView tvVeMailId;
    List<Contact> listContacts;
    String email;
    public static final String TAG = "FRANK: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionsManager = new PermissionsManager(this, this);

        permissionsManager.checkPermission();
        etVName = findViewById(R.id.etName);
        tvVeMailId = findViewById(R.id.tvEmailId);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.checkResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionResult(boolean isGranted) {
        Log.d("TAG", "onPermissionResult: YEP");
        if (isGranted) {
            getContacts();
        } else {
            Toast.makeText(this, "Can not process", Toast.LENGTH_SHORT).show();
        }
    }

    public void getContacts() {
        ContactsManager contactsManager = new ContactsManager(this);
        contactsManager.getContacts();


    }

    @Override
    public void onContactsRecieved(List<Contact> contactsList) {
        listContacts = contactsList;
        for (Contact contact : contactsList) {
            Log.d("TAG", "onContactsReceived: " + contact.toString());
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                for (Contact contact : listContacts) {
                    Log.d("TAG", "onClick btnSearch: " + contact.toString());
                    Log.d(TAG, "onClick: contact name " + etVName.getText().toString());
                    if (contact.getName().equals(etVName.getText().toString())) {
                        email = contact.getEmailList().toString();
                        email.replace("[", "");
                        email.replace("]", "");
                        Log.d(TAG, "onClick: " + email);
                        tvVeMailId.setText(email);
                    }
                }
                break;
            case R.id.btnSend:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                email = "mailto:" + email;
                emailIntent.setData(Uri.parse(email));
                startActivity(emailIntent);
                break;
        }
    }
}
