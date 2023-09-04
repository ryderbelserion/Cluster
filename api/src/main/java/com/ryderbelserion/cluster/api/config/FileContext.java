package com.ryderbelserion.cluster.api.config;

import java.io.File;

public interface FileContext {

    void addFile(FileEngine file);

    void saveFile(FileEngine file);

    void removeFile(FileEngine file);

    File getFile(FileEngine file);

}