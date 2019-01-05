package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeListAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";
    private int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);


    public EarthquakeListAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the earthquake_list_item.xml layout with the ID earthquakemagnitude_listitem.
        TextView textViewMagnitude = listItemView.findViewById(R.id.earthquakemagnitude_listitem);
        textViewMagnitude.setText(currentEarthquake.getQuakeMagnitude().toString());

        // Configure a cor de fundo apropriada no círculo de magnitude.
        // Busque o fundo do TextView, que é um GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) textViewMagnitude.getBackground();

        // Obtenha a cor de fundo apropriada, baseada na magnitude do terremoto atual
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getQuakeMagnitude());

        // Configure a cor no círculo de magnitude
        magnitudeCircle.setColor(magnitudeColor);

        String originalLocation = currentEarthquake.getQuakePlace();

        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        // Find the TextView in the earthquake_list_item.xml layout with the ID earthquakeprimarylocation_listitem.
        TextView textViewLocationOffset = listItemView.findViewById(R.id.earthquakelocationoffset_listitem);
        textViewLocationOffset.setText(locationOffset);

        // Find the TextView in the earthquake_list_item.xml layout with the ID earthquakeprimarylocation_listitem.
        TextView textViewPrimaryLocation = listItemView.findViewById(R.id.earthquakeprimarylocation_listitem);
        textViewPrimaryLocation.setText(primaryLocation);

        // Find the TextView in the earthquake_list_item.xml layout with the ID earthquakedate_listitem.
        TextView textViewDate = listItemView.findViewById(R.id.earthquakedate_listitem);
        textViewDate.setText(currentEarthquake.getQuakeDate());

        // Find the TextView in the earthquake_list_item.xml layout with the ID earthquaketime_listitem.
        TextView textViewTime = listItemView.findViewById(R.id.earthquaketime_listitem);
        textViewTime.setText(currentEarthquake.getQuakeTime());

        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
