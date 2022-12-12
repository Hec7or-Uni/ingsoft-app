package eina.unizar.ingsoftapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomAdapter extends ArrayAdapter<String> {
    private final Activity context;

    public RoomAdapter(Activity context) {
        super(context, R.layout.room);
        this.context=context;
    }

    public View getView(View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.room, null,true);

        TextView title      = rowView.findViewById(R.id.title);
        TextView identifier = rowView.findViewById(R.id.identifier);
        TextView ocupantes  = rowView.findViewById(R.id.ocupantes);
        TextView precio     = rowView.findViewById(R.id.precio);

        title.setText("TITLE");
        identifier.setText("IDENTIFIER");
        ocupantes.setText("OCUPANTES");
        precio.setText("PRECIO");

        return rowView;
    };
}