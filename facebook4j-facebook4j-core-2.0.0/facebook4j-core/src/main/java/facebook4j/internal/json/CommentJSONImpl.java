/*
 * Copyright 2012 Ryuji Yamashita
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

package facebook4j.internal.json;

import facebook4j.Category;
import facebook4j.Comment;
import facebook4j.FacebookException;
import facebook4j.ResponseList;
import facebook4j.conf.Configuration;
import facebook4j.internal.http.HttpResponse;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

import java.util.Date;

import static facebook4j.internal.util.z_F4JInternalParseUtil.*;

/**
 * @author Ryuji Yamashita - roundrop at gmail.com
 */
/*package*/ final class CommentJSONImpl extends FacebookResponseImpl implements Comment, java.io.Serializable {
    private static final long serialVersionUID = 4049049358890693823L;

    private String id;
    private Category from;
    private String message;
    private Boolean canRemove;
    private Date createdTime;
    private Integer likeCount;
    private Boolean isUserLikes;
    
    /*package*/CommentJSONImpl(HttpResponse res, Configuration conf) throws FacebookException {
        super(res);
        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.clearThreadLocalMap();
            DataObjectFactoryUtil.registerJSONObject(this, json);
        }
    }

    /*package*/CommentJSONImpl(JSONObject json) throws FacebookException {
        super();
        init(json);
    }

    private void init(JSONObject json) throws FacebookException {
        try {
            id = getRawString("id", json);
            if (!json.isNull("from")) {
                JSONObject fromJSONObject = json.getJSONObject("from");
                from = new CategoryJSONImpl(fromJSONObject);
            } else {
                from = null;
            }
            message = getRawString("message", json);
            canRemove = getBoolean("can_remove", json);
            createdTime = getISO8601Datetime("created_time", json);
            likeCount = getInt("like_count", json);
            isUserLikes = getBoolean("user_likes", json);
        } catch (JSONException jsone) {
            throw new FacebookException(jsone.getMessage(), jsone);
        }
    }

    public String getId() {
        return id;
    }

    public Category getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public Boolean canRemove() {
        return canRemove;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public Boolean isUserLikes() {
        return isUserLikes;
    }

    /*package*/
    static ResponseList<Comment> createCommentList(HttpResponse res, Configuration conf) throws FacebookException {
        try {
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.clearThreadLocalMap();
            }
            JSONObject json = res.asJSONObject();
            JSONArray list = json.getJSONArray("data");
            final int size = list.length();
            ResponseList<Comment> comments = new ResponseListImpl<Comment>(size, json);
            for (int i = 0; i < size; i++) {
                JSONObject commentJSONObject = list.getJSONObject(i);
                Comment comment = new CommentJSONImpl(commentJSONObject);
                if (conf.isJSONStoreEnabled()) {
                    DataObjectFactoryUtil.registerJSONObject(comment, commentJSONObject);
                }
                comments.add(comment);
            }
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.registerJSONObject(comments, list);
            }
            return comments;
        } catch (JSONException jsone) {
            throw new FacebookException(jsone);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommentJSONImpl other = (CommentJSONImpl) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CommentJSONImpl [id=" + id + ", from=" + from + ", message="
                + message + ", canRemove=" + canRemove + ", createdTime="
                + createdTime + ", likeCount=" + likeCount + ", isUserLinks="
                + isUserLikes + "]";
    }

}
