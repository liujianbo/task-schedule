package org.lioujianbo.taskschedule.infrastructure.constants;

public enum ExecStatus {

    RUNNING("RUNNING"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private String status;

    ExecStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
