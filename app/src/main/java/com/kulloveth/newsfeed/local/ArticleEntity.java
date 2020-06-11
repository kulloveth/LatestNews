package com.kulloveth.newsfeed.local;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class ArticleEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    private int id;

    @ColumnInfo(name = "article_title")
    private String title;

    @ColumnInfo(name = "article_desc")
    private String description;

    @ColumnInfo(name = "image_url")
    private String urlToImage;


    public ArticleEntity(int id, String title, String description, String urlToImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
    }

    @Ignore
    public ArticleEntity() {
    }

    protected ArticleEntity(Parcel in) {
        title = in.readString();
        description = in.readString();
        urlToImage = in.readString();

    }

    public static final Creator<ArticleEntity> CREATOR = new Creator<ArticleEntity>() {
        @Override
        public ArticleEntity createFromParcel(Parcel in) {
            return new ArticleEntity(in);
        }

        @Override
        public ArticleEntity[] newArray(int size) {
            return new ArticleEntity[size];
        }
    };

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public String getUrlToImage() {
        return urlToImage;
    }

    @Override
    public String toString() {
        return "Article{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleEntity)) return false;
        ArticleEntity article = (ArticleEntity) o;
        return
                getTitle().equals(article.getTitle()) &&
                        getDescription().equals(article.getDescription()) &&
                        getUrlToImage().equals(article.getUrlToImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getUrlToImage());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(urlToImage);
    }
}
