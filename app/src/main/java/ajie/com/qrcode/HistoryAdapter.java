package ajie.com.qrcode;

//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.List;
//
///**
// * Created by 阿杰 on 2017/9/25.
// */
//
//public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
//    private List<HistoryText> mHisList;
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView hisImage;
//        TextView hisText;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            hisImage = (ImageView) itemView.findViewById(R.id.iv_title);
//            hisText = (TextView) itemView.findViewById(R.id.tv_text);
//        }
//    }
//
//    public HistoryAdapter(List<HistoryText> hisList) {
//        mHisList = hisList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
//        HistoryText hisText = mHisList.get(position);
//        holder.hisImage.setImageResource(hisText.getImageId());
//        holder.hisText.setText(hisText.getText());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mHisList.size();
//    }
//
//
//}
