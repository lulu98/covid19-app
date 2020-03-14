package com.example.covid_19app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.IOException;

import android.os.AsyncTask;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.util.List;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SetUpSpinner().execute();

        Spinner spinner = (Spinner)findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String country = parentView.getItemAtPosition(position).toString();
                if (country.equals("Total")){
                    TextView heading = (TextView)findViewById(R.id.headingField);
                    heading.setText("Total");
                    new TotalNumberOfConfirmed().execute();
                    new TotalNumberOfDeaths().execute();
                    new TotalNumberOfRecoveries().execute();
                }else{
                    TextView heading = (TextView)findViewById(R.id.headingField);
                    heading.setText(country);
                    new ConfirmedForCountry().execute(country);
                    new DeathsForCountry().execute(country);
                    new RecoveriesForCountry().execute(country);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                TextView heading = (TextView)findViewById(R.id.headingField);
                heading.setText("Total");
                new TotalNumberOfConfirmed().execute();
                new TotalNumberOfDeaths().execute();
                new TotalNumberOfRecoveries().execute();
            }

        });
    }

    private class SetUpSpinner extends AsyncTask<String, Void, List<String>> {
        protected List<String> doInBackground(String... req) {
            try {
                return (new COVID19API().getCountries());
            }catch (ParseException pe) {
                Log.i("Parse Exception: ","Error.");
            }catch (IOException io){
                Log.i("IO Exception: ","Error.");
            }
            return null;
        }

        protected void onPostExecute(List<String> result) {
            result.add(0,"Total");
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, result);
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.custom_spinner_item, result);
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
            Spinner sItems = (Spinner) findViewById(R.id.spinner1);
            sItems.setAdapter(adapter);
        }
    }

    private class TotalNumberOfConfirmed extends AsyncTask<String, Void, Long> {
        protected Long doInBackground(String... req) {
            try {
                return (new COVID19API().getTotalNumberOfConfirmed());
            }catch (ParseException pe) {
                Log.i("Parse Exception: ","Error.");
            }catch (IOException io){
                Log.i("IO Exception: ","Error.");
            }
            return (long) 0;
        }

        protected void onPostExecute(Long result) {
            TextView confirmed = (TextView)findViewById(R.id.confirmedField);
            confirmed.setText(String.valueOf(result));
        }
    }

    private class TotalNumberOfDeaths extends AsyncTask<String, Void, Long> {
        protected Long doInBackground(String... req) {
            try {
                return (new COVID19API().getTotalNumberOfDeaths());
            }catch (ParseException pe) {
                Log.i("Parse Exception: ","Error.");
            }catch (IOException io){
                Log.i("IO Exception: ","Error.");
            }
            return (long) 0;
        }

        protected void onPostExecute(Long result) {
            TextView deaths = (TextView)findViewById(R.id.deathField);
            deaths.setText(String.valueOf(result));
        }
    }

    private class TotalNumberOfRecoveries extends AsyncTask<String, Void, Long> {
        protected Long doInBackground(String... req) {
            try {
                return (new COVID19API().getTotalNumberOfRecoveries());
            } catch (ParseException pe) {
                Log.i("Parse Exception: ", "Error.");
            } catch (IOException io) {
                Log.i("IO Exception: ", "Error.");
            }
            return (long) 0;
        }

        protected void onPostExecute(Long result) {
            TextView recovered = (TextView) findViewById(R.id.recoveredField);
            recovered.setText(String.valueOf(result));
        }
    }

    private class ConfirmedForCountry extends AsyncTask<String, Void, Long> {
        protected Long doInBackground(String... req) {
            try {
                return (new COVID19API().getConfirmedForCountry(req[0]));
            } catch (ParseException pe) {
                Log.i("Parse Exception: ", "Error.");
            } catch (IOException io) {
                Log.i("IO Exception: ", "Error.");
            }
            return (long) 0;
        }

        protected void onPostExecute(Long result) {
            TextView confirmed = (TextView) findViewById(R.id.confirmedField);
            confirmed.setText(String.valueOf(result));
        }
    }

    private class DeathsForCountry extends AsyncTask<String, Void, Long> {
        protected Long doInBackground(String... req) {
            try {
                return (new COVID19API().getDeathsForCountry(req[0]));
            } catch (ParseException pe) {
                Log.i("Parse Exception: ", "Error.");
            } catch (IOException io) {
                Log.i("IO Exception: ", "Error.");
            }
            return (long) 0;
        }

        protected void onPostExecute(Long result) {
            TextView deaths = (TextView) findViewById(R.id.deathField);
            deaths.setText(String.valueOf(result));
        }
    }

    private class RecoveriesForCountry extends AsyncTask<String, Void, Long> {
        protected Long doInBackground(String... req) {
            try {
                return (new COVID19API().getRecoveriesForCountry(req[0]));
            } catch (ParseException pe) {
                Log.i("Parse Exception: ", "Error.");
            } catch (IOException io) {
                Log.i("IO Exception: ", "Error.");
            }
            return (long) 0;
        }

        protected void onPostExecute(Long result) {
            TextView recovered = (TextView) findViewById(R.id.recoveredField);
            recovered.setText(String.valueOf(result));
        }
    }
}
