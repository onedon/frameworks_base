/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.media.tv;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Encapsulates the format of tracks played in {@link TvInputService}.
 */
public final class TvTrackInfo implements Parcelable {
    /**
     * The type value for audio tracks.
     */
    public static final int TYPE_AUDIO = 0;

    /**
     * The type value for video tracks.
     */
    public static final int TYPE_VIDEO = 1;

    /**
     * The type value for subtitle tracks.
     */
    public static final int TYPE_SUBTITLE = 2;

    private final int mType;
    private final String mLanguage;
    private final int mAudioChannelCount;
    private final int mAudioSampleRate;
    private final int mVideoWidth;
    private final int mVideoHeight;
    private final Bundle mExtra;

    private TvTrackInfo(int type, String language, int audioChannelCount,
            int audioSampleRate, int videoWidth, int videoHeight, Bundle extra) {
        mType = type;
        mLanguage = language;
        mAudioChannelCount = audioChannelCount;
        mAudioSampleRate = audioSampleRate;
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
        mExtra = extra;
    }

    private TvTrackInfo(Parcel in) {
        mType = in.readInt();
        mLanguage = in.readString();
        mAudioChannelCount = in.readInt();
        mAudioSampleRate = in.readInt();
        mVideoWidth = in.readInt();
        mVideoHeight = in.readInt();
        mExtra = in.readBundle();
    }

    /**
     * Returns the type of the track. The type should be one of the followings:
     * {@link #TYPE_AUDIO}, {@link #TYPE_VIDEO} and {@link #TYPE_SUBTITLE}.
     */
    public final int getType() {
        return mType;
    }

    /**
     * Returns the language information encoded by either ISO 639-1 or ISO 639-2/T. If the language
     * is unknown or could not be determined, the corresponding value will be {@code null}.
     */
    public final String getLanguage() {
        return mLanguage;
    }

    /**
     * Returns the audio channel count. Valid only for {@link #TYPE_AUDIO} tracks.
     */
    public final int getAudioChannelCount() {
        if (mType != TYPE_AUDIO) {
            throw new IllegalStateException("Not an audio track");
        }
        return mAudioChannelCount;
    }

    /**
     * Returns the audio sample rate, in the unit of Hz. Valid only for {@link #TYPE_AUDIO} tracks.
     */
    public final int getAudioSampleRate() {
        if (mType != TYPE_AUDIO) {
            throw new IllegalStateException("Not an audio track");
        }
        return mAudioSampleRate;
    }

    /**
     * Returns the width of the video, in the unit of pixels. Valid only for {@link #TYPE_VIDEO}
     * tracks.
     */
    public final int getVideoWidth() {
        if (mType != TYPE_VIDEO) {
            throw new IllegalStateException("Not a video track");
        }
        return mVideoWidth;
    }

    /**
     * Returns the height of the video, in the unit of pixels. Valid only for {@link #TYPE_VIDEO}
     * tracks.
     */
    public final int getVideoHeight() {
        if (mType != TYPE_VIDEO) {
            throw new IllegalStateException("Not a video track");
        }
        return mVideoHeight;
    }

    /**
     * Returns the extra information about the current track.
     */
    public final Bundle getExtra() {
        return mExtra;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Used to package this object into a {@link Parcel}.
     *
     * @param dest The {@link Parcel} to be written.
     * @param flags The flags used for parceling.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mType);
        dest.writeString(mLanguage);
        dest.writeInt(mAudioChannelCount);
        dest.writeInt(mAudioSampleRate);
        dest.writeInt(mVideoWidth);
        dest.writeInt(mVideoHeight);
        dest.writeBundle(mExtra);
    }

    public static final Parcelable.Creator<TvTrackInfo> CREATOR =
            new Parcelable.Creator<TvTrackInfo>() {
                @Override
                public TvTrackInfo createFromParcel(Parcel in) {
                    return new TvTrackInfo(in);
                }

                @Override
                public TvTrackInfo[] newArray(int size) {
                    return new TvTrackInfo[size];
                }
            };

    /**
     * A builder class for creating {@link TvTrackInfo} objects.
     */
    public static final class Builder {
        private int mType;
        private String mLanguage;
        private int mAudioChannelCount;
        private int mAudioSampleRate;
        private int mVideoWidth;
        private int mVideoHeight;
        private Bundle mExtra;

        /**
         * Create a {@link Builder}. Any field that should be included in the {@link TvTrackInfo}
         * must be added.
         *
         * @param type The type of the track.
         */
        public Builder(int type) {
            if (type != TYPE_AUDIO
                    && type != TYPE_VIDEO
                    && type != TYPE_SUBTITLE) {
                throw new IllegalArgumentException("Unknown type: " + type);
            }
            mType = type;
        }

        /**
         * Sets the language information of the current track.
         *
         * @param language The language string encoded by either ISO 639-1 or ISO 639-2/T.
         */
        public final Builder setLanguage(String language) {
            mLanguage = language;
            return this;
        }

        /**
         * Sets the audio channel count. Valid only for {@link #TYPE_AUDIO} tracks.
         *
         * @param audioChannelCount The audio channel count.
         */
        public final Builder setAudioChannelCount(int audioChannelCount) {
            if (mType != TYPE_AUDIO) {
                throw new IllegalStateException("Not an audio track");
            }
            mAudioChannelCount = audioChannelCount;
            return this;
        }

        /**
         * Sets the audio sample rate, in the unit of Hz. Valid only for {@link #TYPE_AUDIO}
         * tracks.
         *
         * @param audioSampleRate The audio sample rate.
         */
        public final Builder setAudioSampleRate(int audioSampleRate) {
            if (mType != TYPE_AUDIO) {
                throw new IllegalStateException("Not an audio track");
            }
            mAudioSampleRate = audioSampleRate;
            return this;
        }

        /**
         * Sets the width of the video, in the unit of pixels. Valid only for {@link #TYPE_VIDEO}
         * tracks.
         *
         * @param videoWidth The width of the video.
         */
        public final Builder setVideoWidth(int videoWidth) {
            if (mType != TYPE_VIDEO) {
                throw new IllegalStateException("Not a video track");
            }
            mVideoWidth = videoWidth;
            return this;
        }

        /**
         * Sets the height of the video, in the unit of pixels. Valid only for {@link #TYPE_VIDEO}
         * tracks.
         *
         * @param videoHeight The height of the video.
         */
        public final Builder setVideoHeight(int videoHeight) {
            if (mType != TYPE_VIDEO) {
                throw new IllegalStateException("Not a video track");
            }
            mVideoHeight = videoHeight;
            return this;
        }

        /**
         * Sets the extra information about the current track.
         *
         * @param extra The extra information.
         */
        public final Builder setExtra(Bundle extra) {
            mExtra = new Bundle(extra);
            return this;
        }

        /**
         * Creates a {@link TvTrackInfo} instance with the specified fields.
         *
         * @return The new {@link TvTrackInfo} instance
         */
        public TvTrackInfo build() {
            return new TvTrackInfo(mType, mLanguage, mAudioChannelCount,
                    mAudioSampleRate, mVideoWidth, mVideoHeight, mExtra);
        }
    }
}