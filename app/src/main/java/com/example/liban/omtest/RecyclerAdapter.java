package com.example.liban.omtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ReadyPost> posts;
    private Context mContext;

    public RecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setPosts(List<ReadyPost> posts) {
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTextViewId.setText(posts.get(i).getId());
        viewHolder.mTextViewTitle.setText(posts.get(i).getTitle());
        viewHolder.mTextViewBody.setText(new StringBuilder(posts.get(i).getBody().substring(0, 30))
                .append("...").toString());
        viewHolder.mTextViewUrl.setText(posts.get(i).getSmall());

    }

    @Override
    public int getItemCount() {
        if (posts != null) {
            return posts.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextViewId;
        private TextView mTextViewBody;
        private TextView mTextViewUrl;
        private TextView mTextViewTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextViewId = itemView.findViewById(R.id.id_text_view_count);
            mTextViewBody = itemView.findViewById(R.id.id_text_view_body);
            mTextViewUrl = itemView.findViewById(R.id.id_text_view_url);
            mTextViewTitle = itemView.findViewById(R.id.id_text_view_title);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent detailIntent = new Intent(mContext, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.URL, posts.get(position).getSmall());
                detailIntent.putExtra("id", posts.get(position).getId());
                detailIntent.putExtra("title", posts.get(position).getTitle());
                detailIntent.putExtra("body", posts.get(position).getBody());
                mContext.startActivity(detailIntent);
            }
        }
    }
}
