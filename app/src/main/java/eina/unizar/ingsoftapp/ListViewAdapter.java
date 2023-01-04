package eina.unizar.ingsoftapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String>  {

    private final Context context;
    private final List<String> itemsID;
    private final List<String> itemsOcupacion;
    private RoomsDbAdapter mDbRoomHelper;
    private HabitacionesReservasDbAdapter mix;

    public ListViewAdapter(Context context, List<String> itemsID, List<String> itemsOcupacion ) {
        super(context, R.layout.dropdown, itemsID);
        this.context = context;
        this.itemsID = itemsID;
        this.itemsOcupacion = itemsOcupacion;
    }

    @Override
    public long getItemId(int position) {
        // Return a unique ID for each row in the cursor
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for the list item
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.dropdown, parent, false);
        }

        // References
        Spinner spinner = convertView.findViewById(R.id.spinner);
        EditText ocupacion = convertView.findViewById(R.id.ocupacion);

        // Database connection
        mDbRoomHelper = new RoomsDbAdapter( context);
        mDbRoomHelper.open();

        List<DropDownData> dataList = new ArrayList<>();
        Cursor cursor = mDbRoomHelper.fetchAllHabitaciones();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(RoomsDbAdapter.KEY_ROWID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RoomsDbAdapter.KEY_NOMBRE));
            dataList.add(new DropDownData(id, name));
        }
        ArrayAdapter<DropDownData> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Set values
        spinner.setSelection(Integer.parseInt(itemsID.get(position))-1);
        ocupacion.setText(itemsOcupacion.get(position));

        return convertView;
    }


}

