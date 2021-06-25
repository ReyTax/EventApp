package reytax.project.eventapp.utils.api;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reytax.project.eventapp.structure.CityStructure;
import reytax.project.eventapp.structure.CountryStructure;
import reytax.project.eventapp.structure.StateStructure;

public class CountryStateCityApi extends AsyncTask<String, Void, Void> {


    private static List<String> countries = new ArrayList<String>();
    private static List<String> states = new ArrayList<String>();
    private static List<String> cities = new ArrayList<String>();

    @Override
    protected Void doInBackground(String... args) {

        switch (args[0]) {
            case "get_token_and_countries":
                sendCountriesRequest("country","");
                break;
            case "get_states":
                sendCountriesRequest("state", args[1]);
                break;
            case "get_cities":
                sendCountriesRequest("city", args[1]);
                break;
        }


        return null;
    }

    private void sendCountriesRequest(String option, String input) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String jsonToken = "";
                    Response response = null;

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://www.universal-tutorial.com/api/getaccesstoken")
                            .get()
                            .addHeader("Accept", "application/json")
                            .addHeader("api-token", "i9JhcrlEoZuYXT0VQWcveQuoSxECS5oqWLHg4igac6oGeauwR04mq32O10MSoKoqRn4")
                            .addHeader("user-email", "disturbedwakeup@yahoo.com")
                            .build();

                    try {
                        response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    while (!response.isSuccessful())
                        Thread.sleep(500);

                    try {
                        jsonToken = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    client = new OkHttpClient();

                    JSONObject jsonObject = new JSONObject(jsonToken);

                    String token = jsonObject.getString("auth_token");

                    Gson gson;

                    switch (option) {
                        case "country" :
                            request = new Request.Builder()
                                    .url("https://www.universal-tutorial.com/api/countries/")
                                    .get()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .addHeader("Accept", "application/json")
                                    .build();

                            String countriesData = null;

                            try {
                                response = client.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            while (!response.isSuccessful())
                                Thread.sleep(500);

                            try {
                                countriesData = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            gson = new Gson();
                            CountryStructure countryStructure;
                            countryStructure = gson.fromJson(countriesData, CountryStructure.class);

                            for (int i = 0; i < countryStructure.size(); i++){
                                countries.add(countryStructure.get(i).getCountry_name());
                            }
                            break;
                        case "state" :
                            request = new Request.Builder()
                                    .url("https://www.universal-tutorial.com/api/states/" + input)
                                    .get()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .addHeader("Accept", "application/json")
                                    .build();

                            String statesData = null;

                            try {
                                response = client.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            while (!response.isSuccessful())
                                Thread.sleep(500);

                            try {
                                statesData = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            gson = new Gson();
                            StateStructure stateStructure;
                            stateStructure = gson.fromJson(statesData, StateStructure.class);

                            for (int i = 0; i < stateStructure.size(); i++){
                                states.add(stateStructure.get(i).getState_name());
                            }
                            break;
                        case "city" :
                            request = new Request.Builder()
                                    .url("https://www.universal-tutorial.com/api/cities/" + input)
                                    .get()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .addHeader("Accept", "application/json")
                                    .build();

                            String cityData = null;

                            try {
                                response = client.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            while (!response.isSuccessful())
                                Thread.sleep(500);

                            try {
                                cityData = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            gson = new Gson();
                            CityStructure cityStructure;
                            cityStructure = gson.fromJson(cityData, CityStructure.class);

                            for (int i = 0; i < cityStructure.size(); i++){
                                cities.add(cityStructure.get(i).getCity_name());
                            }
                            break;

                    }

                } catch (InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void sendStateRequest(String country) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Response response = null;

                    String jsonToken = "";

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://www.universal-tutorial.com/api/getaccesstoken")
                            .get()
                            .addHeader("Accept", "application/json")
                            .addHeader("api-token", "i9JhcrlEoZuYXT0VQWcveQuoSxECS5oqWLHg4igac6oGeauwR04mq32O10MSoKoqRn4")
                            .addHeader("user-email", "disturbedwakeup@yahoo.com")
                            .build();

                    try {
                        response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    while (!response.isSuccessful())
                        Thread.sleep(500);

                    try {
                        jsonToken = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    client = new OkHttpClient();

                    JSONObject jsonObject = new JSONObject(jsonToken);

                    String token = jsonObject.getString("auth_token");




                } catch (InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void sendCityRequest(String state) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Response response = null;

                    String jsonToken = "";

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://www.universal-tutorial.com/api/getaccesstoken")
                            .get()
                            .addHeader("Accept", "application/json")
                            .addHeader("api-token", "i9JhcrlEoZuYXT0VQWcveQuoSxECS5oqWLHg4igac6oGeauwR04mq32O10MSoKoqRn4")
                            .addHeader("user-email", "disturbedwakeup@yahoo.com")
                            .build();

                    try {
                        response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    while (!response.isSuccessful())
                        Thread.sleep(500);

                    try {
                        jsonToken = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    client = new OkHttpClient();

                    JSONObject jsonObject = new JSONObject(jsonToken);

                    String token = jsonObject.getString("auth_token");




                } catch (InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    public static List<String> getCountries() {
        return countries;
    }

    public static List<String> getStates() {
        return states;
    }

    public static List<String> getCities() {
        return cities;
    }

    public static void resetStates() {
        states = new ArrayList<String>();
    }

    public static void resetCities() {
        cities = new ArrayList<String>();
    }

}