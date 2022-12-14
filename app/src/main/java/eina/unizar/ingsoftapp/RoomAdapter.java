package eina.unizar.ingsoftapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomAdapter extends ArrayAdapter<String> {
    private final String[] maintitle;
    private final String[] subtitle;
    private final Activity context;

    public RoomAdapter(Activity context, String[] maintitle,String[] subtitle) {
        super(context, R.layout.room, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
    }

//    public View getView(View view, ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.room, null,true);
//
//        TextView title      = rowView.findViewById(R.id.title);
//        TextView identifier = rowView.findViewById(R.id.identifier);
//        TextView ocupantes  = rowView.findViewById(R.id.ocupantes);
//        TextView precio     = rowView.findViewById(R.id.precio);
//
//        title.setText("TITLE");
//        identifier.setText("IDENTIFIER");
//        ocupantes.setText("OCUPANTES");
//        precio.setText("PRECIO");
//
//        return rowView;
//    };

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.room, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.identifier);

        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;
    };
}