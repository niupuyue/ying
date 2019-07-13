package com.paulniu.ying.model.data;

import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-13
 * Time: 18:41
 * Desc: 网络数据--电影model
 * Version:
 */
public class MovieModel {

    private int count;
    private int start;
    private int total;
    private List<Subjects> subjects;
    private String title;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public class Subjects {

        private Rating rating;
        private List<String> genres;
        private String title;
        private List<Casts> casts;
        private int collect_count;
        private String original_title;
        private String subtype;
        private List<Directors> directors;
        private String year;
        private Images images;
        private String alt;
        private String id;

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Rating getRating() {
            return rating;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setCasts(List<Casts> casts) {
            this.casts = casts;
        }

        public List<Casts> getCasts() {
            return casts;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setDirectors(List<Directors> directors) {
            this.directors = directors;
        }

        public List<Directors> getDirectors() {
            return directors;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getYear() {
            return year;
        }

        public void setImages(Images images) {
            this.images = images;
        }

        public Images getImages() {
            return images;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getAlt() {
            return alt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

    }

    // 评选等级
    public class Rating {
        private int max;
        private double average;
        private String stars;
        private int min;

        public void setMax(int max) {
            this.max = max;
        }

        public int getMax() {
            return max;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public double getAverage() {
            return average;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public String getStars() {
            return stars;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMin() {
            return min;
        }
    }

    // 主演
    public class Casts {

        private String alt;
        private Avatars avatars;
        private String name;
        private String id;

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getAlt() {
            return alt;
        }

        public void setAvatars(Avatars avatars) {
            this.avatars = avatars;
        }

        public Avatars getAvatars() {
            return avatars;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

    }

    // 头像
    public class Avatars {

        private String small;
        private String large;
        private String medium;

        public void setSmall(String small) {
            this.small = small;
        }

        public String getSmall() {
            return small;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getLarge() {
            return large;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getMedium() {
            return medium;
        }

    }

    // 导演
    public class Directors {

        private String alt;
        private Avatars avatars;
        private String name;
        private String id;

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getAlt() {
            return alt;
        }

        public void setAvatars(Avatars avatars) {
            this.avatars = avatars;
        }

        public Avatars getAvatars() {
            return avatars;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

    }

    // 剧照
    public class Images {

        private String small;
        private String large;
        private String medium;

        public void setSmall(String small) {
            this.small = small;
        }

        public String getSmall() {
            return small;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getLarge() {
            return large;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getMedium() {
            return medium;
        }

    }
}
