package edu.nyu.scps.mark.cursoradapter;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listView);
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        CursorLoader cursorLoader = new CursorLoader(this, uri, projection, null, null, sortOrder);
        Cursor cursor = cursorLoader.loadInBackground();

        /*SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {ContactsContract.Contacts.DISPLAY_NAME},
                new int[] {android.R.id.text1},
                0	//Don't need any flags.
        );*/

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.simple_list_item_2,
                cursor,
                new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID},
                new int[]    {R.id.text1,                             R.id.text2},
                0	//flags
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the selectedString from the Cursor in the CursorAdapter.
                Cursor cursor = (Cursor)parent.getItemAtPosition(position); //downcast
                int indexDisplayName = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
                String selectedString = cursor.getString(indexDisplayName);

                //Another way to get the selectedString:
                //TextView textView = (TextView)view;
                //CharSequence charSequence = textView.getText();
                //String selectedString = charSequence.toString();

                String s = "Selected " + selectedString
                        + ", position = " + position
                        + ", id = " + id + ".";

                Toast toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
