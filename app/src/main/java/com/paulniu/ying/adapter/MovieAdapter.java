package com.paulniu.ying.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.paulniu.ying.R;
import com.paulniu.ying.model.data.MovieModel;
import com.paulniu.ying.util.MovieFormatUtility;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 18:39
 * Desc: 电影adapter
 * Version:
 */
public class MovieAdapter extends BaseQuickAdapter<MovieModel.Subjects, BaseViewHolder> {

    private Context mContext;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 宝贝想看这个电影啊
        }
    };

    public MovieAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieModel.Subjects item) {
        try {
            Glide.with(mContext).load(item.getImages().getSmall()).into((ImageView) helper.getView(R.id.ivItemMovieIcon));
            helper.setText(R.id.tvItemMovieTitle, item.getTitle());
            ((MaterialRatingBar) helper.getView(R.id.RatingBarItemMovie)).setRating(MovieFormatUtility.calStars(item.getRating().getAverage()));
            helper.setText(R.id.tvItemMovieStar, String.valueOf(item.getRating().getAverage()));
            helper.setText(R.id.tvItemMovieDirector, MovieFormatUtility.formatDirectorToString(item.getDirectors()));
            helper.setText(R.id.tvItemMovieActor, MovieFormatUtility.formatActorToString(item.getCasts()));
            helper.setText(R.id.tvItemMovieSaw, mContext.getString(R.string.movie_fragment_saw, MovieFormatUtility.formatNumber2String(item.getCollect_count())));
            ListenerUtility.setOnClickListener(onClickListener, helper.getView(R.id.tvItemMovieWant));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
