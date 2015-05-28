package cs499.knowsie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGroupActivity extends Activity {
    private static final String TAG = "AddGroupActivity";
    private EditText groupNameField;
    private EditText twitterUserField;
    private EditText instagramUserField;
    private Button okButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        groupNameField = (EditText) findViewById(R.id.group_name);

        twitterUserField = (EditText) findViewById(R.id.twitter_user);

        instagramUserField = (EditText) findViewById(R.id.instagram_user);

        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("groupName", groupNameField.getText().toString());
                returnIntent.putExtra("twitterUser", twitterUserField.getText().toString());
                returnIntent.putExtra("instagramUser", instagramUserField.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}
