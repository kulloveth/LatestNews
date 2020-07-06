package com.kulloveth.latestnews.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

public class NewsResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private ArrayList<Article> articles = null;

    public NewsResponse() {
    }

    public NewsResponse(String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    protected NewsResponse(Parcel in) {
        status = in.readString();
        totalResults = in.readInt();
        articles = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Creator<NewsResponse> CREATOR = new Creator<NewsResponse>() {
        @Override
        public NewsResponse createFromParcel(Parcel in) {
            return new NewsResponse(in);
        }

        @Override
        public NewsResponse[] newArray(int size) {
            return new NewsResponse[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsResponse)) return false;
        NewsResponse that = (NewsResponse) o;
        return getTotalResults() == that.getTotalResults() &&
                getStatus().equals(that.getStatus()) &&
                getArticles().equals(that.getArticles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getTotalResults(), getArticles());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeInt(totalResults);
        dest.writeTypedList(articles);
    }
}
