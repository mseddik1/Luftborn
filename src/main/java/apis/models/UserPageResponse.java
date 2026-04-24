package apis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserPageResponse {

    private int page;
    @JsonProperty("per_page")
    private int perPage;
    private int total;
    @JsonProperty("total_pages")
    private int totalPages;
    private List<User> data;
    private Support support;
    @JsonProperty("_meta")
    private Meta meta;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }


    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public static class Support{
        private String url;
        private String text;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class Meta{
        @JsonProperty("powered_by")
        private String poweredBy;
        @JsonProperty("docs_url")
        private String docsUrl;
        @JsonProperty("upgrade_url")
        private String upgradeUrl;
        @JsonProperty("example_url")
        private String exampleUrl;
        private String variant;
        private String message;
        private Cta cta;
        private String context;

        public String getPoweredBy() {
            return poweredBy;
        }

        public void setPoweredBy(String poweredBy) {
            this.poweredBy = poweredBy;
        }

        public String getDocsUrl() {
            return docsUrl;
        }

        public void setDocsUrl(String docsUrl) {
            this.docsUrl = docsUrl;
        }

        public String getUpgradeUrl() {
            return upgradeUrl;
        }

        public void setUpgradeUrl(String upgradeUrl) {
            this.upgradeUrl = upgradeUrl;
        }

        public String getExampleUrl() {
            return exampleUrl;
        }

        public void setExampleUrl(String exampleUrl) {
            this.exampleUrl = exampleUrl;
        }

        public String getVariant() {
            return variant;
        }

        public void setVariant(String variant) {
            this.variant = variant;
        }

        public Cta getCta() {
            return cta;
        }

        public void setCta(Cta cta) {
            this.cta = cta;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }


    }

    public static class Cta {
        private String label;
        private String url;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
