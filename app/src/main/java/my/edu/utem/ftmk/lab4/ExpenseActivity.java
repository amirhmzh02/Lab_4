package my.edu.utem.ftmk.lab4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import my.edu.utem.ftmk.lab4.databinding.ActivityexpenseBinding;

public class ExpenseActivity extends AppCompatActivity {

    ActivityexpenseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityexpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Integer[] numbers = new Integer[15];
        for (int i = 0; i<15;i++){
            numbers[i] = i + 1;
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,numbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnQty.setAdapter(adapter);

        binding.btnSave.setOnClickListener(this::fnSaveExp);
        binding.imgExp.setOnClickListener(this::fnTakePic);

        binding.edtExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnInvokeDatePricker();
            }

            DatePickerDialog pickerDialog;
            private void fnInvokeDatePricker() {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(ExpenseActivity.this, new
                        DatePickerDialog.OnDateSetListener(){
                    @Override
                            public void onDateSet(DatePicker datePicker, int year,int monthofyear,int dayOfMonth)
                    {
                        binding.edtExpDate.setText(dayOfMonth+"/"+(monthofyear+1)+"/"+year);
                    }
                },year,month,day);
                pickerDialog.show();
            }
        });

    }

    private void fnTakePic(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    private void fnSaveExp(View view) {
        int qtyItem = (int) binding.spnQty.getSelectedItem();
        binding.txtTotalPrice.getText();

        binding.txtTotal.setText(""+qtyItem*Float.parseFloat(binding.txtTotalPrice.getText().toString()));




    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,requestCode,data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        binding.imgExp.setImageBitmap(bitmap);
    }



}