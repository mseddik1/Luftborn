package apis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreateResponse {

    private String id;
    private String createdAt;
    @JsonProperty("_meta")
    private UserPageResponse.Meta meta;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserPageResponse.Meta getMeta() {
        return meta;
    }

    public void setMeta(UserPageResponse.Meta meta) {
        this.meta = meta;
    }
}
