package ajie.com.qrcode;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 阿杰 on 2017/9/26.
 */

public class HisAdapter extends ArrayAdapter<HistoryText> {
    private int resourceId;

    public HisAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<HistoryText> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        HistoryText hisText = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.his_image = (ImageView) view.findViewById(R.id.iv_his);
            viewHolder.his_text = (TextView) view.findViewById(R.id.tv_his);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.his_image.setImageResource(hisText.getImageId());
        viewHolder.his_text.setText(hisText.getText());
        return view;
    }

    class ViewHolder {
        ImageView his_image;
        TextView his_text;
    }
}
