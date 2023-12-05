package au.com.geekseat.theta.helper;

import java.util.List;

public class DataTablesResponse<T> {
    private List<T> data;
    private Long size;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public DataTablesResponse(List<T> data, Long size) {
        this.data = data;
        this.size = size;
    }
}
