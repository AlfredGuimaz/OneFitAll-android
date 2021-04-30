package com.example.one_fit_all;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class goCalorieCounter extends AppCompatActivity {
    private Button enterBtn;
    private Button resetBtn;
    private TextView result;
    private EditText searchFood;
    private RequestQueue mQueue;

    private Button scanner;
    public String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_calorie_counter);

        searchFood = (EditText) findViewById(R.id.searchFood);
        enterBtn = (Button) findViewById(R.id.enterBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        result = (TextView) findViewById(R.id.result);

        mQueue = Volley.newRequestQueue(this);

        scanner = findViewById(R.id.scanner);
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        goCalorieCounter.this
                );
                //Set prompt text
                intentIntegrator.setPrompt("For Flash use volume up key");
                //Set beep
                intentIntegrator.setBeepEnabled(true);
                //lock Orientation
                intentIntegrator.setOrientationLocked(true);
                //Set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //Initiate scan
                intentIntegrator.initiateScan();
                //jsonParse(barcode);
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = searchFood.getText().toString();
                foodName = foodName.replace("\\s", "%20");
                String url = "https://api.nutritionix.com/v1_1/search/" + foodName + /*cheddar%20cheese*/ "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat&appId=cd93d7a0&appKey=c4afb3d24d94502987c4193190bb3720";
                Toast msg = Toast.makeText(getBaseContext(),foodName,Toast.LENGTH_LONG);
                msg.show();
                jsonParse(url);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFood.setText("");
                result.setText("Results");
            }
        });
    }

    private void jsonParse(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
                            for (int i = 0; i < 1; i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                //String _index = hit.getString("_index");
                                JSONObject field = hit.getJSONObject("fields");
                                String item_name = field.getString("item_name");
                                double nf_calories = field.getDouble("nf_calories");
                                //int age = hits.getInt("age");
                                //String mail = hits.getString("mail");
                                result.setText(/*_index + */ item_name + ":  " + nf_calories + "\n\n"); //result.append
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode,data
        );
        //Check condition
        if(intentResult.getContents() != null){
            //When result content is not null
            //Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    goCalorieCounter.this
            );
            //Set title
            builder.setTitle("Result");


            //MY SHIIIT
            barcode = intentResult.getContents();
            jsonParseBarcode();


            //Set message
            builder.setMessage(intentResult.getContents());
            //Set positive button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Dismiss dialog
                    dialogInterface.dismiss();
                }
            });
            //Show alert dialog
            builder.show();
        }
        else{
            Toast.makeText(getApplicationContext()
                    , "OOPS... Scan again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void jsonParseBarcode() {
        String url = "https://api.nutritionix.com/v1_1/item?upc=" + barcode +/*49000036756*/"&appId=cd93d7a0&appKey=c4afb3d24d94502987c4193190bb3720";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nf_calorie = response.getString("nf_calories");
                            String brand = response.getString("brand_name");

                            result.setText(brand + ": " + nf_calorie + " calories");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}