package application.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DBHelper myDB;
    EditText e1,eText,e11;
    Spinner spinner2,spinner;
    Button button,button2,button4,button3,button6,button8;

    DatePickerDialog picker;

   // Button btnGet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.e1);
        e11 = findViewById(R.id.e11);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button3 = findViewById(R.id.button3);
        button6 = findViewById(R.id.button6);
        button8 = findViewById(R.id.button8);

        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);


        myDB = new DBHelper(this);


        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Display.class);
                startActivity(intent);
            }
        });

        eText=(EditText) findViewById(R.id.editText1);

        ViewAll();
        updateData1();
        deleteInfo1();
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        //btnGet=(Button)findViewById(R.id.button1);
       // btnGet.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {
                //eText.setText(eText.getText());
           // }
       // });




    }

    public void addData(View view){

        DBHelper dbHandler = new DBHelper(this);
        if (e1.getText().toString().isEmpty()){

            //Toast.makeText(this,"Enter Subject", Toast.LENGTH_SHORT).show();
            e1.setError("FIELD CANNOT BE EMPTY");


        }else if(eText.getText().toString().isEmpty()) {

            eText.setError("Enter Date");
        }else if (spinner.getSelectedItem().toString().isEmpty()) {

            Toast.makeText(this,"Choose a subject", Toast.LENGTH_SHORT).show();


        }else {
            long val = dbHandler.insertContact(e1.getText().toString(), spinner.getSelectedItem().toString(), eText.getText().toString(), spinner2.getSelectedItem().toString());
            Toast.makeText(this,"Appoinment Added Successfully", Toast.LENGTH_SHORT).show();
        }
        //String Name = e1.getText().toString();

       /* if(TextUtils.isEmpty(Name)){

            //e1.requestFocus();
            e1.setError("FIELD CANNOT BE EMPTY");
            return;


        }else if(!Name.matches("[a-zA-Z ]+")) {

            e1.requestFocus();
            e1.setError("Enter Only Alphabetical Character");
            return;
        }else if(val>0){

            Toast.makeText(this,"Appoinment Added Successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Appoinment Not Added Successfully", Toast.LENGTH_SHORT).show();
        }*/

    }

    public void getcount1(View view) {
        DBHelper dbHandler = new DBHelper(this);

        int y = dbHandler.getcount1();
        if (y > 0) {
            Toast.makeText(this, +(dbHandler.getcount1()) + " Appoinments  Entered Successfully", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, " Appoinments does not count Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteInfo1() {

        final DBHelper dbHandler = new DBHelper(this);
        //validation

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer deleteRows = dbHandler.deleteInfo1(e11.getText().toString());

                    if (deleteRows > 0){


                        Toast.makeText(MainActivity.this, e1.getText().toString() + " Deleted Successfully", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(MainActivity.this, e1.getText().toString() + " Deleted Not Successfully", Toast.LENGTH_SHORT).show();


                    }
                }
            });



            //Toast.makeText(this, e1.getText().toString() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
        }


    public void updateData1(){

        final DBHelper dbHandler = new DBHelper(this);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isUpdate = dbHandler.updateInfo1(e11.getText().toString(),e1.getText().toString(),spinner.getSelectedItem().toString(),eText.getText().toString(),spinner2.getSelectedItem().toString());

                if (isUpdate == true){
                    Toast.makeText(MainActivity.this,"Data Updated Successfully",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ViewAll(){

        final DBHelper dbHandler = new DBHelper(this);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
                                       public void onClick(View view) {




                                           Cursor res = dbHandler.getAllData();
                                           if (res.getCount() == 0){

                                               showMessage("Error","Nothing Found");
                                               return;
                                           }

                                           StringBuffer buffer = new StringBuffer();
                                           while (res.moveToNext()){
                                               buffer.append("id :"+ res.getString(0)+"\n");
                                               buffer.append("number :"+ res.getString(1)+"\n");
                                               buffer.append("subject :"+ res.getString(2)+"\n");
                                               buffer.append("date :"+ res.getString(3)+"\n");
                                               buffer.append("mode :"+ res.getString(4)+"\n\n");

                                           }

                                           showMessage("Data",buffer.toString());
                                       }
                                   }
        );

    }
    public void showMessage(String title,String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}