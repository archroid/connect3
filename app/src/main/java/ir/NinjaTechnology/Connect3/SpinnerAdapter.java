package ir.NinjaTechnology.Connect3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter {
     Context context;
     int[] colors;
     String[] color;
     LayoutInflater inflater;

    public SpinnerAdapter(Context context, int[] colors, String[] color) {
        this.context = context;
        this.colors = colors;
        this.color=color;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView =  inflater.inflate(R.layout.spinner_layout,parent,false);
            holder= new ViewHolder();
            holder.iv = convertView.findViewById(R.id.spinnerlayout_iv);
            holder.tv = convertView.findViewById(R.id.spinnerlayout_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(position);
        return convertView;
    }
    public class ViewHolder{
        public TextView tv;
        public ImageView iv;
        public void fill(int position){
            tv.setText(color[position]);
            tv.setTextColor(context.getResources().getColor(colors[position]));
            iv.setBackgroundColor(context.getResources().getColor(colors[position]));
        }

    }
}
