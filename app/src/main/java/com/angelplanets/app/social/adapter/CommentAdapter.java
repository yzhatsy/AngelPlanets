package com.angelplanets.app.social.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.angelplanets.app.R;
import com.angelplanets.app.social.bean.CommentBean;
import com.angelplanets.app.utils.CUtils;
import com.angelplanets.app.utils.URLUtils;
import com.angelplanets.app.view.CircleImageView;

import org.xutils.x;

import java.util.List;

/**
 * 评论列表的adapter
 * Created by 123 on 2016/4/11.
 */
public class CommentAdapter extends BaseAdapter{
    private List<CommentBean.DataEntity.SocialCommentListEntity> commentList;
    private Context context;
    
    public CommentAdapter(Context context,List<CommentBean.DataEntity.SocialCommentListEntity> commentList){
        this.context = context;
        this.commentList = commentList;
    }
    
    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public CommentBean.DataEntity.SocialCommentListEntity getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_comment_list,null);
            holder.icon = (CircleImageView) convertView.findViewById(R.id.iv_comment_item_icon);
            holder.name = (TextView) convertView.findViewById(R.id.tv_comment_name);
            holder.time = (TextView) convertView.findViewById(R.id.tv_comment_time);
            holder.detail = (TextView) convertView.findViewById(R.id.tv_comment_detail);
            holder.reply = (TextView) convertView.findViewById(R.id.tv_comment_reply);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommentBean.DataEntity.SocialCommentListEntity comment = commentList.get(position);
        holder.name.setText(comment.getFromName());
        holder.time.setText(""+ CUtils.getStandardDate(comment.getFromCreateTime()));
        holder.detail.setText(comment.getFromContent());
        if (comment.getToId() != 0){
            holder.reply.setVisibility(View.VISIBLE);
            String content = "回复"+comment.getToName()+": "+comment.getToContent();
            holder.reply.setText(content);
        }else {
            holder.reply.setVisibility(View.GONE);
        }
        x.image().bind(holder.icon, URLUtils.rootUrl+comment.getAvatarUrl());
        return convertView;
    }
    
    class ViewHolder{
       CircleImageView icon;
        TextView name;
        TextView time;
        TextView detail;
        TextView reply;
    }
}
