package com.example.simplecalendar;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.Spinner;
        import android.widget.TextView;

        import java.io.IOException;

public class Maincativity2 extends AppCompatActivity {


    String[] DegreeActivity = {"Min","Low", "Middle", "Height", "Extreme"};
    int sex;
    double degreeLevel = 0;
    EditText editTextAge,editTextHeight,editTextWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerDegree);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DegreeActivity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String degree = (String)parent.getItemAtPosition(position);
                switch (degree){
                    case "Min":
                        degreeLevel = 1.2;
                        break;
                    case "Low":
                        degreeLevel = 1.375;
                        break;
                    case "Middle":
                        degreeLevel = 1.55;
                        break;
                    case "Height":
                        degreeLevel = 1.7;
                        break;
                    case "Extreme":
                        degreeLevel = 1.9;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.Man:
                if (checked){
                    sex = 5;
                }
                break;
            case R.id.Woman:
                if (checked){
                    sex = 161;
                }
                break;
        }
    }

    public void onCalculate(View view) {

        try {
            editTextAge = (EditText) findViewById(R.id.editTextAge);
            editTextHeight = (EditText) findViewById(R.id.editTextHeight);
            editTextWeight = (EditText) findViewById(R.id.editTextWeight);

            TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
            textViewResult.setText(equationMifflinSanJeor(Integer.parseInt(editTextAge.getText().toString()),
                    Double.parseDouble(editTextHeight.getText().toString()),
                    Double.parseDouble(editTextWeight.getText().toString()),
                    degreeLevel, sex));
        }catch (Exception e){

        }

    }

    private String equationMifflinSanJeor(int age, double height, double weight,double degree, int sex){
        double res = 0;
        if (sex == 5){
            res = (10*weight+6.25*height-5*age+sex)*degree;
        }else {
            res = (10*weight+6.25*height-5*age-sex)*degree;
        }
        return String.valueOf(res);
    }

}