package com.dreampany.frame.data.model;

import android.os.Parcel;

import com.google.common.base.Objects;

/**
 * Created by Hawladar Roman on 30/4/18.
 * Dreampany
 * dreampanymail@gmail.com
 */
public abstract class Task<T extends BaseParcel> extends Base {
    protected String id;
    protected String comment;
    protected T input;

    protected Task() {
    }

    protected Task(Parcel in) {
        super(in);
        id = in.readString();
        comment = in.readString();
        if (in.readByte() == 0) {
            input = null;
        } else {
            Class<?> itemClazz = (Class<?>) in.readSerializable();
            input = in.readParcelable(itemClazz.getClassLoader());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(id);
        dest.writeString(comment);
        if (input == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            Class<?> itemClazz = input.getClass();
            dest.writeSerializable(itemClazz);
            dest.writeParcelable(input, flags);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, input);
    }

    @Override
    public boolean equals(Object inObject) {
        if (this == inObject) {
            return true;
        }
        if (inObject == null || getClass() != inObject.getClass()){
            return false;
        }
        Task task = (Task) inObject;
        return Objects.equal(id, task.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setInput(T input) {
        this.input = input;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public T getInput() {
        return input;
    }

}
