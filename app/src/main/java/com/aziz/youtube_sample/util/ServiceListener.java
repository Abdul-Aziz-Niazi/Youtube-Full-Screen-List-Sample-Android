package com.aziz.youtube_sample.util;

import android.support.annotation.Nullable;

public interface ServiceListener<T> {
    void success(@Nullable T obj);

    void fail(@Nullable T obj);
}
