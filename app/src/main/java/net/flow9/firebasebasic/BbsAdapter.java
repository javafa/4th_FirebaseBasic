package net.flow9.firebasebasic;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 10/30/2017.
 */

public class BbsAdapter extends RecyclerView.Adapter<BbsAdapter.Holder>{
    List<Bbs> data = new ArrayList<>();

    public void setDataAndRefresh(List<Bbs> data){
        this.data = data;
        Log.d("Adapter","data========="+data.size());
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);
        Log.d("Adapter","bbs id========="+bbs.id);
        holder.textId.setText(bbs.id);
        holder.textName.setText(bbs.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textId;
        TextView textName;
        public Holder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textName = itemView.findViewById(R.id.textName);
        }
    }
}
