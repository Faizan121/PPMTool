package io.faizanUddin.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {

    private String projectNotFound;

    public ProjectNotFoundExceptionResponse(String project) {
        this.projectNotFound = project;
    }

    public String getProjectNotFound() {
        return projectNotFound;
    }

    public void setProjectNotFound(String projectNotFound) {
        this.projectNotFound = projectNotFound;
    }
}
