package com.example.sqlitedemoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editId, editFirstName, editLastName, editCourse, editMarks, editCredits;
    Button submitButton;

    /* Database Declaration */
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFirstName = findViewById(R.id.firstName);
        editLastName = findViewById(R.id.lastName);
        editCourse = findViewById(R.id.course);
        editMarks = findViewById(R.id.marks);
        editCredits = findViewById(R.id.credits);
        submitButton = findViewById(R.id.submitButton);

        //Focusing the control to the id field
        editId.requestFocus();

        //Creating database
        db = openOrCreateDatabase("School", Context.MODE_PRIVATE, null);

        //Create table student
        db.execSQL("CREATE TABLE IF NOT EXISTS students(id INTEGER PRIMARY KEY, first_name VARCHAR, last_name VARCHAR, course VARCHAR" +
                ", marks VARCHAR, credits VARCHAR)");

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                db.execSQL("INSERT INTO students (first_name, last_name, course, marks, credits) VALUES ('" + editFirstName.getText() +"','" + editLastName.getText()
                        + "','" + editCourse.getText() +"','" + editMarks.getText() + "','" + editCredits.getText() + "');");
                Toast.makeText(MainActivity.this, "Record Added", Toast.LENGTH_SHORT).show();

                //Display records after being added
                Cursor cal = db.rawQuery("SELECT * FROM students", null);
                if(cal.getCount() == 0){
                    showStatus ("ERROR", "NO DATA");
                    return;
                }

                StringBuffer bfr;

                bfr = showRecords(cal);
                showStatus("AFTER INSERTION RECORD", bfr.toString());
                cal.close();
            }
        });



    }

    private void showStatus(String title, String message){
        //Import AlertDialog Builder v7
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    private StringBuffer showRecords(Cursor cal){
        StringBuffer bfr = new StringBuffer();
        bfr.append("DATA" + "\n");
        bfr.append("Id : " + cal.getString(0));
        bfr.append("First Name : " + cal.getString(1));
        bfr.append("Last Name : " + cal.getString(2));
        bfr.append("Course : " + cal.getString(3));
        bfr.append("Marks : " + cal.getString(4));
        bfr.append("Credits : " + cal.getString(5));

        return bfr;
    }
}