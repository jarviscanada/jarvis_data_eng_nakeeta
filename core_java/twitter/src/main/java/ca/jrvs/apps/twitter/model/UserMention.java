package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "indices",
        "screen_name",
        "id",
        "id_str"
})

public class UserMention {

    @JsonProperty("name")
    public String name;

    @JsonProperty("indices")
    public List<Long> indices = null;

    @JsonProperty("screen_name")
    public String screenName;

    @JsonProperty("id")
    public Long id;

    @JsonProperty("id_str")
    public String idStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getIndices() {
        return indices;
    }

    public void setIndices(List<Long> indices) {
        this.indices = indices;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }
}