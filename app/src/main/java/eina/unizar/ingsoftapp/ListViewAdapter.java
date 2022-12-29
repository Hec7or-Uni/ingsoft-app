package eina.unizar.ingsoftapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String>  {

    private final Context context;
    private final List<String> items;
    private RoomsDbAdapter mDbRoomHelper;
    private HabitacionesReservasDbAdapter mDbRoomMixHelper;

    public ListViewAdapter(Context context, List<String> items) {
        super(context, R.layout.dropdown, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for the list item
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.dropdown, parent, false);
        }

        // Set the text for the item
        //TextView textView = convertView.findViewById(R.id.text);
        //textView.setText(items.get(position));
        mDbRoomHelper = new RoomsDbAdapter( context);
        mDbRoomHelper.open();
        // Set up the Spinner
        Spinner spinner = convertView.findViewById(R.id.spinner);

        List<DropDownData> dataList = new ArrayList<>();
        Cursor cursor = mDbRoomHelper.fetchAllHabitaciones();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(mDbRoomHelper.KEY_ROWID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(mDbRoomHelper.KEY_NOMBRE));
            dataList.add(new DropDownData(id, name));
        }
        ArrayAdapter<DropDownData> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        return convertView;
    }


}

