
package com.monederobingo.database.model;

public class ServiceResult<T>
{
    private final boolean success;
    private final String message;
    private final T object;
    private String extraInfo;

    public ServiceResult(boolean success, String serviceMessage, T object)
    {
        this(success, serviceMessage, object, "");
    }

    private ServiceResult(boolean success, String serviceMessage, T object, String extraInfo)
    {
        this.success = success;
        this.message = serviceMessage;
        this.object = object;
        this.extraInfo = extraInfo;
    }

    public ServiceResult(boolean success, String serviceMessage)
    {
        this(success, serviceMessage, null, "");
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }

    public T getObject()
    {
        return object;
    }

    public String getExtraInfo()
    {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo)
    {
        this.extraInfo = extraInfo;
    }
}
