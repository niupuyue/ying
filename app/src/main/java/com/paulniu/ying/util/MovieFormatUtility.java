package com.paulniu.ying.util;

import com.niupuyue.mylibrary.utils.BaseUtility;
import com.paulniu.ying.model.data.MovieDetailModel;
import com.paulniu.ying.model.data.MovieModel;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 20:43
 * Desc:
 * Version:
 */
public class MovieFormatUtility {

    public static float calStars(double star) {
        return (float) star / 10 * 5;
    }


    /**
     * 导演
     */
    public static String formatDirectorToString(List<MovieModel.Directors> directors) {
        String result = "";
        if (BaseUtility.isEmpty(directors)) {
            return result;
        }
        for (int i = 0; i < directors.size(); i++) {
            if (!BaseUtility.isEmpty(directors.get(i).getName())) {
                if (i != directors.size() - 1)
                    result += directors.get(i).getName() + " / ";
                else {
                    result += directors.get(i).getName();
                }
            }
        }
        return result;
    }

    /**
     * 演员
     */
    public static String formatActorToString(List<MovieModel.Casts> casts) {
        String result = "";
        if (BaseUtility.isEmpty(casts)) {
            return result;
        }
        for (int i = 0; i < casts.size(); i++) {
            if (!BaseUtility.isEmpty(casts.get(i).getName())) {
                if (i != casts.size() - 1)
                    result += casts.get(i).getName() + " / ";
                else {
                    result += casts.get(i).getName();
                }
            }
        }
        return result;
    }

    public static String formatKindToString(MovieDetailModel movie) {
        String result = "";
        result += movie.getYear() + " / ";
        for (int i = 0; i < movie.getGenres().size(); i++) {
            if (i != movie.getGenres().size() - 1) {
                result += movie.getGenres().get(i) + " / ";
            } else {
                result += movie.getGenres().get(i);
            }
        }
        return result;
    }

    public static String formatNumber2String(int number) {
        if (number < 9999) {
            return String.valueOf(number);
        } else {
            int first = number / 10000;
            int last = (number % 10000) / 1000;
            if (last == 0) {
                return first + "万";
            } else {
                return first + "." + last + "万";
            }
        }
    }

}
