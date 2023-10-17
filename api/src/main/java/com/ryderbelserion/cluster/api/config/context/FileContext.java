package com.ryderbelserion.cluster.api.config.context;

import java.io.File;

public interface FileContext {

    void addFile(FileData file);

    void saveFile(FileData file);

    void removeFile(FileData file);

    File getFile(FileData file);

}