package SAPAdvertisements.exeptions;

import javax.servlet.http.HttpServletRequest;

public class ErrorInfo {
    private final String url;
    private final String exception;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.exception = ex.getLocalizedMessage();
    }

    public ErrorInfo(HttpServletRequest request, String message){
        this.url = request.getRequestURI().toLowerCase();
        this.exception = message;
    }


    public String getUrl() {
        return url;
    }

    public String getException() {
        return exception;
    }


}
