package ru.iteco.exceptions;

public class PointsOperationException extends RuntimeException{

    public PointsOperationException(String msg) {
        super(msg);
    }

    public PointsOperationException(String msg, Throwable e) {
        super(msg, e);
    }

}
