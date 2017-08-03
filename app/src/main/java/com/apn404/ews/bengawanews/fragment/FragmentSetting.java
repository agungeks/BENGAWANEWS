package com.apn404.ews.bengawanews.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.apn404.ews.bengawanews.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.Fragment;

public class FragmentSetting extends Fragment {

    public FragmentSetting() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        ListView lvCountries = (ListView) view.findViewById(R.id.lv_tampungan);

        try{
            JSONArray data = new JSONArray(getJSONUrl("http://ews.apn404.com/TA/android/getTampungan.php"));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<>();
                //ngambil data dari database
                map.put("id_tampungan", c.getString("id_tampungan"));
                map.put("id_lokasi", c.getString("id_lokasi"));
                map.put("email", c.getString("email"));
                map.put("nama_lokasi", c.getString("nama_lokasi"));

                MyArrList.add(map);

            }

            lvCountries.setAdapter(new SimpleAdapter(getActivity(),
                    MyArrList, R.layout.lv_layout, new String[]
                    {"id_tampungan","id_lokasi", "email","nama_lokasi"}, new int[]
                    {R.id.id_tampungan_lay,R.id.id_lokasi_lay, R.id.email_lay,R.id.nama_lokasi_lay}));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return view;
    }

    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
