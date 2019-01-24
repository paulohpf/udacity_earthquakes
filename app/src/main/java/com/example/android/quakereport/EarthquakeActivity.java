/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Earthquake>> {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
    private TextView mEmptyStateTextView;

    /**
     * Valor constante para o ID do loader de earthquake. Podemos escolher qualquer inteiro.
     * Isto só importa realmente se você estiver usando múltiplos loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        ListView earthquakeListView = findViewById(R.id.list);

        earthquakeListView.setEmptyView(mEmptyStateTextView);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Obtém uma referência ao LoaderManager, a fim de interagir com loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Inicializa o loader. Passa um ID constante int definido acima e passa nulo para
            // o bundle. Passa esta activity para o parâmetro LoaderCallbacks (que é válido
            // porque esta activity implementa a interface LoaderCallbacks).
            Log.i(LOG_TAG, "Iniciando Loader");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // Criar um novo loader para a dada URL
        Log.i(LOG_TAG, "Loader criado");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }


    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        // Esconde o indicador de carregamento porque os dados foram carregados
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Seta o texto do estado vazio para mostrar "Nenhum terremoto encontrado."
        mEmptyStateTextView.setText((R.string.no_earthquakes));

        // Se há uma lista válida de {@link Earthquake}s, então os adiciona ao data set do adapter.
        // Isto ativará a atualização da ListView.
        Log.i(LOG_TAG, "Loader terminado");
        if (earthquakes != null && !earthquakes.isEmpty()) {
            updateUi(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<Earthquake>> loader) {
        Log.i(LOG_TAG, "Loader resetado");
    }

    private void updateUi(ArrayList<Earthquake> earthquakes) {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        final EarthquakeListAdapter adapter = new EarthquakeListAdapter(getApplicationContext(), earthquakes);

        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = adapter.getItem(position);

                Uri earthquakeUri = Uri.parse(currentEarthquake.getURL());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });
    }
}