package vnua.kltn.herb.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto implements Serializable {
    private Integer pageIndex = 1;
    private Integer pageSize = 12;
    private String keyword;
    private String sortField;
    private String sortDirection;
    private Map<String, Object> filters = new HashMap<>();
    private Integer excludeId;

    public SearchDto(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public SearchDto(Integer pageIndex, Integer pageSize, String keyword) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.keyword = keyword;
    }

    public SearchDto removeFilter(String key) {
        if (key != null) {
            this.filters.remove(key);
        }
        return this;
    }

    public SearchDto setSort(String field, String direction) {
        this.sortField = field;
        this.sortDirection = direction;
        return this;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters != null ? filters : new HashMap<>();
    }

    public void setExcludeId(Integer excludeId) {
        this.excludeId = excludeId;
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                ", sortField='" + sortField + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", filters=" + filters + '\'' +
                ", excludeId=" + excludeId +
                '}';
    }
}
