package com.fawry.movie.dto.response;


public class DataResponse<T> extends ApiResponse{

    private T data;
    private long total;

    public DataResponse(boolean success, String message, String errorCode, T data, long total)
    {
        super(success, message, errorCode);
        this.data = data;
        this.total = total;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }
}
