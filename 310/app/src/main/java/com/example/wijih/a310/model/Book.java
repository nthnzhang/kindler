package com.example.wijih.a310.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Book implements Parcelable {
    private String title;
    private String description;
    private String ownerID;
    private boolean forSale;
    private List<String> tags;
    private String bookId;

    public Book(String title, String description, String ownerID, boolean forSale, List<String> tags) {
        this.title = title;
        this.description = description;
        this.ownerID = ownerID;
        this.forSale = forSale;
        this.tags = tags;
    }

    public Book() {
        // empty constructor for firebase purposes
    }

    protected Book(Parcel in) {
        title = in.readString();
        description = in.readString();
        ownerID = in.readString();
        forSale = in.readByte() != 0;
        tags = in.createStringArrayList();
        bookId = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public void setBookId(String id) {
        this.bookId = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBookId() {
        return bookId;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public boolean isForSale() {
        return forSale;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(ownerID);
        dest.writeByte((byte) (forSale ? 1 : 0));
        dest.writeStringList(tags);
        dest.writeString(bookId);
    }
}
